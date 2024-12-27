package com.iridium.iridiumcore.nms;

import com.iridium.iridiumcore.Color;
import net.minecraft.server.MinecraftServer;
import org.bukkit.*;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Interface for working with the net.minecraft.server package.
 * Version-specific, so it has to be implemented for every version we support.
 * This is the implementation for v1_20_R2.
 */
public class NMS_V1_20_R3 implements NMS {

    /**
     * Deletes a block faster than with Spigots implementation.
     * See
     * https://www.spigotmc.org/threads/methods-for-changing-massive-amount-of-blocks-up-to-14m-blocks-s.395868/
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
        chunk.getWorld().refreshChunk(chunk.getX(), chunk.getZ());

        /*
         * net.minecraft.world.level.chunk.Chunk chunkLevel = ((CraftChunk)
         * chunk).getHandle();
         * ClientboundLevelChunkWithLightPacket refresh = new
         * ClientboundLevelChunkWithLightPacket(chunkLevel, chunkLevel.q.l_(), null,
         * null, true);
         * for (Player player : players) {
         * ((CraftPlayer) player).getHandle().b.a(refresh);
         * }
         */
    }

    /**
     * Sends a colored world border to the specified Player with the provided size
     * and center location.
     * The size is half of the length of one side of the border.
     *
     * @param player         The Player which should see the border
     * @param color          The color of the border
     * @param size           The size of this border, see the description above for
     *                       more information
     * @param centerLocation The center of the border
     */
    @Override
    public void sendWorldBorder(Player player, Color color, double size, Location centerLocation) {

        WorldBorder worldBorder = Bukkit.getServer().createWorldBorder();

        // WorldBorder worldBorder = new WorldBorder();
        // worldBorder.world = ((CraftWorld) centerLocation.getWorld()).getHandle();
        if(centerLocation.getWorld().getEnvironment() == Environment.NETHER)
            worldBorder.setCenter(centerLocation.getBlockX()*8 + 0.5, centerLocation.getBlockZ()*8 + 0.5);
        else
            worldBorder.setCenter(centerLocation.getBlockX() + 0.5, centerLocation.getBlockZ() + 0.5);

        
        if (color == Color.OFF) {
            worldBorder.setSize(Integer.MAX_VALUE);
        } else {
            worldBorder.setSize(size);
        }

        worldBorder.setDamageAmount(0);
        worldBorder.setDamageBuffer(0);

        if (color == Color.RED) {
            worldBorder.setSize(size - 0.1D, 20000000L);
        } else if (color == Color.GREEN) {
            worldBorder.setSize( size+0.1D, 20000000L);
        }

        player.setWorldBorder(worldBorder);
    }

    /**
     * Sends a title with the provided properties to the Player.
     *
     * @param player      The Player which should see the title
     * @param title       The upper message of the title
     * @param subtitle    The lower message of the title
     * @param fadeIn      The amount of time this title should fade in in ticks
     * @param displayTime The amount of time this title should stay fully visible in
     *                    ticks
     * @param fadeOut     The amount of time this title should fade out in ticks
     */
    @Override
    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int displayTime, int fadeOut) {
        player.sendTitle(
                ChatColor.translateAlternateColorCodes('&', title),
                ChatColor.translateAlternateColorCodes('&', subtitle),
                fadeIn,
                displayTime,
                fadeOut);
    }

    @Override
    public double[] getTPS() {
        return MinecraftServer.getServer().recentTps;
    }

}
