package org.elmedievo.medievo2.Commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.elmedievo.medievo2.Configuration;
import org.elmedievo.medievo2.Medievo2;
import org.elmedievo.medievo2.Utils.ChatConstant;
import org.elmedievo.medievo2.Utils.MedievoWorld;

import java.util.List;

public class Coords {
    @Command(
            aliases = {"coords"},
            desc = "Prompts your current coordinates to chat.",
            flags = "p"
    )
    public static void coords(final CommandContext args, final CommandSender sender) throws CommandException {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatConstant.NO_CONSOLE.formatAsException());
            return;
        }

        Configuration configuration = Medievo2.getConfiguration;
        Player player = (Player) sender;
        List<String> coords_format = configuration.getCoords_format();

        MedievoWorld playerMedievoWorld = MedievoWorld.getPlayerMedievoWorld(player);

        StringBuilder coords_message = new StringBuilder();
        coords_format.forEach(line ->  {
            String finalLine = line
                    .replaceAll("%player%", player.getDisplayName())
                    .replaceAll("%x%", String.valueOf(player.getLocation().getBlockX()))
                    .replaceAll("%y%", String.valueOf(player.getLocation().getBlockY()))
                    .replaceAll("%z%", String.valueOf(player.getLocation().getBlockZ()))
                    .replaceAll("%world%", playerMedievoWorld.getPrefix())
                    ;
            String coloredFinalLine = ChatColor.translateAlternateColorCodes('&', finalLine);
            coords_message.append(coloredFinalLine).append("\n");
        });

        if (args.hasFlag('p')) player.sendMessage(coords_message.toString());
        else Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(coords_message.toString()));
    }
}