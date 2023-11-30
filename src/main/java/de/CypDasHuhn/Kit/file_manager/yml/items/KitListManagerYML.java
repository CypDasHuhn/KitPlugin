package de.CypDasHuhn.Kit.file_manager.yml.items;

import de.CypDasHuhn.Kit.file_manager.yml.CustomFiles;
import de.CypDasHuhn.Kit.shared.Finals;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class KitListManagerYML {
    public static void add(String kitName) {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration kitListConfig = customFiles[0].getFileConfiguration("KitList","");
        int amount = kitListConfig.getInt("Amount");
        // set
        kitListConfig.set("Amount", amount+1);
        kitListConfig.set("Id."+amount, kitName);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static int findId(String kitName) {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration kitListConfig = customFiles[0].getFileConfiguration("KitList","");
        int amount = kitListConfig.getInt("Amount");
        // find
        for (int i = 0; i < amount; i++) {
            String currentKitName = kitListConfig.getString("Id."+i);
            if (currentKitName.equals(kitName)) return i;
        }
        // not found
        return Finals.NULL_INT;
    }

    public static void remove(String kitName) {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration kitListConfig = customFiles[0].getFileConfiguration("KitList","");
        int amount = kitListConfig.getInt("Amount");
        int targetId = findId(kitName);
        // remove
        for (int i = targetId; i < amount; i++) {
            String nextKit = kitListConfig.getString("Id."+(i+1));
            kitListConfig.set("Id."+i, nextKit);
        }
        kitListConfig.set("amount", amount-1);
        kitListConfig.set("id."+(amount-1), Finals.EMPTY);
        // Save
        CustomFiles.saveArray(customFiles);
    }

    public static void replace(String newKitName, String oldKitName) {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration kitListConfig = customFiles[0].getFileConfiguration("KitList","");
        int targetId = findId(oldKitName);
        // set
        kitListConfig.set("id."+targetId, newKitName);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static List<String> getKits() {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration kitListConfig = customFiles[0].getFileConfiguration("KitList","");
        int amount = kitListConfig.getInt("Amount");
        List<String> kits = new ArrayList<String>();
        // add
        for (int i = 0; i < amount; i++) {
            String item = kitListConfig.getString("Id."+i);
            kits.add(item);
        }
        return kits;
    }
}
