package de.CypDasHuhn.Kit.command.kit.edit;

import de.CypDasHuhn.Kit.Actions.KitAction;
import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.command.general.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitListManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitEditNameCommand extends SkeletonCommand {
    public static final String KIT_EDIT_NAME_COMMAND = "rename";

    @Override
    public void command(CommandSender sender, String[] args, String label) {
        Player player = (Player) sender;

        String kitName = args[0];
        KitDTO kit = KitListManager.specialNames.contains(kitName) ? KitListManager.getKitSpecial(KitListManager.getKits() , kitName) : KitManager.getKit(kitName);

        if (args.length < 3) {
            player.sendMessage("no new name found");
            return;
        }
        String newKitName = args[2];

        boolean specialName = KitListManager.specialNames.contains(newKitName);
        if (specialName) {
            player.sendMessage("name not allowed");
            return;
        }

        boolean newKitExists = KitListManager.exists(newKitName);
        if (newKitExists) {
            player.sendMessage("kit "+kitName+" already exists");
            return;
        }

        KitAction.renameKit(player, kit, newKitName);
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();

        if (args.length == 3) {
            arguments.add("[Name]");
        }

        return arguments;
    }
}
