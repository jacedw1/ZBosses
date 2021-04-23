package me.zelevon.zbosses.mobs.bosses;

import me.zelevon.zbosses.config.mobs.BossConf;
import me.zelevon.zbosses.config.mobs.KnightOfHeartsConf;
import me.zelevon.zbosses.tasks.bosses.HeartsTaskManager;
import me.zelevon.zbosses.tasks.bosses.MindTaskManager;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;

import java.util.List;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class KnightOfHearts extends AbstractWitherSkeleton {

    private KnightOfHeartsConf mob;
    private String name = "&d&lKnight of Hearts";
    private EnderCrystal crystal1 = null;
    private EnderCrystal crystal2 = null;

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

    @Override
    public void runTask() {
        new HeartsTaskManager(this).runTaskTimer(this.getPlugin(), 0, 10);
    }

    public EnderCrystal getCrystal1() {
        return crystal1;
    }

    public void setCrystal1(EnderCrystal crystal1) {
        this.crystal1 = crystal1;
    }

    public EnderCrystal getCrystal2() {
        return crystal2;
    }

    public void setCrystal2(EnderCrystal crystal2) {
        this.crystal2 = crystal2;
    }

    public boolean crystalIsAlive() {
        List<Entity> minions = this.getMobManager().getMinions();
        return minions.contains(crystal1) || minions.contains(crystal2);
    }
}
