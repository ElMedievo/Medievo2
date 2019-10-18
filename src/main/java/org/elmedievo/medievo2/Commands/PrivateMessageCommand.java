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

import static org.elmedievo.medievo2.Commands.ChatCommand.buildMessageFromCommandArgs;

public class PrivateMessageCommand {
    @Command(
            aliases = {"message", "msg", "pm"},
            desc = "Sends a private message.",
            usage = "[player] [msg]",
            min = 2
    )
    @CommandPermissions("medievo2.chat.message")
    public static void message(final CommandContext args, final CommandSender sender) throws CommandException {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Player receiver = Bukkit.getPlayer(args.getString(0));
            String[] arguments = args.getOriginalArgs();
            Message.Text text = new Message.Text(buildMessageFromCommandArgs(arguments, 2));
            if (receiver != null) {
                PrivateMessage privateMessage = new PrivateMessage(player, receiver, text);
                privateMessage.send();
                Medievo2.getReplyQueue.setReplicantPair(player, receiver);
            } else sender.sendMessage(ChatConstant.PLAYER_IS_OFFLINE.formatAsException());
        } else sender.sendMessage(ChatConstant.NO_CONSOLE.formatAsException());
    }
}
