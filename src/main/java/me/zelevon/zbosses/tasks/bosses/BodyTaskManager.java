package me.zelevon.zbosses.tasks.bosses;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.config.mobs.BossConf;
import me.zelevon.zbosses.config.mobs.KnightOfBodyConf;
import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import me.zelevon.zbosses.mobs.bosses.KnightOfBody;
import me.zelevon.zbosses.mobs.skills.GeneralSkills;
import me.zelevon.zbosses.tasks.RandomBuffTask;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.MobEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

@SuppressWarnings({"FieldMayBeFinal", "UnnecessaryReturnStatement", "FieldCanBeLocal"})
public class BodyTaskManager extends BukkitRunnable {
    private KnightOfBody boss;
    private ZBosses plugin;
    private BukkitScheduler scheduler;
    private BossConf bossConf;
    private BukkitTask randomBuffTask;
    private BukkitTask lightningTask;
    private BukkitTask blindTask;
    private boolean two = true;
    private boolean three = true;
    private boolean four = true;

    public BodyTaskManager(KnightOfBody boss) {
        this.boss = boss;
        this.plugin = boss.getPlugin();
        this.scheduler = Bukkit.getScheduler();
        this.bossConf = boss.getBossConf();
        this.randomBuffTask = new RandomBuffTask(this.boss).runTaskTimer(plugin, 0, boss.randomBuffTimer());
    }
    @Override
    public void run() {
        float health = this.boss.getHealth();
        float maxHealth = this.boss.getMaxHealth();
        if(health <= 0) {
            this.randomBuffTask.cancel();
            this.blindTask.cancel();
            this.cancel();
            return;
        }
        if(four && health <= .25F * maxHealth) {
            four = false;
            GeneralSkills.broadcastMessage(boss, ((KnightOfBodyConf)bossConf).getPhaseFourMessage(), 20);
            boss.setCanLifeSteal(true);
            boss.setLifeStealPercent(0.25F);
            return;
        }
        if(three && health <= .5F * maxHealth) {
            three = false;
            GeneralSkills.broadcastMessage(boss, ((KnightOfBodyConf)bossConf).getPhaseThreeMessage(), 20);
            lightningTask.cancel();
            this.boss.resetSpeed();
            boss.addEffect(new MobEffect(5, 100000, 1));
            this.blindTask = scheduler.runTaskTimer(plugin, this::blindPlayers, 0, 200);
            return;
        }
        if(two && health <= .75F * maxHealth) {
            two = false;
            GeneralSkills.broadcastMessage(boss, ((KnightOfBodyConf)bossConf).getPhaseTwoMessage(), 20);
            this.lightningTask = scheduler.runTaskTimerAsynchronously(plugin, () -> {
                GeneralSkills.bossCountdown(boss);
                scheduler.runTaskLater(plugin, this::lightningStrike, 60L);
            }, 20, 300);
            return;
        }
    }


    public void lightningStrike() {
        List<Player> players = GeneralSkills.getNearbyPlayers(boss, 10);
        if(players.size() == 0) {
            return;
        }
        for (Player player : players) {
            player.getWorld().strikeLightningEffect(player.getLocation());
            double damage = 3.0D;
            float speedIncreasePercent = 2.0F;
            if (player.isBlocking()) {
                damage = 1.0D;
                speedIncreasePercent = 1.0F;
            }
            player.damage(damage);
            AttributeInstance speed = boss.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
            speed.setValue(speed.getValue() * (1.0F + (speedIncreasePercent / 100F)));
        }
        GeneralSkills.broadcastMessage(boss, boss.getConf().getLightningMessage(), 20);
    }

    public void blindPlayers() {
        List<Player> players = GeneralSkills.getNearbyPlayers(boss, 15);
        if(players.size() == 0) {
            return;
        }
        PotionEffect blindness = PotionEffectType.BLINDNESS.createEffect(300, 4);
        for(Player player : players) {
            player.addPotionEffect(blindness);
        }
        GeneralSkills.broadcastMessage(boss, ((KnightOfBodyConf)bossConf).getBlindMessage(), 15);
    }
}
