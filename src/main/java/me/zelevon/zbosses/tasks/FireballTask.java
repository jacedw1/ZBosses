package me.zelevon.zbosses.tasks;

import me.zelevon.zbosses.mobs.bosses.AbstractWitherSkeleton;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("FieldMayBeFinal")
public class FireballTask extends BukkitRunnable {

    private AbstractWitherSkeleton boss;
    private Player player;

    public FireballTask(AbstractWitherSkeleton boss, Player player) {
        this.boss = boss;
        this.player = player;
    }
    @Override
    public void run() {
        EntityLiving target = boss.getGoalTarget();
        boss.setGoalTarget((((CraftPlayer)player).getHandle()), TargetReason.CUSTOM, false);
        ((LivingEntity) (boss.getBukkitEntity())).launchProjectile(Fireball.class);
        boss.setGoalTarget(target, TargetReason.CUSTOM, false);
    }
}
