package com.ohyea777.cosmicdropparty.event;

import com.ohyea777.cosmicdropparty.CosmicDropParty;
import com.ohyea777.cosmicdropparty.config.CosmicConfiguration;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class VoteListener implements Listener {

    @EventHandler
    public void onVote(VotifierEvent event) {
        CosmicConfiguration configuration = CosmicDropParty.INSTANCE.getConfiguration();

        if (configuration.getVoteCount() + 1 >= configuration.getVotesToParty()) {
            configuration.setVoteCount(0);
            Bukkit.broadcastMessage(configuration.getMessages().getDpBroadcastFormatted(configuration));

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
            configuration.setVoteCount(configuration.getVoteCount() + 1);
        }
    }

}
