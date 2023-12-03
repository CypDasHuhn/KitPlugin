package de.CypDasHuhn.Kit.interfaces.OverviewInterface;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.DTO.OverviewInformationDTO;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitListManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;
import de.CypDasHuhn.Kit.file_manager.routing.players.PlayerDataManager;
import de.CypDasHuhn.Kit.interfaces.Interface;
import de.CypDasHuhn.Kit.interfaces.skeleton.SkeletonInterfaceListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class OverviewInterfaceListener extends SkeletonInterfaceListener {
    @Override
    public void listener(InventoryClickEvent event, Player player, ItemStack clickedItem, Material clickedMaterial, int clickedSlot) {
        OverviewInformationDTO data = PlayerDataManager.getOverviewInformation(player);
        int row = data.row;

        int lastRow = 5*9;

        boolean pageScroller = clickedSlot == lastRow+OverviewInterface.PAGE_SCROLLER;
        if (pageScroller) {
            int rowAmount = event.isShiftClick() ? 5 : 1; // normal 1 row, shift click 5 rows (1 Page)
            if (event.isRightClick()) rowAmount *= -1; // negate the direction if right click

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
            return;
        }

        if (clickedSlot < lastRow+1) {
            boolean isBackground = clickedMaterial == Material.LIGHT_GRAY_STAINED_GLASS_PANE;

            if (!isBackground) {
                int globalSlot = ((row - 1) * 9) + clickedSlot;

                String kitName = clickedItem.getItemMeta().getDisplayName();
                KitDTO kit = KitManager.getKit(kitName);

                if (event.isLeftClick()) {
                    player.getInventory().setContents(kit.inventory);
                }
            }
        }
    }
}
