# copied from imx-image-full.bb
# Copyright (C) 2015 Freescale Semiconductor
# Copyright 2017-2019 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-fsl/images/imx-image-multimedia.bb

inherit populate_sdk_qt5

CONFLICT_DISTRO_FEATURES = "directfb"

# Add machine learning for certain SoCs
ML_PKGS                   ?= ""
ML_STATICDEV              ?= ""
ML_PKGS_mx8                = "packagegroup-imx-ml"
ML_STATICDEV_mx8           = "tensorflow-lite-staticdev"
ML_PKGS_mx8dxl             = ""
ML_STATICDEV_mx8dxl        = ""
ML_PKGS_mx8phantomdxl      = ""
ML_STATICDEV_mx8phantomdxl = ""
ML_PKGS_mx8mnlite          = ""
ML_STATICDEV_mx8mnlite     = ""

# Add opencv for i.MX GPU
OPENCV_PKGS       ?= ""
OPENCV_PKGS_imxgpu = " \
    opencv-apps \
    opencv-samples \
    python3-opencv \
"

IMAGE_INSTALL += " \
    ${OPENCV_PKGS} \
    ${ML_PKGS} \
    packagegroup-qt5-imx \
    tzdata \
"

TOOLCHAIN_TARGET_TASK += " \
    ${ML_STATICDEV} \
"

IMAGE_INSTALL += " bash gettext python3-core  boost "
IMAGE_INSTALL += " chromium-x11 "
IMAGE_INSTALL += " cifs-utils "
IMAGE_INSTALL += " colord "
IMAGE_INSTALL += " cups "
IMAGE_INSTALL += " cups-filters "
IMAGE_INSTALL += " dfu-util "
IMAGE_INSTALL += " dpkg "
IMAGE_INSTALL += " gettext "
IMAGE_INSTALL += " ghostscript "
IMAGE_INSTALL += " groff "
IMAGE_INSTALL += " micro-boot-chromium "
IMAGE_INSTALL += " micro-install-fonts "
IMAGE_INSTALL += " micromeritics-kb-mouse-detection "
IMAGE_INSTALL += " nginx "
IMAGE_INSTALL += " program-instrument "
IMAGE_INSTALL += " python3-django "
IMAGE_INSTALL += " python3-django-cors-headers "
IMAGE_INSTALL += " python3-matplotlib "
IMAGE_INSTALL += " python3-numpy "
IMAGE_INSTALL += " python3-pip "
IMAGE_INSTALL += " python3-pyusb "
IMAGE_INSTALL += " python3-requests "
IMAGE_INSTALL += " python3-xlsxwriter "
IMAGE_INSTALL += " samba "
IMAGE_INSTALL += " smbclient "
IMAGE_INSTALL += " xmessage "

# These are the packages needed by the target in the SDK
TOOLCHAIN_TARGET_TASK_append = " boost boost-dev "
# Use bitbake -e to find out the values of the variables. You can see that
# TOOLCHAIN_HOST_TASK = "nativesdk-packagegroup-sdk-host packagegroup-cross-canadian-${MACHINE}"
# The 3 packages below, contain 'inherit nativesdk' which includes python3.7 in the SDK which
# we do not want. As a result, these were removed.
#   nativesdk-meson
#   nativesdk-libtool
#   nativesdk-qemu-helper
# I tried to remove python from this list with the following command in local.conf, but it did not work:
#   TOOLCHAIN_HOST_TASK_remove_task-populate-sdk = " nativesdk-python3 nativesdk-python3-* "
# I also attempted to create a custom layer for nativesdk-packagegroup-sdk-host.bb and remove
# these packages from RDEPENDS:
#   RDEPENDS_${PN}_remove = " nativesdk-meson nativesdk-libtool nativesdk-qemu-helper "
# but python3 was still being included in the host tools.
# I ended up trying to list out all the packages I needed and made the list based on the following files:
#   sources/poky/meta/recipes-core/packagegroups/nativesdk-packagegroup-sdk-host.bb
#   sources/poky/meta/recipes-core/packagegroups/packagegroup-cross-canadian.bb
#   sources/poky/meta/recipes-core/meta/buildtools-tarball.bb
BINUTILS = "binutils-cross-canadian-${TRANSLATED_TARGET_ARCH}"
GCC = "gcc-cross-canadian-${TRANSLATED_TARGET_ARCH}"
TOOLCHAIN_HOST_TASK = "\
    ${@all_multilib_tune_values(d, 'BINUTILS')} \
    ${@all_multilib_tune_values(d, 'GCC')} \
    meta-environment-${MACHINE} \
    nativesdk-autoconf \
    nativesdk-automake \
    nativesdk-binutils \
    nativesdk-binutils-symlinks \
    nativesdk-bison \
    nativesdk-cmake \
    nativesdk-cpp \
    nativesdk-cpp-symlinks \
    nativesdk-flex \
    nativesdk-g++ \
    nativesdk-g++-symlinks \
    nativesdk-gcc \
    nativesdk-gcc-symlinks \
    nativesdk-gettext \
    nativesdk-libatomic \
    nativesdk-libgcc \
    nativesdk-libgcc \
    nativesdk-libgomp-dev \
    nativesdk-libstdc++ \
    nativesdk-libstdc++-dev \
    nativesdk-libstdc++-staticdev \
    nativesdk-makedevs \
    nativesdk-opkg \
    nativesdk-pkgconfig \
    nativesdk-pseudo \
    nativesdk-python \
    nativesdk-sdk-provides-dummy \
    nativesdk-shadow \
    nativesdk-qemu \
    nativesdk-unfs3 \
    nativesdk-glibc \
    nativesdk-elfutils \
    nativesdk-gmp \
    nativesdk-mpfr \
    nativesdk-libmpc \
    nativesdk-zlib \
    "
