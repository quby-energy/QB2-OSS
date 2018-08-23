DESCRIPTION= "All perl modules"

inherit task

PR = "r3"

RRECOMMENDS_${PN} = " \
  perl-module-abbrev \
  perl-module-anydbm-file \
  perl-module-assert \
  perl-module-attribute-handlers \
  perl-module-attributes \
  perl-module-attrs \
  perl-module-autoloader \
  perl-module-autosplit \
  perl-module-autouse \
  perl-module-b \
  perl-module-base \
  perl-module-b-asmdata \
  perl-module-b-assembler \
  perl-module-b-bblock \
  perl-module-b-bytecode \
  perl-module-b-c \
  perl-module-b-cc \
  perl-module-b-concise \
  perl-module-b-debug \
  perl-module-b-deparse \
  perl-module-b-disassembler \
  perl-module-benchmark \
  perl-module-bigfloat \
  perl-module-bigint \
  perl-module-bignum \
  perl-module-bigrat \
  perl-module-blib \
  perl-module-b-lint \
  perl-module-b-showlex \
  perl-module-b-stackobj \
  perl-module-b-stash \
  perl-module-b-terse \
  perl-module-b-xref \
  perl-module-byteloader \
  perl-module-bytes \
  perl-module-bytes-heavy \
  perl-module-cacheout \
  perl-module-carp \
  perl-module-carp-heavy \
  perl-module-cgi \
  perl-module-cgi-apache \
  perl-module-cgi-carp \
  perl-module-cgi-cookie \
  perl-module-cgi-fast \
  perl-module-cgi-pretty \
  perl-module-cgi-push \
  perl-module-cgi-switch \
  perl-module-cgi-util \
  perl-module-charnames \
  perl-module-class-isa \
  perl-module-class-struct \
  perl-module-complete \
  perl-module-config \
  perl-module-config-heavy \
  perl-module-constant \
  perl-module-cpan \
  perl-module-cpan-firsttime \
  perl-module-cpan-nox \
  perl-module-ctime \
  perl-module-cwd \
  perl-module-data-dumper \
  perl-module-db \
  perl-module-dbm-filter \
  perl-module-dbm-filter-compress \
  perl-module-dbm-filter-encode \
  perl-module-dbm-filter-int32 \
  perl-module-dbm-filter-null \
  perl-module-dbm-filter-utf8 \
  perl-module-dbm-filter-util \
  perl-module-devel-dprof \
  perl-module-devel-peek \
  perl-module-devel-ppport \
  perl-module-devel-selfstubber \
  perl-module-diagnostics \
  perl-module-digest \
  perl-module-digest-base \
  perl-module-digest-file \
  perl-module-digest-md5 \
  perl-module-dirhandle \
  perl-module-dotsh \
  perl-module-dumpvalue \
  perl-module-dumpvar \
  perl-module-dynaloader \
  perl-module-encode \
  perl-module-encode-alias \
  perl-module-encode-byte \
  perl-module-encode-changes \
  perl-module-encode-cjkconstants \
  perl-module-encode-cn \
  perl-module-encode-cn-hz \
  perl-module-encode-config \
  perl-module-encode-configlocal-pm \
  perl-module-encode-ebcdic \
  perl-module-encode-encoder \
  perl-module-encode-encoding \
  perl-module-encode-guess \
  perl-module-encode-jp \
  perl-module-encode-jp-h2z \
  perl-module-encode-jp-jis7 \
  perl-module-encode-kr \
  perl-module-encode-kr-2022-kr \
  perl-module-encode-makefile-pl \
  perl-module-encode-mime-header \
  perl-module-encode-mime-header-iso-2022-jp \
  perl-module-encode--pm \
  perl-module-encode-readme \
  perl-module-encode-symbol \
  perl-module-encode--t \
  perl-module-encode-tw \
  perl-module-encode-unicode \
  perl-module-encode-unicode-utf7 \
  perl-module-encoding \
  perl-module-english \
  perl-module-env \
  perl-module-errno \
  perl-module-exceptions \
  perl-module-exporter \
  perl-module-exporter-heavy \
  perl-module-extutils-command \
  perl-module-extutils-command-mm \
  perl-module-extutils-constant \
  perl-module-extutils-constant-base \
  perl-module-extutils-constant-utils \
  perl-module-extutils-constant-xs \
  perl-module-extutils-embed \
  perl-module-extutils-install \
  perl-module-extutils-installed \
  perl-module-extutils-liblist \
  perl-module-extutils-liblist-kid \
  perl-module-extutils-makemaker \
  perl-module-extutils-makemaker-bytes \
  perl-module-extutils-makemaker-config \
  perl-module-extutils-makemaker-vmsish \
  perl-module-extutils-manifest \
  perl-module-extutils-miniperl \
  perl-module-extutils-mkbootstrap \
  perl-module-extutils-mksymlists \
  perl-module-extutils-mm \
  perl-module-extutils-mm-aix \
  perl-module-extutils-mm-any \
  perl-module-extutils-mm-beos \
  perl-module-extutils-mm-cygwin \
  perl-module-extutils-mm-dos \
  perl-module-extutils-mm-macos \
  perl-module-extutils-mm-nw5 \
  perl-module-extutils-mm-os2 \
  perl-module-extutils-mm-qnx \
  perl-module-extutils-mm-unix \
  perl-module-extutils-mm-uwin \
  perl-module-extutils-mm-vms \
  perl-module-extutils-mm-vos \
  perl-module-extutils-mm-win32 \
  perl-module-extutils-mm-win95 \
  perl-module-extutils-my \
  perl-module-extutils-packlist \
  perl-module-extutils-testlib \
  perl-module-fastcwd \
  perl-module-fatal \
  perl-module-fcntl \
  perl-module-fields \
  perl-module-file-basename \
  perl-module-filecache \
  perl-module-file-checktree \
  perl-module-file-compare \
  perl-module-file-copy \
  perl-module-file-dosglob \
  perl-module-file-find \
  perl-module-file-glob \
  perl-module-filehandle \
  perl-module-file-path \
  perl-module-file-spec \
  perl-module-file-spec-cygwin \
  perl-module-file-spec-epoc \
  perl-module-file-spec-functions \
  perl-module-file-spec-mac \
  perl-module-file-spec-os2 \
  perl-module-file-spec-unix \
  perl-module-file-spec-vms \
  perl-module-file-spec-win32 \
  perl-module-file-stat \
  perl-module-file-temp \
  perl-module-filetest \
  perl-module-filter-simple \
  perl-module-filter-util-call \
  perl-module-find \
  perl-module-findbin \
  perl-module-finddepth \
  perl-module-flush \
  perl-module-getcwd \
  perl-module-getopt \
  perl-module-getopt-long \
  perl-module-getopts \
  perl-module-getopt-std \
  perl-module-hash-util \
  perl-module-hostname \
  perl-module-i18n-collate \
  perl-module-i18n-langinfo \
  perl-module-i18n-langtags \
  perl-module-i18n-langtags-detect \
  perl-module-i18n-langtags-list \
  perl-module-if \
  perl-module-importenv \
  perl-module-integer \
  perl-module-io \
  perl-module-io-dir \
  perl-module-io-file \
  perl-module-io-handle \
  perl-module-io-pipe \
  perl-module-io-poll \
  perl-module-io-seekable \
  perl-module-io-select \
  perl-module-io-socket \
  perl-module-io-socket-inet \
  perl-module-io-socket-unix \
  perl-module-ipc-msg \
  perl-module-ipc-open2 \
  perl-module-ipc-open3 \
  perl-module-ipc-semaphore \
  perl-module-ipc-sysv \
  perl-module-less \
  perl-module-lib \
  perl-module-list-util \
  perl-module-locale \
  perl-module-locale-constants \
  perl-module-locale-country \
  perl-module-locale-currency \
  perl-module-locale-language \
  perl-module-locale-maketext \
  perl-module-locale-maketext-guts \
  perl-module-locale-maketext-gutsloader \
  perl-module-locale-script \
  perl-module-look \
  perl-module-math-bigfloat \
  perl-module-math-bigfloat-trace \
  perl-module-math-bigint \
  perl-module-math-bigint-calc \
  perl-module-math-bigint-calcemu \
  perl-module-math-bigint-trace \
  perl-module-math-bigrat \
  perl-module-math-complex \
  perl-module-math-trig \
  perl-module-memoize \
  perl-module-memoize-anydbm-file \
  perl-module-memoize-expire \
  perl-module-memoize-expirefile \
  perl-module-memoize-expiretest \
  perl-module-memoize-ndbm-file \
  perl-module-memoize-sdbm-file \
  perl-module-memoize-storable \
  perl-module-mime-base64 \
  perl-module-mime-quotedprint \
  perl-module-net-cmd \
  perl-module-net-config \
  perl-module-net-domain \
  perl-module-net-ftp \
  perl-module-net-ftp-a \
  perl-module-net-ftp-dataconn \
  perl-module-net-ftp-e \
  perl-module-net-ftp-i \
  perl-module-net-ftp-l \
  perl-module-net-hostent \
  perl-module-net-netent \
  perl-module-net-netrc \
  perl-module-net-nntp \
  perl-module-net-ping \
  perl-module-net-pop3 \
  perl-module-net-protoent \
  perl-module-net-servent \
  perl-module-net-smtp \
  perl-module-net-time \
  perl-module-newgetopt \
  perl-module-next \
  perl-module-o \
  perl-module-opcode \
  perl-module-open \
  perl-module-open2 \
  perl-module-open3 \
  perl-module-ops \
  perl-module-overload \
  perl-module-perl5db \
  perl-module-perlio \
  perl-module-perlio-encoding \
  perl-module-perlio-scalar \
  perl-module-perlio-via \
  perl-module-perlio-via-quotedprint \
  perl-module-pod-checker \
  perl-module-pod-find \
  perl-module-pod-functions \
  perl-module-pod-html \
  perl-module-pod-inputobjects \
  perl-module-pod-latex \
  perl-module-pod-man \
  perl-module-pod-parselink \
  perl-module-pod-parser \
  perl-module-pod-parseutils \
  perl-module-pod-perldoc \
  perl-module-pod-perldoc-baseto \
  perl-module-pod-perldoc-getoptsoo \
  perl-module-pod-perldoc-tochecker \
  perl-module-pod-perldoc-toman \
  perl-module-pod-perldoc-tonroff \
  perl-module-pod-perldoc-topod \
  perl-module-pod-perldoc-tortf \
  perl-module-pod-perldoc-totext \
  perl-module-pod-perldoc-totk \
  perl-module-pod-perldoc-toxml \
  perl-module-pod-plainer \
  perl-module-pod-plaintext \
  perl-module-pod-select \
  perl-module-pod-text \
  perl-module-pod-text-color \
  perl-module-pod-text-overstrike \
  perl-module-pod-text-termcap \
  perl-module-pod-usage \
  perl-module-posix \
  perl-module-posix-sigaction \
  perl-module-pwd \
  perl-module-re \
  perl-modules \
  perl-module-safe \
  perl-module-scalar-util \
  perl-module-sdbm \
  perl-module-sdbm-file \
  perl-module-search-dict \
  perl-module-selectsaver \
  perl-module-selfloader \
  perl-module-shell \
  perl-module-shellwords \
  perl-module-sigtrap \
  perl-module-socket \
  perl-module-sort \
  perl-module-stat \
  perl-module-storable \
  perl-module-strict \
  perl-module-subs \
  perl-module-switch \
  perl-module-symbol \
  perl-module-sys-hostname \
  perl-module-syslog \
  perl-module-sys-syslog \
  perl-module-tainted \
  perl-module-term-ansicolor \
  perl-module-termcap \
  perl-module-term-cap \
  perl-module-term-complete \
  perl-module-term-readline \
  perl-module-test \
  perl-module-test-builder \
  perl-module-test-builder-module \
  perl-module-test-builder-tester \
  perl-module-test-builder-tester-color \
  perl-module-test-harness \
  perl-module-test-harness-assert \
  perl-module-test-harness-iterator \
  perl-module-test-harness-point \
  perl-module-test-harness-straps \
  perl-module-test-more \
  perl-module-test-simple \
  perl-module-text-abbrev \
  perl-module-text-balanced \
  perl-module-text-parsewords \
  perl-module-text-soundex \
  perl-module-text-tabs \
  perl-module-text-wrap \
  perl-module-thread \
  perl-module-thread-queue \
  perl-module-threads \
  perl-module-thread-semaphore \
  perl-module-threads-shared \
  perl-module-tie-array \
  perl-module-tie-file \
  perl-module-tie-handle \
  perl-module-tie-hash \
  perl-module-tie-memoize \
  perl-module-tie-refhash \
  perl-module-tie-scalar \
  perl-module-tie-substrhash \
  perl-module-time-gmtime \
  perl-module-time-hires \
  perl-module-timelocal \
  perl-module-time-local \
  perl-module-time-localtime \
  perl-module-time-tm \
  perl-module-unicode-collate \
  perl-module-unicode-normalize \
  perl-module-unicode-ucd \
  perl-module-unicore-canonical \
  perl-module-unicore-combiningclass \
  perl-module-unicore-decomposition \
  perl-module-unicore-exact \
  perl-module-unicore-lib-bc-al \
  perl-module-unicore-lib-bc-an \
  perl-module-unicore-lib-bc-b \
  perl-module-unicore-lib-bc-bn \
  perl-module-unicore-lib-bc-cs \
  perl-module-unicore-lib-bc-en \
  perl-module-unicore-lib-bc-es \
  perl-module-unicore-lib-bc-et \
  perl-module-unicore-lib-bc-l \
  perl-module-unicore-lib-bc-lre \
  perl-module-unicore-lib-bc-lro \
  perl-module-unicore-lib-bc-nsm \
  perl-module-unicore-lib-bc-on \
  perl-module-unicore-lib-bc-pdf \
  perl-module-unicore-lib-bc-r \
  perl-module-unicore-lib-bc-rle \
  perl-module-unicore-lib-bc-rlo \
  perl-module-unicore-lib-bc-s \
  perl-module-unicore-lib-bc-ws \
  perl-module-unicore-lib-ccc-a \
  perl-module-unicore-lib-ccc-al \
  perl-module-unicore-lib-ccc-ar \
  perl-module-unicore-lib-ccc-atar \
  perl-module-unicore-lib-ccc-atb \
  perl-module-unicore-lib-ccc-atbl \
  perl-module-unicore-lib-ccc-b \
  perl-module-unicore-lib-ccc-bl \
  perl-module-unicore-lib-ccc-br \
  perl-module-unicore-lib-ccc-da \
  perl-module-unicore-lib-ccc-db \
  perl-module-unicore-lib-ccc-is \
  perl-module-unicore-lib-ccc-kv \
  perl-module-unicore-lib-ccc-l \
  perl-module-unicore-lib-ccc-nk \
  perl-module-unicore-lib-ccc-nr \
  perl-module-unicore-lib-ccc-ov \
  perl-module-unicore-lib-ccc-r \
  perl-module-unicore-lib-ccc-vr \
  perl-module-unicore-lib-dt-can \
  perl-module-unicore-lib-dt-com \
  perl-module-unicore-lib-dt-enc \
  perl-module-unicore-lib-dt-fin \
  perl-module-unicore-lib-dt-font \
  perl-module-unicore-lib-dt-fra \
  perl-module-unicore-lib-dt-init \
  perl-module-unicore-lib-dt-iso \
  perl-module-unicore-lib-dt-med \
  perl-module-unicore-lib-dt-nar \
  perl-module-unicore-lib-dt-nb \
  perl-module-unicore-lib-dt-sml \
  perl-module-unicore-lib-dt-sqr \
  perl-module-unicore-lib-dt-sub \
  perl-module-unicore-lib-dt-sup \
  perl-module-unicore-lib-dt-vert \
  perl-module-unicore-lib-dt-wide \
  perl-module-unicore-lib-ea-a \
  perl-module-unicore-lib-ea-f \
  perl-module-unicore-lib-ea-h \
  perl-module-unicore-lib-ea-n \
  perl-module-unicore-lib-ea-na \
  perl-module-unicore-lib-ea-w \
  perl-module-unicore-lib-gc-sc-ahex \
  perl-module-unicore-lib-gc-sc-alnum \
  perl-module-unicore-lib-gc-sc-alpha \
  perl-module-unicore-lib-gc-sc-alphabet \
  perl-module-unicore-lib-gc-sc-any \
  perl-module-unicore-lib-gc-sc-arab \
  perl-module-unicore-lib-gc-sc-armn \
  perl-module-unicore-lib-gc-sc-ascii \
  perl-module-unicore-lib-gc-sc-asciihex \
  perl-module-unicore-lib-gc-sc-assigned \
  perl-module-unicore-lib-gc-sc-beng \
  perl-module-unicore-lib-gc-sc-bidic \
  perl-module-unicore-lib-gc-sc-bidicont \
  perl-module-unicore-lib-gc-sc-blank \
  perl-module-unicore-lib-gc-sc-bopo \
  perl-module-unicore-lib-gc-sc-brai \
  perl-module-unicore-lib-gc-sc-bugi \
  perl-module-unicore-lib-gc-sc-buhd \
  perl-module-unicore-lib-gc-sc-c \
  perl-module-unicore-lib-gc-sc-canadian \
  perl-module-unicore-lib-gc-sc--canondc \
  perl-module-unicore-lib-gc-sc--caseign \
  perl-module-unicore-lib-gc-sc-cc \
  perl-module-unicore-lib-gc-sc-cf \
  perl-module-unicore-lib-gc-sc-cher \
  perl-module-unicore-lib-gc-sc-cn \
  perl-module-unicore-lib-gc-sc-cntrl \
  perl-module-unicore-lib-gc-sc-co \
  perl-module-unicore-lib-gc-sc--combabo \
  perl-module-unicore-lib-gc-sc-copt \
  perl-module-unicore-lib-gc-sc-cprt \
  perl-module-unicore-lib-gc-sc-cs \
  perl-module-unicore-lib-gc-sc-cyrl \
  perl-module-unicore-lib-gc-sc-dash \
  perl-module-unicore-lib-gc-sc-dash2 \
  perl-module-unicore-lib-gc-sc-dep \
  perl-module-unicore-lib-gc-sc-deprecat \
  perl-module-unicore-lib-gc-sc-deva \
  perl-module-unicore-lib-gc-sc-dia \
  perl-module-unicore-lib-gc-sc-diacriti \
  perl-module-unicore-lib-gc-sc-digit \
  perl-module-unicore-lib-gc-sc-dsrt \
  perl-module-unicore-lib-gc-sc-ethi \
  perl-module-unicore-lib-gc-sc-ext \
  perl-module-unicore-lib-gc-sc-extender \
  perl-module-unicore-lib-gc-sc-geor \
  perl-module-unicore-lib-gc-sc-glag \
  perl-module-unicore-lib-gc-sc-goth \
  perl-module-unicore-lib-gc-sc-graph \
  perl-module-unicore-lib-gc-sc-grapheme \
  perl-module-unicore-lib-gc-sc-grek \
  perl-module-unicore-lib-gc-sc-grlink \
  perl-module-unicore-lib-gc-sc-gujr \
  perl-module-unicore-lib-gc-sc-guru \
  perl-module-unicore-lib-gc-sc-hang \
  perl-module-unicore-lib-gc-sc-hani \
  perl-module-unicore-lib-gc-sc-hano \
  perl-module-unicore-lib-gc-sc-hebr \
  perl-module-unicore-lib-gc-sc-hex \
  perl-module-unicore-lib-gc-sc-hexdigit \
  perl-module-unicore-lib-gc-sc-hira \
  perl-module-unicore-lib-gc-sc-hyphen \
  perl-module-unicore-lib-gc-sc-hyphen2 \
  perl-module-unicore-lib-gc-sc-idcontin \
  perl-module-unicore-lib-gc-sc-ideo \
  perl-module-unicore-lib-gc-sc-ideograp \
  perl-module-unicore-lib-gc-sc-idsb \
  perl-module-unicore-lib-gc-sc-idsbinar \
  perl-module-unicore-lib-gc-sc-idst \
  perl-module-unicore-lib-gc-sc-idstart \
  perl-module-unicore-lib-gc-sc-idstrina \
  perl-module-unicore-lib-gc-sc-inaegean \
  perl-module-unicore-lib-gc-sc-inalphab \
  perl-module-unicore-lib-gc-sc-inancie2 \
  perl-module-unicore-lib-gc-sc-inancien \
  perl-module-unicore-lib-gc-sc-inarabi2 \
  perl-module-unicore-lib-gc-sc-inarabi3 \
  perl-module-unicore-lib-gc-sc-inarabi4 \
  perl-module-unicore-lib-gc-sc-inarabic \
  perl-module-unicore-lib-gc-sc-inarmeni \
  perl-module-unicore-lib-gc-sc-inarrows \
  perl-module-unicore-lib-gc-sc-inbasicl \
  perl-module-unicore-lib-gc-sc-inbengal \
  perl-module-unicore-lib-gc-sc-inblocke \
  perl-module-unicore-lib-gc-sc-inbopom2 \
  perl-module-unicore-lib-gc-sc-inbopomo \
  perl-module-unicore-lib-gc-sc-inboxdra \
  perl-module-unicore-lib-gc-sc-inbraill \
  perl-module-unicore-lib-gc-sc-inbugine \
  perl-module-unicore-lib-gc-sc-inbuhid \
  perl-module-unicore-lib-gc-sc-inbyzant \
  perl-module-unicore-lib-gc-sc-incherok \
  perl-module-unicore-lib-gc-sc-incjkco2 \
  perl-module-unicore-lib-gc-sc-incjkco3 \
  perl-module-unicore-lib-gc-sc-incjkco4 \
  perl-module-unicore-lib-gc-sc-incjkcom \
  perl-module-unicore-lib-gc-sc-incjkrad \
  perl-module-unicore-lib-gc-sc-incjkstr \
  perl-module-unicore-lib-gc-sc-incjksym \
  perl-module-unicore-lib-gc-sc-incjkun2 \
  perl-module-unicore-lib-gc-sc-incjkun3 \
  perl-module-unicore-lib-gc-sc-incjkuni \
  perl-module-unicore-lib-gc-sc-incombi2 \
  perl-module-unicore-lib-gc-sc-incombi3 \
  perl-module-unicore-lib-gc-sc-incombi4 \
  perl-module-unicore-lib-gc-sc-incombin \
  perl-module-unicore-lib-gc-sc-incontro \
  perl-module-unicore-lib-gc-sc-incoptic \
  perl-module-unicore-lib-gc-sc-incurren \
  perl-module-unicore-lib-gc-sc-incyprio \
  perl-module-unicore-lib-gc-sc-incyril2 \
  perl-module-unicore-lib-gc-sc-incyrill \
  perl-module-unicore-lib-gc-sc-indesere \
  perl-module-unicore-lib-gc-sc-indevana \
  perl-module-unicore-lib-gc-sc-indingba \
  perl-module-unicore-lib-gc-sc-inenclo2 \
  perl-module-unicore-lib-gc-sc-inenclos \
  perl-module-unicore-lib-gc-sc-inethio2 \
  perl-module-unicore-lib-gc-sc-inethio3 \
  perl-module-unicore-lib-gc-sc-inethiop \
  perl-module-unicore-lib-gc-sc-ingenera \
  perl-module-unicore-lib-gc-sc-ingeomet \
  perl-module-unicore-lib-gc-sc-ingeorg2 \
  perl-module-unicore-lib-gc-sc-ingeorgi \
  perl-module-unicore-lib-gc-sc-inglagol \
  perl-module-unicore-lib-gc-sc-ingothic \
  perl-module-unicore-lib-gc-sc-ingreeka \
  perl-module-unicore-lib-gc-sc-ingreeke \
  perl-module-unicore-lib-gc-sc-ingujara \
  perl-module-unicore-lib-gc-sc-ingurmuk \
  perl-module-unicore-lib-gc-sc-inhalfwi \
  perl-module-unicore-lib-gc-sc-inhangu2 \
  perl-module-unicore-lib-gc-sc-inhangu3 \
  perl-module-unicore-lib-gc-sc-inhangul \
  perl-module-unicore-lib-gc-sc-inhanuno \
  perl-module-unicore-lib-gc-sc-inhebrew \
  perl-module-unicore-lib-gc-sc-inhighpr \
  perl-module-unicore-lib-gc-sc-inhighsu \
  perl-module-unicore-lib-gc-sc-inhiraga \
  perl-module-unicore-lib-gc-sc-inideogr \
  perl-module-unicore-lib-gc-sc-inipaext \
  perl-module-unicore-lib-gc-sc-inkanbun \
  perl-module-unicore-lib-gc-sc-inkangxi \
  perl-module-unicore-lib-gc-sc-inkannad \
  perl-module-unicore-lib-gc-sc-inkatak2 \
  perl-module-unicore-lib-gc-sc-inkataka \
  perl-module-unicore-lib-gc-sc-inkharos \
  perl-module-unicore-lib-gc-sc-inkhmer \
  perl-module-unicore-lib-gc-sc-inkhmers \
  perl-module-unicore-lib-gc-sc-inlao \
  perl-module-unicore-lib-gc-sc-inlatin1 \
  perl-module-unicore-lib-gc-sc-inlatin2 \
  perl-module-unicore-lib-gc-sc-inlatin3 \
  perl-module-unicore-lib-gc-sc-inlatine \
  perl-module-unicore-lib-gc-sc-inletter \
  perl-module-unicore-lib-gc-sc-inlimbu \
  perl-module-unicore-lib-gc-sc-inlinea2 \
  perl-module-unicore-lib-gc-sc-inlinear \
  perl-module-unicore-lib-gc-sc-inlowsur \
  perl-module-unicore-lib-gc-sc-inmalaya \
  perl-module-unicore-lib-gc-sc-inmathe2 \
  perl-module-unicore-lib-gc-sc-inmathem \
  perl-module-unicore-lib-gc-sc-inmisce2 \
  perl-module-unicore-lib-gc-sc-inmisce3 \
  perl-module-unicore-lib-gc-sc-inmisce4 \
  perl-module-unicore-lib-gc-sc-inmisce5 \
  perl-module-unicore-lib-gc-sc-inmiscel \
  perl-module-unicore-lib-gc-sc-inmodifi \
  perl-module-unicore-lib-gc-sc-inmongol \
  perl-module-unicore-lib-gc-sc-inmusica \
  perl-module-unicore-lib-gc-sc-inmyanma \
  perl-module-unicore-lib-gc-sc-innewtai \
  perl-module-unicore-lib-gc-sc-innumber \
  perl-module-unicore-lib-gc-sc-inogham \
  perl-module-unicore-lib-gc-sc-inoldita \
  perl-module-unicore-lib-gc-sc-inoldper \
  perl-module-unicore-lib-gc-sc-inoptica \
  perl-module-unicore-lib-gc-sc-inoriya \
  perl-module-unicore-lib-gc-sc-inosmany \
  perl-module-unicore-lib-gc-sc-inphone2 \
  perl-module-unicore-lib-gc-sc-inphonet \
  perl-module-unicore-lib-gc-sc-inprivat \
  perl-module-unicore-lib-gc-sc-inrunic \
  perl-module-unicore-lib-gc-sc-inshavia \
  perl-module-unicore-lib-gc-sc-insinhal \
  perl-module-unicore-lib-gc-sc-insmallf \
  perl-module-unicore-lib-gc-sc-inspacin \
  perl-module-unicore-lib-gc-sc-inspecia \
  perl-module-unicore-lib-gc-sc-insupers \
  perl-module-unicore-lib-gc-sc-insuppl2 \
  perl-module-unicore-lib-gc-sc-insuppl3 \
  perl-module-unicore-lib-gc-sc-insuppl4 \
  perl-module-unicore-lib-gc-sc-insuppl5 \
  perl-module-unicore-lib-gc-sc-insuppl6 \
  perl-module-unicore-lib-gc-sc-insupple \
  perl-module-unicore-lib-gc-sc-insyloti \
  perl-module-unicore-lib-gc-sc-insyriac \
  perl-module-unicore-lib-gc-sc-intagalo \
  perl-module-unicore-lib-gc-sc-intagban \
  perl-module-unicore-lib-gc-sc-intags \
  perl-module-unicore-lib-gc-sc-intaile \
  perl-module-unicore-lib-gc-sc-intaixua \
  perl-module-unicore-lib-gc-sc-intamil \
  perl-module-unicore-lib-gc-sc-intelugu \
  perl-module-unicore-lib-gc-sc-inthaana \
  perl-module-unicore-lib-gc-sc-inthai \
  perl-module-unicore-lib-gc-sc-intibeta \
  perl-module-unicore-lib-gc-sc-intifina \
  perl-module-unicore-lib-gc-sc-inugarit \
  perl-module-unicore-lib-gc-sc-inunifie \
  perl-module-unicore-lib-gc-sc-invaria2 \
  perl-module-unicore-lib-gc-sc-invariat \
  perl-module-unicore-lib-gc-sc-invertic \
  perl-module-unicore-lib-gc-sc-inyijing \
  perl-module-unicore-lib-gc-sc-inyiradi \
  perl-module-unicore-lib-gc-sc-inyisyll \
  perl-module-unicore-lib-gc-sc-joinc \
  perl-module-unicore-lib-gc-sc-joincont \
  perl-module-unicore-lib-gc-sc-kana \
  perl-module-unicore-lib-gc-sc-khar \
  perl-module-unicore-lib-gc-sc-khmr \
  perl-module-unicore-lib-gc-sc-knda \
  perl-module-unicore-lib-gc-sc-l \
  perl-module-unicore-lib-gc-sc-laoo \
  perl-module-unicore-lib-gc-sc-latn \
  perl-module-unicore-lib-gc-sc-lc \
  perl-module-unicore-lib-gc-sc-limb \
  perl-module-unicore-lib-gc-sc-linearb \
  perl-module-unicore-lib-gc-sc-ll \
  perl-module-unicore-lib-gc-sc-lm \
  perl-module-unicore-lib-gc-sc-lo \
  perl-module-unicore-lib-gc-sc-loe \
  perl-module-unicore-lib-gc-sc-logicalo \
  perl-module-unicore-lib-gc-sc-lower \
  perl-module-unicore-lib-gc-sc-lowercas \
  perl-module-unicore-lib-gc-sc-lt \
  perl-module-unicore-lib-gc-sc-lu \
  perl-module-unicore-lib-gc-sc-m \
  perl-module-unicore-lib-gc-sc-math \
  perl-module-unicore-lib-gc-sc-mc \
  perl-module-unicore-lib-gc-sc-me \
  perl-module-unicore-lib-gc-sc-mlym \
  perl-module-unicore-lib-gc-sc-mn \
  perl-module-unicore-lib-gc-sc-mong \
  perl-module-unicore-lib-gc-sc-mymr \
  perl-module-unicore-lib-gc-sc-n \
  perl-module-unicore-lib-gc-sc-nchar \
  perl-module-unicore-lib-gc-sc-nd \
  perl-module-unicore-lib-gc-sc-newtailu \
  perl-module-unicore-lib-gc-sc-nl \
  perl-module-unicore-lib-gc-sc-no \
  perl-module-unicore-lib-gc-sc-nonchara \
  perl-module-unicore-lib-gc-sc-oalpha \
  perl-module-unicore-lib-gc-sc-odi \
  perl-module-unicore-lib-gc-sc-ogam \
  perl-module-unicore-lib-gc-sc-ogrext \
  perl-module-unicore-lib-gc-sc-oidc \
  perl-module-unicore-lib-gc-sc-oids \
  perl-module-unicore-lib-gc-sc-olditali \
  perl-module-unicore-lib-gc-sc-oldpersi \
  perl-module-unicore-lib-gc-sc-olower \
  perl-module-unicore-lib-gc-sc-omath \
  perl-module-unicore-lib-gc-sc-orya \
  perl-module-unicore-lib-gc-sc-osma \
  perl-module-unicore-lib-gc-sc-otheralp \
  perl-module-unicore-lib-gc-sc-otherdef \
  perl-module-unicore-lib-gc-sc-othergra \
  perl-module-unicore-lib-gc-sc-otheridc \
  perl-module-unicore-lib-gc-sc-otherids \
  perl-module-unicore-lib-gc-sc-otherlow \
  perl-module-unicore-lib-gc-sc-othermat \
  perl-module-unicore-lib-gc-sc-otherupp \
  perl-module-unicore-lib-gc-sc-oupper \
  perl-module-unicore-lib-gc-sc-p \
  perl-module-unicore-lib-gc-sc-patsyn \
  perl-module-unicore-lib-gc-sc-patterns \
  perl-module-unicore-lib-gc-sc-patternw \
  perl-module-unicore-lib-gc-sc-patws \
  perl-module-unicore-lib-gc-sc-pc \
  perl-module-unicore-lib-gc-sc-pd \
  perl-module-unicore-lib-gc-sc-pe \
  perl-module-unicore-lib-gc-sc-pf \
  perl-module-unicore-lib-gc-sc-pi \
  perl-module-unicore-lib-gc-sc-po \
  perl-module-unicore-lib-gc-sc-print \
  perl-module-unicore-lib-gc-sc-ps \
  perl-module-unicore-lib-gc-sc-punct \
  perl-module-unicore-lib-gc-sc-qaai \
  perl-module-unicore-lib-gc-sc-qmark \
  perl-module-unicore-lib-gc-sc-quotatio \
  perl-module-unicore-lib-gc-sc-radical \
  perl-module-unicore-lib-gc-sc-radical2 \
  perl-module-unicore-lib-gc-sc-runr \
  perl-module-unicore-lib-gc-sc-s \
  perl-module-unicore-lib-gc-sc-sc \
  perl-module-unicore-lib-gc-sc-sd \
  perl-module-unicore-lib-gc-sc-shaw \
  perl-module-unicore-lib-gc-sc-sinh \
  perl-module-unicore-lib-gc-sc-sk \
  perl-module-unicore-lib-gc-sc-sm \
  perl-module-unicore-lib-gc-sc-so \
  perl-module-unicore-lib-gc-sc-softdott \
  perl-module-unicore-lib-gc-sc-space \
  perl-module-unicore-lib-gc-sc-spaceper \
  perl-module-unicore-lib-gc-sc-sterm \
  perl-module-unicore-lib-gc-sc-sterm2 \
  perl-module-unicore-lib-gc-sc-sylotina \
  perl-module-unicore-lib-gc-sc-syrc \
  perl-module-unicore-lib-gc-sc-tagb \
  perl-module-unicore-lib-gc-sc-taile \
  perl-module-unicore-lib-gc-sc-taml \
  perl-module-unicore-lib-gc-sc-telu \
  perl-module-unicore-lib-gc-sc-term \
  perl-module-unicore-lib-gc-sc-terminal \
  perl-module-unicore-lib-gc-sc-tfng \
  perl-module-unicore-lib-gc-sc-tglg \
  perl-module-unicore-lib-gc-sc-thaa \
  perl-module-unicore-lib-gc-sc-thai \
  perl-module-unicore-lib-gc-sc-tibt \
  perl-module-unicore-lib-gc-sc-title \
  perl-module-unicore-lib-gc-sc-ugar \
  perl-module-unicore-lib-gc-sc-uideo \
  perl-module-unicore-lib-gc-sc-unifiedi \
  perl-module-unicore-lib-gc-sc-upper \
  perl-module-unicore-lib-gc-sc-uppercas \
  perl-module-unicore-lib-gc-sc-variatio \
  perl-module-unicore-lib-gc-sc-vs \
  perl-module-unicore-lib-gc-sc-whitespa \
  perl-module-unicore-lib-gc-sc-word \
  perl-module-unicore-lib-gc-sc-wspace \
  perl-module-unicore-lib-gc-sc-xdigit \
  perl-module-unicore-lib-gc-sc-yiii \
  perl-module-unicore-lib-gc-sc-z \
  perl-module-unicore-lib-gc-sc-zl \
  perl-module-unicore-lib-gc-sc-zp \
  perl-module-unicore-lib-gc-sc-zs \
  perl-module-unicore-lib-gc-sc-zyyy \
  perl-module-unicore-lib-hst-l \
  perl-module-unicore-lib-hst-lv \
  perl-module-unicore-lib-hst-lvt \
  perl-module-unicore-lib-hst-t \
  perl-module-unicore-lib-hst-v \
  perl-module-unicore-lib-jt-c \
  perl-module-unicore-lib-jt-d \
  perl-module-unicore-lib-jt-r \
  perl-module-unicore-lib-jt-u \
  perl-module-unicore-lib-lb-ai \
  perl-module-unicore-lib-lb-al \
  perl-module-unicore-lib-lb-b2 \
  perl-module-unicore-lib-lb-ba \
  perl-module-unicore-lib-lb-bb \
  perl-module-unicore-lib-lb-bk \
  perl-module-unicore-lib-lb-cb \
  perl-module-unicore-lib-lb-cl \
  perl-module-unicore-lib-lb-cm \
  perl-module-unicore-lib-lb-cr \
  perl-module-unicore-lib-lb-ex \
  perl-module-unicore-lib-lb-gl \
  perl-module-unicore-lib-lb-h2 \
  perl-module-unicore-lib-lb-h3 \
  perl-module-unicore-lib-lb-hy \
  perl-module-unicore-lib-lb-id \
  perl-module-unicore-lib-lb-in \
  perl-module-unicore-lib-lb-is \
  perl-module-unicore-lib-lb-jl \
  perl-module-unicore-lib-lb-jt \
  perl-module-unicore-lib-lb-jv \
  perl-module-unicore-lib-lb-lf \
  perl-module-unicore-lib-lb-nl \
  perl-module-unicore-lib-lb-ns \
  perl-module-unicore-lib-lb-nu \
  perl-module-unicore-lib-lb-op \
  perl-module-unicore-lib-lb-po \
  perl-module-unicore-lib-lb-pr \
  perl-module-unicore-lib-lb-qu \
  perl-module-unicore-lib-lb-sa \
  perl-module-unicore-lib-lb-sg \
  perl-module-unicore-lib-lb-sp \
  perl-module-unicore-lib-lb-sy \
  perl-module-unicore-lib-lb-wj \
  perl-module-unicore-lib-lb-xx \
  perl-module-unicore-lib-lb-zw \
  perl-module-unicore-lib-nt-de \
  perl-module-unicore-lib-nt-di \
  perl-module-unicore-lib-nt-nu \
  perl-module-unicore-name \
  perl-module-unicore-pva \
  perl-module-unicore-to-digit \
  perl-module-unicore-to-fold \
  perl-module-unicore-to-lower \
  perl-module-unicore-to-title \
  perl-module-unicore-to-upper \
  perl-module-universal \
  perl-module-user-grent \
  perl-module-user-pwent \
  perl-module-utf8 \
  perl-module-utf8-heavy \
  perl-module-validate \
  perl-module-vars \
  perl-module-vmsish \
  perl-module-warnings \
  perl-module-warnings-register \
  perl-module-xs-apitest \
  perl-module-xsloader \
  perl-module-xs-typemap \
"