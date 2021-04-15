package me.zelevon.zbosses.listeners;

import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import org.bukkit.Effect;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onLifestealAttack(EntityDamageByEntityEvent e) {
        CraftEntity damager = (CraftEntity) e.getDamager();
        if (!((damager.getHandle()) instanceof AbstractWitherSkeleton)) {
            return;
        }
        AbstractWitherSkeleton boss = (AbstractWitherSkeleton) damager.getHandle();
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
}
