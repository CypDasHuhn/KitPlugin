package de.CypDasHuhn.Kit.command;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.DTO.OverviewInformationDTO;
import de.CypDasHuhn.Kit.command.skeleton.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitClassManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitListManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;
import de.CypDasHuhn.Kit.file_manager.routing.players.PlayerDataManager;
import de.CypDasHuhn.Kit.interfaces.Interface;
import de.CypDasHuhn.Kit.interfaces.OverviewInterface.OverviewInterface;
import de.CypDasHuhn.Kit.shared.SpigotMethods;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class KitCommand extends SkeletonCommand {
    public static final String KIT_COMMAND = "kit";
    @Override
    public void command(CommandSender sender, String[] args, String label) {
        if (!(sender instanceof Player player)) return;

        if (args.length < 1) {
            OverviewInformationDTO data = PlayerDataManager.getOverviewInformation(player);
            Interface.openTargetInterface(player, OverviewInterface.interfaceName, data);
            return;
        }
        String kitName = args[0];

        boolean kitExists = KitListManager.exists(kitName);
        if (!kitExists) {
            player.sendMessage("no kitname found");
            return;
        }

        KitDTO kit = KitManager.getKit(kitName);

        player.getInventory().setContents(kit.inventory);
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();

        if (args.length == 1) {
            List<String> kits = KitListManager.getKits();
            arguments.addAll(kits);
        }

        return arguments;
    }
}
