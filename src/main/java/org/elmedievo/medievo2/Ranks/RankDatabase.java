package org.elmedievo.medievo2.Ranks;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.elmedievo.medievo2.Medievo2;
import org.elmedievo.medievo2.Utils.Exceptions.RankDatabaseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class RankDatabase {
    private File ranksYML = new File(Medievo2.getPlugin.getDataFolder(), "ranks.yml");
    private FileConfiguration ranksYMLConfiguration = YamlConfiguration.loadConfiguration(ranksYML);
    private HashMap<String, List<String>> playerRanks = new HashMap<>();

    public RankDatabase() {
    }

    public void load() {
        if (!ranksYML.exists()) {
            try {
                boolean success = ranksYML.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        loadPlayerRanks();
    }

    public void save() {
        try {
            ranksYMLConfiguration.save(ranksYML);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void reload() {
        playerRanks.clear();
        loadPlayerRanks();
    }

    private void loadPlayerRanks() {
        ranksYMLConfiguration.getKeys(false).forEach(uuid -> {
            List<String> ranks = ranksYMLConfiguration.getStringList(uuid);
            playerRanks.put(uuid, ranks);
        });
    }

    public boolean playerIsRegistered(Player player) {
        UUID uuid = player.getUniqueId();
        AtomicBoolean exists = new AtomicBoolean(false);

        ranksYMLConfiguration.getKeys(false).forEach(key -> {
            if (key.equals(uuid.toString())) exists.set(true);
        });

        return exists.get();
    }

    public void registerPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        if (playerIsRegistered(player)) return;

        // TODO: Make the default rank configurable
        List<String> defaultRanks = new ArrayList<>();
        defaultRanks.add("Default");
        ranksYMLConfiguration.set(uuid.toString(), defaultRanks);

        save();
        reload();
    }

    public List<String> getPlayerRanks(Player player) {
        UUID uuid = player.getUniqueId();
        return playerRanks.get(uuid.toString());
    }

    public boolean playerHasRank(Player player, String rankName) {
        UUID uuid = player.getUniqueId();
        AtomicBoolean hasRank = new AtomicBoolean(false);

        playerRanks.get(uuid.toString()).forEach(rank -> {
            if (rank.equalsIgnoreCase(rankName)) hasRank.set(true);
        });

        return hasRank.get();
    }

    public void addPlayerRank(Player player, String rankName) throws RankDatabaseException {
        UUID uuid = player.getUniqueId();

        if (!playerIsRegistered(player)) throw new RankDatabaseException(RankDatabaseException.Causes.PLAYER_NOT_FOUND.getFormattedCause());
        if (!Medievo2.getRankRegistry.containsRank(rankName)) throw new RankDatabaseException(RankDatabaseException.Causes.RANK_NOT_FOUND.getFormattedCause());
        if (playerHasRank(player, rankName)) throw new RankDatabaseException(RankDatabaseException.Causes.DUPLICATED_RANK.getFormattedCause());

        List<String> currentRanks = playerRanks.get(uuid.toString());
        currentRanks.add(rankName);
        ranksYMLConfiguration.set(uuid.toString(), currentRanks);

        save();
        reload();
    }

    public void removePlayerRank(Player player, String rankName) throws RankDatabaseException {
        UUID uuid = player.getUniqueId();

        if (!playerIsRegistered(player)) throw new RankDatabaseException(RankDatabaseException.Causes.PLAYER_NOT_FOUND.getFormattedCause());
        if (!Medievo2.getRankRegistry.containsRank(rankName)) throw new RankDatabaseException(RankDatabaseException.Causes.RANK_NOT_FOUND.getFormattedCause());
        if (!playerHasRank(player, rankName)) throw new RankDatabaseException(RankDatabaseException.Causes.PLAYER_MISSING_RANK.getFormattedCause());

        List<String> currentRanks = ranksYMLConfiguration.getStringList(uuid.toString());
        currentRanks.remove(rankName);
        ranksYMLConfiguration.set(uuid.toString(), currentRanks);

        save();
        reload();
    }

    public void deliverRanksToPlayer(Player player) {
        String[] flairsByPriorityArray = new String[100000];
        List<String> playerRanksList = getPlayerRanks(player);
        StringBuilder flairBuilder = new StringBuilder();

        Medievo2.getRankRegistry.getRanks().forEach(rank -> {
            if (playerRanksList.contains(rank.getName())) {
                flairsByPriorityArray[rank.getPriority()] = rank.getFlair();

                rank.getPermissions().forEach(permission -> player.addAttachment(Medievo2.getPlugin, permission, true));
            }
        });

        for (String flairInstance : flairsByPriorityArray) {
            if (flairInstance != null) {
                String coloredFlair = ChatColor.translateAlternateColorCodes('$', flairInstance);
                flairBuilder.append(coloredFlair);
            }
        }

        String finalFlair = flairBuilder.toString();
        player.setDisplayName(finalFlair + player.getName());
        player.setPlayerListName(finalFlair + player.getName());
    }
}
