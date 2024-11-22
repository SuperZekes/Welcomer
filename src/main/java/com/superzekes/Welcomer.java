package com.superzekes;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Welcomer extends JavaPlugin implements Listener {

    // Store the welcome message from the config file
    private String welcomeMessage;

    @Override
    public void onEnable() {
        // Save the default config if it doesn't already exist
        saveDefaultConfig();

        // Load the welcome message from the config file
        FileConfiguration config = getConfig();
        welcomeMessage = config.getString("welcome-message", "Welcome to the server, %player%!");

        // Register the event listener to handle player join events
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic (if needed)
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Get the player name
        String playerName = event.getPlayer().getName();

        // Schedule the welcome message with a 1-second delay (20 ticks = 1 second)
        new BukkitRunnable() {
            @Override
            public void run() {
                // Replace %player% with the player's name in the welcome message
                String message = welcomeMessage.replace("%player%", playerName);
                event.getPlayer().sendMessage(message);
            }
        }.runTaskLater(this, 20L); // 20 ticks = 1 second delay
    }
}
