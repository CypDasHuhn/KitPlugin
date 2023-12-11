package de.CypDasHuhn.Kit.command.kit_plugin;

import de.CypDasHuhn.Kit.command.general.SkeletonCommand;
import de.CypDasHuhn.Kit.compability.items.ClassCompability;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitClassManager;
import de.CypDasHuhn.Kit.file_manager.routing.players.PermissionManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitPluginClassCommand extends SkeletonCommand {
    public static final String KIT_CLASS_COMMAND = "class";

    public void command(CommandSender sender, String[] args, String label) {
        if (!(sender instanceof Player player)) return;

        boolean isPermissioned = PermissionManager.isPermissioned(player.getName());
        if (!isPermissioned) {
            player.sendMessage("No permission");
            return;
        }

        if (args.length < 1) {
            player.sendMessage("No class color given");
            return;
        }
        String kitClassColor = args[0];

        boolean kitClassColorExists = KitClassManager.colorMap.containsKey(kitClassColor);
        if (!kitClassColorExists) {
            player.sendMessage("no class color found");
            return;
        }

        if (args.length > 2) {
            player.sendMessage("No class name given");
            return;
        }
        String kitClassName = args[1];

        boolean kitClassNameExists = KitClassManager.getClasses().containsKey(kitClassName);
        if (kitClassNameExists) {
            player.sendMessage("class name already exists");
            return;
        }


        String oldKitClassName = "";
        HashMap<String, String> map = KitClassManager.getClasses();
        for (Map.Entry entry : map.entrySet()) {
            if (kitClassColor.equals(entry.getValue())) {
                oldKitClassName = (String) entry.getKey();
            }
        }

        ClassCompability.renameClasses(oldKitClassName, kitClassName);
        KitClassManager.setClass(kitClassColor, kitClassName);

        player.sendMessage(kitClassColor+" now is "+kitClassName);
    }

    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();

        if (args.length == 1) {
            List<String> classColors = KitClassManager.colorMap.keySet().stream().toList();
            arguments.addAll(classColors);
        }
        else if (args.length == 2) {
            boolean kitClassColorExists = KitClassManager.colorMap.containsKey(args[0]);
            if (kitClassColorExists) {
                arguments.add("[Name]");
            }
        }

        return arguments;
    }
}
