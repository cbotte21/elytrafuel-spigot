package me.cbotte21.elytrafuel.commands;

import java.util.Objects;
import java.util.Optional;
import me.cbotte21.elytrafuel.battery.BatteryItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BatterySpawn implements CommandExecutor {
    ArrayList<BatteryItem> batteries;
    Component playerNotFoundMessage, batteryNotFoundMessage;
    public BatterySpawn(ArrayList<BatteryItem> batteries, Component playerNotFoundMessage, Component batteryNotFoundMessage) {
        this.batteries = batteries;
        this.playerNotFoundMessage = playerNotFoundMessage;
        this.batteryNotFoundMessage = batteryNotFoundMessage;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args != null && args.length >= 2 && args[0].equalsIgnoreCase("spawn")) {
            Player player = null;
            if(args.length >= 3) {
                player = Bukkit.getPlayer(args[2]);
            } else {
                if(sender instanceof Player) {
                    player = (Player) sender;
                }
            }

            if (Objects.nonNull(player)) {
                for (BatteryItem battery : batteries) {
                    if (battery.equalsIgnoreCase(args[1])) {
                        player.getInventory().addItem(battery);
                        return true;
                    }
                }
                sender.sendMessage(batteryNotFoundMessage);
            } else {
                sender.sendMessage(playerNotFoundMessage);
            }
            return true;
        }
        return false;
    }
}
