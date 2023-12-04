package de.CypDasHuhn.Kit.file_manager.yml.players;

import de.CypDasHuhn.Kit.file_manager.yml.CustomFiles;
import de.CypDasHuhn.Kit.shared.Finals;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerDataManager {
    public static final String dataFileDir = "Data";
    public static final String inventoryDir = "Inventory";
    public static final String pageDir = "Page";

    public static void setInventory(Player player, String inventory) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(dataFileDir,UUID);
        // set
        if (inventory == null) inventory = Finals.EMPTY;
        playerDataConfig.set(inventoryDir, inventory);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static String getInventory(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(dataFileDir,UUID);
        // get
        String inventory = playerDataConfig.getString(inventoryDir);
        if (inventory == null) inventory = Finals.EMPTY;
        return inventory;
    }

    public static void setPage(Player player, int page) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(dataFileDir,UUID);
        // set
        playerDataConfig.set(pageDir, page);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static int getPage(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(dataFileDir,UUID);
        // get
        int page = playerDataConfig.getInt(pageDir);
        if (page == 0) {
            page = 1;
            setPage(player, page);
        }
        return page;
    }
}
