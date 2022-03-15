import logging
import subprocess
from time import sleep
import usb

logging.basicConfig(level=logging.INFO)

def check_device_attached(kbs_and_mice, new_kbs_and_mice, device):
    logging.debug("{} Old length={}, New length={}".format(device, len(kbs_and_mice[device]), len(new_kbs_and_mice[device])))
    return len(kbs_and_mice[device]) == 0 and len(new_kbs_and_mice[device]) > 0

def check_device_detached(kbs_and_mice, new_kbs_and_mice, device):
    logging.debug("{} Old length={}, New length={}".format(device, len(kbs_and_mice[device]), len(new_kbs_and_mice[device])))
    return len(kbs_and_mice[device]) == 1 and len(new_kbs_and_mice[device]) == 0

def find_keyboards_and_mice():
    devices = []
    for device in usb.core.find(find_all=1):
        try:
            if device is not None:
                # The product method sometimes throws an exception because of missing language IDs.
                # Ignore the exception, and the device
                if device.product is not None:
                    devices.append(device)
        except:
            pass
    ret = {
        "keyboard" : [(device, device.product) for device in devices
                      if "keyboard" in str.lower(device.product)],
        "mouse"    : [(device, device.product) for device in devices
                      if "mouse" in str.lower(device.product)]
    }
    logging.debug("Found: {}".format(ret))
    return ret

def update_weston_ini_file(device, operation):
    if device != "mouse":
        logging.error("Incorrectly attempting to update /etc/xdg/weston/weston.ini \
                       file for device={},operation={}".format(device, operation))
        return
    file = "/etc/xdg/weston/weston.ini"
    if operation == "attached":
        cmd = "sed -i 's/hide-cursor=true/hide-cursor=false/g' {file}".format(file=file)
    elif operation == "detached":
        cmd = "sed -i 's/hide-cursor=false/hide-cursor=true/g' {file}".format(file=file)
    else:
        logging.error("Unable to update {} because operation={} is unhandled.".format(file, operation))
        return

    try:
        subprocess.check_output(cmd, shell=True)
    except subprocess.CalledProcessError as exc:
        logging.error(cmd + " failed with error code", exc.returncode, exc.output)

# Setting up and using django in order to manipulate the database takes 2 seconds.
# This adds to the boot-time which is already long as it is, so direct SQL commands
# to access the database are used.
def do_db_ops(device, operation):
    import contextlib
    import sqlite3
    with contextlib.closing(sqlite3.connect("/usr/lib/python3.8/site-packages/db.sqlite3")) as conn:
        with conn:
            with contextlib.closing(conn.cursor()) as cursor:
                if device == "keyboard":
                    logging.debug("has_phys_kb is now {}".format(operation=="attached"))
                    update_cmd = "UPDATE mic_python_lib_settings SET has_phys_kb={} WHERE id=1"\
                                 .format(1 if operation=="attached" else 0)
                if device == "mouse":
                    logging.debug("has_phys_mouse is now {}".format(operation=="attached"))
                    update_cmd = "UPDATE mic_python_lib_settings SET has_phys_mouse={} WHERE id=1"\
                                 .format(1 if operation=="attached" else 0)
                cursor.execute(update_cmd)

def do_linux_ops(device, operation):
    if device == "mouse":
        update_weston_ini_file(device, operation)

def device_attached(device):
    logging.info("{} attached".format(device))
    do_linux_ops(device, "attached")
    do_db_ops(device, "attached")

def device_detached(device):
    logging.info("{} detached".format(device))
    do_linux_ops(device, "detached")
    do_db_ops(device, "detached")

def main():
    kbs_and_mice = find_keyboards_and_mice()
    for device in ["keyboard", "mouse"]:
        if len(kbs_and_mice[device]) == 0:
            device_detached(device)
        if len(kbs_and_mice[device]) > 0:
            device_attached(device)
    logging.debug("OLD:".format(kbs_and_mice))
    try:
        subprocess.check_output("/bin/systemd-notify --ready", shell=True)
    except:
        # If this script is run outside systemd, then don't exit with error.
        pass
    while True:
        sleep(1)
        new_kbs_and_mice = find_keyboards_and_mice()
        for device in ["keyboard", "mouse"]:
            if check_device_attached(kbs_and_mice, new_kbs_and_mice, device):
                device_attached(device)
                logging.debug("OLD:{}, NEW:{}".format(kbs_and_mice, new_kbs_and_mice))
                kbs_and_mice[device] = list(new_kbs_and_mice[device])
                continue
            elif check_device_detached(kbs_and_mice, new_kbs_and_mice, device):
                device_detached(device)
                logging.debug("OLD:{}, NEW:{}".format(kbs_and_mice, new_kbs_and_mice))
                kbs_and_mice[device] = list(new_kbs_and_mice[device])
                continue


if __name__ == "__main__":
    main()
