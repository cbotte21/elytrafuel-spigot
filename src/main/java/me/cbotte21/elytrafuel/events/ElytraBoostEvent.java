package me.cbotte21.elytrafuel.events;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import java.util.Optional;

import me.cbotte21.elytrafuel.battery.BatteryItem;
import me.cbotte21.elytrafuel.battery.BatteryPayloadType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ElytraBoostEvent implements Listener {
    ArrayList<BatteryItem> batteries;
    Component breakMessage, updateMessage;
    int wearNotification;
    public ElytraBoostEvent(ArrayList<BatteryItem> batteries, Component breakMessage, Component updateMessage, int wearNotification) {
        this.batteries = batteries;
        this.wearNotification = wearNotification;
        this.breakMessage = breakMessage;
        this.updateMessage = updateMessage;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Optional<ItemStack> item = Optional.ofNullable(event.getItem());
        if(item.isPresent()) {
            Material type = item.get().getType();
            if(type.equals(Material.FIREWORK_ROCKET) && Objects.nonNull(event.getClickedBlock())) {
                ItemMeta fireworkMetadata = event.getItem().getItemMeta();
                batteries.forEach(battery -> {
                    if(fireworkMetadata.getPersistentDataContainer().has(battery.getNamespace())) {
                        event.setCancelled(true);
                    }
                });
            }
        }
    }

    @EventHandler
    public void onElytraBoost(PlayerElytraBoostEvent event) {
        BatteryPayloadType payload = new BatteryPayloadType();
        ItemMeta fireworkMetadata = event.getItemStack().getItemMeta();
        for (BatteryItem battery : batteries) { //Check for all batteries
            if (fireworkMetadata.getPersistentDataContainer().has(battery.getNamespace())) { //Item is a battery
                event.setShouldConsume(false); //Do not consume batteries
                int charges = Objects.requireNonNull(fireworkMetadata.getPersistentDataContainer().get(battery.getNamespace(), payload)); //Get charges left on battery
                //Use charge from battery
                --charges;
                if (charges <= 0) {
                    event.getPlayer().sendMessage(breakMessage);
                    event.getPlayer().getInventory().remove(event.getItemStack()); //Remove item
                    return;
                } else if (charges % wearNotification == 0) { //Multiple of 50 charges left
                    event.getPlayer().sendMessage(updateMessage.replaceText(TextReplacementConfig.builder().match("%d").replacement(String.valueOf(charges)).build()));
                }
                fireworkMetadata.lore(List.of(Component.text(ChatColor.translateAlternateColorCodes('&', String.format(battery.getCustomLore(), charges)))));
                fireworkMetadata.getPersistentDataContainer().set(battery.getNamespace(), payload, charges);
                event.getItemStack().setItemMeta(fireworkMetadata);
            }
        }
    }
}
