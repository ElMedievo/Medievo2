package org.elmedievo.medievo2.Commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.NestedCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.elmedievo.medievo2.Configuration;
import org.elmedievo.medievo2.Medievo2;
import org.elmedievo.medievo2.Utils.ChatConstant;

import java.util.List;

public class MedievoCommand {
    @Command(
            aliases = {"reload"},
            desc = "Reloads the plugin configuration"
    )
    @CommandPermissions("medievo2.medievo.reload")
    public static void reload(final CommandContext args, final CommandSender sender) throws CommandException {
        Configuration configuration = Medievo2.getConfiguration;
        configuration.reload();
        configuration.setConfigFile(Medievo2.getPlugin.getConfig());

        sender.sendMessage(ChatConstant.CONFIGURATION_RELOADED.formatAsSuccess());
    }

    @Command(
            aliases = {"version", "ver", "v"},
            desc = "Information regardin the plugin version"
    )
    @CommandPermissions("medievo2.medievo.version")
    public static void version(final CommandContext args, final CommandSender sender) throws CommandException {
        PluginDescriptionFile pluginDescriptionFile = Medievo2.getPlugin.getDescription();

        String name = pluginDescriptionFile.getName();
        String version = pluginDescriptionFile.getVersion();
        String apiVersion = pluginDescriptionFile.getAPIVersion();
        String website = pluginDescriptionFile.getWebsite();
        List<String> authors = pluginDescriptionFile.getAuthors();
        int lastAuthorInRegex = authors.size() - 1;
        int penultimateAuthorInRegex = authors.size() - 2;

        StringBuilder authorsLineBuilder = new StringBuilder();
        authorsLineBuilder.append(ChatColor.AQUA).append("by: ");

        for (String author : authors) {
            if (authors.get(lastAuthorInRegex).equals(author)) {
                authorsLineBuilder.append(ChatColor.AQUA).append(" & ").append(ChatColor.RED).append(author).append(ChatColor.AQUA).append(".");
                break;
            }
            if (!authors.get(penultimateAuthorInRegex).equals(author)) {
                authorsLineBuilder.append(ChatColor.RED).append(author).append(ChatColor.AQUA).append(", ");
            } else authorsLineBuilder.append(ChatColor.RED).append(author).append(ChatColor.AQUA);
        }

        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.STRIKETHROUGH + "------------"  + ChatColor.GREEN + " " + name + " " + ChatColor.GOLD.toString() + ChatColor.STRIKETHROUGH + "------------ " + "\n"
                + ChatColor.YELLOW + "Version: " + ChatColor.AQUA + version + "\n"
                + ChatColor.YELLOW + "API: " + ChatColor.AQUA + apiVersion + "\n"
                + ChatColor.YELLOW + "Created by: " + authorsLineBuilder.toString() + "\n"
                + ChatColor.YELLOW + "Website: " + ChatColor.AQUA + website
        );
    }

    public static class MedievoParentCommand {
        @Command(
                aliases = {"medievo"},
                desc = "Medievo node command."
        )
        @CommandPermissions("medievo2.medievo")
        @NestedCommand(MedievoCommand.class)
        public static void medievo(final CommandContext args, final CommandSender sender) throws CommandException {
        }
    }
}
