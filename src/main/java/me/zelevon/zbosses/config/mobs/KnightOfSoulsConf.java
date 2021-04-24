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
    private float health = 100.0F;

    @Setting
    private float soulMinionHealth = 50.0F;

    @Setting
    private double soulMinionDamage = 5.0D;

    @Setting
    private String phaseTwoMessage = "Prepare to meet a fiery doom! (Phase 2)";

    @Setting
    private String phaseThreeMessage = "Souls of the dead protect me! (Phase 3)";

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

    public float getSoulMinionHealth() {
        return soulMinionHealth;
    }

    public double getSoulMinionDamage() {
        return soulMinionDamage;
    }

    public String getPhaseTwoMessage() {
        return phaseTwoMessage;
    }

    public String getPhaseThreeMessage() {
        return phaseThreeMessage;
    }
}
