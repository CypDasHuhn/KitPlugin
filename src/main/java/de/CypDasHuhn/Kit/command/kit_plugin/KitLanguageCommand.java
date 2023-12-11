package de.CypDasHuhn.Kit.command.kit_plugin;

import de.CypDasHuhn.Kit.command.general.SkeletonCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitLanguageCommand extends SkeletonCommand {
    public static final String KIT_LANGUAGE_COMMAND = "language";

    public void command(CommandSender sender, String[] args, String label) {
        if (sender instanceof Player player) {
            player.sendMessage("wip");
            /*
            HashMap<String, String> classes = KitClassManager.getClasses();

            for (Map.Entry<String, String> entry : classes.entrySet()) {
                ChatColor text = KitClassManager.colorMap.get(entry.getValue());
                player.sendMessage(text+entry.getValue()+": "+entry.getKey());
            }*/
        }
    }

    public List<String> completer(CommandSender sender, String[] args, String label) {
        return new ArrayList<String>(){{add("wip");}};
    }
}
