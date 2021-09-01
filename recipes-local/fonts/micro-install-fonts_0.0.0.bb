DESCRIPTION = "The recipe to install required font files"
SRC_URI = "file://*.ttf"
LICENSE = "CLOSED"

do_install() {
  install -d ${D}/usr/share/fonts/ttf
  install -m 0755 ${WORKDIR}/*.ttf ${D}/usr/share/fonts/ttf
}

FILES_${PN} += "/usr/share/fonts/ttf/*.ttf"
