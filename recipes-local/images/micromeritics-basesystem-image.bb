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

# From: https://stackoverflow.com/a/32277691
inherit extrausers
EXTRA_USERS_PARAMS = "usermod -P 'gE0R6!4' root;"

IMAGE_LINGUAS = "\
en-ag en-au en-bw en-ca en-dk en-gb en-hk en-ie en-il \
en-in en-ng en-nz en-ph en-sc en-sg en-us en-za en-zm \
zh-cn zh-hk zh-sg zh-tw \
fr-be fr-ca fr-ch fr-fr fr-lu \
de-at de-be de-ch de-de de-it de-li de-lu \
it-ch it-it \
ja-jp \
ko-kr \
es-ar es-cl es-co es-cu es-do es-gt es-pa es-us es-ve \
"
GLIBC_GENERATE_LOCALES ?= "\
en_AG.utfi en_AU.utf8 en_BW.utf8 en_CA.utf8 en_DK.utf8 en_GB.utf8 en_HK.utf8 en_IE.utf8 en_IL.utf8 \
en_IN.utf8 en_NG.utf8 en_NZ.utf8 en_PH.utf8 en_SC.utf8 en_SG.utf8 en_US.utf8 en_ZA.utf8 en_ZM.utf8 \
zh_CN.utf8 zh_HK.utf8 zh_SG.utf8 zh_TW.utf8 fr_BE.utf8 fr_CA.utf8 fr_CH.utf8 fr_FR.utf8 fr_LU.utf8 \
de_AT.utf8 de_BE.utf8 de_CH.utf8 de_DE.utf8 de_IT.utf8 de_LI.utf8 de_LU.utf8 \
it-CH.utf8 it-IT.utf8 \
ja-JP.utf8 \
ko-KR.utf8 \
es-AR.utf8 es-CL.utf8 es-CO.utf8 es-CU.utf8 es-DO.utf8 es-GT.utf8 es-PA.utf8 es-US.utf8 es-VE.utf8 \
"
IMAGE_INSTALL += " glibc-utils localedef "

IMAGE_INSTALL += " bash gettext python3-core  boost "
IMAGE_INSTALL += " chromium-x11 "
IMAGE_INSTALL += " cifs "
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
IMAGE_INSTALL += " micro-logrotate "
IMAGE_INSTALL += " micromeritics-kb-mouse-detection "
IMAGE_INSTALL += " nginx "
IMAGE_INSTALL += " numlockx "
IMAGE_INSTALL += " program-instrument "
IMAGE_INSTALL += " python3-babel "
IMAGE_INSTALL += " python3-django "
IMAGE_INSTALL += " python3-django-cors-headers "
IMAGE_INSTALL += " python3-icu "
IMAGE_INSTALL += " python3-matplotlib "
IMAGE_INSTALL += " python3-numpy "
IMAGE_INSTALL += " python3-pip "
IMAGE_INSTALL += " python3-pyusb "
IMAGE_INSTALL += " python3-requests "
IMAGE_INSTALL += " python3-xlsxwriter "
IMAGE_INSTALL += " samba "
IMAGE_INSTALL += " smbclient "
IMAGE_INSTALL += " xmessage "
IMAGE_INSTALL += " source-han-sans-kr-fonts "
IMAGE_INSTALL += " source-han-sans-jp-fonts "
IMAGE_INSTALL += " source-han-sans-cn-fonts "
IMAGE_INSTALL += " source-han-sans-tw-fonts "

# the following are from 2.0.20 meta-asus-imx/dynamic-layers/qt5-layer/recipes-fsl/images/imx-image-full.bbappend
# The following were removed: docker docker-ce vim python3-docker-compose
IMAGE_INSTALL += " \
      asus-overlay \
      gptfdisk \
      exfat-utils \
      fuse-exfat \
      ntfs-3g \
      gpsd \
      networkmanager \
      networkmanager-nmcli \
      networkmanager-nmtui \
      iotedge-daemon \
      iotedge-cli \
      ca-certificates \
      canopensocket \
      can-utils-j1939 \
      whiptail \
      glibc-utils \
      glibc-gconv-utf-16 \
      localedef \
      cmake \
      packagegroup-core-buildessential \
      ppp \
      libqmi \
      libmbim \
      edgetpu \
      mraa \
      modemmanager \
      opkg \
  "

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
