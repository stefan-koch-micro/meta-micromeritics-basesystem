DESCRIPTION = "The recipe to automatically start chromium"
SRC_URI = "\
  file://80xmodmap.sh \
  file://touchid.sh \
  file://mic-chromium.sh \
  file://mic-chromium.service \
  file://no-password-management.json \
  file://Xmodmap \
"
LICENSE = "CLOSED"

RDEPENDS_${PN} += " bash xinput "

# Add the setup to make it a system serive.
inherit systemd
SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "mic-chromium.service"

do_install() {
  install -d ${D}/etc/X11/Xsession.d
  install -m 0755 ${WORKDIR}/80xmodmap.sh ${D}/etc/X11/Xsession.d/80xmodmap.sh
  install -d ${D}/usr/local/bin
  install -m 0755 ${WORKDIR}/touchid.sh ${D}/usr/local/bin/touchid.sh
  install -d ${D}/home/root
  install -m 0755 ${WORKDIR}/Xmodmap ${D}/home/root/.Xmodmap

  install -m 0755 ${WORKDIR}/mic-chromium.sh ${D}/usr/local/bin/mic-chromium.sh
  install -d ${D}/${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/mic-chromium.service ${D}/${systemd_unitdir}/system
  install -d ${D}/etc/chromium/policies/managed
  install -m 0644 ${WORKDIR}/no-password-management.json ${D}/etc/chromium/policies/managed/no-password-management.json
}

FILES_${PN} += "\
  /etc/X11/Xsession.d/80xmodmap.sh \
  /usr/local/bin/touchid.sh \
  /home/root/.Xmodmap \
  /usr/local/bin/touchid.sh \
  /usr/local/bin/mic-chromium.sh \
  /etc/chromium/policies/managed/no-password-management.json \
  ${systemd_unitdir}/system/mic-chromium.service \
"
