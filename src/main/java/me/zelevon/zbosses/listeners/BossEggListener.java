package me.zelevon.zbosses.listeners;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.items.ItemManager;
import me.zelevon.zbosses.mobs.bosses.GodOfMind;
import me.zelevon.zbosses.mobs.bosses.KnightOfBody;
import me.zelevon.zbosses.mobs.bosses.KnightOfHearts;
import me.zelevon.zbosses.mobs.bosses.KnightOfSouls;
import me.zelevon.zbosses.utils.MessageSender;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("FieldMayBeFinal")
public class BossEggListener implements Listener {

    private ItemManager itemManager;
    private ZBosses plugin;
    private MessageSender messageSender;

    public BossEggListener() {
        this.plugin = ZBosses.getInstance();
        this.messageSender = plugin.getMessageSender();
        this.itemManager = plugin.getItemManager();
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if(!(e.getAction() == Action.RIGHT_CLICK_BLOCK)){
            return;
        }
        ItemStack item = e.getItem();

        if(item == null || !(item.getType() == Material.MONSTER_EGG)) {
            return;
        }

        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if(nmsItem.getTag() == null) {
            return;
        }

        try {
            if (!(nmsItem.getTag().getBoolean("BossEgg"))) {
                return;
            }
        } catch (NullPointerException exception) {
            return;
        }

        e.setCancelled(true);
        Player player = e.getPlayer();
        Location loc = e.getClickedBlock().getLocation().add(0,1,0);
        ItemMeta meta = item.getItemMeta();
        String message = plugin.getPrefix();
        if(meta.equals(itemManager.getBodyEgg().getItemMeta())) {
            new KnightOfBody(loc).spawn();
            message += "You have successfully spawned a &c&lKnight of Body&f.";
        }
        if(meta.equals(itemManager.getHeartsEgg().getItemMeta())) {
            new KnightOfHearts(loc).spawn();
            message += "You have successfully spawned a &d&lKnight of Hearts&f.";
        }
        if(meta.equals(itemManager.getSoulsEgg().getItemMeta())) {
            new KnightOfSouls(loc).spawn();
            message += "You have successfully spawned a &5&lKnight of Souls&f.";
        }
        if(meta.equals(itemManager.getMindEgg().getItemMeta())) {
            new GodOfMind(loc).spawn();
            message += "You have successfully spawned a &6&lGod of Mind&f.";
        }
        messageSender.msg(player, message);
        if(player.getGameMode() != GameMode.CREATIVE) {
            player.getInventory().remove(item);
        }
    }
}
