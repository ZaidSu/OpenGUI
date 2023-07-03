package org.zaidsu.opengui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class ReloadCommand implements CommandExecutor {
    private final OpenGUI plugin;

    public ReloadCommand(OpenGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("openguireload")) {
            if (sender.hasPermission("opengui.reload")) {
                plugin.reloadConfig();
                plugin.updateMessages();
                plugin.loadGuis();

                FileConfiguration exampleConfig = plugin.getExampleConfig();
                File exampleFile = new File(plugin.getDataFolder(), "example.yml");
                try {
                    exampleConfig.load(exampleFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                sender.sendMessage(plugin.getReloadSuccessfulMessage());
            } else {
                sender.sendMessage(plugin.getNoPermission());
            }
        }
        return true;
    }
}