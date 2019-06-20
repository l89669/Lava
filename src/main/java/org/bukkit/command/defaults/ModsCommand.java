package org.bukkit.command.defaults;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class ModsCommand extends BukkitCommand {
    public ModsCommand(String name) {
        super(name);

        this.description = "List all mods running on the server";
        this.usageMessage = "/mods";
        this.setPermission("bukkit.command.mods");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Map<String, ModContainer> mods = Loader.instance().getIndexedModList();
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.BLUE).append("Installed Mods (").append(mods.size()).append("): ").append(ChatColor.WHITE);
        for (String m : mods.keySet()) {
            sb.append(m);
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("debug"))
                    sb.append(" (").append(ChatColor.BLUE).append(mods.get(m).getModId()).append(ChatColor.WHITE).append(")");
            }
            sb.append(",");
        }
        sender.sendMessage(sb.toString());

        return true;
    }
}
