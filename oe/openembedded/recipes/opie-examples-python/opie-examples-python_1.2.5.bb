require ${PN}.inc

SRC_URI = "http://sources.openembedded.org/opie-1.2.5-split_examples_python.tar.bz2;name=split_examples_python \
           http://sources.openembedded.org/opie-1.2.5-split_pics.tar.bz2;name=split_pics \
           http://sources.openembedded.org/opie-1.2.5-split_apps.tar.bz2;name=split_apps"
SRC_URI[split_examples_python.md5sum] = "19cc8165731d2500be06ea628be37d5c"
SRC_URI[split_examples_python.sha256sum] = "9f82baf3a3d251e352edecc9efa29501cb98cad7cff9fd2aab192b8c6d04aa3d"
SRC_URI[split_pics.md5sum] = "e9b68749d67295f7080a23e39b17335e"
SRC_URI[split_pics.sha256sum] = "0a7aab6d0bce04d6a9c257d768d8a1a481d38ba527d6e6e50723973789954b4a"
SRC_URI[split_apps.md5sum] = "c765d13427e4dc8ee396e4dfed2dbe73"
SRC_URI[split_apps.sha256sum] = "0fea454d8d286f7febc7449e4a06f73e941e8b22e9bab904a32e57f35479862a"
