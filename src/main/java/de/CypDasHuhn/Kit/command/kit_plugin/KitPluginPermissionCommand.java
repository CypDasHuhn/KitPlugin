package de.CypDasHuhn.Kit.command.kit_plugin;

import de.CypDasHuhn.Kit.command.general.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.routing.players.PermissionManager;
import de.CypDasHuhn.Kit.file_manager.routing.players.PlayerListManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitPluginPermissionCommand extends SkeletonCommand {
    public static final String KIT_PERMISSION_COMMAND = "permission";

    public void command(CommandSender sender, String[] args, String label) {
        // check
        boolean isPermissioned = false;
        if (sender instanceof ConsoleCommandSender) isPermissioned = true;
        else if (sender instanceof Player player) isPermissioned = PermissionManager.isPermissioned(player.getName());

        if (!isPermissioned) {
            if (sender instanceof Player player) player.sendMessage("no permission");
            return;
        }

        if (args.length < 1) {
            if (sender instanceof Player player) player.sendMessage("no player name given");
            return;
        }
        String targetPlayer = args[0];
        boolean playerExists = PlayerListManager.existsByName(targetPlayer);
        if (!playerExists) {
            if (sender instanceof Player player) player.sendMessage("player not found");
            return;
        }
        // prework
        boolean targetPlayerPermissioned = PermissionManager.isPermissioned(targetPlayer);
        // handle
        if (!targetPlayerPermissioned) {
            PermissionManager.add(targetPlayer);
            if (sender instanceof Player player) player.sendMessage(targetPlayer+" is now permissioned");
        } else {
            PermissionManager.remove(targetPlayer);
            if (sender instanceof Player player) player.sendMessage(targetPlayer+" is not permissioned anymore");
        }
    }

    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();
        switch (args.length) {
            case 1:
                List<String> players = PlayerListManager.getPlayers();
                arguments.addAll(players);
                break;
        }
        return arguments;
    }
}
