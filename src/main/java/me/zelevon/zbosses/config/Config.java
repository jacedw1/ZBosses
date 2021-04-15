package me.zelevon.zbosses.config;

import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.io.File;
import java.io.IOException;

public class Config {

    public static MobsConf setup(){
        try {
            File directory = new File("plugins\\Z-Bosses");
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(directory, "mobs.json");
            if (file.exists() || file.createNewFile()) {
                final GsonConfigurationLoader loader = GsonConfigurationLoader.builder()
                        .defaultOptions(opts -> opts.shouldCopyDefaults(true))
                        .path(file.toPath())
                        .build();
                BasicConfigurationNode node = loader.load();
                MobsConf conf = node.get(Config.MobsConf.class);
                loader.save(node);
                return conf;
            }

        } catch  (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @ConfigSerializable
    public static class MobsConf {

        @Setting
        private KnightOfHearts knightOfHearts;

        @Setting
        private KnightOfSouls knightOfSouls;

        @Setting
        private KnightOfBody knightOfBody;

        @Setting
        private GodOfMind godOfMind;

        public KnightOfHearts getKnightOfHearts() {
            return knightOfHearts;
        }

        public KnightOfSouls getKnightOfSouls() {
            return knightOfSouls;
        }

        public KnightOfBody getKnightOfBody() {
            return knightOfBody;
        }

        public GodOfMind getGodOfMind() {
            return godOfMind;
        }
    }

    @ConfigSerializable
    public static class KnightOfHearts {
        
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

        public int getRandomBuffTimer() {
            return randomBuffTimer;
        }
        
        public double getRandomBuffRadius() {
            return randomBuffRadius;
        }
    }

    @ConfigSerializable
    public static class KnightOfSouls {

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

        public int getRandomBuffTimer() {
            return randomBuffTimer;
        }

        public double getRandomBuffRadius() {
            return randomBuffRadius;
        }
    }

    @ConfigSerializable
    public static class KnightOfBody {

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

        public int getRandomBuffTimer() {
            return randomBuffTimer;
        }

        public double getRandomBuffRadius() {
            return randomBuffRadius;
        }
    }

    @ConfigSerializable
    public static class GodOfMind {

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

        public int getRandomBuffTimer() {
            return randomBuffTimer;
        }

        public double getRandomBuffRadius() {
            return randomBuffRadius;
        }
    }

}
