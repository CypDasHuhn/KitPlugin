package de.CypDasHuhn.Kit.interfaces;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.KitContextDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.ShopContextDTO;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;
import de.CypDasHuhn.Kit.file_manager.routing.players.PlayerDataManager;
import de.CypDasHuhn.Kit.interfaces.general.Interface;
import de.CypDasHuhn.Kit.interfaces.general.SkeletonInterface;
import de.CypDasHuhn.Kit.shared.Finals;
import de.CypDasHuhn.Kit.shared.SpigotMethods;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShopInterface extends SkeletonInterface {
    public static final String interfaceName = "shop";

    public static int RETURN_SLOT = 0;

    public static int SWITCH_EDITING_SLOT = 8;

    public static int SWITCH_MOVING_OR_MONEY_SLOT = 4;
    public static int MANAGE_ROW_SLOT = 7;
    public static List<String> MANAGE_ROW_LORE = new ArrayList<>() {{
        add("§5Left click to add Row");
        add("§5Right click to remove Row");
    }};

    public static int SAVE_SLOT = 6;

    public static List<String> ITEM_LORE = new ArrayList<>() {{
        add("§5Left click to increase the costs");
        add("§5Right click to reduce the costs");
        add("§5Shift click for greater difference");
    }};

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

        if (!context.playing && !context.moving) {
            String editingName = context.editing ? "§5Switch to User-Mode" : "§5Switch to Edit-Mode";
            String editingTexture = context.editing ? Finals.CustomHeads.EDIT.texture : Finals.CustomHeads.USER.texture;
            ItemStack switchEditItem = SpigotMethods.createItem(Material.PLAYER_HEAD,editingName,context.editing,null,editingTexture);
            inventory.setItem(lastRow+SWITCH_EDITING_SLOT, switchEditItem);
        }

        if (context.fromInterface) {
            ItemStack returnItem = SpigotMethods.createItem(Material.PLAYER_HEAD,"return",false,null, Finals.CustomHeads.RETURN.texture);
            inventory.setItem(lastRow+RETURN_SLOT, returnItem);
        }

        if (context.moving) {
            ItemStack moveItem = SpigotMethods.createItem(Material.PLAYER_HEAD,"§5Switch Price Mode",true,null,Finals.CustomHeads.UNLOCKED.texture);
            inventory.setItem(lastRow+SWITCH_MOVING_OR_MONEY_SLOT, moveItem);

            ItemStack saveItem = SpigotMethods.createItem(Material.PLAYER_HEAD, "§5Save", false, null, Finals.CustomHeads.APPROVE.texture);
            inventory.setItem(lastRow+SAVE_SLOT, saveItem);
        }

        else if (context.editing) {
            ItemStack moveItem = SpigotMethods.createItem(Material.PLAYER_HEAD,"§5Switch Move Mode",true,null,Finals.CustomHeads.LOCKED.texture);
            inventory.setItem(lastRow+SWITCH_MOVING_OR_MONEY_SLOT, moveItem);

            ItemStack rowItem = SpigotMethods.createItem(Material.PLAYER_HEAD, "§5Add row", false, MANAGE_ROW_LORE, Finals.CustomHeads.ROWS.texture);
            inventory.setItem(lastRow+ MANAGE_ROW_SLOT,rowItem);
        }

        else {
            ItemStack moneyItem = SpigotMethods.createItem(Material.PLAYER_HEAD,"§6"+context.money+"œœ",false,null,Finals.CustomHeads.MONEY.texture);
            inventory.setItem(lastRow+SWITCH_MOVING_OR_MONEY_SLOT, moneyItem);
        }

        for (int i = 0; i < context.shop.inventory.length; i++) {
            if (context.shop.inventory[i] != null) {
                if (!context.moving) {
                    ItemMeta itemMeta = context.shop.inventory[i].getItemMeta();

                    List<String> lore;
                    if (context.editing) {
                        lore = new ArrayList<>(ITEM_LORE);
                    } else {
                        lore = new ArrayList<>();
                    }

                    lore.add("§6" + context.shop.costs[i] + "œœ");

                    if (itemMeta.getLore() != null) {
                        List<String> existingLore = new ArrayList<>(itemMeta.getLore());
                        existingLore.addAll(lore);
                        itemMeta.setLore(existingLore);
                    } else {
                        itemMeta.setLore(lore);
                    }

                    context.shop.inventory[i].setItemMeta(itemMeta);
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
        boolean isShopItem = clickedSlot < lastRow;

        if (!context.playing) {
            if (clickedSlot == lastRow+SWITCH_EDITING_SLOT) {
                context.editing = !context.editing;
                Interface.openCurrentInterface(player, context);
                return;
            } else if (context.fromInterface && clickedSlot == lastRow+RETURN_SLOT) {
                KitDTO kit = KitManager.getKit(context.shop.kitName);
                KitContextDTO kitContext = new KitContextDTO(kit);
                Interface.openTargetInterface(player, EditInterface.interfaceName, kitContext);
                return;
            }
        }
        if (context.moving) {
            if (clickedSlot < lastRow) {
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
                context.moving = false;
                Interface.openCurrentInterface(player, context);
            }
        }
        else if (context.editing && !isShopItem) {
            if (clickedSlot == lastRow+SWITCH_MOVING_OR_MONEY_SLOT) {
                context.moving = true;
                Interface.openCurrentInterface(player, context);
            }
            else if (clickedSlot == lastRow+MANAGE_ROW_SLOT) {
                if (event.isLeftClick()) {
                    if (context.shop.rows < 5) context.shop.rows++;
                } else if (event.isRightClick()) {
                    if (context.shop.rows > 1 && validRow(context.shop.inventory, context.shop.rows-1)) context.shop.rows--;
                }
                Interface.openCurrentInterface(player, context);
            }
        }
        else if (isShopItem) {
            if (context.editing) {
                int moneyAmount = event.isShiftClick() ? 5 : 1;
                if (event.isRightClick()) moneyAmount *= -1;

                context.shop.costs[clickedSlot] += moneyAmount;

                if (context.shop.costs[clickedSlot] < 0) {
                    context.shop.costs[clickedSlot] = 0;
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
                    Interface.openCurrentInterface(player, context);
                }
            }
        }
    }

    public static ItemStack[] removedCosts(ItemStack[] inventory) {
        for (ItemStack item : inventory) {
            if (item != null) {
                ItemMeta itemMeta = item.getItemMeta();
                List<Component> lore = itemMeta.lore();

                if (lore != null) {

                    List<TextComponent> itemLoreAsComponents = ITEM_LORE.stream()
                            .map(Component::text).toList();

                    lore.removeIf(component -> {
                        if (component instanceof TextComponent textComponent) {
                            return textComponent.content().endsWith("œœ");
                        }
                        return false;
                    });

                    lore.removeAll(itemLoreAsComponents);
                    itemMeta.lore(lore);
                    item.setItemMeta(itemMeta);
                }
            }
        }
        return inventory;
    }

    public static boolean validRow(ItemStack[] inventory, int row) {
        for (int i = inventory.length-1; i > 0; i--) {
            if (inventory[i] != null) {
                if (i > row*9) return false;
            }
        }
        return true;
    }
}
