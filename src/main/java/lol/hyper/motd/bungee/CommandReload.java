package lol.hyper.motd.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CommandReload extends Command {
    public CommandReload(String name) {
        super("motdreload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        MOTD.getInstance().loadConfig();
        sender.sendMessage(new TextComponent(ChatColor.GREEN + "Config reloaded!"));
    }
}
