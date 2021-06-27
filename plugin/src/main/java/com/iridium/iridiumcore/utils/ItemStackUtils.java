package com.iridium.iridiumcore.utils;

import com.cryptomorin.xseries.XMaterial;
import com.iridium.iridiumcore.Item;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * Class which creates {@link ItemStack}'s.
 */
public class ItemStackUtils {

    private static final boolean supports = XMaterial.supports(14);

    /**
     * Creates a new ItemStack from the provided arguments.
     *
     * @param material The material of this item
     * @param amount   The amount of this item in the Inventory
     * @param name     The name of this item. Will be colored automatically
     * @param lore     The lore of this item
     * @return The new ItemStack
     */
    public static ItemStack makeItem(XMaterial material, int amount, String name, List<String> lore) {
        ItemStack itemStack = material.parseItem();
        if (itemStack == null) return null;
        itemStack.setAmount(amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(StringUtils.color(lore));
        itemMeta.setDisplayName(StringUtils.color(name));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * Creates a new ItemStack from the provided arguments.
     *
     * @param item         An existing item we should clone
     * @param placeholders A list of Placeholders we should claim to the display name and lore
     * @return The new ItemStack
     */
    public static ItemStack makeItem(Item item, List<Placeholder> placeholders) {
        try {
            ItemStack itemStack = makeItem(item.material, item.amount, StringUtils.processMultiplePlaceholders(item.displayName, placeholders), StringUtils.processMultiplePlaceholders(item.lore, placeholders));
            if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
                return setHeadData(item.headData, itemStack);
            } else if (item.material == XMaterial.PLAYER_HEAD && item.headOwner != null) {
                OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(StringUtils.processMultiplePlaceholders(item.headOwner, placeholders));
                return setHeadData(SkinUtils.getHeadData(offlinePlayer.getUniqueId()), itemStack);
            }
            return itemStack;
        } catch (Exception exception) {
            return makeItem(XMaterial.STONE, item.amount, StringUtils.processMultiplePlaceholders(item.displayName, placeholders), StringUtils.processMultiplePlaceholders(item.lore, placeholders));
        }
    }

    /**
     * Creates a new ItemStack from the provided arguments.
     *
     * @param item An existing item we should clone
     * @return The new ItemStack
     */
    public static ItemStack makeItem(Item item) {
        try {
            ItemStack itemStack = makeItem(item.material, item.amount, item.displayName, item.lore);
            if (item.material == XMaterial.PLAYER_HEAD && item.headData != null) {
                return setHeadData(item.headData, itemStack);
            } else if (item.material == XMaterial.PLAYER_HEAD && item.headOwner != null) {
                OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(item.headOwner);
                if (!offlinePlayer.hasPlayedBefore()) {
                    UUID uuid = SkinUtils.getUUID(item.headOwner);
                    if (uuid == null)
                        return setHeadData(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWI3YWY5ZTQ0MTEyMTdjN2RlOWM2MGFjYmQzYzNmZDY1MTk3ODMzMzJhMWIzYmM1NmZiZmNlOTA3MjFlZjM1In19fQ==",
                                itemStack
                        );
                    return setHeadData(SkinUtils.getHeadData(uuid), itemStack);
                } else {
                    return setHeadData(SkinUtils.getHeadData(offlinePlayer.getUniqueId()), itemStack);
                }
            }
            return itemStack;
        } catch (Exception exception) {
            // Create a fallback item
            return makeItem(XMaterial.STONE, item.amount, item.displayName, item.lore);
        }
    }

    /**
     * Serializes an ItemStack to a Base64 encoded String.
     * Returns an empty String if an error occurs.
     *
     * @param itemStack The ItemStack which should be serialized
     * @return The Base64 encoded String representation of this ItemStack
     */
    public static String serialize(ItemStack itemStack) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
            bukkitObjectOutputStream.writeObject(itemStack);
            bukkitObjectOutputStream.flush();

            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Deserializes an ItemStack from a Base64 encoded String.
     * Returns AIR if an error occurs.
     *
     * @param string The Base64 encoded string which should be deserialized
     * @return The deserialized ItemStack
     */
    public static ItemStack deserialize(String string) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.getDecoder().decode(string));
            BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(byteArrayInputStream);
            return (ItemStack) bukkitObjectInputStream.readObject();
        } catch (Exception exception) {
            return XMaterial.AIR.parseItem();
        }
    }

    /**
     * Applies the provided head data to the provided ItemStack and returns it.
     *
     * @param headData  The head data which should be applied
     * @param itemStack The ItemStack which should have the head data
     * @return          A new ItemStack which is similar to the provided one but has the head data
     */
    private static ItemStack setHeadData(String headData, ItemStack itemStack) {
        if (headData == null) return itemStack;

        NBTItem nbtItem = new NBTItem(itemStack);
        NBTCompound skull = nbtItem.addCompound("SkullOwner");
        if (supports) {
            skull.setUUID("Id", UUID.randomUUID());
        } else {
            skull.setString("Id", UUID.randomUUID().toString());
        }

        NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
        texture.setString("Value", headData);
        return nbtItem.getItem();
    }

}
