package me.cbotte21.elytrafuel;

import me.cbotte21.elytrafuel.autocomplete.BatteryAutocomplete;
import me.cbotte21.elytrafuel.battery.BatteryItem;
import me.cbotte21.elytrafuel.commands.BatterySpawn;
import me.cbotte21.elytrafuel.configs.BatteryConfig;
import me.cbotte21.elytrafuel.configs.MessagesConfig;
import me.cbotte21.elytrafuel.events.ElytraBoostEvent;
import me.cbotte21.elytrafuel.events.RecipeDiscoveryEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;

import static me.cbotte21.elytrafuel.configs.MessagesConfig.*;
import static me.cbotte21.elytrafuel.util.Utils.*;

public final class Elytrafuel extends JavaPlugin {
    final String namespacePrefix = "battery";
    ArrayList<BatteryItem> batteries;

    @Override
    public void onEnable() {
        //Configs
        BatteryConfig batteryConfig = new BatteryConfig(getDataFolder(), "batteries.yml");
        MessagesConfig messageConfig = new MessagesConfig(getDataFolder(), "messages.yml");

        batteries = batteryConfig.getBatteries(this, namespacePrefix);
        //Components of parsed config fields
        Component prefix = coloredComponent(messageConfig.getField(Fields.PREFIX));
        Component breakMessage = coloredComponent(messageConfig.getField(Fields.BREAK));
        Component updateMessage = coloredComponent(messageConfig.getField(Fields.CHARGE_UPDATE));
        Component playerNotFoundMessage = coloredComponent(messageConfig.getField(Fields.CHARGE_UPDATE));
        Component batteryNotFoundMessage = coloredComponent(messageConfig.getField(Fields.BATTERY_NOT_FOUND));

        //Enable crafting
        if (batteryConfig.craftingEnabled()) {
            for (BatteryItem battery : batteries) {
                Bukkit.addRecipe(battery.getRecipe());
                getServer().getPluginManager().registerEvents(new RecipeDiscoveryEvent(batteries), this);
            }
        }
        //Autocompletes
        Objects.requireNonNull(getCommand("battery")).setTabCompleter(new BatteryAutocomplete(batteries));
        //Commands
        Objects.requireNonNull(getCommand("battery")).setExecutor(new BatterySpawn(batteries, playerNotFoundMessage, batteryNotFoundMessage));
        //Events
        getServer().getPluginManager().registerEvents(new ElytraBoostEvent(batteries, breakMessage, updateMessage, batteryConfig.wearNotification()), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
