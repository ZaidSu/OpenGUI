package org.zaidsu.opengui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import java.util.ArrayList;
import java.util.Collections;

public class ConfirmationGui implements InventoryHolder {
    private final Item itemToConfirm;
    private Inventory inventory;

    public static final Item CONFIRM_ITEM = new Item(11, Material.GREEN_CONCRETE.toString(), ChatColor.translateAlternateColorCodes('&', "&a&lConfirm"), false, false, Collections.emptyList(), new ArrayList<>());
    public static final Item CANCEL_ITEM = new Item(15, Material.RED_CONCRETE.toString(), ChatColor.translateAlternateColorCodes('&', "&c&lCancel"), false, false, Collections.emptyList(), new ArrayList<>());

    public ConfirmationGui(Item itemToConfirm) {
        this.itemToConfirm = itemToConfirm;
    }

    public Item getItemToConfirm() {
        return itemToConfirm;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void openConfirmationGui(Player player, Item itemToConfirm) {
        if (player == null || itemToConfirm == null) {
            return;
        }
        this.inventory = Bukkit.createInventory(this, 27, ChatColor.translateAlternateColorCodes('&', "Confirmation"));

        Item confirmItem = new Item(11, Material.GREEN_CONCRETE.toString(), ChatColor.translateAlternateColorCodes('&', "&a&lConfirm"), false, false, Collections.emptyList(), itemToConfirm.getCommands());
        Item cancelItem = new Item(15, Material.RED_CONCRETE.toString(), ChatColor.translateAlternateColorCodes('&', "&c&lCancel"), false, false, Collections.emptyList(), new ArrayList<>());

        this.inventory.setItem(confirmItem.getSlot(), confirmItem.toItemStack());
        this.inventory.setItem(cancelItem.getSlot(), cancelItem.toItemStack());

        player.openInventory(this.inventory);
    }
}