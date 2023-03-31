
package com.iridium.iridiumcore.gui;

import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Represents a clickable GUI.
 * Base for all other classes in this package.
 */
public interface ClosableGUI {

    /**
     * Called when the GUI is closed.
     *
     * @param event The InventoryCloseEvent provided by Bukkit
     */
    void onInventoryClose(InventoryCloseEvent event);

}