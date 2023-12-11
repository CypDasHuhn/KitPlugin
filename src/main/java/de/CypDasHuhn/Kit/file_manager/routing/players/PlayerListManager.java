package de.CypDasHuhn.Kit.file_manager.routing.players;

import de.CypDasHuhn.Kit.KitPluginMain;
import de.CypDasHuhn.Kit.file_manager.sql.players.PlayerListManagerSQL;
import de.CypDasHuhn.Kit.file_manager.yml.players.PlayerListManagerYML;

import java.util.List;

public class PlayerListManager {
    public static boolean existsByName(String name) {
        return KitPluginMain.usesSQL ? PlayerListManagerSQL.existsByName(name) : PlayerListManagerYML.existsByName(name);
    }

    public static List<String> getPlayers() {
        return KitPluginMain.usesSQL ? PlayerListManagerSQL.getPlayers() : PlayerListManagerYML.getPlayers();
    }
}
