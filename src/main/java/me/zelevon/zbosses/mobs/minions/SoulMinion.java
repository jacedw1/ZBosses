package me.zelevon.zbosses.mobs.minions;

import me.zelevon.zbosses.mobs.LivingMobManager;
import me.zelevon.zbosses.mobs.bosses.KnightOfSouls;
import net.minecraft.server.v1_8_R3.EntityZombie;
import org.bukkit.Location;

@SuppressWarnings("FieldMayBeFinal")
public class SoulMinion extends EntityZombie {

    private KnightOfSouls boss;
    private LivingMobManager mobManager;

    public SoulMinion(KnightOfSouls boss) {
        super(boss.getWorld());
        this.boss = boss;
        this.mobManager = boss.getMobManager();
        this.setBaby(true);
        this.setCustomName(boss.getMessageSender().colorize("&5Soul Minion"));
        this.setCustomNameVisible(true);
        Location loc = boss.getBukkitEntity().getLocation();
        this.setPosition(loc.getX(), loc.getY(), loc.getZ());
        this.getWorld().addEntity(this);
        this.mobManager.addMinion(this.getBukkitEntity());
    }

    public LivingMobManager getMobManager() {
        return mobManager;
    }

    public KnightOfSouls getBoss() {
        return boss;
    }
}
