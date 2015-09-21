/*
 * BungeeEssentials: Full customization of a few necessary features for your server!
 * Copyright (C) 2015  David Shen (PantherMan594)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.pantherman594.gssentials.aliases;

import com.pantherman594.gssentials.utils.Dictionary;
import com.pantherman594.gssentials.utils.Permissions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LoadCmds extends Command {
    private final String[] commands;
    private final String main;

    public LoadCmds(String main, String[] commands) {
        super(main);
        this.main = main;
        this.commands = commands;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission(Permissions.General.ALIAS) || sender.hasPermission(Permissions.General.ALIAS + "." + main)) {
            for (String command : commands) {
                command = parseCommands(command, sender, args);
                if (command != null) {
                    ProxyServer.getInstance().getPluginManager().dispatchCommand(sender, command);
                } else {
                    sender.sendMessage(Dictionary.format(Dictionary.ERROR_INVALID_ARGUMENTS, "HELP", "VARIES"));
                }
            }
        } else {
            sender.sendMessage(ProxyServer.getInstance().getTranslation("no_permission"));
        }
    }

    @SuppressWarnings("deprecation")
    private String parseCommands(String command, CommandSender sender, String[] args) {
        int num = 0;
        String server;
        if (sender instanceof ProxiedPlayer) {
            server = ((ProxiedPlayer) sender).getServer().getInfo().getName();
        } else {
            server = "CONSOLE";
        }
        while (args.length > num && command.contains("{" + num + "}")) {
            if ((args[num] != null) && (!args[num].equals(""))) {
                command = command.replace("{" + num + "}", args[num]).replace("{{ PLAYER }}", sender.getName()).replace("{{ SERVER }}", server);
            } else {
                return null;
            }
            num++;
        }
        return command;
    }
}