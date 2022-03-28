#!/bin/sh
VERSION="RELEASE"
VERSION_NUMBER="1.0"
BUILD_SDK="$1"
if [ $BUILD_SDK == "0" ]; then
    TARGET_PRODUCT=imx8mq-im-a
else
    TARGET_PRODUCT=imx8mq-pe100a
fi

BUILD=build_${TARGET_PRODUCT}_${VERSION}

rm -rf $BUILD/conf
BUILD_SDK="$BUILD_SDK" DISTRO=fsl-imx-xwayland MACHINE=$TARGET_PRODUCT EULA=1 IMAGE_VERSION=$VERSION_NUMBER source mic-setup-release.sh -b $BUILD
