package me.zelevon.zbosses.config.mobs;

import me.zelevon.zbosses.config.Droptable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
@ConfigSerializable
public class KnightOfHeartsConf implements BossConf {

    @Setting
    private Droptable dropsNormal;

    @Setting
    private Droptable dropsDelay;

    @Setting
    private String helmet = "DIAMOND_HELMET";

    @Setting
    private String chestplate = "DIAMOND_CHESTPLATE";

    @Setting
    private String leggings = "DIAMOND_LEGGINGS";

    @Setting
    private String boots = "DIAMOND_BOOTS";

    @Setting
    private String weapon = "AIR";

    @Setting
    private float health = 100;

    @Setting
    private int knockbackTimer = 5;

    @Setting
    private int numCrystals = 2;

    @Setting
    private int maxCrystalRadius = 20;

    @Setting
    private String phaseTwoMessage = "My crystals will be your demise! (Phase 2)";

    @Override
    public Droptable getNormalDrops() {
        return dropsNormal;
    }

    @Override
    public Droptable getDelayDrops() {
        return dropsDelay;
    }

    public String getHelmet() {
        return helmet;
    }

    public String getChestplate() {
        return chestplate;
    }

    public String getLeggings() {
        return leggings;
    }

    public String getBoots() {
        return boots;
    }

    public String getWeapon() {
        return weapon;
    }

    public float getHealth() {
        return health;
    }

    public int getKnockbackTimer() {
        return knockbackTimer * 20;
    }

    public int getNumCrystals() {
        return numCrystals;
    }

    public int getMaxCrystalRadius() {
        return maxCrystalRadius;
    }

    public String getPhaseTwoMessage() {
        return phaseTwoMessage;
    }
}
