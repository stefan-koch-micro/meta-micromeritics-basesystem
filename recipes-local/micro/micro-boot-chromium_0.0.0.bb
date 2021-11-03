DESCRIPTION = "The recipe to automatically start chromium"
SRC_URI = " file://40xinput_identify_touchscreen.sh file://80xmodmap.sh file://touchid.sh file://Xsession "
LICENSE = "CLOSED"

RDEPENDS_${PN} += " bash "

do_install() {
  install -d ${D}/etc/X11/Xsession.d
  install -m 0755 ${WORKDIR}/40xinput_identify_touchscreen.sh ${D}/etc/X11/Xsession.d/40xinput_identify_touchscreen.sh
  install -m 0755 ${WORKDIR}/80xmodmap.sh ${D}/etc/X11/Xsession.d/80xmodmap.sh
  install -d ${D}/usr/local/bin
  install -m 0755 ${WORKDIR}/touchid.sh ${D}/usr/local/bin/touchid.sh
  install -d ${D}/home/root
  install -m 0755 ${WORKDIR}/Xmodmap ${D}/home/root/.Xmodmap
  install -m 0755 ${WORKDIR}/Xsession ${D}/home/root/.Xsession
}

FILES_${PN} += "\
  /etc/X11/Xsession.d/40xinput_identify_touchscreen.sh \
  /etc/X11/Xsession.d/80xmodmap.sh \
  /usr/local/bin/touchid.sh \
  /home/root/.Xmodmap \
  /home/root/.Xsession \
"
