package me.zelevon.zbosses.mobs.skills;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import me.zelevon.zbosses.tasks.FireballTask;
import me.zelevon.zbosses.tasks.RandomBuffTask;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.MobEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class RandomBuffs {

    public static void speedBuff(AbstractWitherSkeleton boss) {
        boss.addEffect(new MobEffect(1, 200, 1));
        broadcastMessage(boss, "Speeding up!", 20);
    }

    public static void strengthBuff(AbstractWitherSkeleton boss) {
        boss.addEffect(new MobEffect(5, 100, 0));
        broadcastMessage(boss, "I feel more powerful..", 20);
    }

    public static void slownessBuff(AbstractWitherSkeleton boss) {
        double r = boss.randomBuffRadius();
        for(Entity e : boss.getBukkitEntity().getNearbyEntities(r, r, r)) {
            if(e instanceof Player) {
                ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
            }
        }
        broadcastMessage(boss, "Slow down!", 20);
    }

    public static void miningFatigueBuff(AbstractWitherSkeleton boss) {
        double r = boss.randomBuffRadius();
        for(Entity e : boss.getBukkitEntity().getNearbyEntities(r, r, r)) {
            if(e instanceof Player) {
                ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 2));
            }
        }
        broadcastMessage(boss, "Don't attack so fast...", 20);
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
        broadcastMessage(boss, "Shockwave!", 20);
    }

    public static void noKnockbackBuff(AbstractWitherSkeleton boss) {
        boss.getAttributeInstance(GenericAttributes.c).setValue(1.0D);
        Bukkit.getScheduler().runTaskLater(ZBosses.getInstance(), () -> { boss.getAttributeInstance(GenericAttributes.c).setValue(0.0D); }, 100);
        broadcastMessage(boss, "You can't knock me back! (5s)", 20);
    }

    public static void lifeStealBuff(AbstractWitherSkeleton boss) {
        if(boss.canLifeSteal()) {
            new RandomBuffTask(boss).run();
            return;
        }
        boss.setCanLifeSteal(true);
        Bukkit.getScheduler().runTaskLater(ZBosses.getInstance(), () -> { boss.setCanLifeSteal(false); }, 100);
        broadcastMessage(boss, "I'm going to absorb your soul...", 20);
    }

    public static void fireballBuff(AbstractWitherSkeleton boss) {
        List<Entity> entities = boss.getBukkitEntity().getNearbyEntities(15, 15, 15);
        List<Player> players = new ArrayList<>();
        for(Entity e : entities) {
            if(e instanceof Player) {
                players.add((Player) e);
            }
        }
        if(players.size() == 0) {
            new RandomBuffTask(boss).run();
            return;
        }
        int fireballAmount = 3;
        long delay = 0;
        Random rand = new Random();
        for(int i = 0; i < fireballAmount; i++) {
            Player player = players.get(rand.nextInt(players.size()));
            new FireballTask(boss, player).runTaskLater(ZBosses.getInstance(), delay);
            delay += 10;
        }
        broadcastMessage(boss, "Fireballs!", 20);
    }

    public static void spawnZombieHoard(AbstractWitherSkeleton boss) {
        spawnHoard(boss, false, 5, 6);
    }

    public static void spawnBabyZombieHoard(AbstractWitherSkeleton boss) {
        spawnHoard(boss, true, 5, 4);
    }

    private static void spawnHoard(AbstractWitherSkeleton boss, boolean isBaby, int radius, int amount) {
        Location baseLoc = boss.getBukkitEntity().getLocation();
        Random rand = new Random();
        for(int i = 0; i < amount; i++) {
            int r = rand.nextInt(radius * 2) - radius;
            ((Zombie) baseLoc.getWorld().spawnEntity(
                    baseLoc.add(rand.nextBoolean() ? new Vector(r, 0, 0) : new Vector(0, 0, r)),
                    EntityType.ZOMBIE)).setBaby(isBaby);
        }
        broadcastMessage(boss, "Have fun with your new friends...", 20);
    }

    private static void broadcastMessage(AbstractWitherSkeleton boss, String message, double radius) {
        for(Entity e : boss.getBukkitEntity().getNearbyEntities(radius, radius, radius)) {
            if(e instanceof Player) {
                boss.getMessageSender().msg((Player) e, boss.getName() + " &f" + message);
            }
        }
    }
}
