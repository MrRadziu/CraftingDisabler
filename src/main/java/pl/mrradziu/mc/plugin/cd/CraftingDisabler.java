package pl.mrradziu.mc.plugin.cd;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mrradziu.mc.plugin.cd.config.ConfigHandler;
import pl.mrradziu.mc.plugin.cd.listeners.CraftingListener;

public class CraftingDisabler extends JavaPlugin implements Listener {

    private ConfigHandler configHandler = new ConfigHandler(this);

    @Override
    public void onEnable() {
        configHandler.loadMainConfig();

        registerListeners();

        getLogger().info("CraftingDisabler " + getDescription().getVersion() + " is enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin is disabled.");
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new CraftingListener(this), this);
    }

    public ConfigHandler getConfigHandler() {return configHandler;}

}
