package me.zelevon.zbosses.listeners;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.mobs.LivingMobManager;
import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import me.zelevon.zbosses.mobs.bosses.GodOfMind;
import me.zelevon.zbosses.mobs.bosses.KnightOfHearts;
import me.zelevon.zbosses.mobs.bosses.KnightOfSouls;
import me.zelevon.zbosses.mobs.minions.MindGuard;
import me.zelevon.zbosses.mobs.minions.SoulMinion;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
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
    public void onBossAttack(EntityDamageByEntityEvent e) {
        Entity damager = ((CraftEntity) e.getDamager()).getHandle();
        if(!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = ((Player)e.getEntity());
        if(damager instanceof MindGuard) {
            e.setDamage(e.getDamage() + ((MindGuard)damager).getDamageBonus());
        }
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
        Fireball fireball = (Fireball) e.getEntity();
        if(((CraftEntity)(fireball.getShooter())).getHandle() instanceof AbstractWitherSkeleton) {
            Location loc = fireball.getLocation();
            fireball.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 5.0F, true, false);
            fireball.remove();
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageBoss(EntityDamageByEntityEvent e) {
        Entity entity = ((CraftEntity) e.getEntity()).getHandle();
        if (!(entity instanceof AbstractWitherSkeleton)) {
            return;
        }
        AbstractWitherSkeleton boss = (AbstractWitherSkeleton) entity;
        if(boss instanceof KnightOfHearts) {
            if(((KnightOfHearts) boss).crystalIsAlive()) {
                if(e.getDamager() instanceof Player) {
                    ((Player)e.getDamager()).damage(e.getFinalDamage());
                }
                e.setCancelled(true);
                return;
            }
        }
        if(boss instanceof KnightOfSouls) {
            e.setDamage(e.getDamage() * ((KnightOfSouls) boss).getDamagePercent());
        }
        if(boss instanceof GodOfMind) {
            GodOfMind mind = ((GodOfMind)boss);
            if(mind.isInvuln()) {
                e.setCancelled(true);
                return;
            }
            if(mind.guardIsAlive()) {
                e.setCancelled(true);
                return;
            }
            if(mind.isAxePhase()) {
                e.setDamage(e.getDamage() * boss.getDamageMod());
                AttributeInstance speed = boss.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
                speed.setValue(Math.min(speed.getValue() * (1.02D), boss.getBaseSpeed() + 0.25D * boss.getBaseSpeed()));
                boss.setDamageMod(Math.min(boss.getDamageMod() + 0.005D, 1.15));
            }
        }
        if(!(e.getDamager() instanceof Player)) {
            return;
        }
        this.mobManager.addDamage(boss, (Player)e.getDamager(), e.getFinalDamage());
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

    @EventHandler
    public void onExplode(EntityDamageEvent e) {
        Entity entity = ((CraftEntity) e.getEntity()).getHandle();
        if(! ( (e.getCause() == DamageCause.ENTITY_EXPLOSION) || (e.getCause() == DamageCause.BLOCK_EXPLOSION) ) ) {
            return;
        }
        if(entity instanceof MindGuard || entity instanceof SoulMinion) {
            e.setCancelled(true);
        }
    }
}
