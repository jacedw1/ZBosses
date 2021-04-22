package me.zelevon.zbosses.tasks.bosses;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.config.mobs.BossConf;
import me.zelevon.zbosses.mobs.bosses.KnightOfSouls;
import me.zelevon.zbosses.mobs.skills.GeneralSkills;
import me.zelevon.zbosses.mobs.skills.RandomBuffs;
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
    private BukkitTask fireballTask;
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
            this.fireballTask.cancel();
            this.cancel();
            return;
        }
        if(three && health <= .25F * maxHealth) {
            three = false;
            boss.spawnMinions();
            GeneralSkills.broadcastMessage(boss, "Souls of the dead protect me! (Phase 3)", 20);
            return;
        }
        if(two && health <= .75F * maxHealth) {
            two = false;
            boss.setDamagePercent(0.85);
            this.fireballTask = scheduler.runTaskTimer(plugin, () -> RandomBuffs.fireballBuff(this.boss, 5), 300, 300);
            GeneralSkills.broadcastMessage(boss, "Prepare to meet a fiery doom! (Phase 2)", 20);
            return;
        }
    }
}
