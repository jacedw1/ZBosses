package me.zelevon.zbosses.mobs.bosses;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.config.Config;
import me.zelevon.zbosses.mobs.LivingMobManager;
import me.zelevon.zbosses.utils.MessageSender;
import me.zelevon.zbosses.utils.NMSUtils;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.ItemStack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;

public abstract class AbstractWitherSkeleton extends EntitySkeleton {

    private ZBosses plugin;
    private MessageSender messageSender;
    private LivingMobManager mobManager;
    private Config.MobsConf conf;
    private boolean canLifeSteal = false;

    public AbstractWitherSkeleton(Location location) {
        super(((CraftWorld)location.getWorld()).getHandle());
        this.plugin = ZBosses.getInstance();
        this.messageSender = plugin.getMessageSender();
        this.mobManager = plugin.getMobManager();
        this.conf = plugin.getConf();
        this.setSkeletonType(1);
        this.setCustomNameVisible(true);
        this.setPosition(location.getX(), location.getY(), location.getZ());
    }

    public abstract String getName();

    public abstract double randomBuffRadius();

    public void spawn(Player player, String message) {
        this.spawn();
        this.messageSender.msg(player, this.plugin.getPrefix() + message);
    }

    public void spawn() {
        mobManager.addBoss(this);
        this.getWorld().addEntity(this);
    }
    public void setHelmet(ItemStack item){
        this.setEquipment(NMSUtils.HELMET_SLOT, item);
    }

    public void setChestplate(ItemStack item){
        this.setEquipment(NMSUtils.CHEST_SLOT, item);
    }

    public void setLeggings(ItemStack item){
        this.setEquipment(NMSUtils.LEG_SLOT, item);
    }

    public void setBoots(ItemStack item){
        this.setEquipment(NMSUtils.BOOT_SLOT, item);
    }

    public void setWeapon(ItemStack item){
        this.setEquipment(NMSUtils.WEAPON_SLOT, item);
    }

    public ZBosses getPlugin() {
        return plugin;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    public Config.MobsConf getConf() {
        return conf;
    }

    public void setCustomHealth(float health){
        ((Damageable)((this.getBukkitEntity()))).setMaxHealth(health);
        this.setHealth(health);
    }

    public ItemStack parseItem(String material) {
        return CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.getMaterial(material)));
    }

    public void updateName() {
        float hp = ((float)Math.round(this.getHealth() * 100)) / ((float)100.0);
        String name = this.getName() + " &8[&a" + hp + "&f/&a" + this.getMaxHealth() + "&8]";
        this.setCustomName(this.getMessageSender().colorize(name));
    }

    public boolean canLifeSteal() {
        return canLifeSteal;
    }

    public void setCanLifeSteal(boolean canLifeSteal) {
        this.canLifeSteal = canLifeSteal;
    }
}
