package de.CypDasHuhn.Kit.listeners;

import de.CypDasHuhn.Kit.file_manager.yml.players.PlayerDataManagerYML;
import de.CypDasHuhn.Kit.interfaces.Interface;
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
            PlayerDataManagerYML.setInventory(player, null);
        }
    }
}
