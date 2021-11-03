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
