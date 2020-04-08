package pl.mrradziu.mc.plugin.cd.config;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import pl.mrradziu.mc.plugin.cd.CraftingDisabler;
import pl.mrradziu.mc.plugin.cd.fileutil.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {

    private CraftingDisabler plugin;
    private FileConfiguration mainConfig;

    public ConfigHandler(CraftingDisabler instance) {plugin = instance;}

    public void loadMainConfig() {

        mainConfig = FileUtil.loadFile("config.yml", "config.yml");

        plugin.getLogger().info("Loaded config.yml");
    }

    public boolean isShowPlayerMessageEnabled() {return mainConfig.getBoolean("show-player-message", false);}

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
