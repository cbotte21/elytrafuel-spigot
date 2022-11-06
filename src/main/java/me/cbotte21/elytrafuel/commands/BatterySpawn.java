package me.cbotte21.elytrafuel.commands;

import me.cbotte21.elytrafuel.battery.BatteryItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BatterySpawn implements CommandExecutor {
    ArrayList<BatteryItem> batteries;
    Component prefix;
    public BatterySpawn(ArrayList<BatteryItem> batteries, Component prefix) {
        this.batteries = batteries;
        this.prefix = prefix;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args != null && args.length == 3 && args[0].equalsIgnoreCase("spawn")) {
            Player p = Bukkit.getPlayer(args[1]);
            if (p != null) {
                for (BatteryItem battery : batteries) {
                    if (battery.equalsIgnoreCase(args[2])) {
                        p.getInventory().addItem(battery);
                        return true;
                    }
                }
                sender.sendMessage(prefix.append(Component.text(" Battery not found.")));
            } else {
                sender.sendMessage(prefix.append(Component.text(" Player not found.")));
            }
            return true;
        }
        return false;
    }
}
