package de.CypDasHuhn.Kit.file_manager.routing.players;

import de.CypDasHuhn.Kit.DTO.OverviewInformationDTO;
import de.CypDasHuhn.Kit.KitPluginMain;
import de.CypDasHuhn.Kit.file_manager.sql.players.PlayerDataManagerSQL;
import de.CypDasHuhn.Kit.file_manager.yml.players.PlayerDataManagerYML;
import org.bukkit.entity.Player;

public class PlayerDataManager {
    public static OverviewInformationDTO getOverviewInformation(Player player) {
        return KitPluginMain.usesSQL ? PlayerDataManagerSQL.getOverviewInformation(player) : PlayerDataManagerYML.getOverviewInformation(player);
    }
    public static void setOverviewInformation(Player player, OverviewInformationDTO data) {
        if (KitPluginMain.usesSQL) {
            PlayerDataManagerSQL.setOverviewInformation(player, data);
        } else {
            PlayerDataManagerYML.setOverviewInformation(player, data);
        }
    }
}
