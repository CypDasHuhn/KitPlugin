package de.CypDasHuhn.Kit.DTO;

import org.bukkit.inventory.ItemStack;

public class ShopDTO {
    public String kitName;
    public ItemStack[] inventory;
    public int[] costs;
    public int rows;

    public ShopDTO(ItemStack[] inventory, int[] costs, int rows, String kitName) {
        this.kitName = kitName;
        this.inventory = inventory;
        this.costs = costs;
        this.rows = rows;
    }
}
