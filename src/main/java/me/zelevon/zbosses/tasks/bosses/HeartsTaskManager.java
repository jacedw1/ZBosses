package me.zelevon.zbosses.tasks.bosses;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.config.mobs.BossConf;
import me.zelevon.zbosses.config.mobs.KnightOfHeartsConf;
import me.zelevon.zbosses.mobs.LivingMobManager;
import me.zelevon.zbosses.mobs.bosses.KnightOfHearts;
import me.zelevon.zbosses.mobs.skills.GeneralSkills;
import me.zelevon.zbosses.mobs.skills.RandomBuffs;
import me.zelevon.zbosses.tasks.RandomBuffTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class HeartsTaskManager extends BukkitRunnable {

    private KnightOfHearts boss;
    private ZBosses plugin;
    private LivingMobManager mobManager;
    private BukkitScheduler scheduler;
    private BossConf bossConf;
    private BukkitTask randomBuffTask;
    private BukkitTask knockbackTask;
    private boolean two = true;
    private boolean cancel = true;

    public HeartsTaskManager(KnightOfHearts boss) {
        this.boss = boss;
        this.plugin = boss.getPlugin();
        this.mobManager = plugin.getMobManager();
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
            this.knockbackTask.cancel();
            this.cancel();
            return;
        }
        if(two && health <= .5F * maxHealth) {
            two = false;
            GeneralSkills.broadcastMessage(boss, ((KnightOfHeartsConf)bossConf).getPhaseTwoMessage(), 20);
            Random rand = new Random();
            Location bossLoc = boss.getBukkitEntity().getLocation();
            World w = bossLoc.getWorld();
            int r = ((KnightOfHeartsConf)bossConf).getMaxCrystalRadius();
            int maxX = (int) (bossLoc.getX() + r);
            int minX = (int) (bossLoc.getX() - r);
            int minY = (int) bossLoc.getY() + 3;
            int maxY = (int) bossLoc.getY() + 10;
            int maxZ = (int) (bossLoc.getZ() + r);
            int minZ = (int) (bossLoc.getZ() - r);
            for(int i = 0; i < ((KnightOfHeartsConf)bossConf).getNumCrystals(); i++) {
                int x = rand.nextInt(maxX + 1 - minX) + minX;
                int y = rand.nextInt(maxY + 1 - minY) + minY;
                int z = rand.nextInt(maxZ + 1 - minZ) + minZ;
                EnderCrystal crystal = (EnderCrystal) bossLoc.getWorld().spawnEntity(new Location(w, x, y, z), EntityType.ENDER_CRYSTAL);
                mobManager.addMinion(crystal);
                boss.addCrystal(crystal);
            }
            long kbt = ((KnightOfHeartsConf)bossConf).getKnockbackTimer();
            this.knockbackTask = scheduler.runTaskTimerAsynchronously(plugin, () -> RandomBuffs.knockbackPlayerBuff(this.boss), kbt, kbt);
            return;
        }
        if(cancel && !two && !mobManager.isAlive(EnderCrystal.class, false)) {
            cancel = false;
            this.knockbackTask.cancel();
        }
    }
}
