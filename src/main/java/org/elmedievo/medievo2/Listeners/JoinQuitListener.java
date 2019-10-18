package org.elmedievo.medievo2.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.elmedievo.medievo2.Configuration;
import org.elmedievo.medievo2.Medievo2;

import java.util.List;

public class JoinQuitListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Configuration configuration = Medievo2.getConfiguration;
        String formattedJoinMessage = configuration.getJoin_format().replaceAll("%player%", player.getDisplayName());
        String colorFormattedJoinMessage = ChatColor.translateAlternateColorCodes('&', formattedJoinMessage);

        Medievo2.getChannelRegistry.registerPlayer(player);
        Medievo2.getRankDatabase.registerPlayer(player);
        Medievo2.getRankDatabase.deliverRanksToPlayer(player);

        event.setJoinMessage(colorFormattedJoinMessage);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin2(PlayerJoinEvent event) {
        Configuration configuration = Medievo2.getConfiguration;
        List<String> welcomeMessage = configuration.getWelcome_message();
        Player player = event.getPlayer();

        StringBuilder welcomeMessageBuilder = new StringBuilder();

        welcomeMessage.forEach(line -> {
            String finalLine = line
                    .replaceAll("%player%", player.getDisplayName());
            String coloredFinalLine = ChatColor.translateAlternateColorCodes('&', finalLine);
            welcomeMessageBuilder.append(coloredFinalLine).append("\n");
        });

        player.sendMessage(welcomeMessageBuilder.toString());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Configuration configuration = Medievo2.getConfiguration;
        String formattedLeaveMessage = configuration.getLeave_format().replaceAll("%player%", player.getDisplayName());
        String colorFormattedLeaveMessage = ChatColor.translateAlternateColorCodes('&', formattedLeaveMessage);

        event.setQuitMessage(colorFormattedLeaveMessage);
        Medievo2.getChannelRegistry.destroyPlayerRegistry(player);
        Medievo2.getReplyQueue.removePair(player);
    }
}
