package de.CypDasHuhn.Kit.interfaces.general;

import de.CypDasHuhn.Kit.file_manager.yml.players.PlayerDataManagerYML;
import de.CypDasHuhn.Kit.interfaces.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class Interface {
    public static HashMap<Player, Boolean> opening = new HashMap<>();
    public static final HashMap<String, SkeletonInterface> interfaceMap = new HashMap<>() {{
        put(OverviewInterface.interfaceName, new OverviewInterface());
        put(PreviewInterface.interfaceName, new PreviewInterface());
        put(EditInterface.interfaceName, new EditInterface());
        put(ConfirmationInterface.interfaceName, new ConfirmationInterface());
        put(NameInterface.interfaceName, new NameInterface());
        put(ClassInterface.interfaceName, new ClassInterface());
        put(ShopInterface.interfaceName, new ShopInterface());
    }};

    public static void openTargetInterface(Player player, String interfaceName, Object... vars) {
        if (!interfaceMap.containsKey(interfaceName)) return;

        opening.put(player, true);
        PlayerDataManagerYML.setInventory(player, interfaceName);

        SkeletonInterface skeletonInterface = interfaceMap.get(interfaceName);

        Inventory customInterface = skeletonInterface.getInterface(player, vars);

        player.openInventory(customInterface);

        opening.put(player, false);
    }

    public static void openCurrentInterface(Player player, Object... vars) {
        String interfaceName = PlayerDataManagerYML.getInventory(player);

        openTargetInterface(player, interfaceName, vars);
    }
}
