FILES_${PN} += " 0003-Modify-the-boot-delay-to-0-sec-for-IM-A.patch "

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "${UBOOT_SRC};branch=${SRCBRANCH} \
file://0003-Modify-the-boot-delay-to-0-sec-for-IM-A.patch \
file://miccentericon.bmp \
"

do_unpack_append() {
    os.system("cp ${PWD}/miccentericon.bmp ${PWD}/git/tools/logos/asus.bmp")
}
