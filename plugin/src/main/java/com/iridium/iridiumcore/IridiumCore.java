package com.iridium.iridiumcore;

import com.iridium.iridiumcore.gui.GUI;
import com.iridium.iridiumcore.multiversion.MultiVersion;
import com.iridium.iridiumcore.nms.NMS;
import io.papermc.lib.PaperLib;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.logging.Filter;

/**
 * The main class of this plugin which handles initialization
 * and shutdown of the plugin.
 */
@Getter
@NoArgsConstructor
public class IridiumCore extends JavaPlugin {

    private Persist persist;
    private NMS nms;
    private MultiVersion multiVersion;
    private boolean isTesting = false;
    private BukkitTask saveTask;

    /**
     * Constructor used for UnitTests
     */
    public IridiumCore(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
        this.isTesting = true;
        // Disable logging
        getLogger().setFilter(record -> false);
    }

    /**
     * Code that should be executed before this plugin gets enabled.
     * Initializes the configurations and sets the default world generator.
     */
    @Override
    public void onLoad() {
        // Create the data folder in order to make Jackson work
        getDataFolder().mkdir();

        // Initialize the configs
        this.persist = new Persist(Persist.PersistType.YAML, this);
        loadConfigs();
        saveConfigs();
    }

    /**
     * Plugin startup logic.
     */
    @Override
    public void onEnable() {
        if (isTesting) {
            registerListeners();
            return;
        }
        setupMultiVersion();

        if (!PaperLib.isSpigot()) {
            // isSpigot returns true if the server is using spigot or a fork
            getLogger().warning("CraftBukkit isn't supported, please use spigot or one of its forks");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Save data regularly
        saveTask = Bukkit.getScheduler().runTaskTimerAsynchronously(this, this::saveData, 0, 20 * 60 * 5);

        // Register plugin listeners
        registerListeners();

        // Automatically update all inventories
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            InventoryHolder inventoryHolder = player.getOpenInventory().getTopInventory().getHolder();
            if (inventoryHolder instanceof GUI) {
                ((GUI) inventoryHolder).addContent(player.getOpenInventory().getTopInventory());
            }
        }), 0, 1);
    }

    /**
     * Plugin shutdown logic.
     */
    @Override
    public void onDisable() {
        if (isTesting) return;
        saveTask.cancel();
        saveData();
        Bukkit.getOnlinePlayers().forEach(HumanEntity::closeInventory);
        getLogger().info("-------------------------------");
        getLogger().info("");
        getLogger().info(getDescription().getName() + " Disabled!");
        getLogger().info("");
        getLogger().info("-------------------------------");
    }

    /**
     * Registers the plugin's listeners.
     */
    public void registerListeners() {
    }

    /**
     * Saves islands, users and other data to the database.
     */
    public void saveData() {
    }

    /**
     * Automatically gets the correct {@link MultiVersion} and {@link NMS} support from the Minecraft server version.
     */
    private void setupMultiVersion() {
        try {
            String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            MinecraftVersion minecraftVersion = MinecraftVersion.byName(version);
            if (minecraftVersion == null) {
                getLogger().warning("Un-Supported Minecraft Version: " + version);
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }

            this.nms = minecraftVersion.getNms();
            this.multiVersion = minecraftVersion.getMultiVersion(this);
        } catch (Exception exception) {
            getLogger().warning("Un-Supported Minecraft Version");
        }
    }

    /**
     * Loads the configurations required for this plugin.
     *
     * @see Persist
     */
    public void loadConfigs() {
    }


    /**
     * Saves changes to the configuration files.
     *
     * @see Persist
     */
    public void saveConfigs() {
    }

}
