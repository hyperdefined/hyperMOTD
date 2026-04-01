/*
 * This file is part of hyperMOTD.
 *
 * hyperMOTD is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * hyperMOTD is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with hyperMOTD.  If not, see <https://www.gnu.org/licenses/>.
 */

package lol.hyper.hypermotd.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import lol.hyper.hypermotd.MOTDPaper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;

public class CommandReload implements BasicCommand {

    private final MOTDPaper motdPaper;

    public CommandReload(MOTDPaper motdPaper) {
        this.motdPaper = motdPaper;
    }

    @Override
    public void execute(@NonNull CommandSourceStack source, String @NonNull [] args) {
        CommandSender sender = source.getSender();
        if (!sender.hasPermission("hypermotd.reload")) {
            sender.sendMessage(Component.text("You do not have permission for this command.", NamedTextColor.RED));
            return;
        }

        motdPaper.loadConfig(motdPaper.configFile);
        sender.sendMessage(Component.text("Config reloaded!", NamedTextColor.GREEN));
    }

    @Override
    public String permission() {
        return "hypermotd.reload";
    }
}
