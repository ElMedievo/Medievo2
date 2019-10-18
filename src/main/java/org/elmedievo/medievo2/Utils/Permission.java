package org.elmedievo.medievo2.Utils;

public enum Permission {
    ADMIN_CHAT("medievo2.chat.admin", "Allows access to the admin chat."),
    GLOBAL_CHAT("medievo2.chat.global", "Allows access to chat globally."),
    CHAT_COLOR("medievo2.chat.color", "Chat color permission."),
    CHAT_PM("medievo2.chat.message", "Allows permission to send private messages."),
    CHAT_REPLY("medievo2.chat.reply", "Allows permission to reply the last private message received."),
    CHAT_BROADCAST("medievo2.core.broadcast", "Allows access to broadcast messages."),
    RANK("medievo2.rank", "Rank command node permission."),
    RANK_ADD("medievo.rank.add", "Allows permission to add ranks to players."),
    RANK_REMOVE("medievo.rank.remove", "Allows permission to remove ranks from players.");

    private String node;
    private String description;

    Permission(String node, String description) {
        this.node = node;
        this.description = description;
    }

    public String getNode() {
        return node;
    }

    public String getDescription() {
        return description;
    }
}
