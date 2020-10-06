SUMMARY = "ASGI specs, helper code, and adapters"
HOMEPAGE = "https://github.com/django/asgiref/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f09eb47206614a4954c51db8a94840fa"

PYPI_PACKAGE = "asgiref"
inherit pypi

UPSTREAM_CHECK_REGEX = "(?P<pver>1(\.\d+)+)"

FILES_${PN} += "${datadir}/asgiref"

BBCLASSEXTEND = "native nativesdk"

RDEPENDS_${PN} += "\
"
inherit setuptools3

SRC_URI[md5sum] = "d58c9ec83e6263138073b57d159232fa"
SRC_URI[sha256sum] = "7e51911ee147dd685c3c8b805c0ad0cb58d360987b56953878f8c06d2d1c6f1a"

