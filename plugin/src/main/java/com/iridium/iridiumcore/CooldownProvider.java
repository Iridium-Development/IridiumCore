package com.iridium.iridiumcore;

import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Provides various cooldown features.
 *
 * @param <T> The type that can have this cooldown
 */
public class CooldownProvider<T> {

    private final Map<T, Duration> cooldownTimes = new HashMap<>();
    private final Map<UUID, Duration> cooldownTimesForPlayers = new HashMap<>();
    private final Duration duration;

    /**
     * The default constructor.
     *
     * @param duration The duration which cooldowns from this provider have.
     */
    public CooldownProvider(Duration duration) {
        this.duration = duration;
    }

    /**
     * Returns if the specified entity has a valid cooldown.
     *
     * @param t The entity which should be checked
     * @return True if the entity has a valid cooldown
     */
    public boolean isOnCooldown(T t) {
        if(t instanceof Player) {
            return cooldownTimesForPlayers.containsKey(((Player) t).getUniqueId()) && cooldownTimesForPlayers.get(((Player) t).getUniqueId()).toMillis() > System.currentTimeMillis();
        }

        return cooldownTimes.containsKey(t) && cooldownTimes.get(t).toMillis() > System.currentTimeMillis();
    }

    /**
     * Returns the remaining duration of the cooldown for the provided entity.
     * ZERO if there is no cooldown.
     *
     * @param t The entity which should be checked
     * @return The duration of the cooldown, can be ZERO
     */
    public Duration getRemainingTime(T t) {
        if (!isOnCooldown(t)) return Duration.ZERO;

        if(t instanceof Player){
            return cooldownTimesForPlayers.get(((Player) t).getUniqueId()).minusMillis(System.currentTimeMillis());
        }

        return cooldownTimes.get(t).minusMillis(System.currentTimeMillis());
    }

    /**
     * Reset the cooldown of the specified entity with the duration of this CooldownProvider.
     * {@link CooldownProvider#isOnCooldown(Object)} will return true after this.
     *
     * @param t The entity which should be checked
     */
    public void applyCooldown(T t) {
        if(t instanceof Player) {
            cooldownTimesForPlayers.put(((Player) t).getUniqueId(), duration.plusMillis(System.currentTimeMillis()));
            return;
        }

        cooldownTimes.put(t, duration.plusMillis(System.currentTimeMillis()));
    }
}
