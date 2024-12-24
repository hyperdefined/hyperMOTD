<h1 align="center">hyperMOTD</h1>

<p align="center">
	<a href="https://modrinth.com/plugin/hypermotd"><img alt="modrinth" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/available/modrinth_vector.svg"></a>
	<a href="https://hangar.papermc.io/hyperdefined/hyperMOTD"><img alt="hangar" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/available/hangar_vector.svg"></a>
	<a href="https://papermc.io"><img alt="paper" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/supported/paper_vector.svg"></a>
	<a href="https://papermc.io"><img alt="velocity" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/supported/velocity_vector.svg"></a>
	<a href="https://github.com/hyperdefined/hypermotd/wiki"><img alt="ghpages" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/documentation/ghpages_vector.svg"></a>
	<a href="https://discord.gg/rJuQXVcJz8"><img alt="discord-singular" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/social/discord-singular_vector.svg"></a>
	<a href="https://ko-fi.com/hyperdefined"><img alt="kofi-singular" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/donate/kofi-singular_vector.svg"></a>
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