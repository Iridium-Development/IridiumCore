package com.iridium.iridiumcore.nms;

import com.iridium.iridiumcore.Color;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Interface for working with the net.minecraft.server package.
 * Version-specific, so it has to be implemented for every version we support.
 * This is the implementation for V1_15_R1.
 */
public class NMS_V1_15_R1 implements NMS {

    /**
     * Deletes a block faster than with Spigots implementation.
     * See https://www.spigotmc.org/threads/methods-for-changing-massive-amount-of-blocks-up-to-14m-blocks-s.395868/
     * for more information.
     *
     * @param location The location of the block which should be deleted
     */
    @Override
    public void deleteBlockFast(Location location) {
        World nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
        Chunk nmsChunk = nmsWorld.getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4);
        IBlockData ibd = Block.getByCombinedId(0);

        ChunkSection chunkSection = nmsChunk.getSections()[location.getBlockY() >> 4];
        if (chunkSection == null) {
            chunkSection = new ChunkSection(location.getBlockY() >> 4 << 4);
            nmsChunk.getSections()[location.getBlockY() >> 4] = chunkSection;
        }

        chunkSection.setType(location.getBlockX() & 15, location.getBlockY() & 15, location.getBlockZ() & 15, ibd);
        nmsChunk.getWorld().getChunkProvider().getLightEngine().a(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    /**
     * Sends the provided chunk to all the specified players.
     * Used for updating chunks.
     *
     * @param players The player which should see the updated chunk
     * @param chunk   The chunk which should be updated
     */
    @Override
    public void sendChunk(List<Player> players, org.bukkit.Chunk chunk) {
        PacketPlayOutMapChunk packetPlayOutMapChunk = new PacketPlayOutMapChunk(((CraftChunk) chunk).getHandle(), 65535);
        players.forEach(player ->
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutMapChunk)
        );
    }

    /**
     * Sends a colored world border to the specified Player with the provided size and center location.
     * The size is half of the length of one side of the border.
     *
     * @param player         The Player which should see the border
     * @param color          The color of the border
     * @param size           The size of this border, see the description above for more information
     * @param centerLocation The center of the border
     */
    @Override
    public void sendWorldBorder(Player player, Color color, double size, Location centerLocation) {
        WorldBorder worldBorder = new WorldBorder();
        worldBorder.world = ((CraftWorld) centerLocation.getWorld()).getHandle();
        worldBorder.setCenter(centerLocation.getBlockX() + 0.5, centerLocation.getBlockZ() + 0.5);

        if (color == Color.OFF) {
            worldBorder.setSize(Integer.MAX_VALUE);
        } else {
            worldBorder.setSize(size);
        }

        worldBorder.setWarningDistance(0);
        worldBorder.setWarningTime(0);

        if (color == Color.RED) {
            worldBorder.transitionSizeBetween(size, size - 1.0D, 20000000L);
        } else if (color == Color.GREEN) {
            worldBorder.transitionSizeBetween(size - 0.1D, size, 20000000L);
        }

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE));
    }

    /**
     * Sends a title with the provided properties to the Player.
     *
     * @param player The Player which should see the title
     * @param title The upper message of the title
     * @param subtitle The lower message of the title
     * @param fadeIn The amount of time this title should fade in in ticks
     * @param displayTime The amount of time this title should stay fully visible in ticks
     * @param fadeOut The amount of time this title should fade out in ticks
     */
    @Override
    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int displayTime, int fadeOut) {
        player.sendTitle(
            ChatColor.translateAlternateColorCodes('&', title),
            ChatColor.translateAlternateColorCodes('&', subtitle),
            fadeIn,
            displayTime,
            fadeOut
        );
    }

    @Override
    public double[] getTPS() {
        return MinecraftServer.getServer().recentTps;
    }

}
