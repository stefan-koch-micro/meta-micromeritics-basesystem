FILES_${PN} += " 0001-Add-RTL8XXXU-drivers.patch "
FILES_${PN} += " 0001-Add-rtl8723du-Drvier-from-opensource.patch "
FILES_${PN} += " 0001-Add-rtl823bu-firmware-files.patch "
FILES_${PN} += " 0002-cifs-enable.patch "
FILES_${PN} += " 0004-micro-logo.patch "
FILES_${PN} += " 0001-LF-8337-thermal-qoriq-configure-the-monitoring-site-.patch "
FILES_${PN} += " 0001-PCI-imx6-Enable-PHY-internal-regulator-when-supplied.patch "

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH} \
file://0001-Add-rtl8723du-Drvier-from-opensource.patch \
file://0001-Add-RTL8XXXU-drivers.patch \
file://0001-Add-rtl823bu-firmware-files.patch \
file://0002-cifs-enable.patch \
file://0004-micro-logo.patch \
file://0001-LF-8337-thermal-qoriq-configure-the-monitoring-site-.patch \
file://0001-PCI-imx6-Enable-PHY-internal-regulator-when-supplied.patch \
"
