package org.elmedievo.medievo2.Commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.NestedCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.elmedievo.medievo2.Medievo2;
import org.elmedievo.medievo2.Utils.ChatConstant;

public class RanksCommand {
    @Command(
            aliases = {"reload"},
            desc = "Reloads rank models."
    )
    @CommandPermissions("medievo2.ranks.reload")
    public static void reload(final CommandContext args, final CommandSender sender) throws CommandException {
        Medievo2.getRankRegistry.reload();
        Medievo2.getRankDatabase.reload();
        sender.sendMessage(ChatConstant.RANKS_RELOADED.formatAsSuccess());
    }

    @Command(
            aliases = {"list"},
            desc = "Ranks node command."
    )
    @CommandPermissions("medievo2.ranks.list")
    public static void list(final CommandContext args, final CommandSender sender) throws CommandException {
        StringBuilder rankListBuilder = new StringBuilder();
        String rankListHeader = ChatColor.GRAY + "Server Ranks:" + "\n";

        rankListBuilder.append(rankListHeader);
        Medievo2.getRankRegistry.getRanks().forEach(rank -> rankListBuilder
                .append(ChatColor.WHITE).append("» ").append(rank.getFlair()).append(ChatColor.GRAY).append(rank.getName())
                .append("\n")
        );

        String rankList = ChatColor.translateAlternateColorCodes('$', rankListBuilder.toString());
        sender.sendMessage(rankList);
    }

    public static class RanksParentCommand {
        @Command(
                aliases = {"ranks"},
                desc = "Ranks node command."
        )
        @CommandPermissions("medievo2.ranks")
        @NestedCommand(RanksCommand.class)
        public static void ranks(final CommandContext args, final CommandSender sender) throws CommandException {
        }
    }
}
