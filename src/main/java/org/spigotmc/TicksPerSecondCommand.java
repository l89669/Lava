package org.spigotmc;

import net.minecraft.server.MinecraftServer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TicksPerSecondCommand extends Command {

    public TicksPerSecondCommand(String name) {
        super(name);
        this.description = "Gets the current ticks per second for the server";
        this.usageMessage = "/tps";
        setPermission("bukkit.command.tps");
    }

    public static String format(double tps) { // Paper - static
        return ((tps > 18.0) ? ChatColor.GREEN : (tps > 16.0) ? ChatColor.YELLOW : ChatColor.RED).toString() + ((tps > 20.0) ? "*" : "") + Math.min(Math.round(tps * 100.0) / 100.0, 20.0);
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) {
            return true;
        }

        StringBuilder builder = new StringBuilder(ChatColor.GOLD + "TPS from last 1m, 5m, 15m: ");
        for (double tps : MinecraftServer.getServerInstance().recentTps) {
            builder.append(format(tps));
            builder.append(", ");
        }
        sender.sendMessage(builder.substring(0, builder.length() - 2));

        return true;
    }

}
