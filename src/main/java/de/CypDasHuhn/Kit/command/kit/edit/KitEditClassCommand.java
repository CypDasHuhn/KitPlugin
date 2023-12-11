package de.CypDasHuhn.Kit.command.kit.edit;

import de.CypDasHuhn.Kit.Actions.KitAction;
import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.command.general.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitClassManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitListManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitEditClassCommand extends SkeletonCommand {
    public static final String KIT_EDIT_CLASS_COMMAND = "class";

    @Override
    public void command(CommandSender sender, String[] args, String label) {
        Player player = (Player) sender;

        String kitName = args[0];
        KitDTO kit = KitListManager.specialNames.contains(kitName) ? KitListManager.getKitSpecial(KitListManager.getKits() , kitName) : KitManager.getKit(kitName);

        if (args.length < 3) {
            player.sendMessage("no class found");
            return;
        }
        String kitClass = args[2];

        boolean kitClassExists = KitClassManager.getClasses().containsKey(kitClass);
        if (!kitClassExists) {
            player.sendMessage("no kitclass found");
            return;
        }

        KitAction.changeClass(player, kit, kitClass);
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();

        if (args.length == 3) {
            List<String> classes = KitClassManager.getClasses().keySet().stream().toList();
            arguments.addAll(classes);
        }

        return arguments;
    }
}
