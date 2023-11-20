package de.CypDasHuhn.TP.listeners;

import de.CypDasHuhn.TP.file_manager.player_manager.PlayerDataManager;
import de.CypDasHuhn.TP.interfaces.Interface;
import de.CypDasHuhn.TP.interfaces.skeleton.SkeletonInterfaceListener;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void inventoryClickListener(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String inventory = PlayerDataManager.getInventory(player);

        boolean emptyInventory = inventory.equals(Finals.EMPTY);
        if (emptyInventory) return;

        boolean illegalInventory = !Interface.listenerMap.containsKey(inventory);
        if (illegalInventory) return;

        ItemStack clickedItem = event.getCurrentItem();
        Material clickedMaterial = clickedItem.getType();
        int clickedSlot = event.getSlot();

        if (clickedItem == null) return;

        event.setCancelled(true);

        SkeletonInterfaceListener listener = Interface.listenerMap.get(inventory);
        listener.listener(event, player, clickedItem, clickedMaterial, clickedSlot);
    }
}