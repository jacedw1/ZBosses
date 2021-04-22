package me.zelevon.zbosses.mobs.bosses;

import me.zelevon.zbosses.config.mobs.BossConf;
import me.zelevon.zbosses.config.mobs.GodOfMindConf;
import me.zelevon.zbosses.mobs.minions.MindGuard;
import me.zelevon.zbosses.tasks.bosses.MindTaskManager;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class GodOfMind extends AbstractWitherSkeleton {

    private GodOfMindConf mob;
    private Player lastTarget = null;
    private String name = "&6&lGod of Mind";
    private boolean dot = true;
    private boolean axePhase = false;
    private boolean invuln = false;
    private MindGuard guard1 = null;
    private MindGuard guard2 = null;
    private PathfinderGoal goal = new PathfinderGoalArrowAttack(this, 1.0D, 40, 80, 15.0F);

    public GodOfMind(Location location) {
        super(location);
        this.mob = this.getConf().getGodOfMind();
        this.setCustomHealth(mob.getHealth());
        this.updateName();
        this.setHelmet(this.parseItem(mob.getHelmet()));
        this.setChestplate(this.parseItem(mob.getChestplate()));
        this.setLeggings(this.parseItem(mob.getLeggings()));
        this.setBoots(this.parseItem(mob.getBoots()));
        this.setWeapon(this.parseItem(mob.getWeapon()));
        new MindTaskManager(this).runTaskTimer(this.getPlugin(), 0, 10);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double randomBuffRadius() {
        return mob.getRandomBuffRadius();
    }

    @Override
    public BossConf getBossConf() {
        return this.mob;
    }

    public boolean isDOT() {
        return dot;
    }

    public void setDot(boolean dot) {
        this.dot = dot;
    }

    public void startBowAttacks() {
        this.goalSelector.a(4, goal);
    }

    public void resetPathfinder() {
        this.goalSelector.a(goal);
    }

    public Player getLastTarget() {
        return lastTarget;
    }

    public void setLastTarget(Player lastTarget) {
        this.lastTarget = lastTarget;
    }

    public boolean isAxePhase() {
        return axePhase;
    }

    public void setAxePhase(boolean axePhase) {
        this.axePhase = axePhase;
    }

    public void setInvuln(boolean invuln) {
        this.invuln = invuln;
    }

    public boolean isInvuln() {
        return invuln;
    }

    public void spawnGuards() {
        this.guard1 = new MindGuard(this);
        this.guard2 = new MindGuard(this);
    }

    public boolean guardIsAlive() {
        if(guard1 == null || guard2 == null) {
            return false;
        }
        List<Entity> minions = this.getMobManager().getMinions();
        return minions.contains(guard1.getBukkitEntity()) || minions.contains(guard2.getBukkitEntity());
    }
}