DESCRIPTION = "The recipe to automatically start chromium"
SRC_URI = "\
  file://micro.logrotate \
"
LICENSE = "CLOSED"

do_install() {
    install -d ${D}${sysconfdir}/logrotate.d
    install -m 0644 ${WORKDIR}/micro.logrotate ${D}${sysconfdir}/logrotate.d/micromeritics
}

FILES_${PN} += "\
    ${sysconfdir}/logrotate.d/micromeritics \
"
