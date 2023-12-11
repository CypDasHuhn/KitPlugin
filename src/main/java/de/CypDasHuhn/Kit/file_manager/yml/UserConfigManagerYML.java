package de.CypDasHuhn.Kit.file_manager.yml;

import de.CypDasHuhn.Kit.compability.database.ConvertDatabase;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitClassManager;
import de.CypDasHuhn.Kit.shared.Finals;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

public class UserConfigManagerYML {
    public static void initializeConfig() {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration userProvidedConfig = customFiles[0].getFileConfiguration(Finals.USER_PROVIDED_CONFIG,"");
        // set
        for (Map.Entry<String, ChatColor> entry : KitClassManager.colorMap.entrySet()) {
            String text = entry.getKey();
            userProvidedConfig.set(text, Finals.EMPTY);
        }

        userProvidedConfig.set("useSQL", false);
        userProvidedConfig.set("connectionString", Finals.EMPTY);
        userProvidedConfig.set("convert", false);

        userProvidedConfig.set("initialized", true);

        CustomFiles.saveArray(customFiles);
    }

    public static boolean isInitialized() {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration userProvidedConfig = customFiles[0].getFileConfiguration(Finals.USER_PROVIDED_CONFIG,"");
        // find
        boolean isInitialized = userProvidedConfig.getBoolean("initialized");

        return isInitialized;
    }

    public static boolean usesSQL() {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration userProvidedConfig = customFiles[0].getFileConfiguration(Finals.USER_PROVIDED_CONFIG,"");
        // find

        boolean usesSQL = userProvidedConfig.getBoolean("usesSQL");
        boolean convert = userProvidedConfig.getBoolean("convert");

        if (convert) {
            ConvertDatabase.convert(usesSQL);
        }

        return usesSQL;
    }

    public static String getConnectionString() {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration userProvidedConfig = customFiles[0].getFileConfiguration(Finals.USER_PROVIDED_CONFIG,"");
        // find
        String connectionString = userProvidedConfig.getString("connectionString");

        return connectionString;
    }
}
