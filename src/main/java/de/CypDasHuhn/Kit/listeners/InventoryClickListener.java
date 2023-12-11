package de.CypDasHuhn.Kit.listeners;

import de.CypDasHuhn.Kit.file_manager.yml.players.PlayerDataManagerYML;
import de.CypDasHuhn.Kit.interfaces.general.Interface;
import de.CypDasHuhn.Kit.interfaces.general.SkeletonInterface;
import de.CypDasHuhn.Kit.shared.Finals;
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
        String inventory = PlayerDataManagerYML.getInventory(player);

        boolean emptyInventory = inventory.equals(Finals.EMPTY);
        if (emptyInventory) return;

        boolean illegalInventory = !Interface.interfaceMap.containsKey(inventory);
        if (illegalInventory) return;

        ItemStack clickedItem = event.getCurrentItem();
        Material clickedMaterial = clickedItem != null ? clickedItem.getType() : null;
        int clickedSlot = event.getSlot();

        event.setCancelled(true);

        SkeletonInterface listener = Interface.interfaceMap.get(inventory);
        listener.listener(event, player, clickedItem, clickedMaterial, clickedSlot);

    }
}