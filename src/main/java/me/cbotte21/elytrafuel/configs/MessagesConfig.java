package me.cbotte21.elytrafuel.configs;

import me.cbotte21.elytrafuel.battery.BatteryItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MessagesConfig extends CustomConfig {
    public MessagesConfig(File dataFolder, String filename) {
        super(dataFolder, filename);
    }

    public enum Fields {
        PREFIX,
        BREAK,
        CHARGE_UPDATE,
        PLAYER_NOT_FOUND,
        BATTERY_NOT_FOUND
    }

    @Override
    protected FileConfiguration getDefaultConfig() {
        FileConfiguration config = new YamlConfiguration();
        config.options().pathSeparator('{');

        config.set("prefix", "&7[&b&lElytraFuel&7] ");
        config.setComments("prefix", List.of("Note trailing space"));
        config.set("break", "Your battery has completely depleted!");
        config.set("charge-update", "Your battery has %d charges left!");
        config.set("player-not-found", "Player not found.");
        config.set("battery-not-found", "Battery not found.");

        return config;
    }

    public String getField(Fields field) {
        return switch (field) {
            case PREFIX -> config.getString("prefix");
            case BREAK -> config.getString("break");
            case CHARGE_UPDATE -> config.getString("charge-update");
            case PLAYER_NOT_FOUND -> config.getString("player-not-found");
            case BATTERY_NOT_FOUND -> config.getString("battery-not-found");
        };
    }
}
