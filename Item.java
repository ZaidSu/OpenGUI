package org.zaidsu.opengui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Item {
    private ItemStack itemStack;
    private int slot;
    private String material;
    private String displayName;
    private boolean glowing;
    private boolean conversation;
    private List<String> lore;
    private List<String> commands;

    // Constructor
    public Item(int slot, String material, String displayName, boolean glowing, boolean conversation, List<String> lore, List<String> commands) {
        this.slot = slot;
        this.material = material;
        this.displayName = displayName;
        this.glowing = glowing;
        this.conversation = conversation;
        this.lore = lore;
        this.commands = commands;
    }

    // Getters
    public int getSlot() {
        return slot;
    }

    public String getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isGlowing() {
        return glowing;
    }

    public boolean isConversation() {
        return conversation;
    }

    public List<String> getLore() {
        return lore;
    }

    public List<String> getCommands() {
        return commands;
    }

    public ItemStack toItemStack() {
        if (this.getMaterial() == null || this.getDisplayName() == null) {
            return null;
        }
        this.itemStack = new ItemStack(Material.valueOf(this.getMaterial()), 1);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(this.getDisplayName());

        itemStack.setItemMeta(meta);
        return this.itemStack;
    }

    public void executeCommands(Player player) {
        for (String command : commands) {
            if (command.equalsIgnoreCase("close")) {
                player.closeInventory();
            } else {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
            }
        }
    }
}
