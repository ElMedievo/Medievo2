package org.elmedievo.medievo2.Utils;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public enum MedievoWorld {
    OVERWORLD("overworld",
            ChatColor.WHITE + "[" + ChatColor.GREEN + "Overworld" + ChatColor.WHITE + "]" + ChatColor.RESET,
            0
    ),
    NETHER("nether",
            ChatColor.WHITE + "[" + ChatColor.RED + "Nether" + ChatColor.WHITE + "]" + ChatColor.RESET,
            1
    ),
    END("end",
            ChatColor.WHITE + "[" + ChatColor.AQUA + "End" + ChatColor.WHITE + "]" + ChatColor.RESET,
            2
    ),
    UNKNOWN("unknown",
            ChatColor.WHITE + "[" + ChatColor.RED + "The Unknown Paradise" + ChatColor.WHITE + "]" + ChatColor.RESET,
            99
    );

    private String name;
    private String prefix;
    private int index;

    MedievoWorld(String name, String prefix, int index) {
        this.name = name;
        this.prefix = prefix;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getIndex() {
        return index;
    }

    public static MedievoWorld getPlayerMedievoWorld(Player player) {
        World world = player.getWorld();
        switch (world.getName()) {
            case "world":
                return OVERWORLD;
            case "world_nether":
                return NETHER;
            case "world_the_end":
                return END;
        }
        return UNKNOWN;
    }
}
