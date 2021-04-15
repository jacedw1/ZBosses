package me.zelevon.zbosses.listeners;

import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import org.bukkit.Effect;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        CraftEntity damager = (CraftEntity) e.getDamager();
        if (!((damager.getHandle()) instanceof AbstractWitherSkeleton)) {
            return;
        }
        AbstractWitherSkeleton boss = (AbstractWitherSkeleton) damager.getHandle();
        if(boss.canLifeSteal()) {
            boss.setHealth(boss.getHealth() + (float)e.getDamage() / 10);
            boss.getBukkitEntity().getWorld().spigot().playEffect(boss.getBukkitEntity().getLocation().add(0,2.5,0), Effect.HEART);
            boss.getBukkitEntity().getWorld().spigot().playEffect(boss.getBukkitEntity().getLocation().add(0.5,2.5,0), Effect.HEART);
            boss.getBukkitEntity().getWorld().spigot().playEffect(boss.getBukkitEntity().getLocation().add(0,2.5,0.5), Effect.HEART);
        }
    }
}
