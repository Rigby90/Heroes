package com.herocraftonline.dev.heroes.commands;

import java.util.ArrayList;
import java.util.List;

import com.herocraftonline.dev.heroes.Heroes;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import com.pneumaticraft.commandhandler.Command;

public class HelpCommand extends HeroesCommand {
    private static final int CMDS_PER_PAGE = 7;

    public HelpCommand(Heroes plugin) {
        super(plugin);
        this.setName("Get Help with Heroes");
        this.setCommandUsage("/heroes " + ChatColor.GOLD + "[PAGE #]");
        this.setArgRange(0, 1);
        this.addKey("heroes");
        this.addKey("heroes help");
        this.setPermission("heroes.help", "Displays a nice help menu.", PermissionDefault.TRUE);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        sender.sendMessage(ChatColor.AQUA + "====[ Heroes Help ]====");

        int page = 1;
        if (args.size() == 1) {
            try {
                page = Integer.parseInt(args.get(0));
            } catch (NumberFormatException e) {
            }
        }

        List<Command> availableCommands = new ArrayList<Command>(this.plugin.getCommandHandler().getCommands(sender));

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.AQUA + " Add a '" + ChatColor.DARK_PURPLE + "?" + ChatColor.AQUA + "' after a command to see more about it.");
            for (Command c : availableCommands) {
                sender.sendMessage(ChatColor.AQUA + c.getCommandUsage());
            }
            return;
        }

        int totalPages = (int) Math.ceil(availableCommands.size() / (CMDS_PER_PAGE + 0.0));

        if (page > totalPages) {
            page = totalPages;
        }

        sender.sendMessage(ChatColor.AQUA + " Page " + page + " of " + totalPages);
        sender.sendMessage(ChatColor.AQUA + " Add a '" + ChatColor.DARK_PURPLE + "?" + ChatColor.AQUA + "' after a command to see more about it.");

        this.showPage(page, sender, availableCommands);
    }

    private void showPage(int page, CommandSender sender, List<Command> cmds) {
        int start = (page - 1) * CMDS_PER_PAGE;
        int end = start + CMDS_PER_PAGE;
        for (int i = start; i < end; i++) {
            // For consistancy, print some extra lines if it's a player:
            if (i < cmds.size()) {
                sender.sendMessage(ChatColor.AQUA + cmds.get(i).getCommandUsage());
            } else if (sender instanceof Player) {
                sender.sendMessage(" ");
            }
        }
    }

}
