#!/bin/bash
echo "$(DISPLAY=:0.0 xinput list 2> /dev/null | grep xwayland-pointer | \
    sed -n "s/^.*id=\([[:digit:]]\+\).*$/\1/p")"
