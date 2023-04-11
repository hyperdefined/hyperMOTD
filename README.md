<h1 align="center">hyperMOTD</h1>

<p align="center">
	<img src="https://img.shields.io/badge/Minecraft-idk--1.19.4-orange" alt="Minecraft versions">
	<img src="https://img.shields.io/github/v/release/hyperdefined/hyperMOTD" alt="GitHub release (latest by date)">
	<a href="https://github.com/hyperdefined/hyperMOTD/releases"><img src="https://img.shields.io/github/downloads/hyperdefined/hyperMOTD/total?logo=github" alt="Downloads"></a>
	<a href="https://ko-fi.com/hyperdefined"><img src="https://img.shields.io/badge/Donate-Ko--fi-red" alt="Donate via Ko-fi"></a>
	<a href="https://www.gnu.org/licenses/gpl-3.0"><img src="https://img.shields.io/badge/License-GPLv3-blue.svg" alt="License: GPL v3"></a>
</p>

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
fixed-motd: "fixed motd"

# Set a custom favicon here. This allows you to reload the icon without having to reload the proxy/server.
# Place the icon in the hyperMOTD folder with the config.yml
use-custom-icon: false
custom-icon-filename: "icon.png"
```
