SUMMARY = "A high-level Python Web framework"
HOMEPAGE = "http://www.djangoproject.com/"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4a1ad5f33d40087ebd966d2ea95d07f0"

PYPI_PACKAGE = "django-cors-headers"
inherit pypi

UPSTREAM_CHECK_REGEX = "(?P<pver>1(\.\d+)+)"

BBCLASSEXTEND = "native nativesdk"

RDEPENDS_${PN} += "\
    ${PYTHON_PN}-django \
"
inherit setuptools3

SRC_URI[md5sum] = "7f23798f1c4e11e1736e5cf3bac2247d"
SRC_URI[sha256sum] = "5665fc1b1aabf1b678885cf6f8f8bd7da36ef0a978375e767d491b48d3055d8f"

