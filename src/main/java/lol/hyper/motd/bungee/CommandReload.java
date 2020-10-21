package lol.hyper.motd.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CommandReload extends Command {

    private final MOTD motd;
    public CommandReload(String name, MOTD motd) {
        super(name);
        this.motd = motd;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        motd.loadConfig(motd.configFile);
        sender.sendMessage(new TextComponent(ChatColor.GREEN + "Config reloaded!"));
    }
}
