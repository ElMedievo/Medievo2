package org.elmedievo.medievo2.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.elmedievo.medievo2.Chat.Channel;
import org.elmedievo.medievo2.Chat.Message;
import org.elmedievo.medievo2.Medievo2;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();

        Message.Text messageText = new Message.Text(event.getMessage());
        Channel channel = Medievo2.getChannelRegistry.getPlayerChannel(player);
        Message message = new Message(messageText, channel, player);
        message.send();
    }
}
