#!/bin/bash
# This originally came from the asus yocto reference.

set -xe

mkdir -p $HOME/yocto-3.2/share

if [ -x "$(command -v docker)" ]; then
    echo "Docker is installed and the execute permission is granted."
    if getent group docker | grep &>/dev/null "\b$(id -un)\b"; then
	echo "User $(id -un) is in the group docker."
    else
        echo "Docker is not managed as a non-root user."
	echo "Please refer to the following URL to manage Docker as a non-root user."
        echo "https://docs.docker.com/install/linux/linux-postinstall/"
	exit
    fi
else
    echo "Docker is not installed or the execute permission is not granted."
    echo "Please refer to the following URL to install Docker."
    echo "http://redmine.corpnet.asus/projects/configuration-management-service/wiki/Docker"
    exit
fi

DIRECTORY_PATH_TO_DOCKER_BUILDER="$(dirname $(readlink -f $0))"
echo "DIRECTORY_PATH_TO_DOCKER_BUILDER: $DIRECTORY_PATH_TO_DOCKER_BUILDER"

DIRECTORY_PATH_TO_SOURCE="$(dirname $(dirname $(dirname $DIRECTORY_PATH_TO_DOCKER_BUILDER)))"

if [ $# -eq 0 ]; then
    echo "There is no directory path to the source provided."
    echo "Use the default directory path to the source [$DIRECTORY_PATH_TO_SOURCE]."
else
    DIRECTORY_PATH_TO_SOURCE=$1
    if [ ! -d $DIRECTORY_PATH_TO_SOURCE ]; then
        echo "The source directory [$DIRECTORY_PATH_TO_SOURCE] is not found."
        exit
    fi
fi

# Make the python and vmcfc mic framework pip files available to the docker builder. Copy them
# into the docker directory as that is where they can be accessed.
pushd mic-python-fw
python3 setup.py sdist
cp dist/*.tar.gz ../sources/meta-micromeritics-basesystem/tools
popd
pushd mic-vcmfc-fw
python3 setup.py sdist
cp dist/*.tar.gz ../sources/meta-micromeritics-basesystem/tools
popd
# Fix bitbake errors caused by recipes pulling from a non-existent 'master' branch instead
# of the existing "main" branch.
pushd ./sources/meta-iotedge/recipes-core/iotedge-daemon
sed -i s/nobranch=1/branch=main/g ./iotedge-daemon_1.1.3.bb
popd
pushd ./sources/meta-iotedge/recipes-core/iotedge-cli
sed -i s/nobranch=1/branch=main/g ./iotedge-cli_1.1.3.bb
popd

## ------------------
# Build the image.
DOCKER_IMAGE="mic-asus/yocto-3.2-builder:latest"
docker build \
    --build-arg userid=$(id -u) --build-arg groupid=$(id -g) --build-arg username=$(id -un) -t $DOCKER_IMAGE \
    --file $DIRECTORY_PATH_TO_DOCKER_BUILDER/Dockerfile $DIRECTORY_PATH_TO_DOCKER_BUILDER
