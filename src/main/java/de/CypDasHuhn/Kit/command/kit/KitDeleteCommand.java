package de.CypDasHuhn.Kit.command.kit;

import de.CypDasHuhn.Kit.Actions.KitAction;
import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.command.general.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitListManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitDeleteCommand extends SkeletonCommand {
    public static final String KIT_DELETE_COMMAND = "delete";
    @Override
    public void command(CommandSender sender, String[] args, String label) {
        if (!(sender instanceof Player player)) return;

        String kitName = args[0];

        KitDTO kit = KitListManager.specialNames.contains(kitName) ? KitListManager.getKitSpecial(KitListManager.getKits() , kitName) : KitManager.getKit(kitName);

        KitAction.deleteKit(player, kit);
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
