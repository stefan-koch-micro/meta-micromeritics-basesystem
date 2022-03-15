#!/bin/sh
if test -z "$XDG_RUNTIME_DIR"; then
    export XDG_RUNTIME_DIR=/run/user/`id -u`
    if ! test -d "$XDG_RUNTIME_DIR"; then
        mkdir --parents $XDG_RUNTIME_DIR
        chmod 0700 $XDG_RUNTIME_DIR
    fi
fi

# wait for weston
while [ ! -e  $XDG_RUNTIME_DIR/wayland-0 ] ; do sleep 0.1; done
sleep 1

export DISPLAY=:0.0

TOUCH_DEVICE_ID=$(/usr/local/bin/touchid.sh)
chromium --kiosk --no-sandbox --start-fullscreen --fast --fast-start --disable-infobars --password-store=basic \
         --app=http://localhost:80 --touch-devices=$TOUCH_DEVICE_ID --top-chrome-touch-ui --touch-events \
         --enable-features=OverlayScrollbar &

sleep 5
