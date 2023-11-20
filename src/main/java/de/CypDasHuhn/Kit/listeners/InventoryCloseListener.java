package de.CypDasHuhn.TP.listeners;

import de.CypDasHuhn.TP.file_manager.player_manager.PlayerDataManager;
import de.CypDasHuhn.TP.interfaces.Interface;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void inventoryCloseListener(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Boolean currentlyOpening = Interface.opening.get(player);

        if (currentlyOpening == null) {
            currentlyOpening = false;
            Interface.opening.put(player, false);
        }

        if (!currentlyOpening) {
            PlayerDataManager.setInventory(player, null);
        }
    }
}
