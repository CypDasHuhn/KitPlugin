package de.CypDasHuhn.Kit.file_manager.routing.items;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.KitPluginMain;
import de.CypDasHuhn.Kit.file_manager.sql.items.KitListManagerSQL;
import de.CypDasHuhn.Kit.file_manager.sql.items.KitManagerSQL;
import de.CypDasHuhn.Kit.file_manager.yml.items.KitListManagerYML;
import de.CypDasHuhn.Kit.file_manager.yml.items.KitManagerYML;

import java.util.List;

public class KitListManager {
    public static List<String> getKits() {
        return KitPluginMain.usesSQL ? KitListManagerSQL.getKits() : KitListManagerYML.getKits();
    }
}
