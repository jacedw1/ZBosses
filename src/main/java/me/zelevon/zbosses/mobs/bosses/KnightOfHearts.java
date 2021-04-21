package me.zelevon.zbosses.mobs.bosses;

import me.zelevon.zbosses.config.mobs.BossConf;
import me.zelevon.zbosses.config.mobs.KnightOfHeartsConf;
import me.zelevon.zbosses.tasks.bosses.HeartsTaskManager;
import org.bukkit.Location;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class KnightOfHearts extends AbstractWitherSkeleton {

    private KnightOfHeartsConf mob;
    private String name = "&d&lKnight of Hearts";

    public KnightOfHearts(Location location) {
        super(location);
        this.mob = this.getConf().getKnightOfHearts();
        this.setCustomHealth(mob.getHealth());
        this.updateName();
        this.setHelmet(this.parseItem(mob.getHelmet()));
        this.setChestplate(this.parseItem(mob.getChestplate()));
        this.setLeggings(this.parseItem(mob.getLeggings()));
        this.setBoots(this.parseItem(mob.getBoots()));
        this.setWeapon(this.parseItem(mob.getWeapon()));
        new HeartsTaskManager(this).runTaskTimer(this.getPlugin(), 0, 10);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double randomBuffRadius() {
        return mob.getRandomBuffRadius();
    }

    @Override
    public BossConf getBossConf() {
        return this.mob;
    }
}
