package de.CypDasHuhn.Kit.interfaces;

import de.CypDasHuhn.Kit.Actions.KitAction;
import de.CypDasHuhn.Kit.DTO.interface_context.ConfirmationContextDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.OverviewContextDTO;
import de.CypDasHuhn.Kit.file_manager.routing.players.PlayerDataManager;
import de.CypDasHuhn.Kit.interfaces.general.Interface;
import de.CypDasHuhn.Kit.interfaces.general.SkeletonInterface;
import de.CypDasHuhn.Kit.shared.SpigotMethods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ConfirmationInterface extends SkeletonInterface {
    public static final String interfaceName = "confirm";

    public static final String DELETE_CONFIRM_TYPE = "delete";
    public static final String INVENTORY_CONFIRM_TYPE = "update inventory of";
    public static final String EFFECTS_CONFIRM_TYPE = "update effects of";

    @Override
    public Inventory getInterface(Player player, Object... vars) {
        ConfirmationContextDTO context = (ConfirmationContextDTO) vars[0];
        PlayerDataManager.setConfirmationContext(player, context);

        Inventory inventory = Bukkit.createInventory(null, 6*9, "§6§lConfirm "+context.confirmationType+" "+context.kitContext.kit.kitName);

        ItemStack cancelItem = SpigotMethods.createItem(Material.RED_STAINED_GLASS_PANE, "§cCancel", false, null, null);
        for (int i = 0; i < 6*9; i++) {
            inventory.setItem(i, cancelItem);
        }

        int randomSlot = new Random().nextInt(6*9);
        inventory.setItem(randomSlot, SpigotMethods.createItem(Material.LIME_STAINED_GLASS_PANE, "§aConfirm", true, null, null));

        return inventory;
    }

    @Override
    public void listener(InventoryClickEvent event, Player player, ItemStack clickedItem, Material clickedMaterial, int clickedSlot) {
        ConfirmationContextDTO context = PlayerDataManager.getConfirmationContext(player);

        if (clickedMaterial.equals(Material.RED_STAINED_GLASS_PANE)) {
            player.sendMessage("§ccancelled");
            Interface.openTargetInterface(player, EditInterface.interfaceName, context.kitContext);
        } else if (clickedMaterial.equals(Material.LIME_STAINED_GLASS_PANE)) {
            if (context.confirmationType.equals(DELETE_CONFIRM_TYPE)) {
                KitAction.deleteKit(player, context.kitContext.kit);
                OverviewContextDTO overviewContext = PlayerDataManager.getOverviewInformation(player);
                Interface.openTargetInterface(player, OverviewInterface.interfaceName, overviewContext);
            }
            else if (context.confirmationType.equals(INVENTORY_CONFIRM_TYPE)) {
                KitAction.updateKitInventory(player, context.kitContext.kit);
                Interface.openTargetInterface(player, EditInterface.interfaceName, context.kitContext);
            }
            else if (context.confirmationType.equals(EFFECTS_CONFIRM_TYPE)) {
                KitAction.updateKitEffects(player, context.kitContext.kit);
                Interface.openTargetInterface(player, EditInterface.interfaceName, context.kitContext);
            }
        }
    }
}
