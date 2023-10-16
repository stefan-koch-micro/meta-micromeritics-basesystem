DESCRIPTION = "The recipe to install the Sarasa-Mono-SC-Regular.ttf file to support CJK fonts with haru pdf."
SRC_URI = " file://Sarasa-Mono-SC-Regular.ttf "
LICENSE = "CLOSED"

do_install() {
  install -d ${D}/usr/share/fonts/ttf
  install -m 0755 ${WORKDIR}/Sarasa-Mono-SC-Regular.ttf ${D}/usr/share/fonts/ttf
}

FILES_${PN} += "\
  /usr/share/fonts/ttf/Sarasa-Mono-SC-Regular.ttf \
"
