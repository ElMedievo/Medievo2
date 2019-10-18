package org.elmedievo.medievo2.Commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.elmedievo.medievo2.Chat.Channel;
import org.elmedievo.medievo2.Chat.Message;
import org.elmedievo.medievo2.Utils.ChatConstant;

import static org.elmedievo.medievo2.Commands.ChatCommand.buildMessageFromCommandArgs;

public class BroadcastCommand {
    @Command(
            aliases = {"broadcast", "br"},
            desc = "Broadcasts a message to all players.",
            usage = "[msg]",
            min = 1
    )
    @CommandPermissions("medievo2.chat.broadcast")
    public static void broadcast(final CommandContext args, final CommandSender sender) throws CommandException {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String[] arguments = args.getOriginalArgs();
            Message.Text text = new Message.Text(buildMessageFromCommandArgs(arguments, 1));
            Message msg = new Message(text, Channel.GLOBAL, player);
            msg.broadcast();
        } else sender.sendMessage(ChatConstant.NO_CONSOLE.formatAsException());
    }
}
