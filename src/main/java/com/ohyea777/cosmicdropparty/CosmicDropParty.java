package com.ohyea777.cosmicdropparty;

import com.ohyea777.cosmicdropparty.config.ConfigLoader;
import com.ohyea777.cosmicdropparty.config.CosmicConfiguration;
import com.ohyea777.cosmicdropparty.event.VoteListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicDropParty extends JavaPlugin {

    public static CosmicDropParty INSTANCE;

    private CosmicConfiguration configuration;

    @Override
    public void onEnable() {
        INSTANCE = this;

        reload();

        getServer().getPluginManager().registerEvents(new VoteListener(), INSTANCE);
    }

    @Override
    public void onDisable() {

    }

    public CosmicConfiguration getConfiguration() {
        return configuration;
    }

    public void reload() {
        configuration = ConfigLoader.load(INSTANCE);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("on")) {
                if (!(commandSender instanceof Player)) return true;

                if (commandSender.hasPermission("dp.toggle")) {
                    configuration.togglePlayer((Player) commandSender);
                    commandSender.sendMessage(configuration.getMessages().getDpToggleOnFormatted());
                } else {
                    commandSender.sendMessage(configuration.getMessages().getNoPermissionFormatted());
                }

                return true;
            } else if (args[0].equalsIgnoreCase("off")) {
                if (!(commandSender instanceof Player)) return true;

                if (commandSender.hasPermission("dp.toggle")) {
                    configuration.togglePlayer((Player) commandSender, false);
                    commandSender.sendMessage(configuration.getMessages().getDpToggleOffFormatted());
                } else {
                    commandSender.sendMessage(configuration.getMessages().getNoPermissionFormatted());
                }

                return true;
            } else if (args[0].equalsIgnoreCase("status")) {
                if (commandSender.hasPermission("dp.status")) {
                    commandSender.sendMessage(configuration.getMessages().getVoteStatusFormatted(configuration));
                } else {
                    commandSender.sendMessage(configuration.getMessages().getNoPermissionFormatted());
                }

                return true;
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (commandSender.hasPermission("dp.reload")) {
                    CosmicDropParty.INSTANCE.reload();
                    commandSender.sendMessage(configuration.getMessages().getReloadFormatted());
                } else {
                    commandSender.sendMessage(configuration.getMessages().getNoPermissionFormatted());
                }

                return true;
            } else if (args[0].equalsIgnoreCase("about")) {
                commandSender.sendMessage(translate("%prefix% Plugin by&8: &bOhYea777").replace("%prefix%", configuration.getMessages().getPrefixFormatted()));
                commandSender.sendMessage(translate("%prefix% Version&8: &b" + CosmicDropParty.INSTANCE.getDescription().getVersion()).replace("%prefix%", configuration.getMessages().getPrefixFormatted()));

                return true;
            } else if (args[0].equalsIgnoreCase("force")) {
                if (commandSender.hasPermission("dp.force")) {
                    Bukkit.broadcastMessage(configuration.getMessages().getDpBroadcastForcedFormatted(configuration));

                    for (Player player : configuration.getToggledPlayers()) {
                        for (int i = 0; i < configuration.getCount(); i ++) {
                            if (player != null && player.isOnline()) {
                                for (ItemStack itemStack : configuration.getItemStacks()) {
                                    player.getInventory().addItem(itemStack);
                                }
                            }
                        }
                    }
                } else {
                    commandSender.sendMessage(configuration.getMessages().getNoPermission());
                }

                return true;
            }
        }

        commandSender.sendMessage(configuration.getMessages().getHelpFormatted());

        return true;
    }

    public String translate(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

}
