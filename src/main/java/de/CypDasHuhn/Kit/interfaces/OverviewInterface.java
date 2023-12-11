package de.CypDasHuhn.Kit.interfaces;

import de.CypDasHuhn.Kit.Actions.KitAction;
import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.KitContextDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.OverviewContextDTO;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitClassManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitListManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;
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

import java.util.ArrayList;
import java.util.List;

public class OverviewInterface extends SkeletonInterface {
    public static final String interfaceName = "Overview";
    public static final int PAGE_SCROLLER = 8;
    String PAGE_SCROLLER_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FkN2ZmNGQ2NTZjM2U0NjQxMDJkYmM1ZTI3OGJjYzZhODA2Y2U2NmI4MDY0YmRjOTVlZmMwZDMyNDBjOWUyOCJ9fX0=";
    List<String> PAGE_SCROLLER_LORE = new ArrayList<>(){{
        add("§5Left-click to scroll §6down");
        add("§5Right-click to scroll §6up");
        add("§5Shift-click to scroll §65 rows");
    }};

    public static final int RANDOM = 4;
    public static final String RANDOM_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk3OTU1NDYyZTRlNTc2NjY0NDk5YWM0YTFjNTcyZjYxNDNmMTlhZDJkNjE5NDc3NjE5OGY4ZDEzNmZkYjIifX19";
    List<String> RANDOM_LORE = new ArrayList<>(){{
        add("§5click for §6random kit");
    }};

    public static final int FILTER = 0;


    @Override
    public Inventory getInterface(Player player, Object... vars) {
        OverviewContextDTO data = (OverviewContextDTO) vars[0];
        int row = data.row;

        PlayerDataManager.setOverviewInformation(player, data);

        Inventory inventory = Bukkit.createInventory(null, 6*9, "§6§lKits");

        int lastRow = 5*9;

        ItemStack backgroundGlassPane = SpigotMethods.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE," ",false, null, null);
        for (int i = 0; i < lastRow; i++) {
            inventory.setItem(i, backgroundGlassPane);
        }
        ItemStack selectBarGlassPane = SpigotMethods.createItem(Material.GRAY_STAINED_GLASS_PANE," ",false, null, null);
        for (int i = lastRow; i < 6*9; i++) {
            inventory.setItem(i, selectBarGlassPane);
        }
        inventory.setItem(lastRow+PAGE_SCROLLER, SpigotMethods.createItem(Material.PLAYER_HEAD, "Page Scroller", false, PAGE_SCROLLER_LORE, PAGE_SCROLLER_HEAD));
        inventory.setItem(lastRow+RANDOM, SpigotMethods.createItem(Material.PLAYER_HEAD, "Random Kit", false, RANDOM_LORE, RANDOM_HEAD));
        inventory.setItem(lastRow+FILTER, SpigotMethods.createItem(Material.SPYGLASS, "Filter Settings", false, null, null));

        List<String> kits = KitListManager.getKits();
        List<String> sortedKits = KitListManager.sortKits(kits);
        
        for (int i = 0; i < 5*9; i++) {
            int globalIndex = ((row-1)*9)+i;
            if (sortedKits.size() <= globalIndex) break;
            
            String currentKitName = sortedKits.get(globalIndex);
            KitDTO kit = KitManager.getKit(currentKitName);
            Material shulker = KitClassManager.materialMap.get(KitClassManager.getClasses().get(kit.kitClass));
            ItemStack item = SpigotMethods.createItem(shulker, currentKitName, false, null, null);
            inventory.setItem(i, item);
        }

        return inventory;
    }

    @Override
    public void listener(InventoryClickEvent event, Player player, ItemStack clickedItem, Material clickedMaterial, int clickedSlot) {
        OverviewContextDTO data = PlayerDataManager.getOverviewInformation(player);
        int row = data.row;

        int lastRow = 5*9;

        boolean pageScroller = clickedSlot == lastRow+ OverviewInterface.PAGE_SCROLLER;
        if (pageScroller) {
            int rowAmount = event.isShiftClick() ? 5 : 1; // normal 1 row, shift click 5 rows (1 Page)
            if (event.isLeftClick()) rowAmount *= -1; // negate the direction if right click

            data.row += rowAmount;
            if (data.row < 1) data.row = 1;

            Interface.openCurrentInterface(player, data);
            return;
        }

        boolean randomKit = clickedSlot == lastRow+OverviewInterface.RANDOM;
        if (randomKit) {
            List<String> kits = KitListManager.getKits();

            KitDTO kit = KitListManager.getRandomKit(kits);

            player.getInventory().setContents(kit.inventory);
            return;
        }

        boolean filterSettings = clickedSlot == lastRow+OverviewInterface.FILTER;
        if (filterSettings) {
            player.sendMessage("wip");
            return;
        }

        if (clickedSlot < lastRow+1) {
            boolean isBackground = clickedMaterial == Material.LIGHT_GRAY_STAINED_GLASS_PANE;

            if (!isBackground) {
                int globalSlot = ((row - 1) * 9) + clickedSlot;

                String kitName = clickedItem.getItemMeta().getDisplayName();
                KitDTO kit = KitManager.getKit(kitName);

                KitContextDTO context = new KitContextDTO(kit);
                if (event.isLeftClick()) {
                    if (event.isShiftClick()) {
                        Interface.openTargetInterface(player, EditInterface.interfaceName, context);
                    } else {
                        KitAction.getKit(player, kit);
                    }
                } else if (event.isRightClick()) {
                    if (event.isShiftClick()) {

                    } else {
                        Interface.openTargetInterface(player, PreviewInterface.interfaceName, context);
                    }
                }
            }
        }
    }
}
