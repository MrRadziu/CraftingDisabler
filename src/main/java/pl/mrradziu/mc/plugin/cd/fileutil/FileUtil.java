package pl.mrradziu.mc.plugin.cd.fileutil;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class for file operations utilities
 */
public class FileUtil {

    private static JavaPlugin plugin;

    static {
        plugin = (JavaPlugin) Bukkit.getServer().getPluginManager().getPlugin("CraftingDisabler");
    }

    /**
     * Method to create config file and copy data from JAR config file to new file localized in Server Plugin Subfolder
     * @param input
     * @param target
     * @throws IOException
     */
    public static void copy(InputStream input, File target) throws IOException {

        if (target.exists()) {
            throw new IOException("File already exists!");
        }

        File parentDir = target.getParentFile();

        if (!parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Failed at creating directories!");
            }
        }

        if (!parentDir.isDirectory()) {
            throw new IOException("The parent of this file is no direcotry!");
        }

        if (!target.createNewFile()) {
            throw new IOException("Failed at creating new empty file!");
        }

        if (input == null) {
            throw new NullPointerException("Input is null!");
        }

        byte[] buffer = new byte[1024];

        OutputStream output = new FileOutputStream(target);

        int realLength;

        while (input != null && (realLength = input.read(buffer)) > 0) {
            output.write(buffer, 0 , realLength);
        }

        output.flush();
        output.close();
    }

    /**
     * Method to get input from JAR file
     * @param path
     * @return
     * @throws IOException
     */
    public static InputStream getInputFromJar(String path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("The path cannot be null");
        }

        URL url = plugin.getClass().getClassLoader().getResource(path);

        if (url == null) {
            return null;
        }

        URLConnection connection = url.openConnection();
        connection.setUseCaches(false);
        return connection.getInputStream();
    }

    /**
     * Method to load plugin configuration from Yaml file
     * @param pathTo
     * @param internalPath
     * @return
     */
    public static YamlConfiguration loadFile(String pathTo, String internalPath) {
        File conf = new File(plugin.getDataFolder() + File.separator + pathTo);

        if (!conf.exists()) {

            try {
                InputStream stream = getInputFromJar(internalPath);
                copy(stream, conf);
            } catch (IOException e) {
                e.printStackTrace();
            }

            plugin.getLogger().info("Creating " + pathTo + " for the first time...");
        }

        return YamlConfiguration.loadConfiguration(conf);
    }
}
