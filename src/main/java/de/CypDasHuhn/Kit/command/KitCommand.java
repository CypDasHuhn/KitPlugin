package de.CypDasHuhn.Kit.command;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.command.skeleton.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitClassManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitListManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;
import de.CypDasHuhn.Kit.shared.SpigotMethods;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class KitCommand extends SkeletonCommand {
    public static final String KIT_COMMAND = "kit";
    @Override
    public void command(CommandSender sender, String[] args, String label) {
        if (!(sender instanceof Player player)) return;

        if (args.length < 1) return;

        String kitName = args[0];

        KitDTO kit = KitManager.getKit(kitName);

        player.getInventory().setContents(kit.inventory);
        player.sendMessage("hallo!");
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();

        if (args.length == 1) {
            List<String> kits = KitListManager.getKits();
            arguments.addAll(kits);
        }

        return arguments;
    }
}
