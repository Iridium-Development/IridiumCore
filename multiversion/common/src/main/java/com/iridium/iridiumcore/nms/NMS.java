package com.iridium.iridiumcore.nms;

import com.iridium.iridiumcore.Color;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Interface for working with the net.minecraft.server package.
 * Version-specific, so it has to be implemented for every version we support.
 */
public interface NMS {

  void deleteBlockFast(Location location);

  /**
   * Sends the provided chunk to all the specified players.
   * Used for updating chunks.
   *
   * @param players The player which should see the updated chunk
   * @param chunk   The chunk which should be updated
   */
  void sendChunk(List<Player> players, Chunk chunk);

  /**
   * Sends a colored world border to the specified Player with the provided size and center location.
   * The size is half of the length of one side of the border.
   *
   * @param player         The Player which should see the border
   * @param color          The color of the border
   * @param size           The size of this border, see the description above for more information
   * @param centerLocation The center of the border
   */
  void sendWorldBorder(Player player, Color color, double size, Location centerLocation);

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
  void sendTitle(Player player, String title, String subtitle, int fadeIn, int displayTime, int fadeOut);

  /**
   * @return The server's recent tps
   */
  double[] getTPS();

}
