FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {
  sed -i s/mode=1920x1080@60/mode=720x480@60/g ${D}/etc/xdg/weston/weston.ini
  WESTON_INI=${D}/etc/xdg/weston/weston.ini
  # The hide-cursor setting was only working correctly, when it was placed in the same
  # [libinput] section.
  if grep -Fxq "[libinput]" "$WESTON_INI"
  then
      sed -i '/^\[libinput\].*/a hide-cursor=true' "$WESTON_INI"
  else
      cat <<-"EOF" >> "$WESTON_INI"
[libinput]
hide-cursor=true
touchscreen_calibrator=true
EOF
  fi
}
