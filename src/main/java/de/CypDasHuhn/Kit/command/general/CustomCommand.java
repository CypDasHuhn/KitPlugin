package de.CypDasHuhn.TP.command.general;

import de.CypDasHuhn.TP.command.*;
import de.CypDasHuhn.TP.command.skeleton.SkeletonCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;

public class CustomCommand implements CommandExecutor {
    public static final HashMap<String, SkeletonCommand> commandMap = new HashMap<String, SkeletonCommand>(){{
        put(TeleportCommand.TELEPORT_COMMAND, new TeleportCommand());
        put(TeleportCommand.TELEPORT_USER_COMMAND, new TeleportCommand());
        put(TeleportCommand.TELEPORT_GLOBAL_COMMAND, new TeleportCommand());
        put(TeleportSetCommand.TELEPORT_SET_COMMAND, new TeleportSetCommand());
        put(TeleportEditCommand.TELEPORT_EDIT_COMMAND, new TeleportEditCommand());
        put(TeleportDeleteCommand.TELEPORT_DELETE_COMMAND, new TeleportDeleteCommand());
        put(PermissionCommand.PERMISSION_COMMAND, new PermissionCommand());
        put(LanguageCommand.LANGUAGE_COMMAND, new LanguageCommand());
        put(TeleportFolderCommand.FOLDER_COMMAND, new TeleportFolderCommand());
        put(TeleportTagCommand.TAG_COMMAND, new TeleportTagCommand());
    }};

    public static final HashMap<String, String[]> aliasesMap = new HashMap<String, String[]>(){{
        put(TeleportCommand.TELEPORT_COMMAND, new String[]{"teleport", "t"});
        put(TeleportCommand.TELEPORT_USER_COMMAND, new String[]{"teleportUser", "tUser", "tu"});
        put(TeleportCommand.TELEPORT_GLOBAL_COMMAND, new String[]{"teleportGlobal", "tGlobal", "tg"});
        put(TeleportSetCommand.TELEPORT_SET_COMMAND, new String[]{"teleportSet", "tSet", "ts"});
        put(TeleportEditCommand.TELEPORT_EDIT_COMMAND, new String[]{"teleportEdit", "tEdit", "te"});
        put(TeleportDeleteCommand.TELEPORT_DELETE_COMMAND, new String[]{"teleportDelete", "tDelete", "td"});
        put(PermissionCommand.PERMISSION_COMMAND, new String[]{"teleportPermission", "tPermission", "tpm"});
        put(LanguageCommand.LANGUAGE_COMMAND, new String[]{"teleportLanguage", "tLanguage", "tl"});
        put(TeleportFolderCommand.FOLDER_COMMAND, new String[]{"teleportFolder", "tFolder", "tf"});
        put(TeleportTagCommand.TAG_COMMAND, new String[]{"teleportTag", "tTag", "tt"});

        put(Testcommand.TEST_COMMAND, new String[]{"testCommand"});
    }};

    // if the label (the command written) equals to one of the aliases in aliasesMap,
    // it parses the command of the linked Class with the needed attributes.
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        for (HashMap.Entry<String, String[]> entry : aliasesMap.entrySet()) {
            String commandLabel = entry.getKey();
            String[] aliases = entry.getValue();

            if (label.equalsIgnoreCase(commandLabel) || Arrays.stream(aliases).anyMatch(alias -> label.equalsIgnoreCase(alias))) {
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