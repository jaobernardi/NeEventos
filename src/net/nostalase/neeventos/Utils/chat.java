package net.nostalase.neeventos.Utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class chat {
    public static String formatColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void sendMessage(String text, Player[] player) {
        for (Player p: player) {
            p.sendMessage(config.getPrefix () + chat.formatColor(text));
        }
    }

    public static void sendMessage(String text, Player player) {
        player.sendMessage(config.getPrefix() + chat.formatColor(text));
    }
}
