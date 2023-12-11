package de.CypDasHuhn.Kit.interfaces.general;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SkeletonInterface {
    // method to override over
    public Inventory getInterface(Player player, Object... vars) {
        return null;
    }

    // method to override over
    public void listener(InventoryClickEvent event, Player player, ItemStack clickedItem, Material clickedMaterial, int clickedSlot) {

    }
}
