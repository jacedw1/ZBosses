package me.zelevon.zbosses.mobs.skills;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class GeneralSkills {

    public static void broadcastMessage(AbstractWitherSkeleton boss, String message, double radius) {
        for(Entity e : boss.getBukkitEntity().getNearbyEntities(radius, radius, radius)) {
            if(e instanceof Player) {
                boss.getMessageSender().msg((Player) e, boss.getName() + ": &f" + message);
            }
        }
    }

    public static List<Player> getNearbyPlayers(AbstractWitherSkeleton boss, double radius) {
        List<Entity> entities = boss.getBukkitEntity().getNearbyEntities(radius, radius, radius);
        List<Player> players = new ArrayList<>();
        for(Entity e : entities) {
            if(e instanceof Player) {
                players.add((Player) e);
            }
        }
        return players;
    }

    public static void bossCountdown(AbstractWitherSkeleton boss) {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        ZBosses plugin = boss.getPlugin();
        GeneralSkills.broadcastMessage(boss, "3", 20);
        scheduler.runTaskLater(plugin, () -> GeneralSkills.broadcastMessage(boss, "2", 20), 20);
        scheduler.runTaskLater(plugin, () -> GeneralSkills.broadcastMessage(boss, "1", 20), 40);
    }
}
