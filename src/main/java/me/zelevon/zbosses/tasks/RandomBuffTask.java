package me.zelevon.zbosses.tasks;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import me.zelevon.zbosses.mobs.skills.RandomBuffs;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Random;

@SuppressWarnings("FieldMayBeFinal")
public class RandomBuffTask extends BukkitRunnable {

    private AbstractWitherSkeleton boss;
    private ZBosses plugin;
    private Random rand;
    private BukkitScheduler scheduler;

    public RandomBuffTask(AbstractWitherSkeleton boss) {
        this.boss = boss;
        this.plugin = ZBosses.getInstance();
        this.rand = new Random();
        this.scheduler = Bukkit.getScheduler();
    }

    @Override
    public void run() {
        int r = rand.nextInt(10);
        switch(r) {
            case 0:
                scheduler.runTaskAsynchronously(plugin, () -> RandomBuffs.noKnockbackBuff(boss));
                break;
            case 1:
                RandomBuffs.spawnZombieHoard(boss);
                break;
            case 2:
                RandomBuffs.spawnBabyZombieHoard(boss);
                break;
            case 3:
                scheduler.runTaskAsynchronously(plugin, () -> RandomBuffs.knockbackPlayerBuff(boss));
                break;
            case 4:
                RandomBuffs.speedBuff(boss);
                break;
            case 5:
                RandomBuffs.miningFatigueBuff(boss);
                break;
            case 6:
                RandomBuffs.slownessBuff(boss);
                break;
            case 7:
                RandomBuffs.strengthBuff(boss);
                break;
            case 8:
                RandomBuffs.lifeStealBuff(boss);
                break;
            case 9:
                RandomBuffs.fireballBuff(boss, 3);
                break;
            default:
                break;
        }
    }
}
