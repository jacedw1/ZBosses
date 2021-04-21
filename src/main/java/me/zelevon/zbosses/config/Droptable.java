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

    public int getFirstHighestDamageRewardCountMin() {
        return firstHighestDamageRewardCountMin;
    }

    public int getFirstHighestDamageRewardCountMax() {
        return firstHighestDamageRewardCountMax;
    }

    public Map<String, Double> getFirstHighestDamageDrops() {
        return firstHighestDamageDrops;
    }

    public int getSecondHighestDamageRewardCountMin() {
        return secondHighestDamageRewardCountMin;
    }

    public int getSecondHighestDamageRewardCountMax() {
        return secondHighestDamageRewardCountMax;
    }

    public Map<String, Double> getSecondHighestDamageDrops() {
        return secondHighestDamageDrops;
    }

    public int getThirdHighestDamageRewardCountMin() {
        return thirdHighestDamageRewardCountMin;
    }

    public int getThirdHighestDamageRewardCountMax() {
        return thirdHighestDamageRewardCountMax;
    }

    public Map<String, Double> getThirdHighestDamageDrops() {
        return thirdHighestDamageDrops;
    }
}
