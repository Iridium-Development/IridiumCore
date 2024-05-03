package com.iridium.iridiumcore.nms;

import com.iridium.iridiumcore.Color;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.level.border.WorldBorder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.CraftChunk;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Interface for working with the net.minecraft.server package.
 * Version-specific, so it has to be implemented for every version we support.
 * This is the implementation for v1_18_R2.
 */
public class NMS_V1_18_R2 implements NMS {

    /**
     * Deletes a block faster than with Spigots implementation.
     * See https://www.spigotmc.org/threads/methods-for-changing-massive-amount-of-blocks-up-to-14m-blocks-s.395868/
     * for more information.
     *
     * @param location The location of the block which should be deleted
     */
    @Override
    public void deleteBlockFast(Location location) {
        location.getBlock().setType(Material.AIR, false);
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
        net.minecraft.world.level.chunk.Chunk chunkLevel = ((CraftChunk) chunk).getHandle();
        ClientboundLevelChunkWithLightPacket refresh = new ClientboundLevelChunkWithLightPacket(chunkLevel, chunkLevel.q.l_(), null, null, true);
        for (Player player : players) {
            ((CraftPlayer) player).getHandle().b.a(refresh);
        }
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
        worldBorder.c(centerLocation.getBlockX() + 0.5, centerLocation.getBlockZ() + 0.5);

        if (color == Color.OFF) {
            worldBorder.a(Integer.MAX_VALUE);
        } else {
            worldBorder.a(size);
        }

        worldBorder.b(0);
        worldBorder.c(0);

        if (color == Color.RED) {
            worldBorder.a(size, size - 1.0D, 20000000L);
        } else if (color == Color.GREEN) {
            worldBorder.a(size - 0.1D, size, 20000000L);
        }

        ((CraftPlayer) player).getHandle().b.a(new ClientboundInitializeBorderPacket(worldBorder));
    }

    /**
     * Sends a title with the provided properties to the Player.
     *
     * @param player      The Player which should see the title
     * @param title       The upper message of the title
     * @param subtitle    The lower message of the title
     * @param fadeIn      The amount of time this title should fade in in ticks
     * @param displayTime The amount of time this title should stay fully visible in ticks
     * @param fadeOut     The amount of time this title should fade out in ticks
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
    public void sendHologram(Player player, Location location, List<String> text) {
        CraftWorld craftWorld = (CraftWorld) location.getWorld();
        for (int i = -1; ++i < text.size(); ) {
            EntityArmorStand entityArmorStand = new EntityArmorStand(craftWorld.getHandle(), location.getX(), location.getY(), location.getZ());

            entityArmorStand.j(true);
            entityArmorStand.n(true);
            entityArmorStand.a(new ChatMessage(text.get(i)));

            PacketPlayOutSpawnEntityLiving packetPlayOutSpawnEntityLiving = new PacketPlayOutSpawnEntityLiving(entityArmorStand);
            PacketPlayOutEntityMetadata packetPlayOutEntityMetadata = new PacketPlayOutEntityMetadata(entityArmorStand.ae(), entityArmorStand.ai(), true);
            ((CraftPlayer) player).getHandle().b.a(packetPlayOutSpawnEntityLiving);
            ((CraftPlayer) player).getHandle().b.a(packetPlayOutEntityMetadata);
            location = location.subtract(0, 0.4, 0);
        }
    }

    @Override
    public double[] getTPS() {
        return MinecraftServer.getServer().recentTps;
    }

}
