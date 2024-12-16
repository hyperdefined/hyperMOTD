<h1 align="center">hyperMOTD</h1>

<p align="center">
	<img src="https://img.shields.io/badge/Minecraft-1.21+-orange" alt="Minecraft versions">
	<img src="https://img.shields.io/github/v/release/hyperdefined/hyperMOTD" alt="GitHub release (latest by date)">
	<a href="https://github.com/hyperdefined/hyperMOTD/releases"><img src="https://img.shields.io/github/downloads/hyperdefined/hyperMOTD/total?logo=github" alt="Downloads"></a>
	<a href="https://ko-fi.com/hyperdefined"><img src="https://img.shields.io/badge/Donate-Ko--fi-red" alt="Donate via Ko-fi"></a>
	<img alt="Discord" src="https://img.shields.io/discord/1267600843356639413?style=flat&logo=discord&label=Discord">
	<a href="https://www.gnu.org/licenses/gpl-3.0"><img src="https://img.shields.io/badge/License-GPLv3-blue.svg" alt="License: GPL v3"></a>
</p>

Super simple custom MOTD system that allows for very basic random and fixed MOTDs. Supports Paper & Velocity.

## Features
* Complete support for Minimessage formatting.
* Change your server's MOTD and server icon without restarting.

## Default config
Use `/hypermotd` to reload the config.
### Paper Config
```yaml
# Type can be "random" or "fixed." Random will pick a MOTD from the list. Fixed will only show the fixed MOTD.
type: "random"
random-motd:
  - '<red>random'
  - '<blue>random2'
fixed-motd: "<yellow>fixed motd"

# Set a custom favicon here. This allows you to reload the icon without having to reload the proxy/server.
# Place the icon in the hyperMOTD folder with the config.yml
use-custom-icon: false
custom-icon-filename: "server-icon.png"
```
### Velocity Config
```toml
# Type can be "random" or "fixed." Random will pick a MOTD from the list. Fixed will only show the fixed MOTD.
type="random"
random-motd=[
  "<red>random",
  "<blue>random2",
]
fixed-motd="<yellow>fixed motd"
# Set a custom favicon here. This allows you to reload the icon without having to reload the proxy/server.
# Place the icon in the hyperMOTD folder with the config.yml
use-custom-icon=false
custom-icon-filename="server-icon.png"
```