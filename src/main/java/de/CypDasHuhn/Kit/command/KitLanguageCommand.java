package de.CypDasHuhn.Kit.command;

import de.CypDasHuhn.Kit.command.skeleton.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitClassManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitLanguageCommand extends SkeletonCommand {
    public static final String KIT_LANGUAGE_COMMAND = "kitLanguage";
    @Override
    public void command(CommandSender sender, String[] args, String label) {
        if (sender instanceof Player player) {
            player.sendMessage("wip");

            HashMap<String, String> classes = KitClassManager.getClasses();

            for (Map.Entry<String, String> entry : classes.entrySet()) {
                ChatColor text = KitClassManager.colorMap.get(entry.getValue());
                player.sendMessage(text+entry.getValue()+": "+entry.getKey());
            }
        }
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        return new ArrayList<String>(){{add("wip");}};
    }
}
