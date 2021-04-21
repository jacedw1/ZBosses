package me.zelevon.zbosses.tasks.bosses;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.config.mobs.BossConf;
import me.zelevon.zbosses.mobs.bosses.KnightOfBody;
import me.zelevon.zbosses.mobs.skills.BodySkills;
import me.zelevon.zbosses.tasks.RandomBuffTask;
import net.minecraft.server.v1_8_R3.MobEffect;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

@SuppressWarnings({"FieldMayBeFinal", "UnnecessaryReturnStatement", "FieldCanBeLocal"})
public class BodyTaskManager extends BukkitRunnable {
    private KnightOfBody boss;
    private ZBosses plugin;
    private BukkitScheduler scheduler;
    private BossConf bossConf;
    private BukkitTask randomBuffTask;
    private BukkitTask lightningTask;
    private BukkitTask blindTask;

    public BodyTaskManager(KnightOfBody boss) {
        this.boss = boss;
        this.plugin = boss.getPlugin();
        this.scheduler = Bukkit.getScheduler();
        this.bossConf = boss.getBossConf();
        this.randomBuffTask = new RandomBuffTask(this.boss).runTaskTimer(plugin, 0, bossConf.getRandomBuffTimer());
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
        if(health <= .25F * maxHealth) {
            //send a one time message
            boss.setCanLifeSteal(true);
            boss.setLifeStealPercent(0.25F);
            return;
        }
        if(health <= .5F * maxHealth) {
            //send a one time message
            if(lightningTask != null) {
                lightningTask.cancel();
                this.boss.resetSpeed();
                lightningTask = null;
            }
            if(!boss.hasEffect(5)) {
                boss.addEffect(new MobEffect(5, 100000, 1));
            }
            if(blindTask == null) {
                this.blindTask = scheduler.runTaskTimer(plugin, () -> BodySkills.blindPlayers(this.boss), 0, 200);
            }
            return;
        }
        if(health <= .75F * maxHealth) {
            //send a one time message
            if (lightningTask == null) {
                this.lightningTask = scheduler.runTaskTimer(plugin, () -> BodySkills.lightningStrike(this.boss), 0, 300);
            }
            return;
        }
    }
}
