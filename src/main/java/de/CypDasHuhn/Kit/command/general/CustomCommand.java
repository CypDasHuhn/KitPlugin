package de.CypDasHuhn.Kit.command.general;

import de.CypDasHuhn.Kit.command.*;
import de.CypDasHuhn.Kit.command.skeleton.SkeletonCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;

public class CustomCommand implements CommandExecutor {
    public static final HashMap<String, SkeletonCommand> commandMap = new HashMap<String, SkeletonCommand>(){{
        put(KitCommand.KIT_COMMAND, new KitCommand());
        put(KitRandomCommand.KIT_RANDOM_COMMAND, new KitRandomCommand());

        put(KitSetCommand.KIT_SET_COMMAND, new KitSetCommand());
        put(KitEditCommand.KIT_EDIT_COMMAND, new KitEditCommand());
        put(KitDeleteCommand.KIT_DELETE_COMMAND, new KitDeleteCommand());

        put(KitPermissionCommand.KIT_PERMISSION_COMMAND, new KitPermissionCommand());
        put(KitLanguageCommand.KIT_LANGUAGE_COMMAND, new KitLanguageCommand());
    }};

    public static final HashMap<String, String[]> aliasesMap = new HashMap<String, String[]>(){{
        put(KitCommand.KIT_COMMAND, new String[]{"kit", "k"});
        put(KitRandomCommand.KIT_RANDOM_COMMAND, new String[]{"kitRandom", "kRandom", "kr", "kitR"});

        put(KitSetCommand.KIT_SET_COMMAND, new String[]{"kitSet", "kSet","ks", "kitS"});
        put(KitEditCommand.KIT_EDIT_COMMAND, new String[]{"kitEdit", "kEdit", "ke", "kitE"});
        put(KitDeleteCommand.KIT_DELETE_COMMAND, new String[]{"kitDelete", "kDelete","kd", "kitD"});

        put(KitPermissionCommand.KIT_PERMISSION_COMMAND, new String[]{"kitPermission", "kPermission","kp", "kitP"});
        put(KitLanguageCommand.KIT_LANGUAGE_COMMAND, new String[]{"kitLanguage", "kLanguage","kl", "kitL"});

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