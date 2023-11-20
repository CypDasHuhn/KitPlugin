package de.CypDasHuhn.TP.message;

import de.CypDasHuhn.TP.file_manager.player_manager.LocaleManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class Message {
    public static void sendMessage(Player player, String message) {
        String localizedMessage = getMessage(player, message);
        player.sendMessage(localizedMessage);
    }

    public static void sendMessage(CommandSender sender, String message) {
        if (!(sender instanceof Player player)) return;
        sendMessage(player, message);
    }

    public static void sendMessage(Player player, String message, Object... vars) {
        String formattedMessage = getMessage(player, message, vars);
        player.sendMessage(formattedMessage);
    }

    public static void sendMessage(CommandSender sender, String message, Object... vars) {
        if (!(sender instanceof Player player)) return;
        sendMessage(player, message, vars);
    }

    public static String getMessage(Player player, String message) {
        if (message == null) return null;
        ResourceBundle messagesBundle = ResourceBundle.getBundle("messages", LocaleManager.getLocale(player));
        return messagesBundle.getString(message);
    }

    public static String getMessage(Player player, String message, Object... vars) {
        String localizedMessage = getMessage(player, message);
        String formattedMessage = formatMessage(localizedMessage, vars);
        return formattedMessage;
    }

    public static String getMessage(CommandSender sender, String message) {
        if (!(sender instanceof Player player)) return null;
        String localizedMessage = getMessage(player, message);
        return localizedMessage;
    }

    public static String getMessage(CommandSender sender, String message, Object... vars) {
        if (!(sender instanceof Player player)) return null;
        String localizedMessage = getMessage(player, message, vars);
        return localizedMessage;
    }

    private static String formatMessage(String message, Object... vars) {
        if (!(vars != null && vars.length > 0)) return message;
        MessageFormat messageFormat = new MessageFormat(message);
        return messageFormat.format(vars);
    }
}

