package me.zelevon.zbosses.mobs.skills;

import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

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
}
