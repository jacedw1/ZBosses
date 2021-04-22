package me.zelevon.zbosses.listeners;

import me.zelevon.zbosses.mobs.minions.MindGuard;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityTargetListener implements Listener {

    @EventHandler
    public void onMindGuardTarget(EntityTargetEvent e) {
        Entity entity = ((CraftEntity) e.getEntity()).getHandle();
        if (!(entity instanceof MindGuard)) {
            return;
        }
        if(!(e.getTarget() instanceof Player)) {
            e.setTarget(((MindGuard) entity).getBoss().getLastTarget());
        }
    }
}
