package de.CypDasHuhn.Kit.file_manager.routing.items;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.KitPluginMain;
import de.CypDasHuhn.Kit.file_manager.sql.items.KitManagerSQL;
import de.CypDasHuhn.Kit.file_manager.yml.items.KitManagerYML;
import org.bukkit.inventory.PlayerInventory;

public class KitManager {
    public static void registerKit(KitDTO kit) {
        if (KitPluginMain.usesSQL) {
            KitManagerSQL.registerKit(kit);
        } else {
            KitManagerYML.registerKit(kit);
        }
    }

    public static KitDTO getKit(String kitName) {
        return KitPluginMain.usesSQL ? KitManagerSQL.getKit(kitName) : KitManagerYML.getKit(kitName);
    }
}
