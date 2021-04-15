package me.zelevon.zbosses.tasks;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import org.bukkit.scheduler.BukkitRunnable;

public class RandomBuffTask extends BukkitRunnable {

    private AbstractWitherSkeleton boss;
    private ZBosses plugin;

    public RandomBuffTask(AbstractWitherSkeleton boss) {
        this.boss = boss;
        this.plugin = ZBosses.getInstance();
    }

    @Override
    public void run() {

    }
}
