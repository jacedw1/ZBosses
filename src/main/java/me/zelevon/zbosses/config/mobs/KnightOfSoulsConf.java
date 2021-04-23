package me.zelevon.zbosses.config.mobs;

import me.zelevon.zbosses.config.Droptable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
@ConfigSerializable
public class KnightOfSoulsConf implements BossConf {

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
    private int health = 100;

    @Setting
    private int randomBuffTimer = 30;

    @Setting
    private double randomBuffRadius = 5.0;

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

    public int getHealth() {
        return health;
    }

    @Override
    public int getRandomBuffTimer() {
        return randomBuffTimer * 20;
    }

    public double getRandomBuffRadius() {
        return randomBuffRadius;
    }
}
