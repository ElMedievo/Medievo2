package org.elmedievo.medievo2.Utils;

import org.bukkit.ChatColor;

public enum ChatConstant {
    CONSOLE_PREFIX(ChatColor.GOLD + "❖" + ChatColor.DARK_AQUA + "Console" + ChatColor.RESET),
    NO_PERMISSION("You do not have permission."),
    NUMBER_STRING_EXCEPTION("Expected a number, string received instead."),
    UNKNOWN_ERROR("An unknown error has occurred."),
    NO_CONSOLE("You must be a player to execute this command."),
    CHANNEL_ALREADY_ADMIN("Chat mode already set to ADMIN."),
    CHANNEL_ALREADY_GLOBAL("Chat mode already set to GLOBAL."),
    PLAYER_IS_OFFLINE("Player is currently offline."),
    NOBODY_TO_REPLY_TO("You have nobody to reply to."),
    CHANNEL_SET_ADMIN("Chat mode now set to admin."),
    CHANNEL_SET_GLOBAL("Chat mode now set to global."),
    INVALID_PLAYER("Player not found."),
    RANK_GIVEN("Rank successfully added to "),
    RANK_REMOVED("Rank successfully removed."),
    RANK_RECEIVED("You have been promoted by "),
    RANK_TAKEN("You have been demoted by ");

    private String message;

    ChatConstant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String formatAsSuccess() {
        return ChatColor.GREEN + message;
    }

    public String formatAsException() {
        return ChatColor.YELLOW + "⚠ " + ChatColor.RED + message;
    }
}
