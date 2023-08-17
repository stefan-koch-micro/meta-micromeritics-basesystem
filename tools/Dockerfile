FROM ubuntu:20.04

RUN ln -fs /bin/bash /bin/sh
SHELL ["/bin/bash", "-c"]

USER root
RUN echo "nameserver 8.8.8.8" >> /etc/resolv.conf
RUN echo "nameserver 8.8.4.4" >> /etc/resolv.conf

ARG DEBIAN_FRONTEND=noninteractive
ARG userid
ARG groupid
ARG username

# Install required packages for building Tinker Edge R Android
# kmod: depmod is required by "make modules_install"
RUN apt-get update && apt-get install -y gawk wget git diffstat unzip \
    texinfo gcc-multilib build-essential chrpath socat cpio python python3 \
    python3-pip python3-pexpect xz-utils debianutils iputils-ping python3-git \
    python3-jinja2 libegl1-mesa libsdl1.2-dev pylint3 xterm rsync curl locales \
    python-yaml sudo vim

# the tools needed for 1350 development build.
RUN apt-get update && apt-get install -y cmake npm libboost1.71-all-dev gettext

RUN locale-gen en_US.UTF-8

ENV LANG en_US.UTF-8

RUN groupadd -g $groupid $username && \
    useradd -m -u $userid -g $groupid $username && \
    echo "$username ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers && \
    echo $username >/root/username

ENV HOME=/home/$username
ENV USER=$username

RUN git config --global url.https://git.alsa-project.org/http/.insteadOf git://git.alsa-project.org/
RUN git config --global url.https://github.com/.insteadOf git://github.com/
RUN git config --global url.https://code.qt.io/.insteadOf git://code.qt.io/
RUN git config --global url.https://source.codeaurora.org.insteadOf git://codeaurora.org

WORKDIR /source
ENTRYPOINT chroot --userspec=$(cat /root/username):$(cat /root/username) / /bin/bash -c "cd /source; /bin/bash -i"

