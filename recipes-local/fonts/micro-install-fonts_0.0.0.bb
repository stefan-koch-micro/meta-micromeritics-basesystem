DESCRIPTION = "The recipe to install required font files"
SRC_URI = ""
SRC_URI += "  file://DejaVuMathTeXGyre.ttf "
SRC_URI += "  file://DejaVuSans-BoldOblique.ttf "
SRC_URI += "  file://DejaVuSans-Bold.ttf "
SRC_URI += "  file://DejaVuSansCondensed-BoldOblique.ttf "
SRC_URI += "  file://DejaVuSansCondensed-Bold.ttf "
SRC_URI += "  file://DejaVuSansCondensed-Oblique.ttf "
SRC_URI += "  file://DejaVuSansCondensed.ttf "
SRC_URI += "  file://DejaVuSans-ExtraLight.ttf "
SRC_URI += "  file://DejaVuSansMono-BoldOblique.ttf "
SRC_URI += "  file://DejaVuSansMono-Bold.ttf "
SRC_URI += "  file://DejaVuSansMono-Oblique.ttf "
SRC_URI += "  file://DejaVuSansMono.ttf "
SRC_URI += "  file://DejaVuSans-Oblique.ttf "
SRC_URI += "  file://DejaVuSans.ttf "
SRC_URI += "  file://DejaVuSerif-BoldItalic.ttf "
SRC_URI += "  file://DejaVuSerif-Bold.ttf "
SRC_URI += "  file://DejaVuSerifCondensed-BoldItalic.ttf "
SRC_URI += "  file://DejaVuSerifCondensed-Bold.ttf "
SRC_URI += "  file://DejaVuSerifCondensed-Italic.ttf "
SRC_URI += "  file://DejaVuSerifCondensed.ttf "
SRC_URI += "  file://DejaVuSerif-Italic.ttf "
SRC_URI += "  file://DejaVuSerif.ttf "
LICENSE = "CLOSED"

do_install() {
  install -d ${D}/usr/share/fonts/ttf
  install -m 0755 ${WORKDIR}/*.ttf ${D}/usr/share/fonts/ttf
}

FILES_${PN} += "/usr/share/fonts/ttf/*.ttf"
