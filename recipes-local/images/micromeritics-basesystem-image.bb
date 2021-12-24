DESCRIPTION = "A Micromeritics chromium desktop demo image."
# copied from compulab-image-xfce.bb, but removed xfce from CORE_IMAGE_EXTRA_INSTALL
LICENSE = "MIT"

inherit core-image
inherit distro_features_check

REQUIRED_DISTRO_FEATURES = "x11"

IMAGE_FEATURES += " \
    debug-tweaks \
    tools-profile \
    splash \
    nfs-server \
    nfs-client \
    tools-debug \
    ssh-server-dropbear \
    tools-testapps \
    hwcodecs \
    x11-base \
    dev-pkgs \
"

CORE_IMAGE_EXTRA_INSTALL += " \
    packagegroup-core-x11 \
    packagegroup-core-full-cmdline \
    xf86-video-fbdev \
    xrdb \
"

#    packagegroup-xfce-base
#    packagegroup-xfce-extended
#    packagegroup-xfce-multimedia

CORE_IMAGE_EXTRA_INSTALL += " \
    packagegroup-tools-bluetooth \
    packagegroup-fsl-tools-audio \
    packagegroup-fsl-tools-gpu \
    packagegroup-fsl-tools-gpu-external \
    packagegroup-fsl-tools-testapps \
    packagegroup-fsl-tools-benchmark \
    packagegroup-fsl-gstreamer1.0 \
    packagegroup-fsl-gstreamer1.0-full \
"

# Expand the CORE_IMAGE_EXTRA_INSTALL
# with the custom package list
CORE_IMAGE_EXTRA_INSTALL += " \
"

# Uncoment lines: 51, 52 it for 4G image
# $(( $(( 4096 - 12 )) << 10 ))
# IMAGE_ROOTFS_SIZE = "4194304"
# IMAGE_OVERHEAD_FACTOR = "1.0"

# Uncoment lines: 56, 57 it for 8G image
# $(( $(( 8192 - 12 )) << 10 ))
# IMAGE_ROOTFS_SIZE = "8376320"
# IMAGE_OVERHEAD_FACTOR = "1.0"

IMAGE_INSTALL += " nginx "
IMAGE_INSTALL += " chromium-x11 "
IMAGE_INSTALL += " python3-django "
IMAGE_INSTALL += " python3-django-cors-headers "
IMAGE_INSTALL += " python3-pip "
IMAGE_INSTALL += " python3-pyusb "
IMAGE_INSTALL += " micro-boot-chromium "
IMAGE_INSTALL += " python3-numpy "
IMAGE_INSTALL += " gettext "
IMAGE_INSTALL += " micro-install-fonts "
IMAGE_INSTALL += " micromeritics-kb-mouse-detection "
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
