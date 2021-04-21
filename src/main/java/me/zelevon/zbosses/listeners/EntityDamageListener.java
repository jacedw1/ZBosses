package me.zelevon.zbosses.listeners;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.mobs.LivingMobManager;
import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import me.zelevon.zbosses.mobs.bosses.KnightOfHearts;
import org.bukkit.Effect;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class EntityDamageListener implements Listener {

    private ZBosses plugin;
    private LivingMobManager mobManager;

    public EntityDamageListener() {
        this.plugin = ZBosses.getInstance();
        this.mobManager = plugin.getMobManager();
    }

    @EventHandler
    public void onLifestealAttack(EntityDamageByEntityEvent e) {
        Entity damager = ((CraftEntity) e.getDamager()).getHandle();
        if (!(damager instanceof AbstractWitherSkeleton)) {
            return;
        }
        AbstractWitherSkeleton boss = (AbstractWitherSkeleton) damager;
        if(boss.canLifeSteal()) {
            boss.setHealth(boss.getHealth() + (float)(boss.getLifeStealPercent() * e.getDamage()));
            boss.getBukkitEntity().getWorld().spigot().playEffect(boss.getBukkitEntity().getLocation().add(0,2.5,0), Effect.HEART);
            boss.getBukkitEntity().getWorld().spigot().playEffect(boss.getBukkitEntity().getLocation().add(0.5,2.5,0), Effect.HEART);
            boss.getBukkitEntity().getWorld().spigot().playEffect(boss.getBukkitEntity().getLocation().add(0,2.5,0.5), Effect.HEART);
        }
    }

    @EventHandler
    public void onFireballHit(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Fireball)) {
            return;
        }
        if(((CraftEntity)(((Fireball) e.getEntity()).getShooter())).getHandle() instanceof AbstractWitherSkeleton) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getDamager();
        Entity entity = ((CraftEntity) e.getEntity()).getHandle();
        if (!(entity instanceof AbstractWitherSkeleton)) {
            return;
        }
        AbstractWitherSkeleton boss = (AbstractWitherSkeleton) entity;
        if(boss instanceof KnightOfHearts) {
            if(mobManager.isAlive(EnderCrystal.class, false)) {
                player.damage(e.getFinalDamage());
                e.setCancelled(true);
                return;
            }
        }
        this.mobManager.addDamage(boss, player, e.getFinalDamage());
    }

    @EventHandler
    public void onEndCrystalHit(EntityDamageEvent e) {
        if(!(e.getEntity().getType() == EntityType.ENDER_CRYSTAL)) {
            return;
        }
        org.bukkit.entity.Entity entity = e.getEntity();
        if(!(mobManager.getMinions().contains(entity))) {
           return;
        }
        if(e.getCause() == DamageCause.PROJECTILE) {
            mobManager.removeMinion(entity);
            return;
        }
        e.setCancelled(true);
    }
}
