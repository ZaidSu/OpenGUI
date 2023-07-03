package org.zaidsu.opengui;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public final class OpenGUI extends JavaPlugin {
    private List<Gui> guis;
    private String invalidGuiMessage;
    private String reloadSuccessful;
    private String noPermission;
    private File exampleFile;
    private FileConfiguration exampleConfig;
    private Map<ItemStack, Item> itemMap;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        createExampleConfig();
        updateMessages();
        this.guis = new ArrayList<>();
        this.itemMap = new HashMap<>();
        loadGuis();
        getServer().getPluginManager().registerEvents(new GuiListener(this), this);
        this.getCommand("opengui").setExecutor(new OpenGuiCommand(this));
        this.getCommand("openguireload").setExecutor(new ReloadCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void updateMessages() {
        invalidGuiMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.invalidGui"));
        noPermission = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.noPermission"));
        reloadSuccessful = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.reloadSuccessful"));
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getReloadSuccessfulMessage() {
        return reloadSuccessful;
    }

    private void createExampleConfig() {
        exampleFile = new File(getDataFolder(), "example.yml");
        if (!exampleFile.exists()) {
            exampleFile.getParentFile().mkdirs();
            saveResource("example.yml", false);
        }

        exampleConfig = new YamlConfiguration();
        try {
            exampleConfig.load(exampleFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getExampleConfig() {
        if (exampleConfig == null) {
            createExampleConfig();
        }
        return exampleConfig;
    }

    public void loadGuis() {
        guis = new ArrayList<>();

        File dataFolder = this.getDataFolder();
        if (!dataFolder.exists() || !dataFolder.isDirectory()) {
            return;
        }

        for (File file : Objects.requireNonNull(dataFolder.listFiles())) {
            if (file.isFile() && file.getName().endsWith(".yml")) {
                Gui gui = Gui.load(file);
                if (gui != null) {
                    guis.add(gui);
                }
            }
        }
    }
    public List<Gui> getGuis() {
        return guis;
    }

    public Gui getGuiById(String id) {
        for (Gui gui : guis) {
            if (gui.getId() != null && gui.getId().equalsIgnoreCase(id)) {
                return gui;
            }
        }
        return null;
    }
}