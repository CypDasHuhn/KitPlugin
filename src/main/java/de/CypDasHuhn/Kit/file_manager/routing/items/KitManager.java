package de.CypDasHuhn.Kit.file_manager.routing.items;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.KitPluginMain;
import de.CypDasHuhn.Kit.file_manager.sql.items.KitManagerSQL;
import de.CypDasHuhn.Kit.file_manager.yml.items.KitManagerYML;

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

    public static void deleteKit(KitDTO kit) {
        if (KitPluginMain.usesSQL) {
            KitManagerSQL.deleteKit(kit);
        } else {
            KitManagerYML.deleteKit(kit);
        }
    }

    public static void setKit(KitDTO kit) {
        if (KitPluginMain.usesSQL) {
            KitManagerSQL.setKit(kit);
        } else {
            KitManagerYML.setKit(kit);
        }
    }

    public static void renameKit(KitDTO kit, String newKitName) {
        if (KitPluginMain.usesSQL) {
            KitManagerSQL.renameKit(kit, newKitName);
        } else {
            KitManagerYML.renameKit(kit, newKitName);
        }
    }

    public static void setInventory(KitDTO kit) {
        if (KitPluginMain.usesSQL) {
            KitManagerSQL.setInventory(kit);
        } else {
            KitManagerYML.setInventory(kit);
        }
    }
}
