package me.zelevon.zbosses.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class MessageSender {

    private static MessageSender instance;

    private MessageSender() {

    }

    public static MessageSender get(){
        if(instance == null){
            instance = new MessageSender();
        }
        return instance;
    }

    public void msg(CommandSender recipient, String message){
        recipient.sendMessage(colorize(message));
    }

    public void msg(Player recipient, String message){
        recipient.sendMessage(colorize(message));
    }

    public void msg(Player recipient, String message, Object... replacements) {
        this.msg(recipient, this.format(message, replacements));
    }

    public void msg(CommandSender recipient, String message, Object... replacements) {
        this.msg(recipient, this.format(message, replacements));
    }

    private String format(String message, Object... replacements) {
        return String.format(message, replacements);
    }

    public String replace(String message, String key, String replacement) {
        return message.replaceAll(key, replacement);
    }

    public String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
