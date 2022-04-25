# hyperMOTD
![Build with Maven](https://github.com/DESTROYMC-NET/DMC-MOTD/workflows/Build%20with%20Maven/badge.svg) [![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

Super simple custom MOTD system that allows for very basic random and fixed MOTDs. Supports Paper, Waterfall, and Velocity only.

## Features
* Complete support for Minimessage formatting.
* Change MOTD and server icon on the fly.
* Super simple.

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
# Place the icon in the hyperMOTD folder with the config.yml
use-custom-icon: false
custom-icon-filename: "icon.png"
```
