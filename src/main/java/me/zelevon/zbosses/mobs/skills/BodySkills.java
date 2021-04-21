package me.zelevon.zbosses.mobs.skills;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.List;

public class BodySkills {

    public static void lightningStrike(AbstractWitherSkeleton boss) {
        List<Player> players = GeneralSkills.getNearbyPlayers(boss, 10);
        if(players.size() == 0) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(ZBosses.getInstance(), () -> {
            for(Player player : players) {
                player.getWorld().strikeLightningEffect(player.getLocation());
                double damage = 3.0D;
                int speedIncreasePercent = 1;
                if (player.isBlocking()) {
                    damage = 1.0D;
                    speedIncreasePercent = 0;
                }
                player.damage(damage);
                AttributeInstance speed = boss.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
                speed.setValue(speed.getValue() * (1.0F + (speedIncreasePercent / 100F)));
            }
        });
    }

    public static void blindPlayers(AbstractWitherSkeleton boss) {
        List<Player> players = GeneralSkills.getNearbyPlayers(boss, 15);
        if(players.size() == 0) {
            return;
        }
        PotionEffect blindness = PotionEffectType.BLINDNESS.createEffect(300, 4);
        for(Player player : players) {
            player.addPotionEffect(blindness);
        }
    }

}