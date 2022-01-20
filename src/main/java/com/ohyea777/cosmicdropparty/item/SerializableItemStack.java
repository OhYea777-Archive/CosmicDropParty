package com.ohyea777.cosmicdropparty.item;

import com.ohyea777.cosmicdropparty.util.Utils;
import net.minecraft.server.v1_7_R4.MojangsonParser;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class SerializableItemStack {

    private String item;

    public SerializableItemStack() { }

    public SerializableItemStack(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public ItemStack getItemStack() {
        if (getItem().equals("AIR")) {
            return new ItemStack(Material.AIR);
        } else {
            NBTTagCompound tag = (NBTTagCompound) MojangsonParser.parse(getItem());

            return CraftItemStack.asCraftMirror(net.minecraft.server.v1_7_R4.ItemStack.createStack(tag));
        }
    }

    public static SerializableItemStack createFromItemStack(ItemStack itemStack) {
        net.minecraft.server.v1_7_R4.ItemStack minecraftItemStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = new NBTTagCompound();

        minecraftItemStack.save(tag);

        String item = itemStack != null ? (tag != null ? tag.toString() : "AIR") : "AIR";

        return new SerializableItemStack(Utils.fixNbtString(item));
    }

}
