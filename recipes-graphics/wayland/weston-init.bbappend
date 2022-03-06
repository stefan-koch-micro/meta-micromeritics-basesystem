FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {
  sed -i s/mode=1920x1080@60/mode=720x480@60/g ${D}/etc/xdg/weston/weston.ini
}
