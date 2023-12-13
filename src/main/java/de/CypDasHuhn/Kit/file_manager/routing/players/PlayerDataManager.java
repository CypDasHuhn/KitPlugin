package de.CypDasHuhn.Kit.file_manager.routing.players;

import de.CypDasHuhn.Kit.DTO.interface_context.ConfirmationContextDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.KitContextDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.OverviewContextDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.ShopContextDTO;
import de.CypDasHuhn.Kit.KitPluginMain;
import de.CypDasHuhn.Kit.file_manager.sql.players.PlayerDataManagerSQL;
import de.CypDasHuhn.Kit.file_manager.yml.players.PlayerDataManagerYML;
import org.bukkit.entity.Player;

public class PlayerDataManager {
    public static OverviewContextDTO getOverviewInformation(Player player) {
        return KitPluginMain.usesSQL ? PlayerDataManagerSQL.getOverviewInformation(player) : PlayerDataManagerYML.getOverviewContext(player);
    }
    public static void setOverviewInformation(Player player, OverviewContextDTO data) {
        if (KitPluginMain.usesSQL) {
            PlayerDataManagerSQL.setOverviewInformation(player, data);
        } else {
            PlayerDataManagerYML.setOverviewContext(player, data);
        }
    }

    public static void setConfirmationContext(Player player, ConfirmationContextDTO context) {
        if (KitPluginMain.usesSQL) {
            PlayerDataManagerSQL.setConfirmationContext(player, context);
        } else {
            PlayerDataManagerYML.setConfirmationContext(player, context);
        }
    }

    public static ConfirmationContextDTO getConfirmationContext(Player player) {
        return KitPluginMain.usesSQL ? PlayerDataManagerSQL.getConfirmingContext(player) : PlayerDataManagerYML.getConfirmingContext(player);
    }

    public static void setKitContext(Player player, KitContextDTO context) {
        if (KitPluginMain.usesSQL) {
            PlayerDataManagerSQL.setKitContext(player, context);
        } else {
            PlayerDataManagerYML.setKitContext(player, context);
        }
    }

    public static KitContextDTO getKitContext(Player player) {
        return KitPluginMain.usesSQL ? PlayerDataManagerSQL.getKitContext(player) : PlayerDataManagerYML.getKitContext(player);
    }

    public static void setShopContext(Player player, ShopContextDTO context) {
        if (KitPluginMain.usesSQL) {
            PlayerDataManagerSQL.setShopContext(player, context);
        } else {
            PlayerDataManagerYML.setShopContext(player, context);
        }
    }

    public static ShopContextDTO getShopContext(Player player) {
        return KitPluginMain.usesSQL ? PlayerDataManagerSQL.getShopContext(player) : PlayerDataManagerYML.getShopContext(player);
    }

    public static int getMoney(Player player) {
        return KitPluginMain.usesSQL ? PlayerDataManagerSQL.getMoney(player) : PlayerDataManagerYML.getMoney(player);
    }

    public static void setMoney(Player player, int money) {
        if (KitPluginMain.usesSQL) {
            PlayerDataManagerSQL.setMoney(player, money);
        } else {
            PlayerDataManagerYML.setMoney(player, money);
        }
    }
}
