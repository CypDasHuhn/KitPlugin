package de.CypDasHuhn.Kit.Actions;

import de.CypDasHuhn.Kit.file_manager.routing.players.PlayerDataManager;
import org.bukkit.entity.Player;

public class MoneyAction {
    public static void giveMoney(Player player, int moneyAmount, Player source) {
        if (source != null) {
            player.sendMessage("§aYou §5got §6"+moneyAmount+"œœ §afrom "+source.getName()+"!");
        } else {
            player.sendMessage("§aYou §5got §6"+moneyAmount+"œœ§a!");
        }
        int currentMoney = PlayerDataManager.getMoney(player);
        PlayerDataManager.setMoney(player, currentMoney+moneyAmount);
    }

    public static void setMoney(Player player, int moneyAmount, Player source) {
        if (source != null) {
            player.sendMessage("§aYour money is §5set §ato §6"+moneyAmount+"œœ §afrom "+source.getName()+"!");
        } else {
            player.sendMessage("§aYour money is §5set §ato §6"+moneyAmount+"œœ§a!");
        }
        PlayerDataManager.setMoney(player, moneyAmount);
    }
}
