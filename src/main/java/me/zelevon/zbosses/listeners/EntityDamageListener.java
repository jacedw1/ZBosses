package me.zelevon.zbosses.listeners;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.mobs.LivingMobManager;
import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import me.zelevon.zbosses.mobs.bosses.GodOfMind;
import me.zelevon.zbosses.mobs.bosses.KnightOfHearts;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Random;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class EntityDamageListener implements Listener {

    private ZBosses plugin;
    private LivingMobManager mobManager;
    private BukkitScheduler scheduler;

    public EntityDamageListener() {
        this.plugin = ZBosses.getInstance();
        this.mobManager = plugin.getMobManager();
        this.scheduler = Bukkit.getScheduler();
    }

    @EventHandler
    public void onLifestealAttack(EntityDamageByEntityEvent e) {
        Entity damager = ((CraftEntity) e.getDamager()).getHandle();
        LivingEntity player = ((LivingEntity)e.getEntity());
        if (!(damager instanceof AbstractWitherSkeleton)) {
            return;
        }
        AbstractWitherSkeleton boss = (AbstractWitherSkeleton) damager;
        player.damage(e.getDamage());
        e.setCancelled(true);
        if(boss.canLifeSteal()) {
            boss.setHealth(boss.getHealth() + (float)(boss.getLifeStealPercent() * e.getDamage()));
            boss.getBukkitEntity().getWorld().spigot().playEffect(boss.getBukkitEntity().getLocation().add(0,2.5,0), Effect.HEART);
            boss.getBukkitEntity().getWorld().spigot().playEffect(boss.getBukkitEntity().getLocation().add(0.5,2.5,0), Effect.HEART);
            boss.getBukkitEntity().getWorld().spigot().playEffect(boss.getBukkitEntity().getLocation().add(0,2.5,0.5), Effect.HEART);
        }
        if(!(boss instanceof GodOfMind) || !((GodOfMind)boss).isDOT()) {
            return;
        }
        if(new Random().nextDouble() > 0.35) {
            return;
        }
        scheduler.runTaskLaterAsynchronously(this.plugin, () -> player.damage(5), 100);
        scheduler.runTaskLaterAsynchronously(this.plugin, () -> player.damage(5), 200);
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
        if(boss instanceof GodOfMind) {
            if(((GodOfMind)boss).isInvuln()) {
                e.setCancelled(true);
            }
            if(((GodOfMind)boss).isAxePhase()) {
                e.setDamage(e.getDamage() * boss.getDamageMod());
                AttributeInstance speed = boss.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
                speed.setValue(speed.getValue() * (1.02F));
                boss.setDamageMod(boss.getDamageMod() - 0.01D);
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

    @EventHandler
    public void onProjectileHit(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Arrow)) {
            return;
        }
        if(!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getEntity();
        Projectile arrow = (Projectile) e.getDamager();
        Entity entity = ((CraftEntity) arrow.getShooter()).getHandle();
        if(!(entity instanceof GodOfMind)) {
            return;
        }
        GodOfMind boss = (GodOfMind) entity;
        boss.setLastTarget(player);
    }
}
