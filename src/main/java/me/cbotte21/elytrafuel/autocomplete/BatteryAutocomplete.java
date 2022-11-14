package me.cbotte21.elytrafuel.autocomplete;

import me.cbotte21.elytrafuel.battery.BatteryItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BatteryAutocomplete implements TabCompleter {
    ArrayList<String> batteryTiers;
    public BatteryAutocomplete(ArrayList<BatteryItem> batteries) {
        batteryTiers = new ArrayList<>();
        for (BatteryItem battery : batteries) {
            batteryTiers.add(battery.toString());
        }
        Collections.sort(batteryTiers);
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> suggestions = null;
        if (args.length == 1) { //Sub commands
            suggestions = new ArrayList<>();
            suggestions.add("spawn");
            Collections.sort(suggestions);
            return suggestions;
        }
        // /battery spawn <player> <***>
        if (args.length == 2 && args[0].equalsIgnoreCase("spawn")) {
            return batteryTiers;
        }

        return suggestions;
    }
}
