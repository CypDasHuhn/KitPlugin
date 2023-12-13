package de.CypDasHuhn.Kit.file_manager.routing.items;

import de.CypDasHuhn.Kit.DTO.ShopDTO;
import de.CypDasHuhn.Kit.KitPluginMain;
import de.CypDasHuhn.Kit.file_manager.sql.items.ShopManagerSQL;
import de.CypDasHuhn.Kit.file_manager.sql.players.PlayerDataManagerSQL;
import de.CypDasHuhn.Kit.file_manager.yml.items.ShopManagerYML;
import de.CypDasHuhn.Kit.file_manager.yml.players.PlayerDataManagerYML;
import org.bukkit.entity.Player;

public class ShopManager {
    public static void setShop(ShopDTO shop) {
        if (KitPluginMain.usesSQL) {
            ShopManagerSQL.setShop(shop);
        } else {
            ShopManagerYML.setShop(shop);
        }
    }

    public static ShopDTO getShop(String kitName) {
        return KitPluginMain.usesSQL ? ShopManagerSQL.getShop(kitName) : ShopManagerYML.getShop(kitName);
    }
}
