package net.nostalase.neeventos.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class config {

    private static FileConfiguration config = Bukkit.getServer().getPluginManager().getPlugin("NeEventos").getConfig();

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&',
                config.getString ("prefix"));
    }

    public static String getEventTitle(String event_name, String event_command) {
        return ChatColor.translateAlternateColorCodes('&',
                config.getString ("event.title")
                        .replace("$event_name", event_name)
                        .replace("$event_command", event_command));
    }

    public static String getEventSubTitle(String event_name, String event_command) {
        return ChatColor.translateAlternateColorCodes('&',
                config.getString ("event.sub_title")
                        .replace("$event_name", event_name)
                        .replace("$event_command", event_command));
    }
    public static String getEventActionBar(String event_name, String event_command) {
        return ChatColor.translateAlternateColorCodes('&',
                config.getString ("event.action-bar")
                        .replace("$event_name", event_name)
                        .replace("$event_command", event_command));
    }
}
