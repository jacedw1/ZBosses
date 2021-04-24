package me.zelevon.zbosses.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@SuppressWarnings("FieldMayBeFinal")
@ConfigSerializable
public class RandomBuffConf {

    @Setting
    private int randomBuffTimer = 30;

    @Setting
    private double randomBuffRadius = 5.0D;

    @Setting
    private double fireballDamage = 3.0D;

    @Setting
    private int fireballAmount = 3;

    @Setting
    private String fireballMessage = "Fireballs!";

    @Setting
    private float lifeStealEffect = 0.1F;

    @Setting
    private long lifestealBuffDuration = 5L;

    @Setting
    private String lifestealMessage = "I'm going to absorb your soul...";

    @Setting
    private int speedBuffDuration = 10;

    @Setting
    private String speedMessage = "Speeding up...";

    @Setting
    private int strengthBuffDuration = 5;

    @Setting
    private String strengthMessage = "I feel powerful!";

    @Setting
    private int slownessBuffDuration = 5;

    @Setting
    private String slownessMessage = "Slow down.";

    @Setting
    private int miningFatigueBuffDuration = 10;

    @Setting
    private String miningFatigueMessage = "Stop swinging so quick.";

    @Setting
    private long noKnockbackBuffDuration = 5L;

    @Setting
    private String noKnockbackMessage = "You can't knock me back! (5s)";

    @Setting
    private float hoardMobHp = 35.0F;

    @Setting
    private double hoardMobDamage = 5.0D;

    @Setting
    private String hoardMessage = "Have fun with your new friends...";

    @Setting
    private String knockbackMessage = "Shockwave!";

    public int getRandomBuffTimer() {
        return randomBuffTimer * 20;
    }

    public double getRandomBuffRadius() {
        return randomBuffRadius;
    }

    public double getFireballDamage() {
        return fireballDamage;
    }

    public int getFireballAmount() {
        return fireballAmount;
    }

    public float getLifeStealEffect() {
        return lifeStealEffect;
    }

    public long getLifestealBuffDuration() {
        return lifestealBuffDuration * 20L;
    }

    public int getSpeedBuffDuration() {
        return speedBuffDuration * 20;
    }

    public int getStrengthBuffDuration() {
        return strengthBuffDuration * 20;
    }

    public int getSlownessBuffDuration() {
        return slownessBuffDuration * 20;
    }

    public int getMiningFatigueBuffDuration() {
        return miningFatigueBuffDuration * 20;
    }

    public long getNoKnockbackBuffDuration() {
        return noKnockbackBuffDuration * 20L;
    }

    public float getHoardMobHp() {
        return hoardMobHp;
    }

    public double getHoardMobDamage() {
        return hoardMobDamage;
    }

    public String getFireballMessage() {
        return fireballMessage;
    }

    public String getLifestealMessage() {
        return lifestealMessage;
    }

    public String getSpeedMessage() {
        return speedMessage;
    }

    public String getStrengthMessage() {
        return strengthMessage;
    }

    public String getSlownessMessage() {
        return slownessMessage;
    }

    public String getMiningFatigueMessage() {
        return miningFatigueMessage;
    }

    public String getNoKnockbackMessage() {
        return noKnockbackMessage;
    }

    public String getHoardMessage() {
        return hoardMessage;
    }

    public String getKnockbackMessage() {
        return knockbackMessage;
    }
}
