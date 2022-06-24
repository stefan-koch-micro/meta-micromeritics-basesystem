"The service that checks for the presence/absence of a mouse or keyboard on boot."
import configparser
import subprocess
import os
import usb

FNAME_CONFIG = "/etc/micromeritics-config.ini"
def main():
    """
    The script functionality: check for mouse and keybard then update system accordingly.

    It creates (removes) the /run/mic-{device}-on-boot files when if it finds the {device}.
    {device} is both keyboard, and mouse.
    """
    kbs_and_mice = _find_keyboards_and_mice()
    print("MAIN:", kbs_and_mice) # read with "journalctl -xe -u micromeritics-kb-mouse-detection"
    _update_weston_ini_file(len(kbs_and_mice["mouse"]) > 0)
    _update_config(kbs_and_mice)

# =================================================================================================
def _find_keyboards_and_mice():
    devices = []
    for device in usb.core.find(find_all=1):
        try:
            if device is not None:
                if device.product is not None:
                    devices.append(device)
        except:  #pylint: disable=bare-except
            # The product method sometimes throws an exception because of missing language IDs.
            # Ignore the exception, and the device
            pass
    ret = {
        "keyboard" : [(device, device.product) for device in devices
                      if "keyboard" in str.lower(device.product)],
        "mouse"    : [(device, device.product) for device in devices
                      if "mouse" in str.lower(device.product)]
    }
    return ret

def _update_config(kbs_and_mice):
    config = configparser.ConfigParser()
    config.read(FNAME_CONFIG)
    config['devices'] = {device: len(kbs_and_mice[device]) > 0 for device in ['keyboard', 'mouse']}
    with open(FNAME_CONFIG, 'w') as configfile:
        config.write(configfile)

def _update_weston_ini_file(have_mouse):
    file = "/etc/xdg/weston/weston.ini"
    if have_mouse:
        cmd = "sed -i 's/hide-cursor=true/hide-cursor=false/g' {file}".format(file=file)
    else:
        cmd = "sed -i 's/hide-cursor=false/hide-cursor=true/g' {file}".format(file=file)

    try:
        subprocess.check_output(cmd, shell=True)
    except subprocess.CalledProcessError as exc:
        raise RuntimeError(f"{cmd} failed with error code: {exc.returncode}, {exc.output}")

# =================================================================================================
if __name__ == "__main__":
    main()
