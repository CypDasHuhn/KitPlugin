package de.CypDasHuhn.Kit.interfaces;

import de.CypDasHuhn.Kit.file_manager.yml.players.PlayerDataManagerYML;
import de.CypDasHuhn.Kit.interfaces.OverviewInterface.OverviewInterface;
import de.CypDasHuhn.Kit.interfaces.OverviewInterface.OverviewInterfaceListener;
import de.CypDasHuhn.Kit.interfaces.skeleton.SkeletonInterface;
import de.CypDasHuhn.Kit.interfaces.skeleton.SkeletonInterfaceListener;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class Interface {
    public static HashMap<Player, Boolean> opening = new HashMap<Player, Boolean>();
    public static final HashMap<String, SkeletonInterface> interfaceMap = new HashMap<String, SkeletonInterface>(){{
        put(OverviewInterface.interfaceName, new OverviewInterface());
    }};

    public static final HashMap<String, SkeletonInterfaceListener> listenerMap = new HashMap<String, SkeletonInterfaceListener>(){{
        put(OverviewInterface.interfaceName, new OverviewInterfaceListener());
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
