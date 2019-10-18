package org.elmedievo.medievo2.Chat;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.elmedievo.medievo2.Medievo2;
import org.elmedievo.medievo2.Utils.Permission;

import java.util.HashMap;

public class PrivateMessage {
    private Player sender;
    private Player receiver;
    private Message.Text text;
    private Sound pmSound = Medievo2.getConfiguration.getPmSound();
    private int v = Medievo2.getConfiguration.getPmSoundV();
    private int v1 = Medievo2.getConfiguration.getPmSoundV1();
    private String prefix = Medievo2.getConfiguration.getPm_prefix();
    private String pmToFormat = Medievo2.getConfiguration.getPm_to_format();
    private String pmFromFormat = Medievo2.getConfiguration.getPm_from_format();

    public PrivateMessage(Player sender, Player receiver, Message.Text text) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public void setReceiver(Player receiver) {
        this.receiver = receiver;
    }

    public Message.Text getText() {
        return text;
    }

    public void setText(Message.Text text) {
        this.text = text;
    }

    public void send() {
        if (sender.hasPermission(Permission.CHAT_COLOR.getNode())) {
            sender.sendMessage(parsePMToFormat().replaceAll("%msg%", text.getColoredText()));
            receiver.sendMessage(parsePMFromFormat().replaceAll("%msg%", text.getColoredText()));
        } else {
            sender.sendMessage(parsePMToFormat().replaceAll("%msg%", text.getText()));
            receiver.sendMessage(parsePMFromFormat().replaceAll("%msg%", text.getText()));
        }
        receiver.playSound(receiver.getLocation(), pmSound, v, v1);
    }

    private String parsePMToFormat() {
        return ChatColor.translateAlternateColorCodes('&', pmToFormat
                .replaceAll("%pm_prefix%", prefix)
                .replaceAll("%player%", receiver.getDisplayName())
        );
    }

    private String parsePMFromFormat() {
        return ChatColor.translateAlternateColorCodes('&', pmFromFormat
                .replaceAll("%pm_prefix%", prefix)
                .replaceAll("%player%", sender.getDisplayName())
        );
    }

    public static class ReplyQueue {
        HashMap<String, String> queue;

        public ReplyQueue(HashMap<String, String> queue) {
            this.queue = queue;
        }

        public HashMap<String, String> getReplyQueue() {
            return queue;
        }

        public void setReplyQueue(HashMap<String, String> replyQueue) {
            this.queue = replyQueue;
        }

        public String getPlayerReplier(Player sender) {
            return queue.get(sender.getUniqueId().toString());
        }

        public void setReplicantPair(Player sender, Player replyPlayer) {
            queue.put(sender.getUniqueId().toString(), replyPlayer.getUniqueId().toString());
            queue.put(replyPlayer.getUniqueId().toString(), sender.getUniqueId().toString());
        }

        public void removePair(Player sender) {
            queue.remove(sender.getUniqueId().toString());
        }
    }
}
