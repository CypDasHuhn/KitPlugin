package de.CypDasHuhn.Kit.command;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.command.skeleton.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitListManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;
import de.CypDasHuhn.Kit.shared.Finals;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitDeleteCommand extends SkeletonCommand {
    public static final String KIT_DELETE_COMMAND = "kitDelete";
    @Override
    public void command(CommandSender sender, String[] args, String label) {
        if (!(sender instanceof Player player)) return;

        if (args.length < 1) {
            player.sendMessage("no kitname given");
            return;
        }
        String kitName = args[0];

        boolean kitExists = KitListManager.exists(kitName);
        if (!kitExists) {
            player.sendMessage("no kitname found");
            return;
        }

        KitDTO kit = KitManager.getKit(kitName);

        KitManager.deleteKit(kit);
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
