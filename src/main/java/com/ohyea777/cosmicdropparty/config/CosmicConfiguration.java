package com.ohyea777.cosmicdropparty.config;

import com.ohyea777.cosmicdropparty.CosmicDropParty;
import com.ohyea777.cosmicdropparty.item.SerializableItemStack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CosmicConfiguration {

    private Messages messages;
    private int votesToParty;
    private int count;
    private List<SerializableItemStack> items;

    private int voteCount;
    private List<UUID> toggledPlayers;

    public Messages getMessages() {
        if (messages == null) {
            messages = new Messages();
        }

        return messages;
    }

    public int getVotesToParty() {
        return votesToParty;
    }

    public int getCount() {
        return count;
    }

    public List<SerializableItemStack> getItems() {
        if (items == null) {
            items = new ArrayList<SerializableItemStack>();
        }

        return items;
    }

    public List<ItemStack> getItemStacks() {
        List<SerializableItemStack> serializableItemStacks = getItems();
        List<ItemStack> itemStacks = new ArrayList<ItemStack>();

        for (SerializableItemStack serializableItemStack : serializableItemStacks) {
            itemStacks.add(serializableItemStack.getItemStack());
        }

        return itemStacks;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;

        save();
    }

    public List<UUID> getToggledUUIDs() {
        if (toggledPlayers == null) {
            toggledPlayers = new ArrayList<UUID>();
        }

        return toggledPlayers;
    }

    public List<Player> getToggledPlayers() {
        List<UUID> toggledUUIDs = getToggledUUIDs();
        List<Player> toggledPlayers = new ArrayList<Player>();

        for (UUID uuid : toggledUUIDs) {
            toggledPlayers.add(Bukkit.getPlayer(uuid));
        }

        return toggledPlayers;
    }

    public boolean isToggled(Player player) {
        return isToggled(player.getUniqueId());
    }

    public boolean isToggled(UUID uuid) {
        return getToggledUUIDs().contains(uuid);
    }

    public void togglePlayer(Player player) {
        togglePlayer(player.getUniqueId());
    }

    public void togglePlayer(Player player, boolean toggledOn) {
        togglePlayer(player.getUniqueId(), toggledOn);
    }

    public void togglePlayer(UUID uuid) {
        togglePlayer(uuid, true);
    }

    public void togglePlayer(UUID uuid, boolean toggledOn) {
        if (toggledOn) {
            getToggledUUIDs().add(uuid);
        } else {
            getToggledUUIDs().remove(uuid);
        }

        save();
    }

    public boolean save() {
        return ConfigLoader.save(CosmicDropParty.INSTANCE, this);
    }

    public class Messages {

        private String prefix;
        private String dpToggleOn;
        private String dpToggleOff;
        private String reload;
        private String dpBroadcast;
        private String dpBroadcastForced;
        private String noPermission;
        private String[] help;
        private String voteStatus;

        public String getPrefix() {
            if (prefix == null) {
                prefix = "&8[&3Cosmic Drop Party&8]&7";
            }

            return prefix;
        }

        public String getPrefixFormatted() {
            return translate(getPrefix());
        }

        public String getDpToggleOn() {
            if (dpToggleOn == null) {
                return "%prefix% Toggled dp on!";
            }

            return dpToggleOn;
        }

        public String getDpToggleOnFormatted() {
            return translate(getDpToggleOn()).replace("%prefix%", getPrefixFormatted());
        }

        public String getDpToggleOff() {
            if (dpToggleOff == null) {
                return "%prefix% Toggled dp off!";
            }

            return dpToggleOff;
        }

        public String getDpToggleOffFormatted() {
            return translate(getDpToggleOff()).replace("%prefix%", getPrefixFormatted());
        }

        public String getReload() {
            if (reload == null) {
                return "%prefix% Configuration reloaded!";
            }

            return reload;
        }

        public String getReloadFormatted() {
            return translate(getReload()).replace("%prefix%", getPrefixFormatted());
        }

        public String getDpBroadcast() {
            if (dpBroadcast == null) {
                dpBroadcast = "%prefix% Vote count has reached &b%votes%&7, enjoy your rewards! If you have not toggled dp, do &b/dp on&7 to get rewards next time!";
            }

            return dpBroadcast;
        }

        public String getDpBroadcastFormatted(CosmicConfiguration configuration) {
            return translate(getDpBroadcast()).replace("%prefix%", getPrefixFormatted()).replace("%votes%", String.valueOf(configuration.getVotesToParty()));
        }

        public String getDpBroadcastForced() {
            if (dpBroadcastForced == null) {
                dpBroadcastForced = "%prefix% Forced drop party, enjoy your rewards! If you have not toggled dp, do &b/dp on&7 to get rewards next time!";
            }

            return dpBroadcastForced;
        }

        public String getDpBroadcastForcedFormatted(CosmicConfiguration configuration) {
            return translate(getDpBroadcastForced()).replace("%prefix%", getPrefixFormatted());
        }

        public String getNoPermission() {
            if (noPermission == null) {
                return "%prefix%&c You do not have permission to do that!";
            }

            return noPermission;
        }

        public String getNoPermissionFormatted() {
            return translate(getNoPermission()).replace("%prefix%", getPrefixFormatted());
        }

        public String[] getHelp() {
            if (help == null) {
                help = new String[]{"%prefix% Help&8:", "&8-&3]&7 To toggle dp off&8: &b/dp off", "&8-&3]&7 To toggle dp on&8: &b/dp on", "&8-&3]&7 To see the vote count&8: &b/dp status", "&8-&3]&7 To reload the config&8: &b/dp reload", "&8-&3]&7 For about&8: &b/dp about"};
            }

            return help;
        }

        public String[] getHelpFormatted() {
            String[] help = getHelp();
            String[] newHelp = new String[help.length];

            for (int i = 0; i < help.length; i++) {
                newHelp[i] = translate(help[i]).replace("%prefix%", getPrefixFormatted());
            }

            return newHelp;
        }

        public String getVoteStatus() {
            if (voteStatus == null) {
                return "%prefix% Vote count&8: &b%votes%&7!";
            }

            return voteStatus;
        }

        public String getVoteStatusFormatted(CosmicConfiguration configuration) {
            return translate(getVoteStatus()).replace("%prefix%", getPrefixFormatted()).replace("%votes%", String.valueOf(configuration.getVoteCount()));
        }

        private String translate(String str) {
            return ChatColor.translateAlternateColorCodes('&', str);
        }

    }

}
