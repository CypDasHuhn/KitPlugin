package de.CypDasHuhn.Kit.file_manager.routing.items;

import de.CypDasHuhn.Kit.DTO.ShopDTO;
import de.CypDasHuhn.Kit.KitPluginMain;
import de.CypDasHuhn.Kit.file_manager.sql.items.ShopManagerSQL;
import de.CypDasHuhn.Kit.file_manager.yml.items.ShopManagerYML;

public class ShopManager {
    public static void setShop(ShopDTO shop) {
        if (KitPluginMain.usesSQL) {
            ShopManagerSQL.setShop(shop);
        } else {
            ShopManagerYML.setShop(shop);
        }
    }
}
