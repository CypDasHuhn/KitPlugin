package de.CypDasHuhn.TP.file_manager.player_manager;

import de.CypDasHuhn.TP.file_manager.CustomFiles;
import de.CypDasHuhn.TP.shared.Finals;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class PlayerListManager {
    public static boolean existsByUUID(String UUID) {
        String player = getByUUID(UUID);
        boolean exists = player != null;
        return exists;
    }

    public static boolean existsByName(String name) {
        String player = getByName(name);
        boolean exists = player != null;
        return exists;
    }

    public static String getByUUID(String UUID) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playersConfig = customFiles[0].getFileConfiguration("Players","");
        // find
        String player = playersConfig.getString("UUID."+UUID);
        return player;
    }


    public static String getByName(String name) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playersConfig = customFiles[0].getFileConfiguration("Players","");
        // find
        String player = playersConfig.getString("Name."+name);
        return player;
    }

    public static List<String> getPlayers() {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playersConfig = customFiles[0].getFileConfiguration("Players","");
        int amount = playersConfig.getInt("PlayerAmount");
        List<String> players = new ArrayList<String>();
        // add
        for (int i = 0; i < amount; i++) {
            String currentPlayer = playersConfig.getString("PlayerList."+i);
            players.add(currentPlayer);
        }
        return players;
    }

    public static void add(String name, String UUID) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playersConfig = customFiles[0].getFileConfiguration("Players","");
        int amount = playersConfig.getInt("PlayerAmount");
        // add
        playersConfig.set("PlayerAmount",amount+1);
        playersConfig.set("PlayerList."+amount,name);

        playersConfig.set("Name."+name, UUID);
        playersConfig.set("UUID."+UUID, name);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static void replaceName(String name, String UUID) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playersConfig = customFiles[0].getFileConfiguration("Players","");
        int amount = playersConfig.getInt("PlayerAmount");

        // find
        String oldName = playersConfig.getString("UUID."+UUID);
        int id = Finals.NULL_INT;
        for (int i = 0; i < amount; i++) {
            String currentName = playersConfig.getString("PlayerList."+i);
            if (currentName.equals(oldName)) {
                id = i;
                break;
            }
        }

        playersConfig.set("PlayerList."+id,name);

        playersConfig.set("Name."+oldName, "EMPTY");
        playersConfig.set("Name."+name, UUID);
        playersConfig.set("UUID."+UUID, name);
        // save
        CustomFiles.saveArray(customFiles);
    }
}
