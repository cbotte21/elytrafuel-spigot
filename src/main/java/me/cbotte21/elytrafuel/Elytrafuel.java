package me.cbotte21.elytrafuel;

import me.cbotte21.elytrafuel.autocomplete.BatteryAutocomplete;
import me.cbotte21.elytrafuel.battery.BatteryItem;
import me.cbotte21.elytrafuel.commands.BatterySpawn;
import me.cbotte21.elytrafuel.configs.BatteryConfig;
import me.cbotte21.elytrafuel.events.ElyctraBoostEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public final class Elytrafuel extends JavaPlugin {
    final String namespacePrefix = "elytrafuel";
    final Component prefix = Component.text("[").append(Component.text("ElytraFuel")).color(TextColor.color(102, 153, 255)).append(Component.text("]"));
    ArrayList<BatteryItem> batteries;
    @Override
    public void onEnable() {
        //Config
        BatteryConfig config = new BatteryConfig(this);
        batteries = config.parse(this, namespacePrefix);

        ArrayList<NamespacedKey> namespaces = new ArrayList<>(); //Namespaces of all batteries
        for (BatteryItem battery : batteries) {
            namespaces.add(battery.getNamespace());
        }
        //Enable crafting
        if (config.craftingEnabled()) {
            for (BatteryItem battery : batteries) {
                Bukkit.addRecipe(battery.getRecipe());
            }
        }
        //Autocompletes
        Objects.requireNonNull(getCommand("battery")).setTabCompleter(new BatteryAutocomplete(batteries));
        //Commands
        Objects.requireNonNull(getCommand("battery")).setExecutor(new BatterySpawn(batteries, prefix));
        //Events
        getServer().getPluginManager().registerEvents(new ElyctraBoostEvent(namespaces, prefix, config.wearNotification()), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
