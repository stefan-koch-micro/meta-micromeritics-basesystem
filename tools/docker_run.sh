#!/bin/bash
# This originally came from the asus yocto reference.

set -xe

DIRECTORY_PATH_TO_DOCKER_BUILDER="$(dirname $(readlink -f $0))"
DIRECTORY_PATH_TO_SOURCE="$(dirname $(dirname $(dirname $DIRECTORY_PATH_TO_DOCKER_BUILDER)))"

DOCKER_IMAGE="mic-asus/yocto-3.2-builder:latest"
OPTIONS="--interactive --privileged --rm --tty --network host"
OPTIONS+=" --volume $DIRECTORY_PATH_TO_SOURCE:/source --volume $HOME/yocto-3.2/share:$HOME/yocto-3.2/share"
echo "Options to run docker: $OPTIONS"

docker run $OPTIONS $DOCKER_IMAGE
