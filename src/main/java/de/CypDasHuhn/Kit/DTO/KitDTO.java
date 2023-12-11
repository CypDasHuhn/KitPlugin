package de.CypDasHuhn.Kit.DTO;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class KitDTO {
    public String kitName;
    public String kitClass;
    public ItemStack[] inventory;
    public Collection<PotionEffect> effects;
    public List<String> tags;
    public int complexity;
    public HashMap<String, Boolean> favoriteMap;

    public KitDTO(String kitName, String kitClass, ItemStack[] inventory, Collection<PotionEffect> effects, List<String> tags, int complexity, HashMap<String, Boolean> favoriteMap)  {
        this.kitName = kitName;
        this.kitClass = kitClass;
        this.inventory = inventory;
        this.tags = tags;
        this.complexity = complexity;
        this.favoriteMap = favoriteMap;
        this.effects = effects;
    }
}
