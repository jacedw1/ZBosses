package me.zelevon.zbosses.mobs;

import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

@SuppressWarnings({"FieldMayBeFinal", "unused", "rawtypes"})
public class LivingMobManager {

    private List<AbstractWitherSkeleton> bosses;
    private List<LivingEntity> minions;
    private Map<AbstractWitherSkeleton, BidiMap<Player, Double>> damageTracker;
    private static LivingMobManager instance;

    private LivingMobManager(){
        bosses = new ArrayList<>();
        minions = new ArrayList<>();
        damageTracker = new HashMap<>();
    }

    public static LivingMobManager get(){
        if(instance == null) {
            instance = new LivingMobManager();
        }
        return instance;
    }

    public List<AbstractWitherSkeleton> getBosses() {
        return bosses;
    }

    public List<LivingEntity> getMinions() {
        return minions;
    }

    public void addBoss(AbstractWitherSkeleton boss){
        this.bosses.add(boss);
        this.damageTracker.put(boss, new DualHashBidiMap<>());
    }

    public void addMinion(LivingEntity minion){
        this.minions.add(minion);
    }

    public boolean isAlive(Class c, boolean isBoss){
        if(isBoss) {
            for (AbstractWitherSkeleton boss : bosses) {
                if (c.isInstance(boss)) {
                    return true;
                }
            }
        }
        if(!isBoss) {
            for(LivingEntity mob : minions){
                if(c.isInstance(mob)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeBoss(AbstractWitherSkeleton boss) {
        this.bosses.remove(boss);
        this.damageTracker.remove(boss);
    }

    public void removeMinion(LivingEntity minion){
        this.minions.remove(minion);
    }

    public int getAmountAlive(Class c) {
        int result = 0;
        for(AbstractWitherSkeleton boss : bosses) {
            if(c.isInstance(boss)){
                result++;
            }
        }
        for(LivingEntity minion : minions) {
            if(c.isInstance(minion)){
                result++;
            }
        }
        return result;
    }

    public void killAllMobs() {
        for(AbstractWitherSkeleton boss : bosses){
            boss.setHealth(0);
        }
        for(LivingEntity minion : minions){
            minion.setHealth(0);
        }
    }

    public void updateNames() {
        for(AbstractWitherSkeleton boss : bosses) {
            boss.updateName();
        }
    }

    public void addDamage(AbstractWitherSkeleton boss, Player player, double damage) {
        if(!damageTracker.containsKey(boss)) {
            damageTracker.put(boss, new DualHashBidiMap<Player, Double>() {{
                put(player, damage);
            }});
            return;
        }
        BidiMap<Player, Double> playerDamage = damageTracker.get(boss);
        if(!playerDamage.containsKey(player)) {
            playerDamage.put(player, damage);
            return;
        }
        playerDamage.replace(player, playerDamage.get(player) + damage);
    }

    public List<Player> getTopThreePlayers(AbstractWitherSkeleton boss) {
        BidiMap<Player, Double> playerDamage = damageTracker.get(boss);
        Double[] damages = Arrays.stream(playerDamage.values().toArray(new Double[0])).sorted().toArray(Double[]::new);
        int topBound = Math.min(damages.length-1, 2);
        List<Player> players = new ArrayList<>();
        for(int i = topBound; i >= 0; i--) {
            players.add(playerDamage.getKey(damages[i]));
        }
        return players;
    }
}
