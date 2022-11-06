package me.cbotte21.elytrafuel.events;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import me.cbotte21.elytrafuel.battery.BatteryPayloadType;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ElyctraBoostEvent implements Listener {
    ArrayList<NamespacedKey> namespaces;
    Component prefix;
    int wearNotification;
    public ElyctraBoostEvent(ArrayList<NamespacedKey> namespaces, Component prefix, int wearNotification) {
        this.namespaces = namespaces;
        this.prefix = prefix;
        this.wearNotification = wearNotification;
    }

    @EventHandler
    public void onElyctraBoost(PlayerElytraBoostEvent event) {
        BatteryPayloadType payload = new BatteryPayloadType();
        ItemMeta fireworkMetadata = event.getItemStack().getItemMeta();
        for (NamespacedKey namespace : namespaces) { //Check for all batteries
            if (fireworkMetadata.getPersistentDataContainer().has(namespace)) { //Item is a battery
                event.setShouldConsume(false); //Do not consume batteries
                int charges = Objects.requireNonNull(fireworkMetadata.getPersistentDataContainer().get(namespace, payload)); //Get charges left on battery
                //Use charge from battery
                --charges;
                if (charges <= 0) {
                    event.getPlayer().sendMessage(prefix.append(Component.text(" Your battery has depleted!")));
                    event.getPlayer().getInventory().remove(event.getItemStack()); //Remove item
                    return;
                } else if (charges % wearNotification == 0) { //Multiple of 50 charges left
                    event.getPlayer().sendMessage(prefix.append(Component.text(String.format(" Your battery has %d charges left!", charges))));
                }
                fireworkMetadata.lore(List.of(Component.text(String.format("Remaining charges: %d", charges))));
                fireworkMetadata.getPersistentDataContainer().set(namespace, payload, charges);
                event.getItemStack().setItemMeta(fireworkMetadata);
            }
        }
    }
}
