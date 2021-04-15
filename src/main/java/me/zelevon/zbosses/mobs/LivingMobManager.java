package me.zelevon.zbosses.mobs;

import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class LivingMobManager {

    private List<AbstractWitherSkeleton> bosses;
    private List<LivingEntity> minions;
    private static LivingMobManager instance;

    private LivingMobManager(){
        bosses = new ArrayList<>();
        minions = new ArrayList<>();
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
                if(c.isInstance(mob));
            }
        }
        return false;
    }

    public void removeBoss(AbstractWitherSkeleton boss) {
        this.bosses.remove(boss);
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
}
