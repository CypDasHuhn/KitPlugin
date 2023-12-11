package de.CypDasHuhn.Kit.command.general;

import de.CypDasHuhn.Kit.command.kit.KitCommand;
import de.CypDasHuhn.Kit.command.kit_plugin.KitPluginCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;

public class CustomCommand implements CommandExecutor {
    public static final HashMap<String, SkeletonCommand> commandMap = new HashMap<String, SkeletonCommand>(){{
        put(KitCommand.KIT_COMMAND, new KitCommand());
        put(KitPluginCommand.KIT_PLUGIN_COMMAND, new KitPluginCommand());
    }};

    public static final HashMap<String, String[]> aliasesMap = new HashMap<String, String[]>(){{
        put(KitCommand.KIT_COMMAND, new String[]{"kit", "k"});
        put(KitPluginCommand.KIT_PLUGIN_COMMAND, new String[]{"kitPlugin", "kp"});
    }};

    // if the label (the command written) equals to one of the aliases in aliasesMap,
    // it parses the command of the linked Class with the needed attributes.
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        for (HashMap.Entry<String, String[]> entry : aliasesMap.entrySet()) {
            String commandLabel = entry.getKey();
            String[] aliases = entry.getValue();

            if (label.equalsIgnoreCase(commandLabel) || Arrays.stream(aliases).anyMatch(label::equalsIgnoreCase)) {
                SkeletonCommand skeletonCommand = commandMap.get(commandLabel);
                skeletonCommand.command(sender, args, label);
            }
        }
        return false;
    }

    public static boolean isCommand(String label, String command) {
        String[] aliases = CustomCommand.aliasesMap.get(command);
        for (String alias : aliases) {
            if (alias.equalsIgnoreCase(label)) {
                return true;
            }
        }
        return false;
    }
}