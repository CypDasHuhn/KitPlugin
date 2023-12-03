package de.CypDasHuhn.Kit.file_manager.yml.items;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.file_manager.yml.CustomFiles;
import de.CypDasHuhn.Kit.shared.Finals;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;


public class KitManagerYML {
    public static void registerKit(KitDTO kit) {
        // other calls
        setKit(kit);
        KitListManagerYML.add(kit.kitName);
    }

    public static void setKit(KitDTO kit) {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration kitConfig = customFiles[0].getFileConfiguration(kit.kitName,"Kit");

        kitConfig.set("Class", kit.kitClass);

        CustomFiles.saveArray(customFiles);

        setInventory(kit);
    }

    public static void setInventory(KitDTO kit) {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration kitConfig = customFiles[0].getFileConfiguration(kit.kitName,"Kit");

        for (int i = 0; i < kit.inventory.length; i++) {
            kitConfig.set("Inventory."+i,kit.inventory[i]);
        }

        CustomFiles.saveArray(customFiles);
    }

    public static void deleteKit(KitDTO kit) {
        // delete
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        customFiles[0].delete(kit.kitName, "Kit");
        // remove in list
        KitListManagerYML.remove(kit.kitName);
    }

    public static void renameKit(KitDTO kit, String newName) {
        // rename in list
        KitListManagerYML.replace(kit.kitName, newName);
        // delete original
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        customFiles[0].delete(kit.kitName, "Kit");
        // save new
        kit.kitName = newName;
        setKit(kit);
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
