package org.elmedievo.medievo2.Commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.NestedCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.elmedievo.medievo2.Medievo2;
import org.elmedievo.medievo2.Utils.ChatConstant;
import org.elmedievo.medievo2.Utils.Exceptions.RankDatabaseException;

public class RankCommand {
    @Command(
            aliases = {"add"},
            desc = "Adds a rank to a player.",
            usage = "[player] [rank]",
            min = 2,
            max = 2
    )
    @CommandPermissions("medievo2.rank.add")
    @SuppressWarnings("deprecation")
    public static void add(final CommandContext args, final CommandSender sender) throws CommandException, RankDatabaseException {
        String playerName = args.getString(0);
        String rankName = args.getString(1);
        String senderName;

        if (sender instanceof Player) {
            Player senderPlayer = (Player) sender;
            senderName = senderPlayer.getDisplayName();
        } else senderName = ChatConstant.CONSOLE_PREFIX.getMessage();

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
        Player player = offlinePlayer.getPlayer();

        if (player == null) {
            sender.sendMessage(ChatConstant.INVALID_PLAYER.formatAsException());
            return;
        }

        Medievo2.getRankDatabase.addPlayerRank(player, rankName);

        sender.sendMessage(ChatConstant.RANK_GIVEN.formatAsSuccess() + player.getDisplayName());
        if (player.isOnline()) player.sendMessage(ChatConstant.RANK_RECEIVED.formatAsSuccess() + senderName);
    }

    @Command(
            aliases = {"remove"},
            desc = "Removes a rank from player.",
            usage = "[player] [rank]",
            min = 2,
            max = 2
    )
    @CommandPermissions("medievo2.rank.remove")
    @SuppressWarnings("deprecation")
    public static void remove(final CommandContext args, final CommandSender sender) throws CommandException, RankDatabaseException {
        String playerName = args.getString(0);
        String rankName = args.getString(1);
        String senderName;

        if (sender instanceof Player) {
            Player senderPlayer = (Player) sender;
            senderName = senderPlayer.getDisplayName();
        } else senderName = ChatConstant.CONSOLE_PREFIX.getMessage();

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
        Player player = offlinePlayer.getPlayer();
        if (player == null) {
            sender.sendMessage(ChatConstant.INVALID_PLAYER.formatAsException());
            return;
        }

        Medievo2.getRankDatabase.removePlayerRank(player, rankName);

        sender.sendMessage(ChatColor.RED + ChatConstant.RANK_REMOVED.getMessage() + player.getDisplayName());
        if (player.isOnline()) player.sendMessage(ChatColor.RED + ChatConstant.RANK_TAKEN.getMessage() + senderName);
    }

    @Command(
            aliases = {"list"},
            desc = "Lists all existing ranks."
    )
    @CommandPermissions("medievo2.rank.list")
    public static void list(final CommandContext args, final CommandSender sender) throws CommandException {
        StringBuilder rankListBuilder = new StringBuilder();
        String rankListHeader = ChatColor.GRAY + "Server Ranks:" + "\n";

        rankListBuilder.append(rankListHeader);
        Medievo2.getRankRegistry.getRanks().forEach(rank -> rankListBuilder
                .append(ChatColor.WHITE).append("» ").append(rank.getFlair()).append(" ").append(ChatColor.GRAY).append(rank.getName())
                .append("\n")
        );

        String rankList = ChatColor.translateAlternateColorCodes('$', rankListBuilder.toString());
        sender.sendMessage(rankList);
    }

    public static class RankParentCommand {
        @Command(
                aliases = {"rank"},
                desc = "Ranks node command."
        )
        @CommandPermissions("medievo2.rank")
        @NestedCommand(RankCommand.class)
        public static void rank(final CommandContext args, final CommandSender sender) throws CommandException {
        }
    }
}
