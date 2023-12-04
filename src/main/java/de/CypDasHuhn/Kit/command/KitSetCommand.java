package de.CypDasHuhn.Kit.command;

import de.CypDasHuhn.Kit.command.skeleton.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.yml.items.KitClassManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KitSetCommand extends SkeletonCommand {
    public static final String KIT_SET_COMMAND = "kitSet";

    @Override
    public void command(CommandSender sender, String[] args, String label) {
        if (!(sender instanceof Player player)) return; // command blocks don't have an inventory

        for (Map.Entry<String, ChatColor> entry : KitClassManager.colorMap.entrySet()) {
            String text = entry.getKey();
            ChatColor color = entry.getValue();
            player.sendMessage(color + text);
        }
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        return new ArrayList<String>(){{add("wip");}};
    }// /ks [kit name] [optional: Classname]
}
