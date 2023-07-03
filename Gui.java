package org.zaidsu.opengui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Gui {
    private String title;
    private int size;
    private String id;
    private List<Item> items;
    private Inventory inventory;

    public Gui(String title, int size, String id, List<Item> items) {
        this.title = title;
        this.size = size;
        this.id = id;
        this.items = items;
    }

    public void openGui(Player player) {
        String finalTitle = (title == null) ? "" : ChatColor.translateAlternateColorCodes('&', title);
        Inventory inventory = Bukkit.createInventory(null, size, finalTitle);
        this.inventory = Bukkit.createInventory(null, size, finalTitle);

        for (Item item : items) {
            ItemStack stack = new ItemStack(Material.valueOf(item.getMaterial()), 1);
            ItemMeta meta = stack.getItemMeta();

            String itemName = item.getDisplayName();
            String finalItemName = (itemName == null) ? "" : ChatColor.translateAlternateColorCodes('&', itemName);
            meta.setDisplayName(finalItemName);

            meta.setLore(item.getLore());
            if (item.isGlowing()) {
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            stack.setItemMeta(meta);
            inventory.setItem(item.getSlot(), stack);
        }

        player.openInventory(inventory);
    }


    public List<Item> getItems() {
        return items;
    }

    public String getId() {
        return id;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getTitle() {
        return this.title;
    }

    public static Gui load(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        String id = config.getString("id", "");
        String title = config.getString("title", "");
        int size = config.getInt("size", 9);
        List<Item> items = new ArrayList<>();

        if (config.isConfigurationSection("items")) {
            ConfigurationSection itemsSection = config.getConfigurationSection("items");
            if (itemsSection != null) {
                for (String key : itemsSection.getKeys(false)) {
                    ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
                    if (itemSection != null) {
                        int slot = itemSection.getInt("slot");
                        String material = itemSection.getString("material");
                        String name = itemSection.getString("display_name");
                        boolean glow = itemSection.getBoolean("glow");
                        boolean conversation = itemSection.getBoolean("conversation");
                        List<String> lore = itemSection.getStringList("lore");
                        List<String> commands = itemSection.getStringList("commands");

                        items.add(new Item(slot, material, name, glow, conversation, lore, commands));
                    }
                }
            }
        }
        return new Gui(title, size, id, items);
    }
}