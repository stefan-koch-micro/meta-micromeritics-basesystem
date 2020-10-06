SUMMARY = "Non-validating SQL parser"
HOMEPAGE = "https://github.com/andialbrecht/sqlparse"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2b136f573f5386001ea3b7b9016222fc"

PYPI_PACKAGE = "sqlparse"
inherit pypi

UPSTREAM_CHECK_REGEX = "(?P<pver>1(\.\d+)+)"

FILES_${PN} += "${datadir}/sqlparse"

BBCLASSEXTEND = "native nativesdk"

RDEPENDS_${PN} += "\
"
inherit setuptools3

SRC_URI[md5sum] = "423047887a3590b04dd18f8caf843a2f"
SRC_URI[sha256sum] = "e162203737712307dfe78860cc56c8da8a852ab2ee33750e33aeadf38d12c548"

