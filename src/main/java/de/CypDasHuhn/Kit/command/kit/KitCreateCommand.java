package de.CypDasHuhn.Kit.command.kit;

import de.CypDasHuhn.Kit.Actions.KitAction;
import de.CypDasHuhn.Kit.command.general.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitClassManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitCreateCommand extends SkeletonCommand {
    public static final String KIT_CREATE_COMMAND = "create";

    @Override
    public void command(CommandSender sender, String[] args, String label) {
        Player player = (Player) sender;

        String kitName = args[0];

        if (args.length < 2) {
            player.sendMessage("§cno §6kitclass §cgiven");
            return;
        }
        String kitClass = args[1];

        boolean kitClassExists = KitClassManager.getClasses().containsKey(kitClass);
        if (!kitClassExists) {
            player.sendMessage("§cno §6kitclass §cfound");
            return;
        }

        KitAction.kitCreate(player, kitName, kitClass);
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();

        if (args.length == 2) {
            List<String> classes = KitClassManager.getClasses().keySet().stream().toList();
            arguments.addAll(classes);
        }

        return arguments;
    }
}
