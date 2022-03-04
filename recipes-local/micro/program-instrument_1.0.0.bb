DESCRIPTION = "The recipe to automatically start the boot instrument app programmer"
SRC_URI = " file://program-instrument.py file://program-instrument.service file://mic-bootblock "
LICENSE = "CLOSED"

RDEPENDS_${PN} += " python3-core "

# Add the setup to make it a system serive.
inherit systemd
SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "program-instrument.service"

do_install() {
  install -d ${D}/usr/local/bin
  install -m 0755 ${WORKDIR}/program-instrument.py ${D}/usr/local/bin/
  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/program-instrument.service ${D}/${systemd_unitdir}/system
  install -d ${D}/etc
  install -m 0644 ${WORKDIR}/mic-bootblock ${D}/etc/mic-bootblock
}

FILES_${PN} += "\
  /etc/mic-bootblock \
  /usr/local/bin/program-instrument.py \
  ${systemd_unitdir}/system/program-instrument.service \
"
