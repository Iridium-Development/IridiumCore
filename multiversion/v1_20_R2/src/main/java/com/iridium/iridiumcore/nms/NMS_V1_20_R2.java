package com.iridium.iridiumcore.nms;

import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * Interface for working with the net.minecraft.server package.
 * Version-specific, so it has to be implemented for every version we support.
 * This is the implementation for v1_20_R2.
 */
public class NMS_V1_20_R2 extends NMSDefault {

    @Override
    public void sendHologram(Player player, Location location, List<String> text) {
        CraftWorld craftWorld = (CraftWorld) location.getWorld();
        for (int i = -1; ++i < text.size(); ) {
            EntityArmorStand entityArmorStand = new EntityArmorStand(craftWorld.getHandle(), location.getX(), location.getY(), location.getZ());

            entityArmorStand.j(true);
            entityArmorStand.n(true);
            entityArmorStand.a(text.get(i));

            PacketPlayOutSpawnEntity packetPlayOutSpawnEntityLiving = new PacketPlayOutSpawnEntity(entityArmorStand);
            PacketPlayOutEntityMetadata packetPlayOutEntityMetadata = new PacketPlayOutEntityMetadata(entityArmorStand.ah(), Collections.emptyList());
            ((CraftPlayer) player).getHandle().c.a(packetPlayOutSpawnEntityLiving);
            ((CraftPlayer) player).getHandle().c.a(packetPlayOutEntityMetadata);
            location = location.subtract(0, 0.4, 0);
        }
    }

    @Override
    public double[] getTPS() {
        return MinecraftServer.getServer().recentTps;
    }

}
