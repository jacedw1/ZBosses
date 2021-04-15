package me.zelevon.zbosses.mobs.skills;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import net.minecraft.server.v1_8_R3.AttributeBase;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.MobEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class RandomBuffs {

    public static void speedBuff(AbstractWitherSkeleton boss) {
        boss.addEffect(new MobEffect(1, 200, 1));
    }

    public static void strengthBuff(AbstractWitherSkeleton boss) {
        boss.addEffect(new MobEffect(5, 100, 0));
    }

    public static void slownessBuff(AbstractWitherSkeleton boss) {
        double r = boss.randomBuffRadius();
        for(Entity e : boss.getBukkitEntity().getNearbyEntities(r, r, r)) {
            if(e instanceof Player) {
                ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
            }
        }
    }

    public static void miningFatigueBuff(AbstractWitherSkeleton boss) {
        double r = boss.randomBuffRadius();
        for(Entity e : boss.getBukkitEntity().getNearbyEntities(r, r, r)) {
            if(e instanceof Player) {
                ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 2));
            }
        }
    }

    public static void knockbackPlayerBuff(AbstractWitherSkeleton boss) {
        double r = boss.randomBuffRadius();
        for(Entity e : boss.getBukkitEntity().getNearbyEntities(r, r, r)) {
            if(e instanceof Player) {
                Location bossLoc = boss.getBukkitEntity().getLocation();
                Location playerLoc = e.getLocation();
                double dx = playerLoc.getX() - bossLoc.getX();
                double dz = playerLoc.getZ() - bossLoc.getZ();
                double scaleX = 1.4;
                double scaleZ = 1.4;
                if(dz != 0) {
                    double angle = Math.atan(dx/dz);
                    scaleX *= Math.sin(angle);
                    scaleZ *= Math.cos(angle);
                }
                double knockX = dx == 0 ? 0 : dx / Math.abs(dx) * scaleX;
                double knockY = 0.8;
                double knockZ = dz == 0 ? 0 : dz / Math.abs(dz) * scaleZ;
                Vector knockAway = new Vector(knockX, knockY, knockZ);
                e.setVelocity(knockAway);
            }
        }
    }

    public static void noKnockbackBuff(AbstractWitherSkeleton boss) {
        boss.getAttributeInstance(GenericAttributes.c).setValue(1.0D);
        Bukkit.getScheduler().runTaskLater(ZBosses.getInstance(), () -> { boss.getAttributeInstance(GenericAttributes.c).setValue(0.0D);}, 100);
    }
}
