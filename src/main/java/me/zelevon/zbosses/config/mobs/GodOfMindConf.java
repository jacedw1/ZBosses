package me.zelevon.zbosses.config.mobs;

import me.zelevon.zbosses.config.Droptable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
@ConfigSerializable
public class GodOfMindConf implements BossConf {

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
    private String weapon = "DIAMOND_SWORD";

    @Setting
    private float health = 100;

    @Setting
    private float lifestealEffect = 0.15F;

    @Setting
    private float mindGuardHealth = 50.0F;

    @Setting
    private double mindGuardDamage = 5.0D;

    @Setting
    private String phaseTwoMessage = "Axe and you shall receive! (Phase 2)";

    @Setting
    private String phaseThreeMessage = "Apollo blesses me! (Phase 3)";

    @Setting
    private String phaseFourMessage = "Guards! Protect me! (Phase 4)";

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

    public float getLifestealEffect() {
        return lifestealEffect;
    }

    public float getMindGuardHealth() {
        return mindGuardHealth;
    }

    public double getMindGuardDamage() {
        return mindGuardDamage;
    }

    public String getPhaseTwoMessage() {
        return phaseTwoMessage;
    }

    public String getPhaseThreeMessage() {
        return phaseThreeMessage;
    }

    public String getPhaseFourMessage() {
        return phaseFourMessage;
    }
}
