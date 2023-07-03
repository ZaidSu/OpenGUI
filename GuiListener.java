package org.zaidsu.opengui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import static org.zaidsu.opengui.ConfirmationGui.CANCEL_ITEM;
import static org.zaidsu.opengui.ConfirmationGui.CONFIRM_ITEM;

public class GuiListener implements Listener {
    private final OpenGUI plugin;

    public GuiListener(OpenGUI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedStack = event.getCurrentItem();

        if (clickedStack == null) return;

        String title = event.getView().getTitle();
        Gui clickedGui = plugin.getGuis().stream()
                .filter(gui -> title.equals(gui.getTitle()))
                .findFirst()
                .orElse(null);

        if (clickedGui != null) {
            event.setCancelled(true);
            handleGuiClick(event, player, clickedGui);
            return;
        }

        if (title.equalsIgnoreCase("Confirmation")) {
            handleConfirmationGui(event, player, clickedStack);
        }
    }

    private void handleConfirmationGui(InventoryClickEvent event, Player player, ItemStack clickedStack) {
        event.setCancelled(true);
        InventoryHolder holder = event.getView().getTopInventory().getHolder();
        if (holder instanceof ConfirmationGui) {
            if (clickedStack.getType().equals(CONFIRM_ITEM.toItemStack().getType())) {
                Item itemToConfirm = ((ConfirmationGui) holder).getItemToConfirm();
                itemToConfirm.executeCommands(player);
            } else if (clickedStack.getType().equals(CANCEL_ITEM.toItemStack().getType())) {
                player.closeInventory();
            }
        }
    }

    private void handleGuiClick(InventoryClickEvent event, Player player, Gui clickedGui) {
        event.setCancelled(true);
        int clickedSlot = event.getSlot();
        Item clickedItem = clickedGui.getItems().stream()
                .filter(item -> item.getSlot() == clickedSlot)
                .findFirst()
                .orElse(null);

        if (clickedItem != null) {
            if (clickedItem.isConversation()) {
                new ConfirmationGui(clickedItem).openConfirmationGui(player, clickedItem);
            } else {
                clickedItem.executeCommands(player);
            }
        }
    }
}