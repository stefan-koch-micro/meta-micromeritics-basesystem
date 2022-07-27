FILES_${PN} += " 0001-usb-support-usb-printer.patch "
FILES_${PN} += " 0002-cifs-enable.patch "
FILES_${PN} += " 004-micro-logo.patch "

SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH} file:///source/sources/meta-micromeritics-basesystem/recipes-local/images/files/0001-usb-support-usb-printer.patch file:///source/sources/meta-micromeritics-basesystem/recipes-local/images/files/0002-cifs-enable.patch file:///source/sources/meta-micromeritics-basesystem/recipes-local/images/files/0004-micro-logo.patch "
