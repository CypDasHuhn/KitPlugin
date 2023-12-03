package de.CypDasHuhn.Kit.command;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.command.skeleton.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitListManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitRandomCommand extends SkeletonCommand {
    public static final String KIT_RANDOM_COMMAND = "kitRandom";
    @Override
    public void command(CommandSender sender, String[] args, String label) {
        if (!(sender instanceof Player player)) return;

        List<String> possibleKits = KitListManager.getKits();
        KitDTO kit = KitListManager.getRandomKit(possibleKits);

        player.getInventory().setContents(kit.inventory);
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        return new ArrayList<String>();
    }
}
