package me.cbotte21.elytrafuel.configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class CustomConfig {
    protected FileConfiguration config;
    protected File file;

    //JavaPlugin.getDataFolder(), "config.yml"
    public CustomConfig(File dataFolder, String filename) {
        file = new File(dataFolder, filename);
        if (!file.exists()) { //Create config
            file.getParentFile().mkdirs();
            config = getDefaultConfig();
            save();
        } else {
            //load config
            reload();
        }
    }

    protected abstract FileConfiguration getDefaultConfig();

    private void save() {
        try { //Save default config
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}
