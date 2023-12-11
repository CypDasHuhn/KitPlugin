package de.CypDasHuhn.Kit.interfaces;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.KitContextDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.ShopContextDTO;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;
import de.CypDasHuhn.Kit.file_manager.routing.players.PlayerDataManager;
import de.CypDasHuhn.Kit.interfaces.general.Interface;
import de.CypDasHuhn.Kit.interfaces.general.SkeletonInterface;
import de.CypDasHuhn.Kit.shared.SpigotMethods;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopInterface extends SkeletonInterface {
    public static final String interfaceName = "shop";

    public static int RETURN_SLOT = 0;
    public static String RETURN_TEXTURE = "";

    public static int SWITCH_EDITING_SLOT = 8;
    public static String SWITCH_EDITING_TEXTURE = "";

    public static int SWITCH_MOVING_OR_MONEY_SLOT = 4;
    public static String SWITCH_MOVING_TEXTURE = "";
    public static String MONEY_TEXTURE = "";

    public static int MANAGE_ROW_SLOT = 7;
    public static String MANAGE_ROW_TEXTURE = "";

    public static int SAVE_SLOT = 6;
    public static String SAVE_TEXTURE = "";

    @Override
    public Inventory getInterface(Player player, Object... vars) {
        ShopContextDTO context = (ShopContextDTO) vars[0];
        PlayerDataManager.setShopContext(player, context);

        String editing = context.editing ? "Editing " : "";
        Inventory inventory = Bukkit.createInventory(null, (context.shop.rows+1)*9, "§6§l"+editing+context.shop.kitName+" Shop");

        int lastRow = context.shop.rows*9;

        ItemStack selectBarGlassPane = SpigotMethods.createItem(Material.GRAY_STAINED_GLASS_PANE," ",false, null, null);
        for (int i = lastRow; i < lastRow+9; i++) {
            inventory.setItem(i, selectBarGlassPane);
        }

        if (!context.playing) {
            String editingName = context.editing ? "§5Switch to User-Mode" : "§5Switch to Edit-Mode";
            ItemStack switchEditItem = SpigotMethods.createItem(Material.PLAYER_HEAD,editingName,context.editing,null,SWITCH_EDITING_TEXTURE);
            inventory.setItem(lastRow+SWITCH_EDITING_SLOT, switchEditItem);
        }

        else if (context.moving) {
            ItemStack moveItem = SpigotMethods.createItem(Material.PLAYER_HEAD,"§5Switch Price Mode",true,null,SWITCH_MOVING_TEXTURE);
            inventory.setItem(lastRow+SWITCH_MOVING_OR_MONEY_SLOT, moveItem);

            ItemStack saveItem = SpigotMethods.createItem(Material.PLAYER_HEAD, "§5Save", false, null, SAVE_TEXTURE);
            inventory.setItem(lastRow+SAVE_SLOT, saveItem);
        }

        else if (context.editing) {
            if (context.fromInterface) {
                ItemStack returnItem = SpigotMethods.createItem(Material.PLAYER_HEAD,"return",false,null,RETURN_TEXTURE);
                inventory.setItem(lastRow+RETURN_SLOT, returnItem);
            }

            ItemStack moveItem = SpigotMethods.createItem(Material.PLAYER_HEAD,"§5Switch Move Mode",true,null,SWITCH_MOVING_TEXTURE);
            inventory.setItem(lastRow+SWITCH_MOVING_OR_MONEY_SLOT, moveItem);

            ItemStack rowItem = SpigotMethods.createItem(Material.PLAYER_HEAD, "§5Add row", false, null, MANAGE_ROW_TEXTURE);
            inventory.setItem(lastRow+ MANAGE_ROW_SLOT,rowItem);
        }

        else {
            ItemStack moneyItem = SpigotMethods.createItem(Material.PLAYER_HEAD,"§6"+context.money+"œœ",false,null,MONEY_TEXTURE);
            inventory.setItem(lastRow+SWITCH_MOVING_OR_MONEY_SLOT, moneyItem);
        }

        for (int i = 0; i < lastRow; i++) {
            if (context.shop.inventory[i] != null) {
                if (!context.moving) {
                    String lore = "§5" + context.shop.costs[i] + "œœ";
                    context.shop.inventory[i].setItemMeta(addLore(context.shop.inventory[i].getItemMeta(), lore));
                }
                inventory.setItem(i, context.shop.inventory[i]);
            }
        }

        return inventory;
    }

    @Override
    public void listener(InventoryClickEvent event, Player player, ItemStack clickedItem, Material clickedMaterial, int clickedSlot) {
        ShopContextDTO context = PlayerDataManager.getShopContext(player);

        int lastRow = context.shop.rows*9;
        boolean isShopItem = clickedSlot > lastRow;

        if (context.moving) {
            if (clickedSlot > lastRow-1 && clickedSlot < lastRow+9) {
                event.setCancelled(false);
            }
            else if (clickedSlot == lastRow+SWITCH_MOVING_OR_MONEY_SLOT) {
                context.moving = false;
                Interface.openCurrentInterface(player, context);
            }
            else if (clickedSlot == lastRow+SAVE_SLOT) {
                ItemStack[] inventory = new ItemStack[context.shop.rows*9];
                for (int i = 0; i < inventory.length; i++) {
                    inventory[i] = event.getInventory().getItem(i);
                }
                context.shop.inventory = removedCosts(inventory);
                Interface.openCurrentInterface(player, context);
            }
        }
        else if (context.editing && !isShopItem) {
            if (context.fromInterface) {
                if (clickedSlot == lastRow+RETURN_SLOT) {
                    KitDTO kit = KitManager.getKit(context.shop.kitName);
                    KitContextDTO kitContext = new KitContextDTO(kit);
                    Interface.openTargetInterface(player, EditInterface.interfaceName, kitContext);
                }
            }
            else if (clickedSlot == lastRow+SWITCH_MOVING_OR_MONEY_SLOT) {
                context.moving = true;
                Interface.openCurrentInterface(player, context);
            }
            else if (clickedSlot == lastRow+MANAGE_ROW_SLOT) {
                if (event.isLeftClick()) {
                    if (context.shop.rows < 5) context.shop.rows++;
                } else if (event.isRightClick()) {
                    if (context.shop.rows > 1) context.shop.rows--;
                }
                Interface.openCurrentInterface(player, context);
            }
        }
        else if (isShopItem) {
            if (context.editing) {
                int moneyAmount = event.isShiftClick() ? 5 : 1;
                if (event.isRightClick()) moneyAmount *= -1;
                context.shop.costs[clickedSlot] += moneyAmount;
                if (context.shop.costs[clickedSlot] < 1) {
                    context.shop.costs[clickedSlot] = 1;
                }
                Interface.openCurrentInterface(player, context);
            } else {
                int costs = context.shop.costs[clickedSlot];
                if (costs > context.money) {
                    player.sendMessage("§cNot enough money");
                } else {
                    context.money -= costs;
                    ItemStack item = context.shop.inventory[clickedSlot];
                    player.getInventory().setContents(SpigotMethods.insertItem(player.getInventory(), item));
                }
            }
        }
    }

    public static ItemStack[] removedCosts(ItemStack[] inventory) {
        return null;
    }

    public ItemMeta addLore(ItemMeta meta, String loreString) {
        List<Component> lore = meta.lore() != null ? meta.lore() : new ArrayList<>();
        lore.add(Component.text(loreString));
        meta.lore(lore);
        return meta;
    }
}
