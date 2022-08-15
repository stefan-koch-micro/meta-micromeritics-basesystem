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

# see if there was a mouse connected at starup, and if not, set the touch behavior.
grep -i -e "mouse *= *true" /etc/micromeritics-config.ini > /dev/nul
if [ $? -eq 0 ] ; then
    TOUCH_PARAM=""
else
    TOUCH_DEVICE_ID=$(/usr/local/bin/touchid.sh)
    TOUCH_PARAM="--touch-devices=$TOUCH_DEVICE_ID --touch-events"
fi

# run chromium
chromium --kiosk --no-sandbox --start-fullscreen --fast --fast-start --disable-infobars --password-store=basic \
         --app=http://localhost:80 --top-chrome-touch-ui $TOUCH_PARAM \
         --enable-features=OverlayScrollbar &
sleep 5

# turn on pulse audio for the system.  This allows audio for the help video files.
pulseaudio --start --log-target=syslog
# connect to all known bluetooth devices.  In particular this connected to the trusted bluetooth speaker.
for device in `bluetoothctl devices | awk '{print $2}'` ; do
    bluetoothctl connect $device
done

# make sure that the num-lock is pressed.  This ensures that the values of the
# MettlerTolelo mass balance are properly interpreted.  For some reason this
# must be run after chromium is started, otherwise it does not work.
numlockx
