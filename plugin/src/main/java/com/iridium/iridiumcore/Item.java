package com.iridium.iridiumcore;

import com.cryptomorin.xseries.XMaterial;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Class which represents an item in an inventory.
 * Uses {@link XMaterial} for multi-version support.
 * Serialized in the Configuration files.
 */
@NoArgsConstructor
public class Item {

    public XMaterial material;
    public int amount;
    public String displayName;
    public String skullData;
    @Deprecated
    public String headData;
    @Deprecated
    public String headOwner;
    @Deprecated
    public UUID headOwnerUUID;
    public Integer model;
    public List<String> lore;
    public Integer slot;

    /**
     * Creates a new item with the provided data.
     *
     * @param material    The material of the item. Specifies the type
     * @param amount      The amount of this item. Should not be higher than the max stack size
     * @param displayName The display name of the item with color codes
     * @param lore        The lore of the item, can be empty
     */
    public Item(XMaterial material, int amount, String displayName, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.displayName = displayName;
    }

    /**
     * Creates a new item with the provided data.
     *
     * @param material    The material of the item. Specifies the type
     * @param slot        The slot where this item should be in
     * @param amount      The amount of this item. Should not be higher than the max stack size
     * @param displayName The display name of the item with color codes
     * @param lore        The lore of the item, can be empty
     */
    public Item(XMaterial material, int slot, int amount, String displayName, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.displayName = displayName;
        this.slot = slot;
    }

    /**
     * Creates a new item with the provided data.
     * Used for creating custom heads.
     *
     * @param material    The material of the item. Specifies the type
     * @param slot        The slot where this item should be in
     * @param skullData    The data of the head for custom heads
     * @param amount      The amount of this item. Should not be higher than the max stack size
     * @param displayName The display name of the item with color codes
     * @param lore        The lore of the item, can be empty
     */
    public Item(XMaterial material, int slot, String skullData, int amount, String displayName, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.displayName = displayName;
        this.slot = slot;
        this.skullData = skullData;
    }

    /**
     * Creates a new item with the provided data.
     *
     * @param material    The material of the item. Specifies the type
     * @param amount      The amount of this item. Should not be higher than the max stack size
     * @param displayName The display name of the item with color codes
     * @param model       The Model of the item
     * @param lore        The lore of the item, can be empty
     */
    public Item(XMaterial material, int amount, String displayName, int model, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.displayName = displayName;
        this.model = model;
    }

    /**
     * Migrates deprecated data over to the new methods
     */
    public void migrateData() {
        if (headOwner != null && !headOwner.isEmpty()) {
            IridiumCore.getInstance().getLogger().warning("\"headOwner\" is now deprecated, automatically mapping to \"skullData\"");
            skullData = headOwner;
            headOwner = null;
        }
        if (headOwnerUUID != null) {
            IridiumCore.getInstance().getLogger().warning("\"headOwnerUUID\" is now deprecated, automatically mapping to \"skullData\"");
            skullData = headOwnerUUID.toString();
            headOwnerUUID = null;
        }
        if (headData != null && !headData.isEmpty()) {
            IridiumCore.getInstance().getLogger().warning("\"headData\" is now deprecated, automatically mapping to \"skullData\"");
            skullData = headData;
            headData = null;
        }
    }

}
