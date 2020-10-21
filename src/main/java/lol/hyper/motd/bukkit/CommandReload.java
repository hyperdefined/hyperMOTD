package lol.hyper.motd.bukkit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class CommandReload implements CommandExecutor {

    private final MOTD motd;

    public CommandReload(MOTD motd) {
        this.motd = motd;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.isOp() || commandSender instanceof ConsoleCommandSender) {
            motd.loadConfig(motd.configFile);
            commandSender.sendMessage(ChatColor.GREEN + "Config reloaded!");
        } else {
            commandSender.sendMessage(ChatColor.RED + "No permission.");
        }
        return true;
    }
}
