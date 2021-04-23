package me.zelevon.zbosses.mobs.skills;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import me.zelevon.zbosses.tasks.FireballTask;
import me.zelevon.zbosses.tasks.RandomBuffTask;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.MobEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

@SuppressWarnings("SameParameterValue")
public class RandomBuffs {

    public static void speedBuff(AbstractWitherSkeleton boss) {
        boss.addEffect(new MobEffect(1, 200, 1));
        GeneralSkills.broadcastMessage(boss, "Speeding up!", 20);
    }

    public static void strengthBuff(AbstractWitherSkeleton boss) {
        boss.addEffect(new MobEffect(5, 100, 0));
        GeneralSkills.broadcastMessage(boss, "I feel more powerful..", 20);
    }

    public static void slownessBuff(AbstractWitherSkeleton boss) {
        PotionEffect slowness = PotionEffectType.SLOW.createEffect(200, 1);
        List<Player> players = GeneralSkills.getNearbyPlayers(boss, boss.randomBuffRadius());
        for(Player player : players) {
            player.addPotionEffect(slowness);
        }
        GeneralSkills.broadcastMessage(boss, "Slow down!", 20);
    }

    public static void miningFatigueBuff(AbstractWitherSkeleton boss) {
        PotionEffect miningFatigue = PotionEffectType.SLOW_DIGGING.createEffect(120, 2);
        List<Player> players = GeneralSkills.getNearbyPlayers(boss, boss.randomBuffRadius());
        for(Player player : players) {
            player.addPotionEffect(miningFatigue);
        }
        GeneralSkills.broadcastMessage(boss, "Don't attack so fast...", 20);
    }

    public static void knockbackPlayerBuff(AbstractWitherSkeleton boss) {
        List<Player> players = GeneralSkills.getNearbyPlayers(boss, 5);
        for(Player player : players) {
                Location bossLoc = boss.getBukkitEntity().getLocation();
                Location playerLoc = player.getLocation();
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
                player.setVelocity(knockAway);
            }
        GeneralSkills.broadcastMessage(boss, "Shockwave!", 20);
    }

    public static void noKnockbackBuff(AbstractWitherSkeleton boss) {
        boss.getAttributeInstance(GenericAttributes.c).setValue(1.0D);
        Bukkit.getScheduler().runTaskLater(ZBosses.getInstance(), () -> boss.getAttributeInstance(GenericAttributes.c).setValue(0.0D), 100);
        GeneralSkills.broadcastMessage(boss, "You can't knock me back! (5s)", 20);
    }

    public static void lifeStealBuff(AbstractWitherSkeleton boss) {
        if(boss.canLifeSteal()) {
            new RandomBuffTask(boss).run();
            return;
        }
        boss.setCanLifeSteal(true);
        Bukkit.getScheduler().runTaskLater(ZBosses.getInstance(), () -> boss.setCanLifeSteal(false), 100);
        GeneralSkills.broadcastMessage(boss, "I'm going to absorb your soul...", 20);
    }

    public static void fireballBuff(AbstractWitherSkeleton boss, int fireballAmount) {
        AttributeInstance speed = boss.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
        speed.setValue(0);
        Bukkit.getScheduler().runTaskLater(boss.getPlugin(), boss::resetSpeed, fireballAmount * 10L);
        List<Player> players = GeneralSkills.getNearbyPlayers(boss, boss.randomBuffRadius());
        if(players.size() == 0) {
            new RandomBuffTask(boss).run();
            return;
        }
        long delay = 10;
        Random rand = new Random();
        for(int i = 0; i < fireballAmount; i++) {
            Player player = players.get(rand.nextInt(players.size()));
            new FireballTask(boss, player).runTaskLater(ZBosses.getInstance(), delay);
            delay += 10;
        }
        GeneralSkills.broadcastMessage(boss, "Fireballs!", 20);
    }

    public static void spawnZombieHoard(AbstractWitherSkeleton boss) {
        spawnHoard(boss, false, (int) boss.randomBuffRadius(), 6);
    }

    public static void spawnBabyZombieHoard(AbstractWitherSkeleton boss) {
        spawnHoard(boss, true, (int) boss.randomBuffRadius(), 4);
    }

    private static void spawnHoard(AbstractWitherSkeleton boss, boolean isBaby, int radius, int amount) {
        Location baseLoc = boss.getBukkitEntity().getLocation();
        Random rand = new Random();
        for(int i = 0; i < amount; i++) {
            int r = rand.nextInt(radius * 2) - radius;
            Zombie zombie = ((Zombie) baseLoc.getWorld().spawnEntity(
                    baseLoc.add(rand.nextBoolean() ? new Vector(r, 0, 0) : new Vector(0, 0, r)),
                    EntityType.ZOMBIE));
            zombie.setBaby(isBaby);
            zombie.setMaxHealth(35);
            zombie.setHealth(35);
            AttributeInstance strength = ((CraftZombie)zombie).getHandle().getAttributeInstance(GenericAttributes.ATTACK_DAMAGE);
            strength.setValue(strength.getValue() + 2.0);

        }
        GeneralSkills.broadcastMessage(boss, "Have fun with your new friends...", 20);
    }
}
