package com.iridium.iridiumcore.multiversion;

import com.cryptomorin.xseries.XMaterial;
import io.papermc.lib.PaperLib;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for working with methods that were changed during an update by Spigot.
 */
public class MultiVersion_V1_20_R3 extends MultiVersion {

    public MultiVersion_V1_20_R3(JavaPlugin javaPlugin) {
        super(javaPlugin);
    }

    /**
     * Returns the material at a position in a chunk.
     *
     * @param chunk The snapshot of the chunk where the position is in
     * @param x     The relative x position of the block in the chunk
     * @param y     The relative y position of the block in the chunk
     * @param z     The relative z position of the block in the chunk
     * @return The material at the provided position in the chunk
     */
    @Override
    public XMaterial getMaterialAtPosition(ChunkSnapshot chunk, int x, int y, int z) {
        return XMaterial.matchXMaterial(chunk.getBlockType(x, y, z));
    }

    @Override
    public boolean isPassable(Block block) {
        return block.isPassable();
    }

    @Override
    public CompletableFuture<Chunk> getChunkAt(World world, int x, int z) {
        return PaperLib.getChunkAtAsync(world, x, z, true);
    }

}
