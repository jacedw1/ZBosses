package me.zelevon.zbosses.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.items.BossEgg;
import me.zelevon.zbosses.items.ItemManager;
import me.zelevon.zbosses.mobs.LivingMobManager;
import me.zelevon.zbosses.mobs.bosses.*;
import me.zelevon.zbosses.mobs.minions.MindGuard;
import me.zelevon.zbosses.mobs.minions.SoulMinion;
import me.zelevon.zbosses.utils.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

@SuppressWarnings({"FieldMayBeFinal", "UnusedAssignment"})
@CommandAlias("zbosses|zb|zboss|bosses|boss")
public class ZBossesCommands extends BaseCommand {
    private ZBosses plugin;
    private MessageSender messageSender;
    private LivingMobManager mobManager;
    private ItemManager itemManager;
    private String prefix;

    public ZBossesCommands(){
        this.plugin = ZBosses.getInstance();
        this.messageSender = plugin.getMessageSender();
        this.mobManager = plugin.getMobManager();
        this.itemManager = plugin.getItemManager();
        this.prefix = plugin.getPrefix();
    }

    @Default
    @Subcommand("help")
    public void onDefault(CommandSender sender) {
        String message = prefix + "&bCommand Help" + '\n'
                + "&c/zb spawn &b<boss> &3<optional:delay> &e- spawn &b<boss>&e with an optional &3delay&e. Spawning a mob with a &3delay&e will use the delay loottables." + '\n'
                + "&c/zb get &b<boss> &e- get a spawn egg for &b<boss>&e to place down and spawn." + '\n'
                + "&c/zb list &e- get a list of possible &bbosses&e to use in &b<boss>&e." + '\n'
                + "&c/zb living &e- get a list of currently living &bbosses&e and &aminions&e." + '\n'
                + "&c/zb help &e- bring up this help page";
        messageSender.msg(sender, message);
    }

    @Subcommand("list")
    public void onList(CommandSender sender) {
        String message = prefix + "Bosses Registered: &6&lGod of Mind&f, &c&lKnight of Body&f, &d&lKnight of Heart&f, &5&lKnight of Souls";
        messageSender.msg(sender, message);
    }

    @Subcommand("living")
    public void onLiving(CommandSender sender) {
        if(mobManager.getBosses().size() == 0 && this.mobManager.getMinions().size() == 0) {
            messageSender.msg(sender, prefix + "There are no ZBosses mobs alive.");
            return;
        }
        String message = "";
        if(!(mobManager.getBosses().size() == 0)) {
            message += prefix + "&l&nBosses Alive&f " + '\n' + " ";
            if (mobManager.isAlive(KnightOfBody.class, true)) {
                message += "     &c&lKnight of Body: " + mobManager.getAmountAlive(KnightOfBody.class) + '\n';
            }
            if (mobManager.isAlive(KnightOfHearts.class, true)) {
                message += "     &d&lKnight of Hearts: " + mobManager.getAmountAlive(KnightOfHearts.class) + '\n';
            }
            if (mobManager.isAlive(KnightOfSouls.class, true)) {
                message += "     &5&lKnight of Souls: " + mobManager.getAmountAlive(KnightOfSouls.class) + '\n';
            }
            if (mobManager.isAlive(GodOfMind.class, true)) {
                message += "     &6&lGod of Mind: " + mobManager.getAmountAlive(GodOfMind.class) + '\n';
            }
        }
        if(!(mobManager.getMinions().size() == 0)) {
            message += prefix + "&l&nMinions Alive&f" + '\n';
        }
        if (mobManager.isAlive(EnderCrystal.class, false)) {
            message += "      &dHeart Crystal: " + mobManager.getAmountAlive(EnderCrystal.class) + '\n';
        }
        if (mobManager.isAlive(MindGuard.class, false)) {
            message += "      &6Mind Guard: " + mobManager.getAmountAlive(MindGuard.class) + '\n';
        }
        if (mobManager.isAlive(SoulMinion.class, false)) {
            message += "      &5Soul Minion: " + mobManager.getAmountAlive(SoulMinion.class) + '\n';
        }

        messageSender.msg(sender, message);

    }

    @Subcommand("get")
    @CommandCompletion("@custommobs")
    public void onGet(Player sender, String mob) {
        BossEgg egg;
        String message = prefix;
        switch(mob) {
            case "God_of_Mind":
                egg = itemManager.getMindEgg();
                message = "Successfully given &6&lGod of Mind&f Egg.";
                break;
            case "Knight_of_Body":
                egg = itemManager.getBodyEgg();
                message = "Successfully given &c&lKnight of Body&f Egg.";
                break;
            case "Knight_of_Hearts":
                egg = itemManager.getHeartsEgg();
                message = "Successfully given &d&lKnight of Hearts&f Egg.";
                break;
            case "Knight_of_Souls":
                egg = itemManager.getSoulsEgg();
                message = "Successfully given &5&lKnight of Souls&f Egg.";
                break;
            default:
                egg = null;
                messageSender.msg(sender, prefix + "That is not a valid Z-Boss.");
                return;
        }
        messageSender.msg(sender, message);
        sender.getInventory().addItem(egg);

    }

    @CommandCompletion("@custommobs seconds")
    @Subcommand("spawn")
    public void onSpawn(Player sender, String mob, @Optional Long seconds){
        AbstractWitherSkeleton entity;
        Location loc = sender.getLocation();
        String message;
        boolean delayMob = seconds != null;
        switch(mob) {
            case "God_of_Mind":
                entity = new GodOfMind(loc, delayMob);
                message = "Successfully spawned &6&lGod of Mind&f.";
                break;
            case "Knight_of_Body":
                entity = new KnightOfBody(loc, delayMob);
                message = "Successfully spawned &c&lKnight of Body&f.";
                break;
            case "Knight_of_Hearts":
                entity = new KnightOfHearts(loc, delayMob);
                message = "Successfully spawned &d&lKnight of Hearts&f.";
                break;
            case "Knight_of_Souls":
                entity = new KnightOfSouls(loc, delayMob);
                message = "Successfully spawned &5&lKnight of Souls&f.";
                break;
            default:
                entity = null;
                messageSender.msg(sender, prefix + "That is not a valid Z-Boss.");
                return;
        }

        if(seconds == null){
            seconds = 0L;
        }
        long ticks = seconds * 20;

        if(ticks != 0) {
            messageSender.msg(sender, prefix + "Scheduled a spawn in " + seconds + " seconds");
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> entity.spawn(sender, message), ticks);
    }
}
