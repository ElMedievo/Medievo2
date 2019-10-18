package org.elmedievo.medievo2.Commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.elmedievo.medievo2.Chat.Message;
import org.elmedievo.medievo2.Chat.PrivateMessage;
import org.elmedievo.medievo2.Medievo2;
import org.elmedievo.medievo2.Utils.ChatConstant;

import java.util.UUID;

import static org.elmedievo.medievo2.Commands.ChatCommand.buildMessageFromCommandArgs;

public class ReplyCommand {
    @Command(
            aliases = {"reply", "r"},
            desc = "Replies to your last message receiver",
            usage = "[player] [msg]"
    )
    @CommandPermissions("medievo2.chat.reply")
    public static void reply(final CommandContext args, final CommandSender sender) throws CommandException {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String receiverUUID = Medievo2.getReplyQueue.getPlayerReplier(player);
            if (Medievo2.getReplyQueue.getPlayerReplier(player) != null) {
                Player receiver = Bukkit.getPlayer(UUID.fromString(receiverUUID));
                assert receiver != null;
                if (receiver.isOnline()) {
                    String[] arguments = args.getOriginalArgs();
                    Message.Text text = new Message.Text(buildMessageFromCommandArgs(arguments, 1));
                    PrivateMessage pm = new PrivateMessage(player, receiver.getPlayer(), text);
                    pm.send();
                } else {
                    Medievo2.getReplyQueue.removePair(player);
                    throw new CommandException(ChatConstant.PLAYER_IS_OFFLINE.formatAsException());
                }
            } else throw new CommandException(ChatConstant.NOBODY_TO_REPLY_TO.formatAsException());
        } else throw new CommandException(ChatConstant.NO_CONSOLE.formatAsException());
    }
}
