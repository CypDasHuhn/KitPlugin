package de.CypDasHuhn.TP.file_manager.player_manager;

import de.CypDasHuhn.TP.file_manager.CustomFiles;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.configuration.file.FileConfiguration;

public class PermissionManager {
    private static final String PERMISSION_FILE_NAME = "Permission";
    private static final String AMOUNT_DIRECTORY = "Amount";
    private static final String PLAYERS_DIRECTORY = "Players";
    public static void add(String playerName) {
        // check
        boolean exists = PlayerListManager.existsByName(playerName);
        if (!exists) return;

        boolean isPermissioned = isPermissioned(playerName);
        if (isPermissioned) return;
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration permissionConfig = customFiles[0].getFileConfiguration(PERMISSION_FILE_NAME,"");
        int amount = permissionConfig.getInt(AMOUNT_DIRECTORY);
        String UUID = PlayerListManager.getByName(playerName);
        // add
        permissionConfig.set(AMOUNT_DIRECTORY, amount+1);
        permissionConfig.set(PLAYERS_DIRECTORY+"."+amount, UUID);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static void remove(String playerName) {
        // check
        boolean exists = PlayerListManager.existsByName(playerName);
        if (!exists) return;

        boolean isPermissioned = isPermissioned(playerName);
        if (!isPermissioned) return;
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration permissionConfig = customFiles[0].getFileConfiguration(PERMISSION_FILE_NAME,"");
        int amount = permissionConfig.getInt(AMOUNT_DIRECTORY);
        int permissionID = getPermissionID(playerName);
        // delete
        for (int i = permissionID; i < amount; i++) {
            String nextItem = permissionConfig.getString(PLAYERS_DIRECTORY+"."+(i+1));
            permissionConfig.set(PLAYERS_DIRECTORY+"."+i, nextItem);
        }
        permissionConfig.set(AMOUNT_DIRECTORY, amount-1);
        permissionConfig.set(PLAYERS_DIRECTORY+"."+permissionID, Finals.EMPTY);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static boolean isPermissioned(String playerName) {
        // check
        boolean exists = PlayerListManager.existsByName(playerName);
        if (!exists) return false;
        //find
        int permissionID = getPermissionID(playerName);
        boolean isPermissioned = permissionID != Finals.NULL_INT;
        return isPermissioned;
    }

    public static int getPermissionID(String playerName) {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration permissionConfig = customFiles[0].getFileConfiguration(PERMISSION_FILE_NAME,"");
        String UUID = PlayerListManager.getByName(playerName);
        int amount = permissionConfig.getInt(AMOUNT_DIRECTORY);
        int targetID = Finals.NULL_INT;
        // find
        for (int i = 0; i < amount; i++) {
            String currentUUID = permissionConfig.getString(PLAYERS_DIRECTORY+"."+i);
            if (currentUUID.equals(UUID)) {
                targetID = i;
                break;
            }
        }
        return targetID;
    }
}
