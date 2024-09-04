package com.iridium.iridiumcore.multiversion;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class IridiumInventory_V1_19_R2 extends IridiumInventory {
    @Override
    public Inventory getTopInventory(Player player) {
        return player.getOpenInventory().getTopInventory();
    }
}
