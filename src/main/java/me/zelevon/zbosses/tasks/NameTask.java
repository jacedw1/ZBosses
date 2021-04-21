package me.zelevon.zbosses.tasks;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.mobs.LivingMobManager;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class NameTask extends BukkitRunnable {

    private ZBosses plugin;
    private LivingMobManager mobManager;

    public NameTask(){
        this.plugin = ZBosses.getInstance();
        this.mobManager = this.plugin.getMobManager();
    }
    @Override
    public void run() {
        this.mobManager.updateNames();
    }
}
