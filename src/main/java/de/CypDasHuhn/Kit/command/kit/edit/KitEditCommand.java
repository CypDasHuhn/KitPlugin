package de.CypDasHuhn.Kit.command.kit.edit;

import de.CypDasHuhn.Kit.command.general.SkeletonCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KitEditCommand extends SkeletonCommand {
    public static final String KIT_EDIT_COMMAND = "edit";

    public static final HashMap<String, SkeletonCommand> subCommands = new HashMap<String, SkeletonCommand>(){{
        put(KitEditClassCommand.KIT_EDIT_CLASS_COMMAND, new KitEditClassCommand());
        put(KitEditEffectCommand.KIT_EDIT_EFFECT_COMMAND, new KitEditEffectCommand());
        put(KitEditInventoryCommand.KIT_EDIT_INVENTORY_COMMAND, new KitEditInventoryCommand());
        put(KitEditNameCommand.KIT_EDIT_NAME_COMMAND, new KitEditNameCommand());
    }};


    @Override
    public void command(CommandSender sender, String[] args, String label) {
        Player player = (Player) sender;

        String kitName = args[0];

        if (args.length < 2) {
            player.sendMessage("no edit mode given");
            return;
        }
        String editMode = args[1];

        boolean editModeExists = subCommands.containsKey(editMode);
        if (!editModeExists) {
            player.sendMessage("no edit mode found");
            return;
        }

        SkeletonCommand command = subCommands.get(editMode);
        command.command(sender, args, label);
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();

        if (args.length == 2) {
            List<String> commands = subCommands.keySet().stream().toList();
            arguments.addAll(commands);
        }
        else if (args.length > 2) {
            String editMode = args[1];

            boolean isCommand = subCommands.containsKey(editMode);
            if (!isCommand) return arguments;

            SkeletonCommand command = subCommands.get(editMode);
            arguments = command.completer(sender, args, label);
        }

        return arguments;
    }
}
