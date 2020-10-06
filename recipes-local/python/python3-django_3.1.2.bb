SUMMARY = "A high-level Python Web framework"
HOMEPAGE = "http://www.djangoproject.com/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f09eb47206614a4954c51db8a94840fa"

PYPI_PACKAGE = "Django"
inherit pypi

UPSTREAM_CHECK_REGEX = "(?P<pver>1(\.\d+)+)"

FILES_${PN} += "${datadir}/django"

BBCLASSEXTEND = "native nativesdk"

RDEPENDS_${PN} += "\
    ${PYTHON_PN}-asgiref \
    ${PYTHON_PN}-compression \
    ${PYTHON_PN}-ctypes \
    ${PYTHON_PN}-datetime \
    ${PYTHON_PN}-email \
    ${PYTHON_PN}-html \
    ${PYTHON_PN}-json \
    ${PYTHON_PN}-logging \
    ${PYTHON_PN}-multiprocessing \
    ${PYTHON_PN}-netserver \
    ${PYTHON_PN}-numbers \
    ${PYTHON_PN}-pkgutil \
    ${PYTHON_PN}-pytz \
    ${PYTHON_PN}-sqlparse \
    ${PYTHON_PN}-threading \
    ${PYTHON_PN}-unixadmin \
    ${PYTHON_PN}-xml \
"
inherit setuptools3

SRC_URI[md5sum] = "5fd4b5bd4f474f59fbd70137f4a053ed"
SRC_URI[sha256sum] = "a2127ad0150ec6966655bedf15dbbff9697cc86d61653db2da1afa506c0b04cc"

