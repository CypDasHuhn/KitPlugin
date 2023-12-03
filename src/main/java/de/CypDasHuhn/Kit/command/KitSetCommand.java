package de.CypDasHuhn.Kit.command;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.command.skeleton.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitClassManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;
import de.CypDasHuhn.Kit.file_manager.yml.items.KitClassManagerYML;
import de.CypDasHuhn.Kit.shared.SpigotMethods;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitSetCommand extends SkeletonCommand {
    public static final String KIT_SET_COMMAND = "kitSet";

    @Override
    public void command(CommandSender sender, String[] args, String label) {
        if (!(sender instanceof Player player)) return; // command blocks don't have an inventory

        if (args.length < 1) {
            player.sendMessage("no kitname");
            return;
        }
        String kitName = args[0];


        if (args.length < 2) {
            player.sendMessage("no kitclass given");
            return;
        }
        String kitClass = args[1];

        boolean kitClassExists = KitClassManager.getClasses().containsKey(kitClass);
        if (!kitClassExists) {
            player.sendMessage("no kitclass found");
            return;
        }

        ItemStack[] inventory = SpigotMethods.inventoryToArray(player.getInventory());

        KitDTO kit = new KitDTO(kitName, kitClass,inventory,null,false);

        KitManager.registerKit(kit);

        player.sendMessage("durchgekommen");
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();

        if (args.length == 1) {
            arguments.add("[Name]");
        }
        else if (args.length == 2) {
            List<String> classes = KitClassManager.getClasses().keySet().stream().toList();
            arguments.addAll(classes);
        }

        return arguments;
    }// /ks [kit name] [optional: Classname]
}
