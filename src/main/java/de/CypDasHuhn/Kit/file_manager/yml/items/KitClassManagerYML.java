package de.CypDasHuhn.Kit.file_manager.yml.items;

import de.CypDasHuhn.Kit.file_manager.routing.items.KitClassManager;
import de.CypDasHuhn.Kit.file_manager.yml.CustomFiles;
import de.CypDasHuhn.Kit.shared.Finals;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class KitClassManagerYML {
    public static HashMap<String, String> getClasses() {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration userProvidedConfig = customFiles[0].getFileConfiguration(Finals.USER_PROVIDED_CONFIG,"");
        HashMap<String, String> classes = new HashMap<String, String>();
        // find
        for (Map.Entry<String, ChatColor> entry : KitClassManager.colorMap.entrySet()) {
            String text = entry.getKey();
            String customClass = userProvidedConfig.getString(text);
            if (!customClass.equals(Finals.EMPTY)) {
                classes.put(customClass, text);
            }
        }

        return classes;
    }
}
