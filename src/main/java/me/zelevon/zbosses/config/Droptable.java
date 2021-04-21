package me.zelevon.zbosses.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
@ConfigSerializable
public class Droptable {

    @Setting
    private int firstHighestDamageRewardCountMin = 1;

    @Setting
    private int firstHighestDamageRewardCountMax = 3;

    @Setting
    private Map<String, Double> firstHighestDamageDrops = new HashMap<String, Double>() {{
        put("give %player% diamond 3", 0.5D);
        put("give %player% emerald 3", 0.5D);
    }};

    @Setting
    private int secondHighestDamageRewardCountMin = 1;

    @Setting
    private int secondHighestDamageRewardCountMax = 3;

    @Setting
    private Map<String, Double> secondHighestDamageDrops = new HashMap<String, Double>() {{
        put("give %player% diamond 2", 0.5D);
        put("give %player% emerald 2", 0.5D);
    }};

    @Setting
    private int thirdHighestDamageRewardCountMin = 1;

    @Setting
    private int thirdHighestDamageRewardCountMax = 3;

    @Setting
    private Map<String, Double> thirdHighestDamageDrops = new HashMap<String, Double>() {{
        put("give %player% diamond 1", 0.5D);
        put("give %player% emerald 1", 0.5D);
    }};

    public int getFirstMin() {
        return firstHighestDamageRewardCountMin;
    }

    public int getFirstMax() {
        return firstHighestDamageRewardCountMax;
    }

    public Map<String, Double> getFirstDrops() {
        return firstHighestDamageDrops;
    }

    public int getSecondMin() {
        return secondHighestDamageRewardCountMin;
    }

    public int getSecondMax() {
        return secondHighestDamageRewardCountMax;
    }

    public Map<String, Double> getSecondDrops() {
        return secondHighestDamageDrops;
    }

    public int getThirdMin() {
        return thirdHighestDamageRewardCountMin;
    }

    public int getThirdMax() {
        return thirdHighestDamageRewardCountMax;
    }

    public Map<String, Double> getThirdDrops() {
        return thirdHighestDamageDrops;
    }
}
