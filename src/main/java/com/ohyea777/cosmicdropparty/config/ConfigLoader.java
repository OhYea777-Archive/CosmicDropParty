package com.ohyea777.cosmicdropparty.config;

import com.ohyea777.cosmicdropparty.CosmicDropParty;
import com.ohyea777.cosmicdropparty.util.GsonUtil;
import net.minecraft.util.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {

    public static CosmicConfiguration load(CosmicDropParty plugin) {
        File file = new File(plugin.getDataFolder(), "Config.json");

        if (!file.exists()) {
            plugin.saveResource("Config.json", true);
        }

        try {
            String json = FileUtils.readFileToString(file);

            return GsonUtil.getGson().fromJson(json, CosmicConfiguration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean save(CosmicDropParty plugin, CosmicConfiguration config) {
        File file = new File(plugin.getDataFolder(), "Config.json");

        file.getParentFile().mkdirs();

        try {
            String json = GsonUtil.getGson().toJson(config).replace("\\u0026", "&");

            FileUtils.writeStringToFile(file, json);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
