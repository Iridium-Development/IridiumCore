package com.iridium.iridiumcore.nms;

import net.minecraft.server.MinecraftServer;

/**
 * Interface for working with the net.minecraft.server package.
 * Version-specific, so it has to be implemented for every version we support.
 * This is the implementation for v1_19_R1.
 */
public class NMS_V1_19_R1 extends NMSDefault {

    @Override
    public double[] getTPS() {
        return MinecraftServer.getServer().recentTps;
    }

}

