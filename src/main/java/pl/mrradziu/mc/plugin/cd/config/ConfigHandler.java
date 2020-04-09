package pl.mrradziu.mc.plugin.cd.config;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import pl.mrradziu.mc.plugin.cd.CraftingDisabler;
import pl.mrradziu.mc.plugin.cd.fileutil.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler to load plugin configuration from config file
 */
public class ConfigHandler {

    private CraftingDisabler plugin;
    private FileConfiguration mainConfig;

    public ConfigHandler(CraftingDisabler instance) {plugin = instance;}

    /**
     * Loading config from filr
     */
    public void loadMainConfig() {

        mainConfig = FileUtil.loadFile("config.yml", "config.yml");

        plugin.getLogger().info("Loaded config.yml");
    }

    /**
     * Method to discover if message for user about disabling specific crafting is shown
     * @return true or false
     */
    public boolean isShowPlayerMessageEnabled() {return mainConfig.getBoolean("show-player-message", false);}

    /**
     * Method to discover the full list of items player cannot craft in game
     * @return list of items (Material)
     */
    public List<Material> getDisabledItems() {

        List<String> fakeMaterials = mainConfig.getStringList("disabled-items");
        List<Material> realMaterials = new ArrayList<Material>();

        for (String fakeMaterial : fakeMaterials) {
            Material mat = Material.getMaterial(fakeMaterial.toUpperCase().trim());

            if (mat != null) {
                realMaterials.add(mat);
            }
        }

        return realMaterials;
    }

}
