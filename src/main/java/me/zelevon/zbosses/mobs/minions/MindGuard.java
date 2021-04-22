package me.zelevon.zbosses.mobs.minions;

import me.zelevon.zbosses.config.mobs.GodOfMindConf;
import me.zelevon.zbosses.mobs.LivingMobManager;
import me.zelevon.zbosses.mobs.bosses.GodOfMind;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityIronGolem;
import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R3.PathfinderGoalTargetNearestPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class MindGuard extends EntityIronGolem {

    private GodOfMind boss;
    private GodOfMindConf bossConf;
    private LivingMobManager mobManager;

    public MindGuard(GodOfMind boss) {
        super(boss.getWorld());
        this.boss = boss;
        this.bossConf = (GodOfMindConf)boss.getBossConf();
        this.mobManager = boss.getMobManager();
        this.setCustomName(boss.getMessageSender().colorize("&6Mind Guard"));
        this.setCustomNameVisible(true);
        Damageable guard = ((Damageable)((this.getBukkitEntity())));
        double health = Math.max(guard.getMaxHealth(), bossConf.getHealth()/4.0D);
        guard.setMaxHealth(health);
        this.setHealth((float)health);
        Location loc = boss.getBukkitEntity().getLocation();
        this.setPosition(loc.getX(), loc.getY(), loc.getZ());
        this.getWorld().addEntity(this);
        this.mobManager.addMinion(guard);

        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, true));
        this.targetSelector.a(4, new PathfinderGoalTargetNearestPlayer(this));
    }

    public LivingMobManager getMobManager() {
        return mobManager;
    }

    public GodOfMind getBoss() {
        return boss;
    }
}
