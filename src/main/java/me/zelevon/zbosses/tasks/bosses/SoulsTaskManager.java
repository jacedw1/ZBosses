package me.zelevon.zbosses.tasks.bosses;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.config.mobs.BossConf;
import me.zelevon.zbosses.mobs.bosses.KnightOfSouls;
import me.zelevon.zbosses.tasks.RandomBuffTask;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

@SuppressWarnings({"FieldMayBeFinal", "UnnecessaryReturnStatement", "FieldCanBeLocal"})
public class SoulsTaskManager extends BukkitRunnable {

    private KnightOfSouls boss;
    private ZBosses plugin;
    private BukkitScheduler scheduler;
    private BossConf bossConf;
    private BukkitTask randomBuffTask;
    private boolean two = true;
    private boolean three = true;

    public SoulsTaskManager(KnightOfSouls boss) {
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
            this.cancel();
            return;
        }
        if(health <= .25F * maxHealth && three) {
            three = false;
            return;
        }
        if(health <= .75F * maxHealth && two) {
            two = false;
            return;
        }
    }
}
