package de.CypDasHuhn.Kit.command.kit.edit;

import de.CypDasHuhn.Kit.DTO.ShopDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.ShopContextDTO;
import de.CypDasHuhn.Kit.command.general.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.routing.items.ShopManager;
import de.CypDasHuhn.Kit.file_manager.routing.players.PlayerDataManager;
import de.CypDasHuhn.Kit.interfaces.ShopInterface;
import de.CypDasHuhn.Kit.interfaces.general.Interface;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitEditShopCommand extends SkeletonCommand {
    public static final String KIT_EDIT_SHOP_COMMAND = "shop";

    @Override
    public void command(CommandSender sender, String[] args, String label) {
        Player player = (Player) sender;

        String kitName = args[0];

        ShopDTO shop= ShopManager.getShop(kitName);
        int money = PlayerDataManager.getMoney(player);
        ShopContextDTO context = new ShopContextDTO(shop, false, true, false, false, money);

        Interface.openTargetInterface(player, ShopInterface.interfaceName, context);
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {

        return new ArrayList<>();
    }
}
