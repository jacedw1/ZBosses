package me.zelevon.zbosses.mobs.minions;

import me.zelevon.zbosses.mobs.LivingMobManager;
import me.zelevon.zbosses.mobs.bosses.KnightOfSouls;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;

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
        Damageable guard = ((Damageable)((this.getBukkitEntity())));
        guard.setMaxHealth(50D);
        this.setHealth(50F);
        AttributeInstance strength = this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE);
        strength.setValue(strength.getValue() + 3.0D);
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
