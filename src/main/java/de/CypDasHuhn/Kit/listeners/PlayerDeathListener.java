package de.CypDasHuhn.Kit.listeners;

import de.CypDasHuhn.Kit.Actions.MoneyAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class PlayerDeathListener implements Listener {
    public static int moneyReward = 100;
    @EventHandler
    public void listener(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        MoneyAction.setMoney(player, 0, null);

        List<MetadataValue> metadata = player.getMetadata("player");
        String playerName = metadata.size() > 0 ? (String) metadata.get(0).value() : null;
        if (playerName != null) {
            Player attackingPlayer = Bukkit.getPlayer(playerName);

            if (player != attackingPlayer) {
                MoneyAction.giveMoney(attackingPlayer, moneyReward, null);
            }
        }

    }
}
