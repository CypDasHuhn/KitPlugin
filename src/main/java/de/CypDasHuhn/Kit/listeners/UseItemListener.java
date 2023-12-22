package de.CypDasHuhn.Kit.listeners;

import de.CypDasHuhn.Kit.DTO.ShopDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.ShopContextDTO;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitListManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.ShopManager;
import de.CypDasHuhn.Kit.file_manager.routing.players.PlayerDataManager;
import de.CypDasHuhn.Kit.interfaces.ShopInterface;
import de.CypDasHuhn.Kit.interfaces.general.Interface;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UseItemListener implements Listener {
    public static final Map<Location, UUID> spawnEggTargetLocation = new HashMap<>();

    @EventHandler
    public void listener(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null) return;

        ItemMeta itemMeta = item.getItemMeta();
        String displayName = itemMeta.getDisplayName();

        boolean isShopItem = item.getType().equals(Material.EMERALD) && itemMeta.hasEnchant(Enchantment.DAMAGE_ALL) && displayName.startsWith("§a") && displayName.endsWith(" shop");
        if (isShopItem)  {
            String kitName = displayName.substring(2,displayName.length()-(1+("shop".length())));
            System.out.println(kitName);

            if (KitListManager.exists(kitName)) {
                ShopDTO shop = ShopManager.getShop(kitName);
                int money = PlayerDataManager.getMoney(player);
                ShopContextDTO context = new ShopContextDTO(shop, true, false, false, false, money);
                Interface.openTargetInterface(player, ShopInterface.interfaceName, context);
            }
            return;
        }

        boolean isSpawnEggUse = event.getAction() == Action.RIGHT_CLICK_BLOCK && item.getType().toString().endsWith("_SPAWN_EGG");
        if (isSpawnEggUse) {
            Location targetLocation = player.getTargetBlockExact(5).getLocation();
            spawnEggTargetLocation.put(targetLocation, player.getUniqueId());
        }
    }
}
