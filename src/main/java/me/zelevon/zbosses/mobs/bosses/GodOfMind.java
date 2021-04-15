package me.zelevon.zbosses.mobs.bosses;

import me.zelevon.zbosses.config.Config;
import org.bukkit.Location;

public class GodOfMind extends AbstractWitherSkeleton {

    private Config.GodOfMind mob;
    private String name = "&6&lGod of Mind";

    public GodOfMind(Location location) {
        super(location);
        this.mob = this.getConf().getGodOfMind();
        this.setCustomHealth(mob.getHealth());
        this.updateName();
        this.setHelmet(this.parseItem(mob.getHelmet()));
        this.setChestplate(this.parseItem(mob.getChestplate()));
        this.setLeggings(this.parseItem(mob.getLeggings()));
        this.setBoots(this.parseItem(mob.getBoots()));
        this.setWeapon(this.parseItem(mob.getWeapon()));
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