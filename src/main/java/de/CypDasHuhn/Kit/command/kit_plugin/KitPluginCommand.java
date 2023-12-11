package de.CypDasHuhn.Kit.command.kit_plugin;

import de.CypDasHuhn.Kit.command.general.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.routing.players.PermissionManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class KitPluginCommand extends SkeletonCommand {
    public static final String KIT_PLUGIN_COMMAND = "kitPlugin";

    public static final HashMap<String, SkeletonCommand> subCommands = new HashMap<String, SkeletonCommand>(){{
       put(KitPluginClassCommand.KIT_CLASS_COMMAND, new KitPluginClassCommand());
       put(KitLanguageCommand.KIT_LANGUAGE_COMMAND, new KitLanguageCommand());
       put(KitPluginPermissionCommand.KIT_PERMISSION_COMMAND, new KitPluginPermissionCommand());
       put(KitPluginHelpCommand.KIT_PLUGIN_HELP_COMMAND, new KitPluginHelpCommand());
    }};

    @Override
    public void command(CommandSender sender, String[] args, String label) {
        if (args.length < 1) {
            if (sender instanceof Player player) player.sendMessage("no command given");
            return;
        }
        String commandName = args[0];

        boolean commandExists = subCommands.containsKey(commandName);
        if (!commandExists) {
            if (sender instanceof Player player) player.sendMessage("command couldnt be found");
            return;
        }

        SkeletonCommand command = subCommands.get(commandName);
        command.command(sender, Arrays.copyOfRange(args, 1, args.length), label);
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();

        if (args.length == 1) {
            List<String> commands = new ArrayList<>(subCommands.keySet().stream().toList());

            boolean isPermissioned = PermissionManager.isPermissioned(((Player)sender).getName());
            if (!isPermissioned) commands.remove(KitPluginPermissionCommand.KIT_PERMISSION_COMMAND);

            arguments.addAll(commands);
        }

        if (args.length > 1) {
            String commandName = args[0];
            boolean commandExists = !subCommands.keySet().contains(commandName);
            if (commandExists) return arguments;

            SkeletonCommand command = subCommands.get(commandName);
            arguments = command.completer(sender, Arrays.copyOfRange(args, 1, args.length), label);
        }

        return arguments;
    }
}
