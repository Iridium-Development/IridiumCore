package com.iridium.iridiumcore.multiversion;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for working with methods that were changed during an update by Spigot.
 */
public interface MultiVersion {

    /**
     * Returns the material at a position in a chunk.
     *
     * @param chunk The snapshot of the chunk where the position is in
     * @param x     The relative x position of the block in the chunk
     * @param y     The relative y position of the block in the chunk
     * @param z     The relative z position of the block in the chunk
     * @return The material at the provided position in the chunk
     */
    XMaterial getMaterialAtPosition(ChunkSnapshot chunk, int x, int y, int z);

    /**
     * Returns if a player can pass through a block
     *
     * @param block The specified block
     * @return if a player cna pass through the block or not
     */
    boolean isPassable(Block block);

    /**
     * Gets the chunk at this location
     *
     * @param world The world the chunk is in
     * @param x     The chunk's x coord
     * @param z     The chunk's z coord
     * @return The chunk
     */
    CompletableFuture<Chunk> getChunkAt(World world, int x, int z);

    /**
     * Gets the chunk at this location
     *
     * @param location The location of the chunk
     * @return The chunk
     */
    CompletableFuture<Chunk> getChunkAt(Location location);

}
