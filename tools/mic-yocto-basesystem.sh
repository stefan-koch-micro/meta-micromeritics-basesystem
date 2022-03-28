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

function collect_flashall {
    IMAGE_PATH=$TOP_DIR/${PRODUCT_NAME}_flashall
    BOOTLOADER_IMG=$TOP_DIR/$BUILD/tmp/deploy/images/$TARGET_PRODUCT/imx-boot-$TARGET_PRODUCT-4G.bin-flash_evk
    ROOTFS_IMG=$TOP_DIR/$BUILD/tmp/deploy/images/$TARGET_PRODUCT/$IMAGE_TYPE-$TARGET_PRODUCT.wic.bz2

    mkdir -p $IMAGE_PATH

    if [ -f $BOOTLOADER_IMG ]
    then
        echo -n "create bootloader..."
        cp $BOOTLOADER_IMG $IMAGE_PATH/imx-boot-$TARGET_PRODUCT-4G.bin
        echo
    else
        echo -e "\e[31m error: imx-boot-$TARGET_PRODUCT-4G.bin not found! \e[0m"
    fi

    if [ -f $ROOTFS_IMG ]
    then
        echo -n "create rootfs image..."
        cp $ROOTFS_IMG $IMAGE_PATH/$IMAGE_TYPE-$TARGET_PRODUCT.wic.bz2
        echo
    else
        echo -e "\e[31m error: $IMAGE_TYPE-$TARGET_PRODUCT.wic.bz2 not found! \e[0m"
    fi

    echo -n "copy flashall tools..."
    cp $TOP_DIR/sources/meta-micromeritics-basesystem/tools/flashall/* $IMAGE_PATH
    echo
}

# =================================================================================================
# the actual work using the functions defined above.

BUILD_SDK=0
. /source/mic-setup-environment.sh "$BUILD_SDK"
bitbake_basesystem "$BUILD_SDK"
collect_flashall
