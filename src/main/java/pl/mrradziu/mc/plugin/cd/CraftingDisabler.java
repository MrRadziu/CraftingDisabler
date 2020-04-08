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

public class CraftingDisabler extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("CraftingDisabler is enabled.");

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin is disabled.");
    }

    @EventHandler
    public void craftItem(PrepareItemCraftEvent event) {

        try {

            if (event.getRecipe().getResult() == null) return;

            Material itemType = event.getRecipe().getResult().getType();

            if (itemType == Material.GOLDEN_APPLE) {
                event.getInventory().setResult(new ItemStack(Material.AIR));
                for (HumanEntity he : event.getViewers()) {
                    if (he instanceof Player) {
                        ((Player) he).sendMessage(ChatColor.RED + "This item is not craftable! You can purchase it at the admin shop.");

                        String playerName = ((Player) he).getDisplayName();
                        getLogger().info("Player " + playerName + " tried to craft " + itemType.name() + ".");
                    }
                }
            }
        } catch (Exception e) {
            getLogger().severe(e.toString());
        }
    }
}
