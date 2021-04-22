package me.zelevon.zbosses.listeners;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.config.Droptable;
import me.zelevon.zbosses.mobs.LivingMobManager;
import me.zelevon.zbosses.mobs.bosses.*;
import me.zelevon.zbosses.utils.MessageSender;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;
import java.util.Random;

@SuppressWarnings("FieldMayBeFinal")
public class EntityDeathListener implements Listener {

    private ZBosses plugin;
    private MessageSender messageSender;

    public EntityDeathListener() {
        this.plugin = ZBosses.getInstance();
        this.messageSender = plugin.getMessageSender();
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        Entity entity = ((CraftEntity) e.getEntity()).getHandle();
        if (!(entity instanceof AbstractWitherSkeleton)) {
            return;
        }
        e.getDrops().clear();
        AbstractWitherSkeleton boss = (AbstractWitherSkeleton) entity;
        Droptable droptable = boss.getBossConf().getDrops();
        List<Player> players = LivingMobManager.get().getTopThreePlayers(boss);
        String message = plugin.getPrefix() + "&n" + boss.getName() + " &fSlain\n";
        Random rand = new Random();
        int rewardNum = rand.nextInt(droptable.getFirstMax() - droptable.getFirstMin() + 1) + droptable.getFirstMin();
        String name = players.get(0).getName();
        while(rewardNum > 0) {
            for (String cmd : droptable.getFirstDrops().keySet()) {
                double chance = rand.nextDouble();
                if (chance < droptable.getFirstDrops().get(cmd)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), messageSender.replace(cmd, "%player%", name));
                    rewardNum--;
                    if(rewardNum == 0) {
                        break;
                    }
                }
            }
        }
        message += "      &fHighest Damage: &b" + name;
        players.remove(0);
        if(!(players.size() > 0)) {
            Bukkit.broadcastMessage(messageSender.colorize(message));
            return;
        }
        message += '\n';
        rewardNum = rand.nextInt(droptable.getSecondMax() - droptable.getSecondMin() + 1) + droptable.getSecondMin();
        name = players.get(0).getName();
        while(rewardNum > 0) {
            for (String cmd : droptable.getSecondDrops().keySet()) {
                double chance = rand.nextDouble();
                if (chance < droptable.getSecondDrops().get(cmd)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), messageSender.replace(cmd, "%player%", name));
                    rewardNum--;
                    if(rewardNum == 0) {
                        break;
                    }
                }
            }
        }
        message += "      &fSecond Highest Damage: &b" + name;
        players.remove(0);
        if(!(players.size() > 0)) {
            Bukkit.broadcastMessage(messageSender.colorize(message));
            return;
        }
        message += '\n';
        rewardNum = rand.nextInt(droptable.getThirdMax() - droptable.getThirdMin() + 1) + droptable.getThirdMin();
        name = players.get(0).getName();
        while(rewardNum > 0) {
            for (String cmd : droptable.getThirdDrops().keySet()) {
                double chance = rand.nextDouble();
                if (chance < droptable.getThirdDrops().get(cmd)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), messageSender.replace(cmd, "%player%", name));
                    rewardNum--;
                    if(rewardNum == 0) {
                        break;
                    }
                }
            }
        }
        Bukkit.broadcastMessage(messageSender.colorize(message + "      &fThird Highest Damage: &b" + name));

    }
}
