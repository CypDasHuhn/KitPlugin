package de.CypDasHuhn.Kit.command;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.command.skeleton.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitClassManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitListManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;
import de.CypDasHuhn.Kit.shared.SpigotMethods;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitEditCommand extends SkeletonCommand {
    public static final String KIT_EDIT_COMMAND = "kitEdit";

    public static final String RENAME_EDIT_MODE = "rename";
    public static final String UPDATE_EDIT_MODE = "update";
    public static final String CLASS_EDIT_MODE = "class";


    @Override
    public void command(CommandSender sender, String[] args, String label) {
        if (!(sender instanceof Player player)) return;

        if (args.length < 1) {
            player.sendMessage("no kitname given");
            return;
        }
        String kitName = args[0];

        boolean kitExists = KitListManager.exists(kitName);
        if (!kitExists) {
            player.sendMessage("no kitname found");
            return;
        }

        if (args.length < 2) {
            player.sendMessage("no edit mode given");
            return;
        }
        String editMode = args[1];

        boolean editModeExists = editMode.equals(RENAME_EDIT_MODE) || editMode.equals(UPDATE_EDIT_MODE) || editMode.equals(CLASS_EDIT_MODE);
        if (!editModeExists) {
            player.sendMessage("no edit mode found");
            return;
        }

        KitDTO kit = KitManager.getKit(kitName);

        if (editMode.equals(RENAME_EDIT_MODE)) { // rename
            if (args.length < 3) {
                player.sendMessage("no new name found");
                return;
            }
            String newKitName = args[2];

            KitManager.renameKit(kit, newKitName);
        }
        else if (editMode.equals(UPDATE_EDIT_MODE)){ // update
            kit.inventory = SpigotMethods.inventoryToArray(player.getInventory());
            KitManager.setInventory(kit);
            player.sendMessage("s");
        } else { // change class
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

            kit.kitClass = kitClass;
            KitManager.setKit(kit);
        }
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();

        if (args.length == 1) {
            List<String> kits = KitListManager.getKits();
            arguments.addAll(kits);
        }
        else if (args.length == 2) {
            arguments.add(RENAME_EDIT_MODE);
            arguments.add(UPDATE_EDIT_MODE);
            arguments.add(CLASS_EDIT_MODE);
        }
        else if (args.length == 3) {
            String editMode = args[1];
            if (editMode.equals(RENAME_EDIT_MODE)) {
                arguments.add("[Name]");
            } else if (editMode.equals(CLASS_EDIT_MODE)) {
                List<String> classes = KitClassManager.getClasses().keySet().stream().toList();
                arguments.addAll(classes);
            }
        }

        return arguments;
    }
}
