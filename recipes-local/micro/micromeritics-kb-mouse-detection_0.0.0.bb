DESCRIPTION = "Systemd unit which polls for keyboard/mouse attaching/detaching events"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM=""

RDEPENDS_${PN} += " bash "

do_fetch[nostamp] = "1"

# Add the setup to make it a system service.
inherit systemd
SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "micromeritics-kb-mouse-detection.service"

SRC_URI_append = " file://micromeritics-kb-mouse-detection.service file://micromeritics-kb-mouse-detection.py "
FILES_${PN} += "${systemd_unitdir}/system/micromeritics-kb-mouse-detection.service"
FILES_${PN} += "/usr/local/bin/micromeritics-kb-mouse-detection.py"

do_install_append() {
  install -d ${D}/usr/local/bin
  install -m 0644 ${WORKDIR}/micromeritics-kb-mouse-detection.py ${D}/usr/local/bin
  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/micromeritics-kb-mouse-detection.service ${D}/${systemd_unitdir}/system
}
