package me.cbotte21.elytrafuel.util;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {

    private static boolean papiChecked = false;
    private static boolean papiAvailable = false;

    public static Component coloredComponent(String string) {
        return Component.text(ChatColor.translateAlternateColorCodes('&', string));
    }

    public static Component parsedColoredComponent(String string, Player player) {
        return coloredComponent(isPapiAvailable() ? PlaceholderAPI.setPlaceholders(player, string) : coloredString(string));
    }

    public static String coloredString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String parsedColoredString(String string, Player player) {
        return coloredString(isPapiAvailable() ? PlaceholderAPI.setPlaceholders(player, string) : coloredString(string));
    }

    public static boolean isPapiAvailable() {
        if (!papiChecked) {
            papiAvailable = org.bukkit.Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
            papiChecked = true;
        }
        return papiAvailable;
    }
}
