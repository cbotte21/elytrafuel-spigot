package me.cbotte21.elytrafuel.events;

import me.cbotte21.elytrafuel.battery.BatteryItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class recipeDiscoveryEvent implements Listener {
    ArrayList<BatteryItem> batteries;
    public recipeDiscoveryEvent(ArrayList<BatteryItem> batteries) {
        this.batteries = batteries;
    }
    @EventHandler
    public void recipeDiscoveryEvent(PlayerJoinEvent event) {
        for (BatteryItem battery : batteries) {
            if (!event.getPlayer().hasDiscoveredRecipe(battery.getNamespace())) {
                event.getPlayer().discoverRecipe(battery.getNamespace());
            }
        }
    }
}
