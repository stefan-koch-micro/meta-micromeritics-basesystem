#!/usr/bin/python3
"""Python application that runs as part of the embedded linux micromeritics-basesystem.  This is
meant to run at boot time, and if no instrument has been installed to install it from the boot
process.

If instrument software is already installed, then exit quickly.
If not, wait up to 10 seconds to see if the user inserts/or the system mounts a flash drive.
If it does, and there is exactly one version of one product available, install it and reboot to
allow the normal startup to take place with the instrument software.

The format of the instrument USB stick is as follows:
1. There is an instrument directory in the root of the usb stick, named after the model number.
2. There is a vX.Y.Z version directory in the instrument directory.
3. There are .deb files and a mic-instrument file in the version directory that are to be installed
   for that version of the instrument.
"""
import datetime
import glob
import os
import re
import shutil
import subprocess
import time

# Where to store the information about what instrument has been installed.
INST_FILE = '/etc/mic-instrument'

# The file to log information to.
LOG = open("/tmp/program-instrument.log", "a")

def get_usb_drive_mounts():
    "Return a list of paths for each usb drive mountd"
    process = subprocess.Popen(['mount'], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    stdout, _ = process.communicate()
    re_usb = re.compile(r'^/dev/sd\w+\s+on\s+(?P<mount>[/\w]+).*')
    ret = []
    for line in stdout.decode('utf-8').splitlines():
        match = re_usb.match(line)
        if match:
            ret.append(match.group('mount'))
    return ret

def has_instrument_app():
    """Check to see if instrument software has been installed."""
    is_inst = os.path.exists(INST_FILE)
    if is_inst:
        print("Instrument: '" + open(INST_FILE).read().strip() +"' already programmed.", file=LOG)
    return is_inst

def wait_usb(max_time):
    """Keep looking for up to max_time seconds to see if a usb stick has been added."""
    for _ in range(max_time):
        time.sleep(1)
        dirs = get_usb_drive_mounts()
        if len(dirs) != 0:
            break

def find_instrument_app_versions():
    """Search over the usb stick directory structure to see if we have a properly formatted usb
    stick.  This returns a dictionary with each key being the instrument name, and each value an
    array of versions."""
    all_inst = {}
    for path in get_usb_drive_mounts():
        for instrument_dir in glob.glob(path+"/*"):
            if not os.path.isdir(instrument_dir):
                continue
            model = os.path.basename(instrument_dir)
            for version_dir in glob.glob(instrument_dir+'/v*'):
                ver = os.path.basename(version_dir)
                if not os.path.isdir(version_dir):
                    continue
                if not re.match(r"^v(\d+)\.(\d+).(\d+)$", ver):
                    continue
                if len(glob.glob(version_dir+'/*.deb')) == 0:
                    continue
                if not os.path.isfile(version_dir+'/mic-instrument'):
                    continue
                all_inst.setdefault(model, []).append(ver)
    return all_inst

def install(model, version):
    """Install the instrument software (all the .deb files) for model and version."""

    for path in get_usb_drive_mounts():
        debs = glob.glob(path+'/'+model+'/'+version+"/*.deb")
        if len(debs) == 0:
            continue
        cmd = "dpkg -i " + " ".join(debs)
        inst_id = model + " " + version
        os.system(cmd)

        shutil.copyfile(path+'/'+model+'/'+version+"/mic-instrument", INST_FILE)

        print("Installed:", inst_id, file=LOG)

        # now that it is installed, allow the system to restart the UI to load the app normally.
        os.system("systemctl restart mic-chromium")


def install_if_available():
    """Install the instrument software, but only if one model and version is available in the usb
    stick."""
    all_inst = find_instrument_app_versions()
    if len(all_inst.keys()) != 1:
        print("No Unique Inst", file=LOG)
        return # don't do anything if too many models are on the usb stick.
    model = list(all_inst.keys())[0]
    vers = all_inst[model]
    if len(vers) != 1:
        print("No Unique Ver", file=LOG)
        return # don't do anything if too many versions for this model are present on the usb stick.

    install(model, vers[0])

def main():
    """Run the installer, if no instrument software has been installed, try to install from USB. """
    print(datetime.datetime.now(), file=LOG)
    if not has_instrument_app():
        # wait for up to 10 seconds for the usb stick to be mounted.
        wait_usb(10)
        install_if_available()
    else:
        print("Has Inst", file=LOG)

if __name__ == "__main__":
    try:
        main()
    except Exception as exc: # pylint: disable=broad-except
        print("Exception occurred:", str(exc),  file=LOG)
