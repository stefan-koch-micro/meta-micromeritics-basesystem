DESCRIPTION = "The recipe to automatically start chromium"
SRC_URI = " file://Xsession file://touchid.sh"
LICENSE = "CLOSED"

RDEPENDS_${PN} += " bash "

do_install() {
  install -d ${D}/usr/local/bin
  install -m 0755 ${WORKDIR}/touchid.sh ${D}/usr/local/bin/touchid.sh
  install -d ${D}/home/root
  install -m 0755 ${WORKDIR}/Xsession ${D}/home/root/.Xsession
}

FILES_${PN} += "\
  /usr/local/bin/touchid.sh \
  /home/root/.Xsession \
"
