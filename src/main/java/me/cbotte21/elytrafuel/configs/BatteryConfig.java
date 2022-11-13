package me.cbotte21.elytrafuel.configs;

import me.cbotte21.elytrafuel.battery.BatteryItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class BatteryConfig extends CustomConfig {
    public BatteryConfig(File dataFolder, String filename) {
        super(dataFolder, filename);
    }
    public ArrayList<BatteryItem> getBatteries(JavaPlugin plugin, String namespacePrefix) {
        ArrayList<BatteryItem> batteries = new ArrayList<>();
        Set<String> tiers = config.getKeys(false);
        config.options().pathSeparator('{');
        tiers.remove("craft-able");
        tiers.remove("wear-notification");
        for (String tier : tiers) { //loop through all tiers
            ArrayList<String> recipe = new ArrayList<>();
            recipe.add(config.getString(String.format("%s{recipe{top", tier)));
            recipe.add(config.getString(String.format("%s{recipe{middle", tier)));
            recipe.add(config.getString(String.format("%s{recipe{bottom", tier)));
            batteries.add(new BatteryItem(
                    plugin,
                    namespacePrefix,
                    config.getString(String.format("%s{tier", tier)),
                    config.getInt(String.format("%s{charges", tier)),
                    recipe,
                    Objects.requireNonNull(config.getString(String.format("%s{lore", tier)))
            ));
        }
        return batteries;
    }
    public boolean craftingEnabled() {
        return config.getBoolean("craft-able");
    }
    public int wearNotification() {
        return config.getInt("wear-notification");
    }
    protected FileConfiguration getDefaultConfig() {
        FileConfiguration config = new YamlConfiguration();
        config.options().pathSeparator('{');

        config.set("craft-able", true);
        config.set("wear-notification", 50);
        //Bedrock
        config.set("tesla{tier", "Tesla");
        config.set("tesla{lore", "Remaining charges: %d");
        config.set("tesla{charges", Integer.MAX_VALUE);
        config.set("tesla{recipe{top", "RBR");
        config.set("tesla{recipe{middle", "BFB");
        config.set("tesla{recipe{bottom", "RBR");
        //Comments
        config.setInlineComments("tesla{recipe", List.of(
                "Each row should contain 3 block abbreviations, encoded by the following:",
                "B - Bedrock",
                "E - Emerald",
                "L - Lapis Lazuli",
                "N - Nether Quartz Ore",
                "A - Amethyst shard",
                "P - Gunpowder",
                "S - Firework star",
                "D - Diamond",
                "G - Gold",
                "I - Iron",
                "R - Redstone",
                "F - Firework rocket",
                "Z - Iron Block",
                "Y - Gold Block",
                "X - Diamond Block",
                "W - Emerald Block"
        ));

        //Diamond
        config.set("diamond{tier", "Diamond");
        config.set("diamond{lore", "Remaining charges: %d");
        config.set("diamond{charges", 500);
        config.set("diamond{recipe{top", "RDR");
        config.set("diamond{recipe{middle", "DFD");
        config.set("diamond{recipe{bottom", "RDR");
        //Gold
        config.set("gold{tier", "Gold");
        config.set("gold{lore", "Remaining charges: %d");
        config.set("gold{charges", 250);
        config.set("gold{recipe{top", "RGR");
        config.set("gold{recipe{middle", "GFG");
        config.set("gold{recipe{bottom", "RGR");
        //Iron
        config.set("iron{tier", "Iron");
        config.set("iron{lore", "Remaining charges: %d");
        config.set("iron{charges", 100);
        config.set("iron{recipe{top", "RIR");
        config.set("iron{recipe{middle", "IFI");
        config.set("iron{recipe{bottom", "RIR");

        return config;
    }
}
