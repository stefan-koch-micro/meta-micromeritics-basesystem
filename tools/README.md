This directory sets up the tools that are needed to build the
micromeritics yocto cross compiler and basesystem image build.

## How to use

### Setup the Linux build system
Make sure the correct drives are mounted (or at least available).
1. sudo mkdir -p /mnt/k
2. sudo mount -t drvfs K: /mnt/k # This fails if the network drive is down or not responding.

If you don't have access to the network drive copying these and
keeping them up to date will also work. In particular you need
k:/git/yocto, k:/git/yocto/meta-micromeritics-basesystem,

The WSL distribution must be large enough (Sagar had to resize from 256 GB to
456 GB).  This link describes how to do that: https://docs.microsoft.com/en-us/windows/wsl/vhd-size

### Do a full clean build
1. Setup a build DIRECTORY
2. `repo init -u git@gitlab.micusa.com:/software/meta-micromeritics-basesystem -b master -m yocto-repo.xml`
3. `repo sync`
5. `./docker_setup.sh`
6. `./docker_run.sh`
7. `./mic-yocto-basesystem.sh` This builds the basesystem and puts it
   into the basesystem_flashall directory.  This is the image that
   contains the basesystem flashall.  This contains the full, common
   linux system that can be flashed onto an asus imx8p-im-a sbc using
   the flashall.sh or flashall.cmd commands that are also in that
   directory.

   Note that the yocto build (done inside the docker container) uses a build
   directory (build_imx8mq-im-a_RELEASE) that is shared between the docker and
   build machines.  To do a clean build, but keep the yocto cache you can
   delete all the directories of the build dir except this one.
8. `./mic-yocto-sdk.sh` This builds the cross compiler for use
   in developing applications.  This builds the
   ./build_imx8mq-pe100a_RELEASE/tmp/deploy/sdk/ directory that
   contains the sdk that can be installed on other linux development systems.

## Tools
### Building and running the docker VM
The docker VM is used to make the yocto builds.  There are two scripts that
are useful:

1. `docker_setup.sh`: This will create the docker vm to be used for the builds.
2. `docker_run.sh`: This will run the docker image created with `docker_setup.sh`.

Both of these should be lined (through the repo mechanism) to the root yocto
build directory.

### Yocto build support (from within the docker vm)
1. `mic-yocto-basesystem.sh`: This is meant to run inside the docker build machine.
   It will setup the environment, and do the full yocto build.

   It does the following things:
   1. setup the bitbake environment . /source/mic-setup-environment.sh
   2. run the bitbake to create the image bitbake $IMAGE_TYPE
   3. put the right stuff in the right places including flashall: rest of the script.

1. `mic-yocto-sdk.sh`: This is meant to run inside the docker build machine.
   It will setup the environment, and do the full yocto sdk build.

   It does the following things:
   2. setup the bitbake environment . /source/mic-setup-environment.sh
   3. run the bitbake to create the sdk

3. `mic-setup-environment.sh`: This is meant to be sourced in the docker build
   machine.  It will setup the yocto build environment so the user can run bitbake
   on specific items.

4. `flashall`: This directory contains the tools that are needed to flash the
   generated yocto build onto the asus sbc.

