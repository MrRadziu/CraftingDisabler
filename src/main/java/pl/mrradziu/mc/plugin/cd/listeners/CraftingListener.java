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

import java.util.ArrayList;
import java.util.List;

public class CraftingListener implements Listener {

    private CraftingDisabler plugin;

    public CraftingListener(CraftingDisabler instance) {plugin = instance;}

    @EventHandler
    public void craftItem(PrepareItemCraftEvent event) {

        try {

            Material itemType = event.getRecipe().getResult().getType();

            List<Material> materials = plugin.getConfigHandler().getDisabledItems();

            if (materials.contains(itemType)) {
                event.getInventory().setResult(new ItemStack(Material.AIR));
                for (HumanEntity he : event.getViewers()) {
                    if (he instanceof Player) {
                        if (plugin.getConfigHandler().isShowPlayerMessageEnabled()) {
                            ((Player) he).sendMessage(ChatColor.RED + "This item is not craftable! You can purchase it at the admin shop.");
                        }

                        String playerName = ((Player) he).getDisplayName();
                        plugin.getLogger().info("Player " + playerName + " tried to craft " + itemType.name() + ".");
                    }
                }
            }
        } catch (NullPointerException e) {

        } catch (Exception e) {
            plugin.getLogger().info(e.getMessage());
        }
    }

}
