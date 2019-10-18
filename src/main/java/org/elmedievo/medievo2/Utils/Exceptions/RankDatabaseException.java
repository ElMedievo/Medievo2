package org.elmedievo.medievo2.Utils.Exceptions;

import org.bukkit.ChatColor;

public class RankDatabaseException extends Exception {
    private String message;

    public RankDatabaseException(String message) {
        this.message = message;
    }

    public String getReason() {
        return message;
    }

    public enum Causes {
        INVALID_PLAYER("Invalid player"),
        PLAYER_NOT_FOUND("Player not found."),
        RANK_NOT_FOUND("Rank not found."),
        DUPLICATED_RANK("Player already has rank."),
        PLAYER_MISSING_RANK("Player does not have rank.");

        private String cause;

        Causes(String cause) {
            this.cause = cause;
        }

        public String getFormattedCause() {
            return ChatColor.RED + cause;
        }
    }
}
