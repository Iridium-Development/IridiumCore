package com.iridium.iridiumcore.gui;

import com.iridium.iridiumcore.Background;
import com.iridium.iridiumcore.IridiumCore;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public abstract class BackGUI implements GUI {
    private final Background background;
    private final Inventory previousInventory;
    private final Item backButton;
    private final BackButtonBehavior backButtonBehavior;

    public BackGUI(Background background, Player player, Item backButton, BackButtonBehavior backButtonBehavior) {
        this.background = background;
        this.backButton = backButton;
        this.backButtonBehavior = backButtonBehavior;

        if (player == null || backButtonBehavior == BackButtonBehavior.DISABLED) {
            this.previousInventory = null;
            return;
        }

        Inventory previousInventory = IridiumCore.getInstance().getIridiumInventory().getTopInventory(player);
        if (previousInventory == null || previousInventory.getType() != InventoryType.CHEST) {
            this.previousInventory = null;
            return;
        }

        if (backButtonBehavior == BackButtonBehavior.ONLY_SKYBLOCK) {
            this.previousInventory = isSkyblockInventory(previousInventory) ? previousInventory : null;
            return;
        }

        this.previousInventory = previousInventory;
    }

    private boolean isSkyblockInventory(Inventory inventory) {
        return inventory.getHolder() instanceof GUI;
    }

    @Override
    public void addContent(Inventory inventory) {
        InventoryUtils.fillInventory(inventory, background);
        if (previousInventory != null && backButtonBehavior != BackButtonBehavior.DISABLED) {
            inventory.setItem(inventory.getSize() + backButton.slot, ItemStackUtils.makeItem(backButton));
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        if (previousInventory != null && backButtonBehavior != BackButtonBehavior.DISABLED && event.getSlot() == (event.getInventory().getSize() + backButton.slot)) {
            event.getWhoClicked().openInventory(previousInventory);
        }
    }
}
