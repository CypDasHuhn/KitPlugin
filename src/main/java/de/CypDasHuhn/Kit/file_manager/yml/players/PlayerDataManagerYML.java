package de.CypDasHuhn.Kit.file_manager.yml.players;

import de.CypDasHuhn.Kit.DTO.OverviewInformationDTO;
import de.CypDasHuhn.Kit.file_manager.yml.CustomFiles;
import de.CypDasHuhn.Kit.shared.Finals;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerDataManagerYML {
    public static final String dataFileDir = "Players";
    public static final String inventoryDir = "Inventory";
    public static final String rowDir = "Row";

    public static void setInventory(Player player, String inventory) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(UUID,dataFileDir);
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
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(UUID,dataFileDir);
        // get
        String inventory = playerDataConfig.getString(inventoryDir);
        if (inventory == null) inventory = Finals.EMPTY;
        return inventory;
    }

    public static void setRow(Player player, int row) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(UUID,dataFileDir);
        // set
        playerDataConfig.set(rowDir, row);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static int getRow(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(UUID,dataFileDir);
        // get
        int row = playerDataConfig.getInt(rowDir);
        if (row == 0) {
            row = 1;
            setRow(player, row);
        }
        return row;
    }

    public static OverviewInformationDTO getOverviewInformation(Player player) {
        int row = getRow(player);
        String inventory = getInventory(player);

        OverviewInformationDTO data = new OverviewInformationDTO(row);

        return data;
    }
    public static void setOverviewInformation(Player player, OverviewInformationDTO data) {
        setRow(player, data.row);
    }
}
