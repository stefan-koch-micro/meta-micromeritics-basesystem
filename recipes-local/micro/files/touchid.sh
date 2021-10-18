#!/bin/bash
DEVICES=$(ls /dev/input/event*)
TOUCHSCREEN=""
for device in $DEVICES; do
    MESSAGE=$(udevadm info --query=property --name=$device);
    if [[ $(echo ${MESSAGE} | grep "ID_INPUT_TOUCHSCREEN=1") ]]; then
        if [[ $(echo ${MESSAGE} | grep "ID_BUS=usb") ]]; then
            TOUCHSCREEN=$device
        fi
    fi
done
if [[ -z $TOUCHSCREEN ]]; then
    # Touchscreen not found.
    echo "-1";
    exit 1;
fi
IDS=$(DISPLAY=:0.0 xinput list --id-only)
for id in $IDS; do
    MESSAGE=$(DISPLAY=:0.0 xinput list-props $id)
    if [[ $(echo ${MESSAGE} | grep $TOUCHSCREEN) ]]; then
        echo "$id";
    fi
done
