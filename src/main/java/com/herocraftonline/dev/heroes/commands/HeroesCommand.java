package com.herocraftonline.dev.heroes.commands;

import com.herocraftonline.dev.heroes.Heroes;
import com.pneumaticraft.commandhandler.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HeroesCommand extends Command {

    protected Heroes plugin;

    public HeroesCommand(Heroes plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
