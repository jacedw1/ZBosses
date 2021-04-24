package me.zelevon.zbosses.mobs.bosses;

import me.zelevon.zbosses.config.mobs.BossConf;
import me.zelevon.zbosses.config.mobs.KnightOfHeartsConf;
import me.zelevon.zbosses.tasks.bosses.HeartsTaskManager;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class KnightOfHearts extends AbstractWitherSkeleton {

    private KnightOfHeartsConf mob;
    private String name = "&d&lKnight of Hearts";
    private List<EnderCrystal> crystals;

    public KnightOfHearts(Location location, boolean delayMob) {
        super(location, delayMob);
        this.mob = this.getConf().getKnightOfHearts();
        this.setCustomHealth(mob.getHealth());
        this.updateName();
        this.setHelmet(this.parseItem(mob.getHelmet()));
        this.setChestplate(this.parseItem(mob.getChestplate()));
        this.setLeggings(this.parseItem(mob.getLeggings()));
        this.setBoots(this.parseItem(mob.getBoots()));
        this.setWeapon(this.parseItem(mob.getWeapon()));
        this.crystals = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BossConf getBossConf() {
        return this.mob;
    }

    @Override
    public void runTask() {
        new HeartsTaskManager(this).runTaskTimer(this.getPlugin(), 0, 10);
    }

    public void addCrystal(EnderCrystal crystal) {
        this.crystals.add(crystal);
    }

    public boolean crystalIsAlive() {
        List<Entity> minions = this.getMobManager().getMinions();
        boolean ret = false;
        for(Entity minion : minions) {
            if(minion instanceof EnderCrystal) {
                ret = true;
                break;
            }
        }
        return ret;
    }
}
