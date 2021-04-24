package me.zelevon.zbosses.tasks.bosses;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.config.mobs.BossConf;
import me.zelevon.zbosses.config.mobs.GodOfMindConf;
import me.zelevon.zbosses.mobs.bosses.GodOfMind;
import me.zelevon.zbosses.mobs.skills.GeneralSkills;
import me.zelevon.zbosses.mobs.skills.RandomBuffs;
import me.zelevon.zbosses.tasks.RandomBuffTask;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Random;

@SuppressWarnings({"FieldMayBeFinal", "UnnecessaryReturnStatement", "FieldCanBeLocal"})
public class MindTaskManager extends BukkitRunnable {

    private GodOfMind boss;
    private ZBosses plugin;
    private BukkitScheduler scheduler;
    private BossConf bossConf;
    private BukkitTask randomBuffTask;
    private BukkitTask knockbackTask;
    private BukkitTask lightningTask;
    private boolean two = true;
    private boolean three = true;
    private boolean four = true;

    public MindTaskManager(GodOfMind boss) {
        this.boss = boss;
        this.plugin = boss.getPlugin();
        this.scheduler = Bukkit.getScheduler();
        this.bossConf = boss.getBossConf();
        this.randomBuffTask = new RandomBuffTask(this.boss).runTaskTimer(plugin, 0, boss.randomBuffTimer());
        this.knockbackTask = scheduler.runTaskTimerAsynchronously(plugin, () -> RandomBuffs.knockbackPlayerBuff(this.boss), 500L, 500L);
    }

    @Override
    public void run() {
        float health = this.boss.getHealth();
        float maxHealth = this.boss.getMaxHealth();

        if(health <= 0) {
            this.randomBuffTask.cancel();
            this.cancel();
            return;
        }
        if(four && health <= .25F * maxHealth) {
            four = false;
            this.lightningTask.cancel();
            boss.setWeapon(new ItemStack(Items.DIAMOND_HOE));
            boss.resetPathfinder();
            boss.addEffect(new MobEffect(1, 100000, 2));
            boss.setCanLifeSteal(true);
            boss.setLifeStealPercent(((GodOfMindConf)bossConf).getLifestealEffect());
            boss.spawnGuards();
            GeneralSkills.broadcastMessage(boss, ((GodOfMindConf) bossConf).getPhaseFourMessage(), 20);
            return;
        }
        if(three && health <= .5F * maxHealth) {
            three = false;
            this.lightningTask.cancel();
            boss.setAxePhase(false);
            boss.setInvuln(true);
            RandomBuffs.knockbackPlayerBuff(boss);
            AttributeInstance speed = boss.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
            speed.setValue(0);
            GeneralSkills.bossCountdown(boss);
            scheduler.runTaskLaterAsynchronously(plugin, () -> {
                boss.setWeapon(new ItemStack(Items.BOW));
                boss.startBowAttacks();
                boss.resetSpeed();
                boss.setInvuln(false);
                GeneralSkills.broadcastMessage(boss, ((GodOfMindConf) bossConf).getPhaseThreeMessage(), 20);
            }, 60);
            this.lightningTask = scheduler.runTaskTimerAsynchronously(plugin, () -> lightningStrike(boss.getLastTarget()), 260, 200);
            return;
        }
        if(two && health <= .75F * maxHealth) {
            two = false;
            boss.setWeapon(new ItemStack(Items.DIAMOND_AXE));
            boss.setAxePhase(true);
            this.knockbackTask.cancel();
            this.lightningTask = scheduler.runTaskTimerAsynchronously(plugin, () -> {
                List<Player> players = GeneralSkills.getNearbyPlayers(boss, 10);
                if(players.size() == 0) {
                    return;
                }
                lightningStrike(players.get(new Random().nextInt(players.size())));
            }, 500, 500);
            boss.setDot(false);
            GeneralSkills.broadcastMessage(boss, ((GodOfMindConf) bossConf).getPhaseTwoMessage(), 20);
            return;
        }
    }

    private void lightningStrike(Player target) {
        if(target == null) {
            return;
        }
        GeneralSkills.bossCountdown(boss);
        scheduler.runTaskLater(plugin, () -> {
            target.getWorld().strikeLightningEffect(target.getLocation());
            double damage = 8.0D;
            if (target.isBlocking()) {
                damage /= 4.0D;
            }
            target.damage(damage);
            GeneralSkills.broadcastMessage(boss, boss.getConf().getLightningMessage(), 20);
        }, 60L);
    }
}
