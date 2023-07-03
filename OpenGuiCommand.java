package org.zaidsu.opengui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenGuiCommand implements CommandExecutor {
    private final OpenGUI plugin;

    public OpenGuiCommand(OpenGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Usage: /opengui [id]");
            return true;
        }

        String id = args[0];
        Gui gui = plugin.getGuiById(id);

        if (gui == null) {
            player.sendMessage("Invalid GUI ID.");
            return true;
        }

        gui.openGui(player);
        return true;
    }
}
