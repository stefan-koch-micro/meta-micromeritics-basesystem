#!/bin/bash

TOUCHSCREEN_DEVICE_NAME="/home/root/.touchscreen"
TOUCHSCREEN_LOG_FILE="/home/root/.touchscreen.log"
if [[ ! -f "$TOUCHSCREEN_DEVICE_NAME" ]]; then
    # Unfortunately, no matter what font I tried, I could not increase the size of the
    # font used by the dialog. I tried using fonts by including xlsfonts, xorg-minimal-fonts
    # to the basesystem image, and then tried the following commands to display and use them:
    # DISPLAY=:0.0 xlsfonts
    # DISPLAY=:0.0 fc-cache -f -v
    echo -e "This dialog will close after 10 seconds.\nAfter this happens, please keep "\
            "tapping the screen until the screen is no longer black.\nYou shouldn't have "\
            "to tap for longer than 10 seconds." \
        | xmessage -file - -center -timeout 10 -fn \
          "-misc-fixed-bold-r-semicondensed--13-120-75-75-c-60-iso8859-16"
    XINPUT_OUT=$(xinput --test-xi2 --root | grep -E "device:" -m 1)
    echo "raw: $XINPUT_OUT" >> "$TOUCHSCREEN_LOG_FILE"
    XINPUT_ID=$(echo "$XINPUT_OUT" | awk -vRS=")" -vFS="(" '{printf $2}')
    echo "id: $XINPUT_ID" >> "$TOUCHSCREEN_LOG_FILE"
    XINPUT_DEVICE_NAME=$(echo "$XINPUT_ID" | xargs xinput list-props | head -1 | cut -d"'" -f 2)
    echo -n "$XINPUT_DEVICE_NAME" > "$TOUCHSCREEN_DEVICE_NAME"
fi
