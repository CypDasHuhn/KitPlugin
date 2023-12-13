package de.CypDasHuhn.Kit.interfaces;

import de.CypDasHuhn.Kit.DTO.ShopDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.ConfirmationContextDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.KitContextDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.OverviewContextDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.ShopContextDTO;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitClassManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.ShopManager;
import de.CypDasHuhn.Kit.file_manager.routing.players.PlayerDataManager;
import de.CypDasHuhn.Kit.interfaces.general.Interface;
import de.CypDasHuhn.Kit.interfaces.general.SkeletonInterface;
import de.CypDasHuhn.Kit.shared.Finals;
import de.CypDasHuhn.Kit.shared.SpigotMethods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class EditInterface extends SkeletonInterface {
    public static final String interfaceName = "edit";

    public static final int RETURN_SLOT = 0;
    public static final int FAVORIZE_SLOT = 0-9;
    public static final int RENAME_SLOT = 2-9;
    public static final int CLASS_SLOT = 3-9;
    public static final int TAG_SLOT = 4-9;
    public static final int INVENTORY_SLOT = 5-9;
    public static final int EFFECT_SLOT = 6-9;
    public static final int SHOP_SLOT = 7-9;
    public static final int DELETE_SLOT = 8-9;


    @Override
    public Inventory getInterface(Player player, Object... vars) {
        KitContextDTO context = (KitContextDTO) vars[0];
        PlayerDataManager.setKitContext(player, context);

        Inventory inventory = Bukkit.createInventory(null, 3*9, "§6§l"+context.kit.kitName+" edit");

        int lastRow = 2*9;

        ItemStack backgroundGlassPane = SpigotMethods.createItem(Material.GRAY_STAINED_GLASS_PANE," ",false, null, null);
        for (int i = 0; i < 3*9; i++) {
            inventory.setItem(i, backgroundGlassPane);
        }

        inventory.setItem(lastRow+RETURN_SLOT, SpigotMethods.createItem(Material.PLAYER_HEAD, "return", false, null, Finals.CustomHeads.RETURN.texture));

        String favorizeName = context.kit.favoriteMap != null ? context.kit.favoriteMap.get(player.getName()) ? "favorize" : "un-favorize" : "favorize";
        inventory.setItem(lastRow+FAVORIZE_SLOT, SpigotMethods.createItem(Material.PLAYER_HEAD,"§5"+favorizeName, false, null, Finals.CustomHeads.FAVORITE.texture));
        inventory.setItem(lastRow+RENAME_SLOT, SpigotMethods.createItem(Material.PLAYER_HEAD,"§5Rename",false,null,Finals.CustomHeads.RENAME.texture));
        Material classShulker = KitClassManager.materialMap.get(KitClassManager.getClasses().get(context.kit.kitClass));
        inventory.setItem(lastRow+CLASS_SLOT, SpigotMethods.createItem(classShulker,"§5Change Class",false,null,null));
        inventory.setItem(lastRow+TAG_SLOT, SpigotMethods.createItem(Material.PLAYER_HEAD,"§5Manage Tags",false,null,Finals.CustomHeads.TAG.texture));
        inventory.setItem(lastRow+INVENTORY_SLOT, SpigotMethods.createItem(Material.PLAYER_HEAD,"§5Update Inventory", false, null, Finals.CustomHeads.INVENTORY.texture));
        inventory.setItem(lastRow+EFFECT_SLOT, SpigotMethods.createItem(Material.PLAYER_HEAD,"§5Update Effect",false,null,Finals.CustomHeads.EFFECTS.texture));
        inventory.setItem(lastRow+SHOP_SLOT, SpigotMethods.createItem(Material.PLAYER_HEAD, "§5Shop",false,null,Finals.CustomHeads.SHOP.texture));
        inventory.setItem(lastRow+DELETE_SLOT, SpigotMethods.createItem(Material.PLAYER_HEAD,"§5Delete",false,null,Finals.CustomHeads.DELETE.texture));

        return inventory;
    }

    @Override
    public void listener(InventoryClickEvent event, Player player, ItemStack clickedItem, Material clickedMaterial, int clickedSlot) {
        KitContextDTO context = PlayerDataManager.getKitContext(player);

        int lastRow = 2*9;

        boolean returnItem = clickedSlot == lastRow+RETURN_SLOT;
        if (returnItem) {
            OverviewContextDTO overviewContext = PlayerDataManager.getOverviewInformation(player);
            Interface.openTargetInterface(player, OverviewInterface.interfaceName, overviewContext);
            return;
        }

        boolean favorize = clickedSlot == lastRow+FAVORIZE_SLOT;
        if (favorize) {
            player.sendMessage("wip");
            return;
        }

        boolean rename = clickedSlot == lastRow+RENAME_SLOT;
        if (rename) {
            player.sendMessage("wip, use text command");
            //Interface.openTargetInterface(player, NameInterface.interfaceName, context);
            return;
        }

        boolean changeClass = clickedSlot == lastRow+CLASS_SLOT;
        if (changeClass) {
            Interface.openTargetInterface(player, ClassInterface.interfaceName, context);
            return;
        }

        boolean tag = clickedSlot == lastRow+TAG_SLOT;
        if (tag) {
            player.sendMessage("wip");
            return;
        }

        boolean inventory = clickedSlot == lastRow+INVENTORY_SLOT;
        if (inventory) {
            ConfirmationContextDTO confirmationContext = new ConfirmationContextDTO(context, ConfirmationInterface.INVENTORY_CONFIRM_TYPE);
            Interface.openTargetInterface(player, ConfirmationInterface.interfaceName, confirmationContext);
            return;
        }

        boolean effect = clickedSlot == lastRow+EFFECT_SLOT;
        if (effect) {
            ConfirmationContextDTO confirmationContext = new ConfirmationContextDTO(context, ConfirmationInterface.EFFECTS_CONFIRM_TYPE);
            Interface.openTargetInterface(player, ConfirmationInterface.interfaceName, confirmationContext);
            return;
        }

        boolean shop = clickedSlot == lastRow+SHOP_SLOT;
        if (shop) {
            ShopDTO shopDTO = ShopManager.getShop(context.kit.kitName);
            int money = PlayerDataManager.getMoney(player);
            ShopContextDTO shopContext = new ShopContextDTO(shopDTO, false, true, false, true, money);
            Interface.openTargetInterface(player, ShopInterface.interfaceName, shopContext);
            return;
        }

        boolean delete = clickedSlot == lastRow+DELETE_SLOT;
        if (delete) {
            ConfirmationContextDTO confirmationContext = new ConfirmationContextDTO(context, ConfirmationInterface.DELETE_CONFIRM_TYPE);
            Interface.openTargetInterface(player, ConfirmationInterface.interfaceName, confirmationContext);
        }
    }
}
