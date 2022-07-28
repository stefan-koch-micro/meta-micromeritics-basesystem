FILES_${PN} += " 0001-usb-support-usb-printer.patch "
FILES_${PN} += " 0002-cifs-enable.patch "
FILES_${PN} += " 004-micro-logo.patch "

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH} \
file://0001-usb-support-usb-printer.patch \
file://0002-cifs-enable.patch \
file://0004-micro-logo.patch \
"
