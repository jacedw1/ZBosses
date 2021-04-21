package me.zelevon.zbosses.mobs.bosses;

import me.zelevon.zbosses.config.mobs.BossConf;
import me.zelevon.zbosses.config.mobs.KnightOfBodyConf;
import me.zelevon.zbosses.tasks.bosses.BodyTaskManager;
import org.bukkit.Location;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class KnightOfBody extends AbstractWitherSkeleton {

    private KnightOfBodyConf mob;
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
        new BodyTaskManager(this).runTaskTimer(this.getPlugin(), 0, 10);
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
