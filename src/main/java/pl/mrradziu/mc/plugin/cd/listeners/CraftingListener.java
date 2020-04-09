package pl.mrradziu.mc.plugin.cd.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import pl.mrradziu.mc.plugin.cd.CraftingDisabler;

import java.util.List;

/**
 * Listener to handle event of crafting new item and to make decision if new item is available on the server
 */
public class CraftingListener implements Listener {

    private CraftingDisabler plugin;

    public CraftingListener(CraftingDisabler instance) {plugin = instance;}

    /**
     * Handler for crafting new item. Plugin gets information about item to craft and makes decision if new item is available or not
     * @param event
     */
    @EventHandler
    public void craftItem(PrepareItemCraftEvent event) {

        try {

            Material itemType = event.getRecipe().getResult().getType();

            List<Material> materials = plugin.getConfigHandler().getDisabledItems();

            // If crafted item is on disabled list
            if (materials.contains(itemType)) {
                event.getInventory().setResult(new ItemStack(Material.AIR));
                for (HumanEntity he : event.getViewers()) {
                    if (he instanceof Player) {

                        // Sent info to player - decision based on config file
                        if (plugin.getConfigHandler().isShowPlayerMessageEnabled()) {
                            ((Player) he).sendMessage(ChatColor.RED + "This item is not craftable! You can purchase it at the admin shop.");
                        }

                        // Sent info to Server Log
                        String playerName = ((Player) he).getDisplayName();
                        plugin.getLogger().info("Player " + playerName + " tried to craft " + itemType.name() + ".");
                    }
                }
            }
        } catch (NullPointerException e) {
            // If there is no item after crafting - do nothing
        } catch (Exception e) {
            plugin.getLogger().info(e.getMessage());
        }
    }

}
