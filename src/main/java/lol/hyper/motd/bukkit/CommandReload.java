package bukkit.lol.hyper.motd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class CommandReload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.isOp() || commandSender instanceof ConsoleCommandSender) {
            MOTD.getInstance().config = YamlConfiguration.loadConfiguration(MOTD.getInstance().configFile);
            commandSender.sendMessage(ChatColor.GREEN + "Config reloaded!");
        } else {
            commandSender.sendMessage(ChatColor.RED + "No permission.");
        }
        return true;
    }
}
