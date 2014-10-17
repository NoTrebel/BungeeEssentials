/*
 * Copyright (c) 2014 Connor Spencer Harries
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.hadrondev.commands;

import me.hadrondev.BungeeEssentials;
import me.hadrondev.Settings;
import me.hadrondev.permissions.Permission;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Command;

import java.io.UnsupportedEncodingException;

/**
 * Created by Connor Harries on 17/10/2014.
 */
@SuppressWarnings("deprecation")
public class Dispatch extends Command {
    public Dispatch(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if (Permission.has(sender, Permission.ADMIN_DISPATCH)) {
            if (strings.length > 0) {

                StringBuilder builder = new StringBuilder();
                for (String s : strings) {
                    builder.append(s + " ");
                }

                for (ServerInfo server : BungeeEssentials.me.getProxy().getServers().values()) {
                    try {
                        server
                            .sendData("gssentials.dispatch", builder.toString().getBytes("UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                sender.sendMessage(Settings.colour(Settings.INVALID_ARGS));
            }
        } else {
            sender.sendMessage(Settings.colour(Settings.NO_PERMS));
        }
    }
}
