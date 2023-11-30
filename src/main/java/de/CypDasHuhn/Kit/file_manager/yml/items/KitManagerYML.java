package de.CypDasHuhn.Kit.file_manager.yml.items;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.file_manager.yml.CustomFiles;
import de.CypDasHuhn.Kit.shared.Finals;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.checkerframework.checker.units.qual.K;

public class KitManagerYML {
    public static void registerKit(KitDTO kit) {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration kitConfig = customFiles[0].getFileConfiguration(kit.kitName,"Kit");

        for (int i = 0; i < kit.inventory.length; i++) {
            kitConfig.set("Inventory."+i,kit.inventory[i]);
        }

        CustomFiles.saveArray(customFiles);

        KitListManagerYML.add(kit.kitName);
    }

    public static KitDTO getKit(String kitName) {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration kitConfig = customFiles[0].getFileConfiguration(kitName,"Kit");
        // find
        String kitClass = kitConfig.getString("Class");

        ItemStack[] inventory = new ItemStack[Finals.OFF_HAND_INDEX+1];
        for (int i = 0; i < inventory.length; i++) {
            inventory[i] = kitConfig.getItemStack("Inventory."+i);
        }

        return new KitDTO(kitName, kitClass, inventory, null, false);
    }
}
