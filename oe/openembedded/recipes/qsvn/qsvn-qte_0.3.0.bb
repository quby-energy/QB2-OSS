require qsvn.inc
FILESDIR += "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/qsvn"
inherit qt4e

SRC_URI[md5sum] = "66cfcc9cb5f4e32ef30b2c13de51499b"
SRC_URI[sha256sum] = "99e73cec97c62fecaccc4a7f354392ea9964d8c20b0a4a330890c32dbac87732"
