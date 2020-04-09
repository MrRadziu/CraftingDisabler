package pl.mrradziu.mc.plugin.cd;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import pl.mrradziu.mc.plugin.cd.config.ConfigHandler;
import pl.mrradziu.mc.plugin.cd.listeners.CraftingListener;

/**
 *
 */
public class CraftingDisabler extends JavaPlugin implements Listener {

    private ConfigHandler configHandler = new ConfigHandler(this);

    /**
     * Class runs when plugin is enabled on server
     */
    @Override
    public void onEnable() {
        // Load configuration
        configHandler.loadMainConfig();

        // Register all listeners
        registerListeners();

        // Send info to Server Log
        getLogger().info("CraftingDisabler " + getDescription().getVersion() + " is enabled.");
    }

    /**
     * Class runs when plugin is disabled on server
     */
    @Override
    public void onDisable() {
        getLogger().info("Plugin is disabled.");
    }

    /**
     * Class registers all listeners in Bukkit Plugin Manager
     */
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new CraftingListener(this), this);
    }

    /**
     * Getter for Config Handler
     * @return ConfigHandler
     */
    public ConfigHandler getConfigHandler() {return configHandler;}

}
