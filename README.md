# DMC-MOTD
![Build with Maven](https://github.com/DESTROYMC-NET/DMC-MOTD/workflows/Build%20with%20Maven/badge.svg) [![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

Custom MOTD system. Allows for random and fixed MOTDs. Supports Spigot/Paper and BungeeCord/Waterfall servers. Works on 1.16 and above.

## Download
To download the latest build, head over to the [Actions](https://github.com/DESTROYMC-NET/DMC-MOTD/actions) page and grab the latest build. It will download a zip file. Inside the zip file is the jar file.

## Default config
Use `/motdreload` to reload the config.
```yaml
# Type can be "random" or "fixed." Random will pick a MOTD from the list. Fixed will only show the fixed MOTD.
type: "random"
random-motd:
 - '&6random1'
 - '&6random2'
fixed-motd: "fixed test"

# Set a custom favicon here. This allows you to reload the icon without having to reload the proxy/server.
# Place the icon in the DMC-MOTD folder with the config.yml
use-custom-icon: false
custom-icon-filename: "icon.png"
```
