package de.CypDasHuhn.TP.listeners;

import de.CypDasHuhn.TP.file_manager.player_manager.PlayerListManager;
import de.CypDasHuhn.TP.interfaces.Interface;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void playerJoinListener(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        boolean nameExists = PlayerListManager.existsByName(playerName);
        if (!nameExists) {
            String playerUUID = player.getUniqueId().toString();
            boolean uuidExists = PlayerListManager.existsByUUID(playerUUID);
            Interface.opening.put(player, false);
            if (!uuidExists) {
                PlayerListManager.add(playerName, playerUUID);
            } else {
                PlayerListManager.replaceName(playerName, playerUUID);
            }
        }
    }
}
