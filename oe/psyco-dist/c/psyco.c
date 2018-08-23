#include "psyco.h"
#include "psyfunc.h"
#include "codemanager.h"
#include "stats.h"
#include "profile.h"
#include "dispatcher.h"
#include "Python/pycompiler.h"
#include "Python/frames.h"
#include "alarm.h"
#include "timing.h"
#include "mergepoints.h"

#define PSYCO_INITIALIZATION
#include "initialize.h"  /* generated by files.py */

static PyObject* thread_dict_key;

DEFINEFN
PyObject* psyco_thread_dict()
{
  PyObject* dict = PyThreadState_GetDict();
  PyObject* result;
  bool err;

  if (dict == NULL)
    return NULL;
  result = PyDict_GetItem(dict, thread_dict_key);
  if (result == NULL)
    {
      result = PyDict_New();
      if (result == NULL)
        return NULL;
      err = PyDict_SetItem(dict, thread_dict_key, result);
      Py_DECREF(result);  /* one reference left in 'dict' */
      if (err)
        result = NULL;
    }
  return result;
}

DEFINEFN
void psyco_out_of_memory(char *filename, int lineno)
{
  char *msg;
  if (PyErr_Occurred())
    {
      PyErr_Print();
      msg = "psyco cannot recover from the error above";
    }
  else
    msg = "psyco: out of memory";
  fprintf(stderr, "%s:%d: ", filename, lineno);
  Py_FatalError(msg);
}

 /***************************************************************/
/***   Implementation of the '_psyco' built-in module          ***/
 /***************************************************************/

DEFINEVAR PyObject* PyExc_PsycoError;
DEFINEVAR long psyco_memory_usage = 0;
DEFINEVAR PyObject* CPsycoModule;
DEFINEVAR PyObject* psyco_logger = NULL;


#if VERBOSE_LEVEL
DEFINEFN
void psyco_debug_printf(char* msg, ...)
{
  va_list vargs;
  fprintf(stderr, "psyco: ");
  
#ifdef HAVE_STDARG_PROTOTYPES
  va_start(vargs, msg);
#else
  va_start(vargs);
#endif
  vfprintf(stderr, msg, vargs);
  va_end(vargs);
}
#endif /* VERBOSE_LEVEL */


/* Trace */
#if defined(PSYCO_TRACE)
static void* trace_buffer[4096];
static int   trace_bufdata = 4096;
static FILE* trace_f = NULL;
DEFINEFN void psyco_trace_execution(char* msg, void* code_position)
{
  if (trace_bufdata == 4096)
    {
      if (trace_f == NULL)
        trace_f = fopen(PSYCO_TRACE, "wb");
      else
        fwrite(trace_buffer, sizeof(void*), 4096, trace_f);
      trace_bufdata = 0;
    }
  trace_buffer[trace_bufdata++] = code_position;
}
static void trace_flush(void)
{
  if (trace_f != NULL)
    {
      fwrite(trace_buffer, sizeof(void*), trace_bufdata, trace_f);
      fclose(trace_f);
      trace_f = NULL;
      trace_bufdata = 4096;
    }
}
#elif VERBOSE_LEVEL >= 4
DEFINEFN void psyco_trace_execution(char* msg, void* code_position)
{
  debug_printf(4, ("trace %p for %s\n", code_position, msg));
}
DEFINEFN void psyco_trace_execution_noerr(char* msg, void* code_position)
{
  debug_printf(4, ("trace %p for %s\n", code_position, msg));
  psyco_assert(!PyErr_Occurred());
}
#endif


DEFINEFN
void psyco_flog(char* msg, ...)
{
  va_list vargs;
  PyObject* s;
  PyObject* r;
  PyObject *etype, *evalue, *etb;
  extra_assert(psyco_logger != NULL);

  PyErr_Fetch(&etype, &evalue, &etb);
  
#ifdef HAVE_STDARG_PROTOTYPES
  va_start(vargs, msg);
#else
  va_start(vargs);
#endif
  s = PyString_FromFormatV(msg, vargs);
  va_end(vargs);

  if (s == NULL)
    OUT_OF_MEMORY();
  r = PyObject_CallFunction(psyco_logger, "O", s);
  if (r == NULL)
    PyErr_WriteUnraisable(psyco_logger);
  else
    Py_DECREF(r);
  Py_DECREF(s);

  PyErr_Restore(etype, evalue, etb);
}


DEFINEFN
int psyco_fatal_error(char* msg, char* filename, int lineno)
{
  fprintf(stderr, "\n%s:%d: %s\n", filename, lineno, msg);
  Py_FatalError("Psyco assertion failed");
  return 0;
}

#if CODE_DUMP
static void vinfo_array_dump(vinfo_array_t* array, FILE* f, PyObject* d)
{
  int err;
  int i = array->count;
  fprintf(f, "%d\n", i);
  while (i--)
    {
      vinfo_t* vi = array->items[i];
      PyObject* key = PyInt_FromLong((long)vi);
      psyco_assert(key);
      fprintf(f, "%ld\n", (long)vi);
      if (vi != NULL && !PyDict_GetItem(d, key))
        {
          switch (gettime(vi->source)) {
          case CompileTime:
            fprintf(f, "ct %ld %ld\n",
                    CompileTime_Get(vi->source)->refcount1_flags,
                    CompileTime_Get(vi->source)->value);
            break;
          case RunTime:
            fprintf(f, "rt %ld\n", vi->source);
            break;
          case VirtualTime:
            fprintf(f, "vt 0x%lx\n", (long) VirtualTime_Get(vi->source));
            break;
          default:
            psyco_fatal_msg("gettime() corrupted");
          }
          err = PyDict_SetItem(d, key, Py_None);
          psyco_assert(!err);
          vinfo_array_dump(vi->array, f, d);
        }
      Py_DECREF(key);
    }
}
#ifndef MS_WIN32
static void vinfo_dump_a1(vinfo_array_t* array)
{
  char cmdline[999];
  FILE* f;
  sprintf(cmdline, "\"%s\" ../py-utils/vinfo_dump.py", Py_GetProgramFullPath());
  f = popen(cmdline, "w");
  if (f == NULL)
    fprintf(stderr, "shell cannot execute: %s\n", cmdline);
  else
    {
      PyObject* d = PyDict_New();
      psyco_assert(d);
      vinfo_array_dump(array, f, d);
      Py_DECREF(d);
      pclose(f);
    }
}
DEFINEFN void vinfo_dump(vinfo_t* vi)
{
  /* use this interactively, within the C debugger. Unix only */
  vinfo_array_t array;
  array.count = 1;
  array.items[0] = vi;
  vinfo_dump_a1(&array);
  if (&vinfo_dump) ;  /* force the compiler to consider it as used */
}
DEFINEFN void po_dump(PsycoObject* po)
{
  /* use this interactively, within the C debugger. Unix only */
  vinfo_dump_a1(&po->vlocals);
  if (&po_dump) ;  /* force the compiler to consider it as used */
}
DEFINEFN void po_inlined_in(PsycoObject* po)
{
  if (po->pr.is_inlining)
    {
      fprintf(stderr, "inlined from position %d in\n",
              (int) (CompileTime_Get(po->vlocals.items[0]->
                                     array->items[1]->source)->value));
      _PyObject_Dump((PyObject*)
                     (CompileTime_Get(po->vlocals.items[0]->
                                      array->items[0]->source)->value));
    }
  else
    fprintf(stderr, "not inlined\n");
  if (&po_inlined_in) ;  /* force the compiler to consider it as used */
}
#endif  /* !MS_WIN32 */
#if !ALL_STATIC
 EXTERNFN int psyco_top_array_count(FrozenPsycoObject* fpo);  /*in dispatcher.c*/
#else
# define psyco_top_array_count   fz_top_array_count
#endif
DEFINEFN
void psyco_dump_code_buffers(void)
{
  static int is_dumping = 0;
  FILE* f;
  int saved_rec_limit;

#if CODE_DUMP >= 3
  static int alt = 1;
  char filename[200];
  sprintf(filename, "%s-%d", CODE_DUMP_FILE, alt);
  alt = 3-alt;
#else
  char* filename = CODE_DUMP_FILE;
#endif

#if defined(PSYCO_TRACE)
  trace_flush();
#endif
  
  if (is_dumping) return;
  is_dumping = 1;

  /* protection against obscure failures during this debug dumping
     caused by RuntimeErrors */
  saved_rec_limit = Py_GetRecursionLimit();
  Py_SetRecursionLimit(saved_rec_limit + 50);

  f = fopen(filename, "wb");
  if (f != NULL)
    {
      CodeBufferObject* obj;
      PyObject *exc, *val, *tb;
      long buftablepos;
      void** chain;
      int bufcount = 0;
      long* buftable;
      PyErr_Fetch(&exc, &val, &tb);
      debug_printf(1, ("writing %s\n", filename));

      for (obj=psyco_codebuf_chained_list; obj != NULL; obj=obj->chained_list)
        bufcount++;
      buftable = PyMem_NEW(long, bufcount);
      fprintf(f, "Psyco dump [%s]\n", MACHINE_CODE_FORMAT);
      fwrite(&bufcount, sizeof(bufcount), 1, f);
      buftablepos = ftell(f);
      fwrite(buftable, sizeof(long), bufcount, f);

      /* give the address of an arbitrary symbol from the Python interpreter
         and from the Psyco module */
      fprintf(f, "PyInt_FromLong: 0x%lx\n", (long) &PyInt_FromLong);
      fprintf(f, "psyco_dump_code_buffers: 0x%lx\n",
              (long) &psyco_dump_code_buffers);

      for (obj=psyco_codebuf_chained_list; obj != NULL; obj=obj->chained_list)
        {
          PyCodeObject* co = obj->snapshot.fz_pyc_data ?
		  obj->snapshot.fz_pyc_data->co : NULL;
          fprintf(f, "CodeBufferObject 0x%lx %d '%s' '%s' %d '%s'\n",
                  (long) obj->codestart, get_stack_depth(&obj->snapshot),
                  co?PyString_AsString(co->co_filename):"",
                  co?PyCodeObject_NAME(co):"",
                  co?obj->snapshot.fz_pyc_data->next_instr:-1,
                  obj->codemode);
        }
      
      psyco_dump_bigbuffers(f);

      for (chain = psyco_codebuf_spec_dict_list; chain; chain=(void**)*chain)
        {
          PyObject* spec_dict = (PyObject*)(chain[-1]);
          int i = 0;
          PyObject *key, *value;
          fprintf(f, "spec_dict 0x%lx\n", (long) chain);
          while (PyDict_Next(spec_dict, &i, &key, &value))
            {
              PyObject* repr;
              if (PyInt_Check(key))
                {
                  repr = (key->ob_type->tp_as_number->nb_hex)(key);
                }
              else
                {
                  repr = PyObject_Repr(key);
                }
              psyco_assert(!PyErr_Occurred());
              psyco_assert(PyString_Check(repr));
              psyco_assert(CodeBuffer_Check(value));
              fprintf(f, "0x%lx %s\n",
                      (long)((CodeBufferObject*)value)->codestart,
                      PyString_AsString(repr));
              Py_DECREF(repr);
            }
          fprintf(f, "\n");
        }
      {
        int i = 0;
        fprintf(f, "vinfo_array\n");
        for (obj=psyco_codebuf_chained_list; obj != NULL; obj=obj->chained_list)
          {
            PsycoObject* live_po;
            PyObject* d;
            if (psyco_top_array_count(&obj->snapshot) > 0)
              live_po = fpo_unfreeze(&obj->snapshot);
            else
              live_po = NULL;
            d = PyDict_New();
            psyco_assert(d);
            buftable[i++] = ftell(f);
            vinfo_array_dump(live_po ? &live_po->vlocals : NullArray, f, d);
            Py_DECREF(d);
            if (live_po)
              PsycoObject_Delete(live_po);
          }
        psyco_assert(i==bufcount);
        fseek(f, buftablepos, 0);
        fwrite(buftable, sizeof(long), bufcount, f);
      }
      PyMem_FREE(buftable);
      psyco_assert(!PyErr_Occurred());
      fclose(f);
      PyErr_Restore(exc, val, tb);
    }
  Py_SetRecursionLimit(saved_rec_limit);
  is_dumping = 0;
}
static PyObject* Psyco_dumpcodebuf(PyObject* self, PyObject* args)
{
  psyco_dump_code_buffers();
  Py_INCREF(Py_None);
  return Py_None;
}
#endif  /* CODE_DUMP */


/***************************************************************/
 /***   Replacements for PyEval_GetXxx()                      ***/

DEFINEFN
PyObject* need_cpsyco_obj(char* name)
{
	PyObject* d = PyModule_GetDict(CPsycoModule);
	PyObject* result = PyDict_GetItemString(d, name);
	if (result == NULL)
		PyErr_Format(PyExc_PsycoError, "missing _psyco.%s", name);
	return result;
}

static PyObject* psyco_get_locals_msg(char* msg, int flag)
{
	static int already_logged = 0;
	PyObject* o;
	PyObject* result;

	o = psyco_find_frame(Py_False);
	if (o == NULL)
		return PyDict_New();  /* no frame at all -- no locals */
	
	if (!PyFrame_Check(o)) {
		/* it is a Psyco frame -- no locals available */
		char buffer[400];
		int i;
		PyObject* w = need_cpsyco_obj("NoLocalsWarning");
		if (w == NULL) {
			Py_DECREF(o);
			return NULL;
		}
		for (i=0; msg[i] != '\\' && msg[i] != 0; i++)
			buffer[i] = msg[i];
		if (psyco_logger && (flag & already_logged) == 0) {
			already_logged |= flag;
			buffer[i] = '\n';
			buffer[i+1] = 0;
			debug_printf(1, (buffer));
		}
		if (msg[i] == '\\') {
			buffer[i++] = ' ';
			for (; msg[i] != 0; i++)
				buffer[i] = msg[i];
		}
		buffer[i] = 0;
		if (PyErr_Warn(w, buffer))
			result = NULL;
		else
			result = PyDict_New();
	}
	else {
		PyFrame_FastToLocals((PyFrameObject*) o);
		result = ((PyFrameObject*) o)->f_locals;
		Py_INCREF(result);
	}
	Py_DECREF(o);
	return result;
}

static PyObject* psyco_get_locals(void)
{
	return psyco_get_locals_msg("no locals() in functions bound by Psyco",
				    0x01);
}

#define WARN_IMPLICIT_LOCALS " cannot see the locals\\in functions bound by " \
                             "Psyco; consider using eval() in its two- or " \
                             "three-arguments form"

/*****************************************************************/

static PyObject* Psyco_proxycode(PyObject* self, PyObject* args)
{
	int recursion = DEFAULT_RECURSION;
	PyFunctionObject* function;
	
	if (!PyArg_ParseTuple(args, "O!|i",
			      &PyFunction_Type, &function,
			      &recursion))
		return NULL;

	return psyco_proxycode(function, recursion);
}

static PyObject* Psyco_unproxycode(PyObject* self, PyObject* args)
{
	PyCodeObject* code;
	PsycoFunctionObject* proxy;
	PyObject* func;
	
	if (!PyArg_ParseTuple(args, "O!", &PyCode_Type, &code))
		return NULL;

	if (!is_proxycode(code)) {
		PyErr_SetString(PyExc_PsycoError, "code object is not a proxy");
		return NULL;
	}
	proxy = (PsycoFunctionObject*) PyTuple_GET_ITEM(code->co_consts, 1);
	
	func = PyFunction_New((PyObject*) proxy->psy_code, proxy->psy_globals);
	if (func == NULL)
		return NULL;
	if (proxy->psy_defaults != NULL &&
	    PyFunction_SetDefaults(func, proxy->psy_defaults)) {
		Py_DECREF(func);
		return NULL;
	}
	return func;
}

static PyObject* Psyco_cannotcompile(PyObject* self, PyObject* args)
{
	PyCodeObject* code;
	PyCodeStats* cs;
	
	if (!PyArg_ParseTuple(args, "O!", &PyCode_Type, &code))
		return NULL;
	cs = PyCodeStats_Get(code);
	if (cs->st_mergepoints == NULL) {
		Py_INCREF(Py_None);
		cs->st_mergepoints = Py_None;
	}
	else if (cs->st_mergepoints != Py_None) {
		PyErr_SetString(PyExc_PsycoError, "code is already compiled");
		return NULL;
	}
	Py_INCREF(Py_None);
	return Py_None;
}

static PyObject* Psyco_setfilter(PyObject* self, PyObject* args)
{
	PyObject* func;
	PyObject* prev;

	if (!PyArg_ParseTuple(args, "O", &func))
		return NULL;

	if (func == Py_None)
		func = NULL;
	else {
		if (!PyCallable_Check(func)) {
			PyErr_SetString(PyExc_TypeError,
					"setfilter() arg must be callable");
			return NULL;
		}
		Py_INCREF(func);
	}
	prev = psyco_codeobj_filter_fn;
	psyco_codeobj_filter_fn = func;
	if (prev == NULL) {
		prev = Py_None;
		Py_INCREF(prev);
	}
	return prev;
}

/* replacement for sys._getframe() (together with _getframe() in support.py) */
static PyObject* Psyco_getframe(PyObject* self, PyObject* args)
{
	PyObject* o = Py_False;
	int emulate = 0;
	if (!PyArg_ParseTuple(args, "|Oi:getframe", &o, &emulate))
		return NULL;

	o = psyco_find_frame(o);
	if (emulate && o != NULL) {
		PyObject* f = (PyObject*) psyco_emulate_frame(o);
		Py_DECREF(o);
		return f;
	}
	else
		return o;
}

/* replacement for __builtin__.globals() */
static PyObject *
Psyco_globals(PyObject* self, PyObject* args)
{
	PyObject *d;

	if (!PyArg_ParseTuple(args, ":globals"))
		return NULL;
	d = psyco_get_globals();
	Py_INCREF(d);
	return d;
}

/* replacement for __builtin__.locals() */
static PyObject *
Psyco_locals(PyObject* self, PyObject* args)
{
	if (!PyArg_ParseTuple(args, ":locals"))
		return NULL;
	return psyco_get_locals();
}

static PyObject* builtinevaluator(PyObject* args, char* oname)
{
	PyObject *o;
	PyObject *cmd;
	PyObject *freeme;
	PyObject *globals = Py_None, *locals = Py_None;

	o = need_cpsyco_obj(oname);
	if (o == NULL)
		return NULL;

	if (!PyArg_ParseTuple(args, "O|O!O!",
			&cmd,
			&PyDict_Type, &globals,
			&PyDict_Type, &locals)) {
		/* let the original function report the problem, if any */
		PyErr_Clear();
	}
	else if (globals == Py_None) {
		globals = psyco_get_globals();
		if (locals == Py_None) {
			locals = psyco_get_locals_msg("eval()/execfile()"
						      WARN_IMPLICIT_LOCALS,
						      0x02);
			if (locals == NULL)
				return NULL;
			freeme = locals;
		}
		else {
			freeme = NULL;
		}
		o = PyObject_CallFunction(o, "OOO", cmd, globals, locals);
		Py_XDECREF(freeme);
		return o;
	}

	/* fallback */
	return PyObject_CallObject(o, args);
}

/* replacement for __builtin__.eval() */
static PyObject *
Psyco_eval(PyObject* self, PyObject* args)
{
	return builtinevaluator(args, "original_eval");
}

/* replacement for __builtin__.execfile() */
static PyObject *
Psyco_execfile(PyObject *self, PyObject *args)
{
	return builtinevaluator(args, "original_execfile");
}

/* replacement for __builtin__.vars() */
static PyObject *
Psyco_vars(PyObject *self, PyObject *args)
{
	PyObject* o;
	if (PyTuple_Size(args) == 0) {
		return psyco_get_locals();
	}
	/* fallback */
	o = need_cpsyco_obj("original_vars");
	if (o == NULL)
		return NULL;
	return PyObject_CallObject(o, args);
}

/* replacement for __builtin__.dir() */
static PyObject *
Psyco_dir(PyObject *self, PyObject *args)
{
	PyObject* o;
	if (PyTuple_Size(args) == 0) {
		PyObject* locals = psyco_get_locals();
		if (locals == NULL)
			return NULL;
		o = PyMapping_Keys(locals);
		Py_DECREF(locals);
                if (o == NULL)
			return NULL;
		if (!PyList_Check(o)) {
			Py_DECREF(o);
			PyErr_SetString(PyExc_TypeError,
					"Expected keys() to be a list.");
			return NULL;
		}
		if (PyList_Sort(o) != 0) {
			Py_DECREF(o);
			return NULL;
		}
		return o;
	}
	/* fallback */
	o = need_cpsyco_obj("original_dir");
	if (o == NULL)
		return NULL;
	return PyObject_CallObject(o, args);
}

/* replacement for __builtin__.input() */
static PyObject *
Psyco_input(PyObject *self, PyObject *args)
{
	PyObject* cmd;
	PyObject* o = need_cpsyco_obj("original_raw_input");
	if (o == NULL)
		return NULL;

	cmd = PyObject_CallObject(o, args);
	if (cmd != NULL) {
		PyObject* globals = psyco_get_globals();
		PyObject* locals = psyco_get_locals_msg("input()"
							WARN_IMPLICIT_LOCALS,
							0x04);
		if (locals == NULL) {
			o = NULL;
		}
		else {
			o = need_cpsyco_obj("original_eval");
			if (o != NULL)
				o = PyObject_CallFunction(o, "OOO", cmd,
							  globals, locals);
			Py_DECREF(locals);
		}
		Py_DECREF(cmd);
		return o;
	}
	/* error in original_raw_input() */
	return NULL;
}

static PyObject* hooks_busy(void)
{
  PyErr_SetString(PyExc_PsycoError, "Python trace/profile hooks are busy");
  return NULL;
}

/* Enable or disable profiling. */
static PyObject* Psyco_profiling(PyObject* self, PyObject* args)
{
  char mode;
  void (*rs)(void*, int);
  if (!PyArg_ParseTuple(args, "c", &mode))
    return NULL;

  switch (mode) {
  case 'p':  rs = &psyco_rs_profile;      break;
  case 'f':  rs = &psyco_rs_fullcompile;  break;
  case 'n':  rs = &psyco_rs_nocompile;    break;
  case '.':  rs = NULL;                   break;
  default:
    PyErr_SetString(PyExc_ValueError, "unknown or unsupported mode");
    return NULL;
  }
  if (!psyco_set_profiler(rs))
    return hooks_busy();

  Py_INCREF(Py_None);
  return Py_None;
}

/* turbo-mark a frame or all frames running a given code */
static PyObject* Psyco_turbo_frame(PyObject* self, PyObject* args)
{
  PyObject* o = NULL;
  
  if (!PyArg_ParseTuple(args, "O", &o))
    return NULL;

  if (PyCode_Check(o))
    psyco_turbo_frames((PyCodeObject*) o);
  else if (PyFrame_Check(o))
    {
      if (!psyco_turbo_frame((PyFrameObject*) o))
        return hooks_busy();
    }
  else
    {
      PyErr_SetString(PyExc_TypeError, "frame or code object");
      return NULL;
    }

  Py_INCREF(Py_None);
  return Py_None;
}

/* turbo-mark a code object (does not affect frames currently running it) */
static PyObject* Psyco_turbo_code(PyObject* self, PyObject* args)
{
  int rec = DEFAULT_RECURSION;
  PyCodeObject* co;
  if (!PyArg_ParseTuple(args, "O!|i", &PyCode_Type, &co, &rec))
    return NULL;

  psyco_turbo_code(co, rec);
  Py_INCREF(Py_None);
  return Py_None;
}

/* update collected statistics */
static PyObject* Psyco_statcollect(PyObject* self, PyObject* args)
{
  psyco_profile_threads(1);
  psyco_stats_collect();
  Py_INCREF(Py_None);
  return Py_None;
}

/* return the top n code objects */
static PyObject* Psyco_stattop(PyObject* self, PyObject* args)
{
  int n;
  if (!PyArg_ParseTuple(args, "i", &n))
    return NULL;

  return psyco_stats_top(n);
}

/* clear all collected statistics now */
static PyObject* Psyco_statreset(PyObject* self, PyObject* args)
{
  if (!PyArg_ParseTuple(args, ""))
    return NULL;

  psyco_stats_reset();
  Py_INCREF(Py_None);
  return Py_None;
}

/* get tunable parameters */
static PyObject* Psyco_statread(PyObject* self, PyObject* args)
{
  char* name;
  if (!PyArg_ParseTuple(args, "s", &name))
    return NULL;
  
  return psyco_stats_read(name);
}

/* set tunable parameters */
static PyObject* Psyco_statwrite(PyObject* self, PyObject* args, PyObject* kwds)
{
  if (!psyco_stats_write(args, kwds))
    return NULL;
  Py_INCREF(Py_None);
  return Py_None;
}

/* return a dump of all current statistics
   (it also prints a nice table to stderr in verbose mode) */
static PyObject* Psyco_statdump(PyObject* self, PyObject* args)
{
  if (!PyArg_ParseTuple(args, ""))
    return NULL;

  psyco_stats_collect();
  return psyco_stats_dump();
}

/* get the current usage charge of a code object */
static PyObject* Psyco_getcharge(PyObject* self, PyObject* args)
{
  PyCodeStats* cs;
  PyCodeObject* co;
  if (!PyArg_ParseTuple(args, "O!", &PyCode_Type, &co))
    return NULL;

  cs = PyCodeStats_Get(co);
  return PyFloat_FromDouble((double) cs->st_charge);
}

/* set the usage charge of a code object */
static PyObject* Psyco_setcharge(PyObject* self, PyObject* args)
{
  PyCodeStats* cs;
  PyCodeObject* co;
  float new_charge;
  if (!PyArg_ParseTuple(args, "O!f", &PyCode_Type, &co, &new_charge))
    return NULL;

  cs = PyCodeStats_Get(co);
  cs->st_charge = new_charge;
  Py_INCREF(Py_None);
  return Py_None;
}

static PyObject* Psyco_memory(PyObject* self, PyObject* args)
{
  if (!PyArg_ParseTuple(args, ""))
    return NULL;
  return PyInt_FromLong(psyco_memory_usage / 1024);
}

#if 0
--- disabled ---
static PyObject* t_finalizer(PyObject* self, PyObject* args, PyObject* kwds)
{
  PyObject* f;
  PyObject* g;
  PyObject* fres;
  PyObject* gres;
  PyObject *exc, *val, *tb;
  
  if (!PyArg_ParseTuple(self, "OO:finalizer", &f, &g))
    return NULL;
  fres = PyEval_CallObjectWithKeywords(f, args, kwds);
  PyErr_Fetch(&exc, &val, &tb);
  gres = PyEval_CallObject(g, NULL);
  if (gres == NULL)
    {
      Py_XDECREF(exc);
      Py_XDECREF(val);
      Py_XDECREF(tb);
      Py_XDECREF(fres);
      return NULL;
    }
  else
    {
      Py_DECREF(gres);
      PyErr_Restore(exc, val, tb);
      return fres;
    }
}
static PyMethodDef m_finalizer = {
  "finalizer_fn", (PyCFunction)t_finalizer, METH_VARARGS|METH_KEYWORDS };

/* finalizer(f,g) returns a built-in function object h
   such that h(*args, **kw) is equivalent to

      try:
         f(*args, **kw)
      finally:
         g()
         
   The purpose is for g() to be a lock.release() so that when the
   lock is released we are sure that the thread will not run any
   Python code any more (which cannot be done in Python). */
static PyObject* Psyco_finalizer(PyObject* self, PyObject* args)
{
  return PyCFunction_New(&m_finalizer, args);
}
#endif


/*****************************************************************/

static char proxycode_doc[] =
"proxycode(func[, rec]) -> code object\n\
\n\
Return a proxy code object that invokes Psyco on the argument. The code\n\
object can be used to replace func.func_code. Raise psyco.error if func\n\
uses unsupported features. Return func.func_code itself if it is already\n\
a proxy code object. The optional second argument specifies the number of\n\
recursive compilation levels: all functions called by func are compiled\n\
up to the given depth of indirection.";

static char unproxycode_doc[] =
"unproxycode(code) -> function object\n\
\n\
Return a new copy of the original function that was used to build the\n\
given proxy code object. Raise psyco.error if code is not a proxy.";

static char getframe_doc[] =
"getframe([location, emulate]) -> frameobject or (code, globals, addr)\n\
\n\
Return a frame object from the call stack.\n\
If location is an integer, return the nth frame (0=top).\n\
If location was returned by a previous call to getframe(),\n\
return the previous frame (as if by reading f_back).";

static char globals_doc[] =
"globals() -> dictionary\n\
\n\
Return the dictionary containing the current scope's global variables.\n\
This is the Psyco-aware version.";

static char gennolocals_doc[] =
"This is the Psyco-aware version of the builtin function of the same name.\n\
For more information see psyco._psyco.original_<<<function name>>>.__doc__.\n\
\n\
Functions have no locals() dictionary with Psyco.\n\
Accessing it returns {} and throws a NoLocalsWarning.";

static char setfilter_doc[] =
"setfilter(func or None) -> previous filter or None\n\
\n\
Set a global filter function: func(co) will be called once per code object\n\
'co' considered by Psyco.  If it returns False, the code object will not\n\
be compiled.";

static PyMethodDef PsycoMethods[] = {
	{"proxycode",	&Psyco_proxycode,	METH_VARARGS,	proxycode_doc},
	{"unproxycode",	&Psyco_unproxycode,	METH_VARARGS,	unproxycode_doc},
	{"getframe",	&Psyco_getframe,	METH_VARARGS,	getframe_doc},
	{"globals",	&Psyco_globals,		METH_VARARGS,	globals_doc},
	{"eval",	&Psyco_eval,		METH_VARARGS,	gennolocals_doc},
	{"execfile",	&Psyco_execfile,	METH_VARARGS,	gennolocals_doc},
	{"locals",	&Psyco_locals,		METH_VARARGS,	gennolocals_doc},
	{"vars",	&Psyco_vars,		METH_VARARGS,	gennolocals_doc},
	{"dir",		&Psyco_dir,		METH_VARARGS,	gennolocals_doc},
	{"input",	&Psyco_input,		METH_VARARGS,	gennolocals_doc},
	{"profiling",	&Psyco_profiling,	METH_VARARGS},
	{"turbo_frame",	&Psyco_turbo_frame,	METH_VARARGS},
	{"turbo_code",	&Psyco_turbo_code,	METH_VARARGS},
	{"statcollect",	&Psyco_statcollect,	METH_VARARGS},
	{"stattop",	&Psyco_stattop,		METH_VARARGS},
	{"statreset",	&Psyco_statreset,	METH_VARARGS},
      /*{"statread",	&Psyco_statread,	METH_VARARGS},*/
        {"statwrite", (PyCFunction)&Psyco_statwrite, METH_VARARGS|METH_KEYWORDS},
        {"statread",	&Psyco_statread,	METH_VARARGS},
        {"statdump",	&Psyco_statdump,	METH_VARARGS},
	{"getcharge",	&Psyco_getcharge,	METH_VARARGS},
	{"setcharge",	&Psyco_setcharge,	METH_VARARGS},
	{"memory",	&Psyco_memory,		METH_VARARGS},
	{"cannotcompile",&Psyco_cannotcompile,	METH_VARARGS},
	{"setfilter",	&Psyco_setfilter,	METH_VARARGS,	setfilter_doc},
#if 0
        {"finalizer",   &Psyco_finalizer,       METH_VARARGS},
#endif
#if CODE_DUMP
	{"dumpcodebuf",	&Psyco_dumpcodebuf,	METH_VARARGS},
#endif
        ALARM_FUNCTIONS,
	{NULL,		NULL}        /* Sentinel */
};

/* Initialization */
PyMODINIT_FUNC init_psyco(void)
{
  PsycoFunction_Type.ob_type = &PyType_Type;
  CodeBuffer_Type.ob_type = &PyType_Type;
  thread_dict_key = PyString_InternFromString("PsycoT");
  if (thread_dict_key == NULL)
    return;
  
  CPsycoModule = Py_InitModule("_psyco", PsycoMethods);
  if (CPsycoModule == NULL)
    return;
  PyExc_PsycoError = PyErr_NewException("psyco.error", NULL, NULL);
  if (PyExc_PsycoError == NULL)
    return;
  Py_INCREF(PyExc_PsycoError);
  if (PyModule_AddObject(CPsycoModule, "error", PyExc_PsycoError))
    return;
  Py_INCREF(&PsycoFunction_Type);
  if (PyModule_AddObject(CPsycoModule, "PsycoFunctionType",
			 (PyObject*) &PsycoFunction_Type))
    return;
/*if (PyModule_AddIntConstant(CPsycoModule, "DEFAULT_RECURSION", DEFAULT_RECURSION))
    return;*/
#if ALL_CHECKS
  if (PyModule_AddIntConstant(CPsycoModule, "ALL_CHECKS", ALL_CHECKS))
    return;
#endif
#if VERBOSE_LEVEL
  if (PyModule_AddIntConstant(CPsycoModule, "VERBOSE_LEVEL", VERBOSE_LEVEL))
    return;
#endif
#ifdef PY_PSYCO_MODULE
  PyPsycoModule = PyImport_ImportModule("psyco");
  if (PyPsycoModule == NULL)
    return;
#endif
  if (PyModule_AddIntConstant(CPsycoModule, "PYVER", PY_VERSION_HEX))
    return;
  if (PyModule_AddIntConstant(CPsycoModule, "PSYVER", PSYCO_VERSION_HEX))
    return;
  if (PyModule_AddIntConstant(CPsycoModule, "MEASURE_ALL_THREADS",
                              MEASURE_ALL_THREADS))
    return;
  if (PyModule_AddStringConstant(CPsycoModule, "PROCESSOR",
                                 MACHINE_CODE_FORMAT))
    return;

  initialize_all_files();
}
