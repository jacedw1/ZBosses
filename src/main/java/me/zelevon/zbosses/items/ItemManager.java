package me.zelevon.zbosses.items;

import me.zelevon.zbosses.ZBosses;
import me.zelevon.zbosses.utils.MessageSender;

public class ItemManager {

    private static ItemManager instance;
    private MessageSender messageSender;
    private ZBosses plugin;
    private BossEgg mindEgg;
    private BossEgg bodyEgg;
    private BossEgg heartsEgg;
    private BossEgg soulsEgg;

    private ItemManager() {
        this.plugin = ZBosses.getInstance();
        this.messageSender = plugin.getMessageSender();
        this.init();
    }

    public static ItemManager get() {
        if(instance == null) {
            instance = new ItemManager();
        }
        return instance;
    }

    private void init(){
        this.mindEgg = new BossEgg(messageSender.colorize("&6God of Mind &fEgg"));
        this.bodyEgg = new BossEgg(messageSender.colorize("&cKnight of Body &fEgg"));
        this.heartsEgg = new BossEgg(messageSender.colorize("&dKnight of Hearts &fEgg"));
        this.soulsEgg = new BossEgg(messageSender.colorize("&5Knight of Souls &fEgg"));
    }

    public BossEgg getMindEgg() {
        return mindEgg;
    }

    public BossEgg getBodyEgg() {
        return bodyEgg;
    }

    public BossEgg getHeartsEgg() {
        return heartsEgg;
    }

    public BossEgg getSoulsEgg() {
        return soulsEgg;
    }
}
