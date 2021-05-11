DESCRIPTION = "The recipie to automatically start chromium"
SRC_URI = " file://Xsession "
LICENSE = "CLOSED"

do_install() {
  install -d ${D}/home/root
  install -m 0755 ${WORKDIR}/Xsession ${D}/home/root/.Xsession
}

FILES_${PN} += "/home/root/.Xsession"

DEPENDS += " micromeritics-mic-cpp "

