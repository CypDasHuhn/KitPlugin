package de.CypDasHuhn.Kit.file_manager.sql.players;

import de.CypDasHuhn.Kit.DTO.interface_context.ConfirmationContextDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.KitContextDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.OverviewContextDTO;
import org.bukkit.entity.Player;

public class PlayerDataManagerSQL {
    public static OverviewContextDTO getOverviewInformation(Player player) {
        return null;
    }
    public static void setOverviewInformation(Player player, OverviewContextDTO data) {

    }

    public static ConfirmationContextDTO getConfirmingContext(Player player) {
        return null;
    }

    public static void setKitContext(Player player, KitContextDTO context) {
    }

    public static KitContextDTO getKitContext(Player player) {
        return null;
    }

    public static void setConfirmationContext(Player player, ConfirmationContextDTO context) {
    }
}
