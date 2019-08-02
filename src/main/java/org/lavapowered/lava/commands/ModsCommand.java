package org.lavapowered.lava.commands;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ModsCommand extends BukkitCommand {

    public ModsCommand(String name) {
        super(name);

        this.description = "Lists all mods running on the server";
        this.usageMessage = "/mods";
        this.setPermission("lava.command.mods");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!testPermission(sender)) return true;

        sender.sendMessage("Installed Mods" + getModList());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return Collections.emptyList();
    }

    private String getModList() {
        TreeMap<String, ModContainer> mods = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (ModContainer mod : Loader.instance().getModList()) {
            mods.put(mod.getName(), mod);
        }

        StringBuilder modList = new StringBuilder();
        for (Map.Entry<String, ModContainer> entry : mods.entrySet()) {
            if (modList.length() > 0) {
                modList.append(ChatColor.GREEN);
                modList.append(", ");
            }
            modList.append(entry.getValue());
            modList.append(entry.getKey());
        }

        return "(" + mods.size() + "): " + modList.toString();
    }

}