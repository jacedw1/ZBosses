package me.zelevon.zbosses;

import co.aikar.commands.PaperCommandManager;
import com.google.common.collect.ImmutableList;
import me.zelevon.zbosses.commands.ZBossesCommands;
import me.zelevon.zbosses.config.Config;
import me.zelevon.zbosses.items.ItemManager;
import me.zelevon.zbosses.listeners.BossEggListener;
import me.zelevon.zbosses.listeners.EntityDamageListener;
import me.zelevon.zbosses.listeners.EntityDeathListener;
import me.zelevon.zbosses.mobs.LivingMobManager;
import me.zelevon.zbosses.mobs.bosses.*;
import me.zelevon.zbosses.tasks.NameTask;
import me.zelevon.zbosses.utils.MessageSender;
import me.zelevon.zbosses.utils.NMSUtils;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
public final class ZBosses extends JavaPlugin {

    private static ZBosses instance;
    private Config.MobsConf conf;
    private PaperCommandManager commandManager;
    private MessageSender messageSender;
    private LivingMobManager livingMobManager;
    private ItemManager itemManager;
    private BukkitTask task;
    private String prefix = "&8[&3Z&9B&8] &f";

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.conf = Config.setup();
        this.messageSender = MessageSender.get();
        this.livingMobManager = LivingMobManager.get();
        this.itemManager = ItemManager.get();

        NMSUtils.registerEntity("Knight_of_Body", 51, EntitySkeleton.class, KnightOfBody.class);
        NMSUtils.registerEntity("Knight_of_Hearts", 51, EntitySkeleton.class, KnightOfHearts.class);
        NMSUtils.registerEntity("Knight_of_Souls", 51, EntitySkeleton.class, KnightOfSouls.class);
        NMSUtils.registerEntity("God_of_Mind", 51, EntitySkeleton.class, GodOfMind.class);

        this.getServer().getPluginManager().registerEvents(new BossEggListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityDeathListener(), this);

        this.commandManager = new PaperCommandManager(this);
        this.commandManager.getCommandCompletions().registerCompletion("custommobs", c -> ImmutableList.of("Knight_of_Souls", "Knight_of_Hearts", "Knight_of_Body", "God_of_Mind"));
        this.commandManager.registerCommand(new ZBossesCommands());

        this.task = new NameTask().runTaskTimerAsynchronously(this, 0, 10);
        System.out.println("Z-Bosses has successfully enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.task.cancel();
        this.livingMobManager.killAllMobs();
    }

    public static ZBosses getInstance() {
        return instance;
    }

    public Config.MobsConf getConf() {
        return conf;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    public String getPrefix(){
        return prefix;
    }

    public LivingMobManager getMobManager() {
        return this.livingMobManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }
}
