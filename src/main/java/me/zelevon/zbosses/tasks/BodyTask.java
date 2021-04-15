package me.zelevon.zbosses.tasks;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.mobs.LivingMobManager;
import me.zelevon.zbosses.mobs.bosses.KnightOfBody;
import me.zelevon.zbosses.mobs.skills.RandomBuffs;
import org.bukkit.scheduler.BukkitRunnable;

public class BodyTask extends BukkitRunnable {

    private KnightOfBody mob;
    private ZBosses plugin;
    private LivingMobManager mobManager;

    public BodyTask(KnightOfBody mob){
        this.mob = mob;
        this.plugin = mob.getPlugin();
        this.mobManager = plugin.getMobManager();
    }

    @Override
    public void run() {
        if(this.mob.getHealth() <= 0){
            this.cancel();
            mobManager.removeBoss(this.mob);
            return;
        }
        RandomBuffs.fireballBuff(this.mob);
    }
}
