package de.CypDasHuhn.TP.file_manager.player_manager;

import de.CypDasHuhn.TP.DTO.FolderInterfaceDTO;
import de.CypDasHuhn.TP.file_manager.CustomFiles;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerDataManager {
    public static void setInventory(Player player, String inventory) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);
        // set
        if (inventory == null) inventory = Finals.EMPTY;
        playerDataConfig.set("Data.Inventory", inventory);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static String getInventory(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);
        // get
        String inventory = playerDataConfig.getString("Data.Inventory");
        if (inventory == null) inventory = Finals.EMPTY;
        return inventory;
    }

    public static void setDirectory(Player player, String directory) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);
        // set
        playerDataConfig.set("Data.Directory", directory);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static String getDirectory(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);
        // get
        String directory = playerDataConfig.getString("Data.Directory");
        return directory;
    }

    public static void setParent(Player player, String parent) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);
        // set
        playerDataConfig.set("Data.Parent", parent);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static String getParent(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);
        // get
        String parentName = playerDataConfig.getString("Data.Parent");
        if (parentName == null) parentName = Finals.DEFAULT_PARENT;
        return parentName;
    }

    public static void setPage(Player player, int page) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);
        // set
        playerDataConfig.set("Data.Page", page);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static int getPage(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);
        // get
        int page = playerDataConfig.getInt("Data.Page");
        if (page == 0) {
            page = 1;
            setPage(player, page);
        }
        return page;
    }

    public static FolderInterfaceDTO getInterfaceInformation(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);

        String directory = playerDataConfig.getString("CurrentDirectory");
        String parentName = playerDataConfig.getString("CurrentParentName");
        int page = playerDataConfig.getInt("CurrentPage");
        int slot = playerDataConfig.getInt("CurrentSlot");
        boolean moving = playerDataConfig.getBoolean("CurrentlyMoving");

        return new FolderInterfaceDTO(directory, parentName, page, slot, moving);
    }

    public static void setInterfaceInformation(Player player, FolderInterfaceDTO data) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration("Data",UUID);
        // set
        playerDataConfig.set("CurrentDirectory", data.directory);
        playerDataConfig.set("CurrentParentName",data.parentName);
        playerDataConfig.set("CurrentPage",data.page);
        playerDataConfig.set("CurrentSlot",data.slot);
        playerDataConfig.set("CurrentlyMoving",data.moving);
    }
}
