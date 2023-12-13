package de.CypDasHuhn.Kit.file_manager.yml.items;

import de.CypDasHuhn.Kit.DTO.ShopDTO;
import de.CypDasHuhn.Kit.file_manager.yml.CustomFiles;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;

public class ShopManagerYML {
    public static final int DEFAULT_COSTS = 25;
    public static void setShop(ShopDTO shop) {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration shopConfig = customFiles[0].getFileConfiguration(shop.kitName,"Shops");

        shopConfig.set("Rows",shop.rows);

        for (int i = 0; i < shop.inventory.length; i++) {
            shopConfig.set("Inventory."+i+".Item",shop.inventory[i]);
            shopConfig.set("Inventory."+i+".Cost",shop.costs[i]);

            if (shop.inventory[i] != null && shop.inventory[i].getItemMeta() instanceof SpawnEggMeta spawnEggMeta) { // if egg
                EntityType spawnType = spawnEggMeta.getCustomSpawnedType();
                String displayMaterial = shop.inventory[i].getType().name();

                shopConfig.set("Eggs." + i + ".Spawn", spawnType.toString());
                shopConfig.set("Eggs." + i + ".Display", displayMaterial);
            }
        }

        CustomFiles.saveArray(customFiles);
    }

    public static ShopDTO getShop(String kitName) {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration shopConfig = customFiles[0].getFileConfiguration(kitName,"Shops");

        int rows = shopConfig.getInt("Rows");
        if (rows == 0) rows = 1;

        ItemStack[] inventory = new ItemStack[rows*9];
        int[] costs = new int[rows*9];
        for (int i = 0; i < inventory.length; i++) {
            inventory[i] = shopConfig.getItemStack("Inventory."+i+".Item");
            costs[i] = shopConfig.getInt("Inventory."+i+".Cost");
            if (costs[i] == 0) costs[i] = DEFAULT_COSTS;

            if (inventory[i] != null && inventory[i].getItemMeta() instanceof SpawnEggMeta spawnEggMeta) {
                String spawnType = shopConfig.getString("Eggs."+i+".Spawn");
                String displayMaterial = shopConfig.getString("Eggs."+i+".Display");

                if (spawnType != null && displayMaterial != null) {
                    Material egg = Material.matchMaterial(displayMaterial);
                    spawnEggMeta.setCustomSpawnedType(EntityType.valueOf(spawnType));

                    inventory[i].setItemMeta(spawnEggMeta);
                    inventory[i].setType(egg);
                }
            }
        }

        return new ShopDTO(inventory,costs,rows,kitName);
    }
}
