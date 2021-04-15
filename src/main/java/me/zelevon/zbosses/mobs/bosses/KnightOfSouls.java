package me.zelevon.zbosses.mobs.bosses;

import me.zelevon.zbosses.config.Config;
import org.bukkit.Location;

public class KnightOfSouls extends AbstractWitherSkeleton {

    private Config.KnightOfSouls mob;
    private String name = "&5&lKnight of Souls";

    public KnightOfSouls(Location location){
        super(location);
        this.mob = this.getConf().getKnightOfSouls();
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
