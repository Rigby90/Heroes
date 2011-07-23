package com.herocraftonline.dev.heroes;

import com.herocraftonline.dev.heroes.commands.HelpCommand;
import com.herocraftonline.dev.heroes.datasource.Downloader;
import com.pneumaticraft.commandhandler.CommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Heroes extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");

    private CommandHandler commandHandler;
    public HeroesPermissions ph;

    public void onLoad() {
        Heroes.log("Initializing...");
        // The following runs the Downloader class which downloads the H2 and MySQL Libs.
        new Downloader();
    }

    /**
     * Called when this plugin is disabled
     */
    @Override
    public void onDisable() {
        Heroes.log("- Disabled!");
    }

    /**
     * Called when this plugin is enabled
     */
    @Override
    public void onEnable() {
        // Setup Permissions, we'll do an initial check for the Permissions plugin then fall back on isOP().
        this.ph = new HeroesPermissions(this);
        // Setup the command manager
        this.commandHandler = new CommandHandler(this, this.ph);

        registerCommands();

        // Simple output to log.
        Heroes.log("- Version " + getDescription().getVersion() + " is Enabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (this.isEnabled() == false) {
            sender.sendMessage("This plugin is Disabled!");
            return true;
        }
        ArrayList<String> allArgs = new ArrayList<String>(Arrays.asList(args));
        allArgs.add(0, command.getName());
        return this.commandHandler.locateAndRunCommand(sender, allArgs);
    }

    private void registerCommands() {
        this.commandHandler.registerCommand(new HelpCommand(this));
    }

    public CommandHandler getCommandHandler() {
        return this.commandHandler;
    }

    // Below are the Methods to log outputs to log files.

    public static void debugLog(String message) {
        Heroes.debugLog(Level.INFO, message);
    }

    public static void debugLog(Level level, String message) {

    }

    public static void log(String message) {
        Heroes.log(Level.INFO, message);
    }

    public static void log(Level level, String message) {
        log.log(level, "[Heroes] " + message);
    }
}
