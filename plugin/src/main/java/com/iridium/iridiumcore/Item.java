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
    public String headData;
    public String headOwner;
    public UUID headOwnerUUID;
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
     * @param headData    The data of the head for custom heads
     * @param amount      The amount of this item. Should not be higher than the max stack size
     * @param displayName The display name of the item with color codes
     * @param lore        The lore of the item, can be empty
     */
    public Item(XMaterial material, int slot, String headData, int amount, String displayName, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.displayName = displayName;
        this.slot = slot;
        this.headData = headData;
    }

    /**
     * Creates a new item with the provided data.
     * Used for creating player heads.
     *
     * @param material    The material of the item. Specifies the type
     * @param slot        The slot where this item should be in
     * @param amount      The amount of this item. Should not be higher than the max stack size
     * @param displayName The display name of the item with color codes
     * @param headOwner   The owner of the head
     * @param lore        The lore of the item, can be empty
     */
    public Item(XMaterial material, int slot, int amount, String displayName, String headOwner, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.displayName = displayName;
        this.headOwner = headOwner;
        this.slot = slot;
    }

    /**
     * Creates a new item with the provided data.
     * Used for creating player heads in an inventory.
     *
     * @param material    The material of the item. Specifies the type
     * @param amount      The amount of this item. Should not be higher than the max stack size
     * @param displayName The display name of the item with color codes
     * @param headOwner   The owner of the head
     * @param lore        The lore of the item, can be empty
     */
    public Item(XMaterial material, int amount, String displayName, String headOwner, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.displayName = displayName;
        this.headOwner = headOwner;
    }

    /**
     * Creates a new item with the provided data.
     * Used for creating player heads in an inventory.
     *
     * @param material    The material of the item. Specifies the type
     * @param amount      The amount of this item. Should not be higher than the max stack size
     * @param displayName The display name of the item with color codes
     * @param headOwner   The owner of the head
     * @param ownerUUID   The UUID owner of the head
     * @param lore        The lore of the item, can be empty
     */
    public Item(XMaterial material, int amount, String displayName, String headOwner, UUID ownerUUID, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.lore = lore;
        this.displayName = displayName;
        this.headOwnerUUID = ownerUUID;
        this.headOwner = headOwner;
    }

}
