package org.elmedievo.medievo2.Chat;

import org.elmedievo.medievo2.Utils.Permission;

public enum Channel {
    ADMIN("admin", "&f[&6A&f]&r ", Permission.ADMIN_CHAT),
    GLOBAL("global", "", Permission.GLOBAL_CHAT);

    private String name;
    private String prefix;
    private Permission permissionParent;

    Channel(String name, String prefix, Permission permissionParent) {
        this.name = name;
        this.prefix = prefix;
        this.permissionParent = permissionParent;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public Permission getPermissionParent() {
        return permissionParent;
    }
}
