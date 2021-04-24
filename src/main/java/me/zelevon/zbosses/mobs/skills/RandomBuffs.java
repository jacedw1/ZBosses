package me.zelevon.zbosses.mobs.skills;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.config.RandomBuffConf;
import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import me.zelevon.zbosses.tasks.FireballTask;
import me.zelevon.zbosses.tasks.RandomBuffTask;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.MobEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

@SuppressWarnings("SameParameterValue")
public class RandomBuffs {

    public static void speedBuff(AbstractWitherSkeleton boss) {
        boss.addEffect(new MobEffect(1, boss.getConf().getRandomBuffs().getSpeedBuffDuration(), 1));
        GeneralSkills.broadcastMessage(boss, boss.getConf().getRandomBuffs().getSpeedMessage(), 20);
    }

    public static void strengthBuff(AbstractWitherSkeleton boss) {
        boss.addEffect(new MobEffect(5, boss.getConf().getRandomBuffs().getStrengthBuffDuration(), 0));
        GeneralSkills.broadcastMessage(boss, boss.getConf().getRandomBuffs().getStrengthMessage(), 20);
    }

    public static void slownessBuff(AbstractWitherSkeleton boss) {
        MobEffect slowness = new MobEffect(2, boss.getConf().getRandomBuffs().getSlownessBuffDuration(), 1);
        List<Player> players = GeneralSkills.getNearbyPlayers(boss, boss.randomBuffRadius());
        for(Player player : players) {
            ((CraftPlayer)player).getHandle().addEffect(slowness);
        }
        GeneralSkills.broadcastMessage(boss, boss.getConf().getRandomBuffs().getSlownessMessage(), 20);
    }

    public static void miningFatigueBuff(AbstractWitherSkeleton boss) {
        MobEffect miningFatigue = new MobEffect(4, boss.getConf().getRandomBuffs().getMiningFatigueBuffDuration(), 2);
        List<Player> players = GeneralSkills.getNearbyPlayers(boss, boss.randomBuffRadius());
        for(Player player : players) {
            ((CraftPlayer)player).getHandle().addEffect(miningFatigue);
        }
        GeneralSkills.broadcastMessage(boss, boss.getConf().getRandomBuffs().getMiningFatigueMessage(), 20);
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
        GeneralSkills.broadcastMessage(boss, boss.getConf().getRandomBuffs().getKnockbackMessage(), 20);
    }

    public static void noKnockbackBuff(AbstractWitherSkeleton boss) {
        boss.getAttributeInstance(GenericAttributes.c).setValue(1.0D);
        Bukkit.getScheduler().runTaskLater(ZBosses.getInstance(), () -> boss.getAttributeInstance(GenericAttributes.c).setValue(0.0D), boss.getConf().getRandomBuffs().getNoKnockbackBuffDuration());
        GeneralSkills.broadcastMessage(boss, boss.getConf().getRandomBuffs().getNoKnockbackMessage(), 20);
    }

    public static void lifeStealBuff(AbstractWitherSkeleton boss) {
        if(boss.canLifeSteal()) {
            new RandomBuffTask(boss).run();
            return;
        }
        boss.setCanLifeSteal(true);
        Bukkit.getScheduler().runTaskLater(ZBosses.getInstance(), () -> boss.setCanLifeSteal(false), boss.getConf().getRandomBuffs().getLifestealBuffDuration());
        GeneralSkills.broadcastMessage(boss, boss.getConf().getRandomBuffs().getLifestealMessage(), 20);
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
        GeneralSkills.broadcastMessage(boss, boss.getConf().getRandomBuffs().getFireballMessage(), 20);
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
        RandomBuffConf conf = boss.getConf().getRandomBuffs();
        for(int i = 0; i < amount; i++) {
            int r = rand.nextInt(radius * 2) - radius;
            Zombie zombie = ((Zombie) baseLoc.getWorld().spawnEntity(
                    baseLoc.add(rand.nextBoolean() ? new Vector(r, 0, 0) : new Vector(0, 0, r)),
                    EntityType.ZOMBIE));
            zombie.setBaby(isBaby);
            zombie.setMaxHealth(conf.getHoardMobHp());
            zombie.setHealth(conf.getHoardMobHp());
            AttributeInstance strength = ((CraftZombie)zombie).getHandle().getAttributeInstance(GenericAttributes.ATTACK_DAMAGE);
            strength.setValue(conf.getHoardMobDamage());

        }
        GeneralSkills.broadcastMessage(boss, boss.getConf().getRandomBuffs().getHoardMessage(), 20);
    }
}
