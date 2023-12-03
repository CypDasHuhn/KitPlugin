package de.CypDasHuhn.Kit.command;

import de.CypDasHuhn.Kit.command.skeleton.SkeletonCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitPermissionCommand extends SkeletonCommand {
    public static final String KIT_PERMISSION_COMMAND = "kitPermission";
    @Override
    public void command(CommandSender sender, String[] args, String label) {
        if (sender instanceof Player player) {
            player.sendMessage("wip");
        }
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        return new ArrayList<String>(){{add("wip");}};
    }
}
