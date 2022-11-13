package me.cbotte21.elytrafuel.battery;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class BatteryItem extends ItemStack {
    String tier;
    NamespacedKey namespace;
    String customLore;
    ShapedRecipe recipe;
    public BatteryItem(JavaPlugin plugin, String namespacePrefix, String tier, int charges, ArrayList<String> recipeString, String lore) {
        super(Material.FIREWORK_ROCKET, 1);
        this.namespace = new NamespacedKey(plugin, namespacePrefix.concat("_").concat(tier));
        this.tier = tier;
        customLore = lore;

        BatteryPayloadType payload = new BatteryPayloadType();
        ItemMeta meta = getItemMeta();
        meta.getPersistentDataContainer().set(namespace, payload, charges);
        meta.displayName(Component.text(tier.concat(" battery")));
        meta.addEnchant(Enchantment.DURABILITY, 5, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.lore(List.of(Component.text(ChatColor.translateAlternateColorCodes('&', String.format(lore, charges)))));
        setItemMeta(meta);

        recipe = generateRecipe(recipeString);
    }

    private Material getIngredient(char c) {
        return switch (c) {
            case 'B' -> Material.BEDROCK;
            case 'E' -> Material.EMERALD;
            case 'L' -> Material.LAPIS_LAZULI;
            case 'N' -> Material.NETHER_QUARTZ_ORE;
            case 'A' -> Material.AMETHYST_SHARD;
            case 'P' -> Material.GUNPOWDER;
            case 'S' -> Material.FIREWORK_STAR;
            case 'D' -> Material.DIAMOND;
            case 'G' -> Material.GOLD_INGOT;
            case 'I' -> Material.IRON_INGOT;
            case 'R' -> Material.REDSTONE;
            case 'F' -> Material.FIREWORK_ROCKET;
            case 'Z' -> Material.IRON_BLOCK;
            case 'Y' -> Material.GOLD_BLOCK;
            case 'X' -> Material.DIAMOND_BLOCK;
            case 'W' -> Material.EMERALD_BLOCK;
            default -> Material.AIR;
        };
    }
    public NamespacedKey getNamespace() {
        return namespace;
    }
    private ShapedRecipe generateRecipe(ArrayList<String> recipeString) {
            ShapedRecipe recipe = new ShapedRecipe(namespace, this);
            if (recipeString.size() != 3 || recipeString.get(0).length() != 3 || recipeString.get(1).length() != 3 || recipeString.get(2).length() != 3) { //DOES NOT MATCH RECIPE SIZE
                Bukkit.getServer().getLogger().log(Level.SEVERE, "Invalid crafting recipe:");
                for (String s : recipeString) {
                    Bukkit.getServer().getLogger().log(Level.SEVERE, s);
                }
            }
            recipe.shape(recipeString.get(0), recipeString.get(1), recipeString.get(2));
            //Set ingredients
            ArrayList<Character> ingredientsSet = new ArrayList<>();
            for (String s : recipeString) {
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    if (!ingredientsSet.contains(c)) {
                        ingredientsSet.add(c);
                        recipe.setIngredient(c, getIngredient(c));
                    }
                }
            }
            return recipe;
    }

    public String getCustomLore() {
        return customLore;
    }

    public ShapedRecipe getRecipe() {
        return recipe;
    }

    public boolean equalsIgnoreCase(String tier) {
        return this.tier.equalsIgnoreCase(tier);
    }

    @Override
    public String toString() {
        return tier;
    }
}
