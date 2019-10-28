package org.elmedievo.medievo2;

import com.sk89q.bukkit.util.BukkitCommandsManager;
import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.elmedievo.medievo2.Chat.ChannelRegistry;
import org.elmedievo.medievo2.Chat.PrivateMessage;
import org.elmedievo.medievo2.Commands.*;
import org.elmedievo.medievo2.Listeners.ChatListener;
import org.elmedievo.medievo2.Listeners.JoinQuitListener;
import org.elmedievo.medievo2.Listeners.WeatherListener;
import org.elmedievo.medievo2.Ranks.RankDatabase;
import org.elmedievo.medievo2.Ranks.RankRegistry;
import org.elmedievo.medievo2.Utils.ChatConstant;
import org.elmedievo.medievo2.Utils.Exceptions.RankDatabaseException;

import java.util.HashMap;


public final class Medievo2 extends JavaPlugin {
    public static Medievo2 getPlugin;
    public static Configuration getConfiguration;
    public static ChannelRegistry getChannelRegistry;
    public static PrivateMessage.ReplyQueue getReplyQueue;
    public static RankRegistry getRankRegistry;
    public static RankDatabase getRankDatabase;

    private CommandsManager commands;
    private CommandsManagerRegistration commandRegistry;

    @SuppressWarnings("unchecked")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            this.commands.execute(command.getName(), args, sender, sender);
        } catch (CommandPermissionsException exception) {
            sender.sendMessage(ChatConstant.NO_PERMISSION.formatAsException());
        } catch (MissingNestedCommandException exception) {
            sender.sendMessage(ChatColor.YELLOW + "⚠ " + ChatColor.RED + exception.getUsage());
        } catch (CommandUsageException exception) {
            sender.sendMessage(ChatColor.RED + exception.getMessage());
            sender.sendMessage(ChatColor.RED + exception.getUsage());
        } catch (WrappedCommandException exception) {
            if (exception.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatConstant.NUMBER_STRING_EXCEPTION.formatAsException());
            } else if (exception.getCause() instanceof RankDatabaseException) {
                RankDatabaseException rankDatabaseException = (RankDatabaseException) exception.getCause();
                sender.sendMessage(rankDatabaseException.getReason());
            } else {
                sender.sendMessage(ChatConstant.UNKNOWN_ERROR.formatAsException());
                exception.printStackTrace();
            }
        } catch (CommandException exception) {
            sender.sendMessage(ChatColor.RED + exception.getLocalizedMessage());
        }
        return true;
    }


    // TODO: Make all of this configurable
    private void addFurnaceRecipe(String key, Material source, ItemStack result, float experience, int cookingTime) {
        Bukkit.getServer().addRecipe(new FurnaceRecipe(new NamespacedKey(getPlugin, key), result, source, experience, cookingTime));
    }

    private void loadFurnaceRecipes() {
        addFurnaceRecipe(
                "leather_from_flesh",
                Material.ROTTEN_FLESH,
                new ItemStack(Material.LEATHER),
                0.03f,
                200
        );
    }

    @Override
    public void onEnable() {
        getPlugin = this;

        this.commands = new BukkitCommandsManager();
        this.commandRegistry = new CommandsManagerRegistration(this, this.commands);

        loadConfiguration();

        getConfiguration = new Configuration();
        getChannelRegistry = new ChannelRegistry(new HashMap<>());
        getReplyQueue = new PrivateMessage.ReplyQueue(new HashMap<>());

        getRankRegistry = new RankRegistry();
        getRankDatabase = new RankDatabase();

        getRankRegistry.load();
        getRankDatabase.load();

        registerCommands();
        registerEvents();

        loadFurnaceRecipes();
    }

    @Override
    public void onDisable() {
    }

    private void registerCommands() {
        commandRegistry.register(Coords.class);
        commandRegistry.register(ChatCommand.class);
        commandRegistry.register(PrivateMessageCommand.class);
        commandRegistry.register(ReplyCommand.class);
        commandRegistry.register(BroadcastCommand.class);
        commandRegistry.register(RankCommand.RankParentCommand.class);
        commandRegistry.register(RankCommand.class);
        commandRegistry.register(RanksCommand.RanksParentCommand.class);
        commandRegistry.register(RanksCommand.class);
        commandRegistry.register(MedievoCommand.MedievoParentCommand.class);
        commandRegistry.register(MedievoCommand.class);
    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new WeatherListener(), this);
        pluginManager.registerEvents(new JoinQuitListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);
    }

    private void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
