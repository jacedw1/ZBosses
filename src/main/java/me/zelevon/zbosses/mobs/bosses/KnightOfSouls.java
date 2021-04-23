package me.zelevon.zbosses.mobs.bosses;

import me.zelevon.zbosses.config.mobs.BossConf;
import me.zelevon.zbosses.config.mobs.KnightOfSoulsConf;
import me.zelevon.zbosses.mobs.minions.SoulMinion;
import me.zelevon.zbosses.tasks.bosses.MindTaskManager;
import me.zelevon.zbosses.tasks.bosses.SoulsTaskManager;
import org.bukkit.Location;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal", "unused"})
public class KnightOfSouls extends AbstractWitherSkeleton {

    private KnightOfSoulsConf mob;
    private String name = "&5&lKnight of Souls";
    private double damagePercent = 1.0;
    private SoulMinion minion1 = null;
    private SoulMinion minion2 = null;
    private SoulMinion minion3 = null;
    private SoulMinion minion4 = null;

    public KnightOfSouls(Location location, boolean delayMob) {
        super(location, delayMob);
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

    @Override
    public BossConf getBossConf() {
        return this.mob;
    }

    @Override
    public void runTask() {
        new SoulsTaskManager(this).runTaskTimer(this.getPlugin(), 0, 10);
    }

    public double getDamagePercent() {
        return damagePercent;
    }

    public void setDamagePercent(double damagePercent) {
        this.damagePercent = damagePercent;
    }

    public void spawnMinions() {
        this.minion1 = new SoulMinion(this);
        this.minion2 = new SoulMinion(this);
        this.minion3 = new SoulMinion(this);
        this.minion4 = new SoulMinion(this);
        this.setDamagePercent(this.getDamagePercent() - 0.8);
    }
}
