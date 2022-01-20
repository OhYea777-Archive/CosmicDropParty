package com.ohyea777.cosmicdropparty.util;

public class Utils {

    public static String fixNbtString(String nbt) {
        String fixedString = "";

        for (int i = 0; i < nbt.length(); i ++) {
            if (nbt.charAt(i) == ',' && i + 1 < nbt.length() && (nbt.charAt(i + 1) == '}' || nbt.charAt(i + 1) == ']'))
                continue;

            fixedString += nbt.charAt(i);
        }

        return fixedString;
    }

}
