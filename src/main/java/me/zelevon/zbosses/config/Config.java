package me.zelevon.zbosses.config;

import me.zelevon.zbosses.config.mobs.GodOfMindConf;
import me.zelevon.zbosses.config.mobs.KnightOfBodyConf;
import me.zelevon.zbosses.config.mobs.KnightOfHeartsConf;
import me.zelevon.zbosses.config.mobs.KnightOfSoulsConf;
import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.io.File;
import java.io.IOException;

@SuppressWarnings({"unused", "FieldMayBeFinal", "ResultOfMethodCallIgnored"})
public class Config {

    public static MobsConf setup(){
        try {
            File plugins = new File("plugins");
            File directory = new File(plugins,"Z-Bosses");
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
        private RandomBuffConf randomBuffs;

        @Setting
        private String lightningMessage = "How electric!";

        @Setting
        private KnightOfHeartsConf knightOfHearts;

        @Setting
        private KnightOfSoulsConf knightOfSouls;

        @Setting
        private KnightOfBodyConf knightOfBody;

        @Setting
        private GodOfMindConf godOfMind;

        public RandomBuffConf getRandomBuffs() {
            return randomBuffs;
        }

        public String getLightningMessage() {
            return lightningMessage;
        }

        public KnightOfHeartsConf getKnightOfHearts() {
            return knightOfHearts;
        }

        public KnightOfSoulsConf getKnightOfSouls() {
            return knightOfSouls;
        }

        public KnightOfBodyConf getKnightOfBody() {
            return knightOfBody;
        }

        public GodOfMindConf getGodOfMind() {
            return godOfMind;
        }
    }

}
