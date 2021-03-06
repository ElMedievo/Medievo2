package org.elmedievo.medievo2.Commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.NestedCommand;
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
