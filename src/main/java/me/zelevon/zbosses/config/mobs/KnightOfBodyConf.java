package me.zelevon.zbosses.config.mobs;

import me.zelevon.zbosses.config.Droptable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
@ConfigSerializable
public class KnightOfBodyConf implements BossConf {

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
    private String phaseTwoMessage = "Electricity courses through me! (Phase 2)";

    @Setting
    private String phaseThreeMessage = "More power... (Phase 3)";

    @Setting
    private String phaseFourMessage = "Your flesh heals me! (Phase 4)";

    @Setting
    private String blindMessage = "Blinding, isn't it?";

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

    public String getPhaseTwoMessage() {
        return phaseTwoMessage;
    }

    public String getPhaseThreeMessage() {
        return phaseThreeMessage;
    }

    public String getPhaseFourMessage() {
        return phaseFourMessage;
    }

    public String getBlindMessage() {
        return blindMessage;
    }
}
