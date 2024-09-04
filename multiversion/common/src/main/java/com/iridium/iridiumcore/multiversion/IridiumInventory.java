package com.iridium.iridiumcore.multiversion;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class IridiumInventory {
    public abstract Inventory getTopInventory(Player player);
}
