package de.CypDasHuhn.Kit.command.kit;

import de.CypDasHuhn.Kit.DTO.interface_context.OverviewContextDTO;
import de.CypDasHuhn.Kit.command.kit.edit.KitEditCommand;
import de.CypDasHuhn.Kit.command.general.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitListManager;
import de.CypDasHuhn.Kit.file_manager.routing.players.PlayerDataManager;
import de.CypDasHuhn.Kit.interfaces.general.Interface;
import de.CypDasHuhn.Kit.interfaces.OverviewInterface;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class KitCommand extends SkeletonCommand {
    public static final String KIT_COMMAND = "kit";

    public static final HashMap<String, SkeletonCommand> subCommands = new HashMap<String,SkeletonCommand>() {{
        put(KitGetCommand.KIT_GET_COMMAND,new KitGetCommand());
        put(KitCreateCommand.KIT_CREATE_COMMAND, new KitCreateCommand());
        put(KitEditCommand.KIT_EDIT_COMMAND, new KitEditCommand());
        put(KitDeleteCommand.KIT_DELETE_COMMAND, new KitDeleteCommand());
    }};
    @Override
    public void command(CommandSender sender, String[] args, String label) {
        if (!(sender instanceof Player player)) return;

        if (args.length < 1) {
            OverviewContextDTO data = PlayerDataManager.getOverviewInformation(player);
            Interface.openTargetInterface(player, OverviewInterface.interfaceName, data);
            return;
        }
        String commandName = args[0];

        boolean commandExists = subCommands.containsKey(commandName);
        if (!commandExists) {
            player.sendMessage("§6command §cdoesnt exist");
            return;
        }

        if (args.length < 2) {
            player.sendMessage("§cno §6kitname §cgiven");
            return;
        }
        String kitName = args[1];

        boolean specificKit = !commandName.equals(KitCreateCommand.KIT_CREATE_COMMAND);
        if (specificKit) {
            boolean selectedKit = KitListManager.specialNames.contains(kitName);

            boolean kitExists = KitListManager.exists(kitName) || selectedKit;
            if (!kitExists) {
                player.sendMessage("§cno §6kitname §cfound");
                return;
            }
        }
        else {
            boolean specialName = KitListManager.specialNames.contains(kitName);
            if (specialName) {
                player.sendMessage("§cname not allowed");
                return;
            }

            boolean kitExists = KitListManager.exists(kitName);
            if (kitExists) {
                player.sendMessage("§ckit §6"+kitName+" §calready §6exists");
                return;
            }
        }

        SkeletonCommand command = subCommands.get(commandName);
        command.command(sender, Arrays.copyOfRange(args, 1, args.length), label);
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();

        if (args.length == 1) {
            List<String> commands = subCommands.keySet().stream().toList();
            arguments.addAll(commands);
        }
        else if (args.length == 2) {
            String commandName = args[0];
            boolean specificKit = !commandName.equals(KitCreateCommand.KIT_CREATE_COMMAND);

            if (specificKit) {
                List<String> kits = KitListManager.getKits();
                arguments.addAll(kits);
                arguments.addAll(KitListManager.specialNames);
            } else {
                arguments.add("[Name]");
            }
        }
        else if (args.length > 2) {
            String commandName = args[0];
            boolean commandExists = subCommands.containsKey(commandName);
            if (!commandExists) return arguments;

            SkeletonCommand command = subCommands.get(commandName);
            arguments = command.completer(sender, Arrays.copyOfRange(args, 1, args.length), label);
        }

        return arguments;
    }
}
