package me.zelevon.zbosses.items;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings({"unused", "deprecation"})
public class BossEgg extends ItemStack {

    public BossEgg(String name) {
        super(Material.MONSTER_EGG, 1, EntityType.ENDERMAN.getTypeId());
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);
        NBTTagCompound tag = nmsItem.getTag();
        if(tag == null) {
            tag = new NBTTagCompound();
        }
        tag.setBoolean("BossEgg", true);
        nmsItem.setTag(tag);
        ItemStack item = CraftItemStack.asBukkitCopy(nmsItem);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        this.setItemMeta(meta);
    }

    public String getName() {
        return this.getItemMeta().getDisplayName();
    }
}
