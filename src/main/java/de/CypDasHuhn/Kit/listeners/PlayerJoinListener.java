package de.CypDasHuhn.Kit.listeners;

import de.CypDasHuhn.Kit.file_manager.yml.players.PlayerListManagerYML;
import de.CypDasHuhn.Kit.interfaces.Interface;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void playerJoinListener(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        boolean nameExists = PlayerListManagerYML.existsByName(playerName);
        if (!nameExists) {
            String playerUUID = player.getUniqueId().toString();
            boolean uuidExists = PlayerListManagerYML.existsByUUID(playerUUID);
            Interface.opening.put(player, false);
            if (!uuidExists) {
                PlayerListManagerYML.addPlayer(playerName, playerUUID);
            } else {
                PlayerListManagerYML.replacePlayerName(playerName, playerUUID);
            }
        }
    }
}
