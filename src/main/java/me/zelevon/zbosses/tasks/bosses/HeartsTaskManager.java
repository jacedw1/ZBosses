package me.zelevon.zbosses.tasks.bosses;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.config.mobs.BossConf;
import me.zelevon.zbosses.config.mobs.KnightOfHeartsConf;
import me.zelevon.zbosses.mobs.LivingMobManager;
import me.zelevon.zbosses.mobs.bosses.KnightOfHearts;
import me.zelevon.zbosses.mobs.skills.RandomBuffs;
import me.zelevon.zbosses.tasks.RandomBuffTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

@SuppressWarnings({"FieldMayBeFinal", "UnnecessaryReturnStatement", "FieldCanBeLocal"})
public class HeartsTaskManager extends BukkitRunnable {

    private KnightOfHearts boss;
    private ZBosses plugin;
    private LivingMobManager mobManager;
    private BukkitScheduler scheduler;
    private BossConf bossConf;
    private BukkitTask randomBuffTask;
    private BukkitTask knockbackTask;
    private boolean half = false;

    public HeartsTaskManager(KnightOfHearts boss) {
        this.boss = boss;
        this.plugin = boss.getPlugin();
        this.mobManager = plugin.getMobManager();
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
            this.knockbackTask.cancel();
            this.cancel();
            return;
        }
        if(health <= .5F * maxHealth && !half) {
            half = true;
            Location bossLoc = boss.getBukkitEntity().getLocation();
            EnderCrystal crystal1 = (EnderCrystal) bossLoc.getWorld().spawnEntity(bossLoc.add(10, 5, 10), EntityType.ENDER_CRYSTAL);
            EnderCrystal crystal2 = (EnderCrystal) bossLoc.getWorld().spawnEntity(bossLoc.add(-10, 5, -10), EntityType.ENDER_CRYSTAL);
            mobManager.addMinion(crystal1);
            mobManager.addMinion(crystal2);
            long kbt = ((KnightOfHeartsConf)bossConf).getKnockbackTimer();
            this.knockbackTask = scheduler.runTaskTimer(plugin, () -> RandomBuffs.knockbackPlayerBuff(this.boss), kbt, kbt);
            return;
        }
        if(half && !mobManager.isAlive(EnderCrystal.class, false)) {
            this.knockbackTask.cancel();
        }
    }
}
