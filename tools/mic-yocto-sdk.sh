#!/bin/bash
# =================================================================================================
TOP_DIR=/source
OS=YOCTO-3.2
PRODUCT_NAME=basesystem
IMAGE_TYPE=micromeritics-basesystem-image

# =================================================================================================
function bitbake_basesystem {
    if [ $1 == "0" ]; then
        bitbake $IMAGE_TYPE
    else
        bitbake $IMAGE_TYPE -c populate_sdk
    fi
    if [ $? -eq 0 ]; then
        echo "====Build yocto ok!===="
    else
        echo "====Build yocto failed!===="
        exit 1
    fi
}

# =================================================================================================
# the actual work using the functions defined above.

BUILD_SDK=1
. /source/mic-setup-environment.sh "$BUILD_SDK"
bitbake_basesystem "$BUILD_SDK"
