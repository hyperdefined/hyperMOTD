package lol.hyper.motd.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.IOException;

public class CommandReload extends Command {
    public CommandReload(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        MOTD.getInstance().loadConfig(MOTD.getInstance().configFile);
        sender.sendMessage(new TextComponent(ChatColor.GREEN + "Config reloaded!"));
    }
}
