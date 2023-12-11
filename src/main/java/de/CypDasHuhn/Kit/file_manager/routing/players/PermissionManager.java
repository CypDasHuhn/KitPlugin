package de.CypDasHuhn.Kit.file_manager.routing.players;

import de.CypDasHuhn.Kit.KitPluginMain;
import de.CypDasHuhn.Kit.file_manager.sql.players.PermissionManagerSQL;
import de.CypDasHuhn.Kit.file_manager.yml.players.PermissionManagerYML;

public class PermissionManager {
    public static boolean isPermissioned(String playerName) {
        return KitPluginMain.usesSQL ? PermissionManagerSQL.isPermissioned(playerName) : PermissionManagerYML.isPermissioned(playerName);
    }

    public static void add(String playerName) {
        if (KitPluginMain.usesSQL) {
            PermissionManagerSQL.add(playerName);
        } else {
            PermissionManagerYML.add(playerName);
        }
    }

    public static void remove(String playerName) {
        if (KitPluginMain.usesSQL) {
            PermissionManagerSQL.remove(playerName);
        } else {
            PermissionManagerYML.remove(playerName);
        }
    }
}
