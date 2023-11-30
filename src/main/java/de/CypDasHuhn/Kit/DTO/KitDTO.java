package de.CypDasHuhn.Kit.DTO;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KitDTO {
    public String kitName;
    public String kitClass;
    public ItemStack[] inventory;
    public List<String> tags;
    public boolean isFavorite;

    public KitDTO(String kitName, String kitClass, ItemStack[] inventory, List<String> tags, boolean isFavorite)  {
        this.kitName = kitName;
        this.kitClass = kitClass;
        this.inventory = inventory;
        this.tags = tags;
        this.isFavorite = isFavorite;
    }
}
