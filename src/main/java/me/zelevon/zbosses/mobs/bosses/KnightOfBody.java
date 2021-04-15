package me.zelevon.zbosses.mobs.bosses;

import me.zelevon.zbosses.config.Config;
import me.zelevon.zbosses.tasks.BodyTask;
import org.bukkit.Location;

public class KnightOfBody extends AbstractWitherSkeleton {

    private Config.KnightOfBody mob;
    private String name = "&c&lKnight of Body";

    public KnightOfBody(Location location) {
        super(location);
        this.mob = this.getConf().getKnightOfBody();
        this.setCustomHealth(mob.getHealth());
        this.updateName();
        this.setHelmet(this.parseItem(mob.getHelmet()));
        this.setChestplate(this.parseItem(mob.getChestplate()));
        this.setLeggings(this.parseItem(mob.getLeggings()));
        this.setBoots(this.parseItem(mob.getBoots()));
        this.setWeapon(this.parseItem(mob.getWeapon()));
//        new BodyTask(this).runTaskTimerAsynchronously(this.getPlugin(), 0, 100);
        new BodyTask(this).runTaskTimer(this.getPlugin(), 0, 300);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double randomBuffRadius() {
        return mob.getRandomBuffRadius();
    }
}
