package org.elmedievo.medievo2.Chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.elmedievo.medievo2.Medievo2;
import org.elmedievo.medievo2.Utils.Permission;

public class Message {
    private Channel channel;
    private Text text;
    private Player sender;

    public Message(Text text, Channel channel, Player sender) {
        this.text = text;
        this.channel = channel;
        this.sender = sender;
    }

    public void send() {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (onlinePlayer.hasPermission(channel.getPermissionParent().getNode())) {
                if (sender.hasPermission(Permission.CHAT_COLOR.getNode())) onlinePlayer.sendMessage(buildColoredMessage());
                else onlinePlayer.sendMessage(buildRawMessage());
            }
        });
    }

    public void broadcast() {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Medievo2.getConfiguration.getBroadcast_format() + text.getColoredText()));
    }

    private String buildColoredMessage() {
        return parseChatFormat().replaceAll("%msg%", text.getColoredText());
    }

    private String buildRawMessage() {
        return parseChatFormat().replaceAll("%msg%", text.getText());
    }

    private String parseChatFormat() {
        return ChatColor.translateAlternateColorCodes('&', Medievo2.getConfiguration.getChat_format()
                .replaceAll("%channel_prefix%", channel.getPrefix())
                .replaceAll("%player%", sender.getDisplayName()
                )
        );
    }

    public static class Text {
        private char colorChar = '&';
        private String text;

        public Text(String text) {
            this.text = text;
        }

        String getText() {
            return text;
        }

        public void setColorChar(char colorChar) {
            this.colorChar = colorChar;
        }

        String getColoredText() {
            return ChatColor.translateAlternateColorCodes(colorChar, text);
        }
    }
}
