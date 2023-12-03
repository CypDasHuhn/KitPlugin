package de.CypDasHuhn.Kit.file_manager.routing.items;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.KitPluginMain;
import de.CypDasHuhn.Kit.file_manager.sql.items.KitListManagerSQL;
import de.CypDasHuhn.Kit.file_manager.yml.items.KitListManagerYML;
import de.CypDasHuhn.Kit.shared.Finals;

import java.util.List;

public class KitListManager {
    public static List<String> getKits() {
        return KitPluginMain.usesSQL ? KitListManagerSQL.getKits() : KitListManagerYML.getKits();
    }

    public static List<String> sortKits(List<String> kits) {
        return KitPluginMain.usesSQL ? KitListManagerSQL.sortKits(kits) : KitListManagerYML.sortKits(kits);
    }

    public static boolean exists(String kitName) {
        int id = KitPluginMain.usesSQL ? KitListManagerSQL.findId(kitName) : KitListManagerYML.findId(kitName);
        return id != Finals.NULL_INT;
    }

    public static KitDTO getRandomKit(List<String> kits) {
        return KitPluginMain.usesSQL ? KitListManagerSQL.getRandomKit(kits) : KitListManagerYML.getRandomKit(kits);
    }
}
