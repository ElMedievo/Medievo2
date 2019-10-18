package org.elmedievo.medievo2.Commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.elmedievo.medievo2.Chat.Channel;
import org.elmedievo.medievo2.Chat.Message;
import org.elmedievo.medievo2.Medievo2;
import org.elmedievo.medievo2.Utils.ChatConstant;

public class ChatCommand {
    static String buildMessageFromCommandArgs(String[] args, int beginAtArgument) {
        StringBuilder builder = new StringBuilder();
        for (int i = beginAtArgument; i < args.length; i++) {
            builder.append(args[i]).append(" ");
        }
        return builder.toString();
    }

    @Command(
            aliases = {"admin", "a"},
            desc = "Sets player's chat to admin mode."
    )
    @CommandPermissions("medievo2.chat.admin")
    public static void admin(final CommandContext args, final CommandSender sender) throws CommandException {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.argsLength() == 0) {
                if (Medievo2.getChannelRegistry.getPlayerChannel(player) != Channel.ADMIN) {
                    Medievo2.getChannelRegistry.setPlayerChannel(player, Channel.ADMIN);
                    player.sendMessage(ChatConstant.CHANNEL_SET_ADMIN.formatAsSuccess());
                } sender.sendMessage(ChatConstant.CHANNEL_ALREADY_ADMIN.formatAsException());
            } else {
                String[] arguments = args.getOriginalArgs();
                Message.Text text = new Message.Text(buildMessageFromCommandArgs(arguments, 1));
                Message msg = new Message(text, Channel.ADMIN, player);
                msg.send();
            }
        } else sender.sendMessage(ChatConstant.NO_CONSOLE.formatAsException());
    }

    @Command(
            aliases = {"global", "g"},
            desc = "Sets player's chat to global mode."
    )
    @CommandPermissions("medievo2.chat.global")
    public static void global(final CommandContext args, final CommandSender sender) throws CommandException {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.argsLength() == 0) {
                if (Medievo2.getChannelRegistry.getPlayerChannel(player) != Channel.GLOBAL) {
                    Medievo2.getChannelRegistry.setPlayerChannel(player, Channel.GLOBAL);
                    player.sendMessage(ChatConstant.CHANNEL_SET_GLOBAL.formatAsSuccess());
                } else sender.sendMessage(ChatConstant.CHANNEL_ALREADY_GLOBAL.formatAsException());
            } else {
                String[] arguments = args.getOriginalArgs();
                Message.Text text = new Message.Text(buildMessageFromCommandArgs(arguments, 1));
                Message msg = new Message(text, Channel.GLOBAL, player);
                msg.send();
            }
        } else sender.sendMessage(ChatConstant.NO_CONSOLE.formatAsException());
    }
}
