package de.CypDasHuhn.Kit.interfaces;

import de.CypDasHuhn.Kit.DTO.interface_context.KitContextDTO;
import de.CypDasHuhn.Kit.file_manager.routing.players.PlayerDataManager;
import de.CypDasHuhn.Kit.interfaces.general.SkeletonInterface;
import de.CypDasHuhn.Kit.shared.SpigotMethods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class NameInterface extends SkeletonInterface {
    public static final String interfaceName = "Name";

    @Override
    public Inventory getInterface(Player player, Object... vars) {
        KitContextDTO context = (KitContextDTO) vars[0];
        if (context.kit != null) PlayerDataManager.setKitContext(player, context);

        Inventory inventory = Bukkit.createInventory(null, InventoryType.ANVIL, "§6§lName the Kit");

        String itemTagName = context.kit != null && context.kit.kitName != null ? context.kit.kitName : "[Name]";
        inventory.setItem(0, SpigotMethods.createItem(Material.NAME_TAG, itemTagName, false, null, null));

        return inventory;
    }

    @Override
    public void listener(InventoryClickEvent event, Player player, ItemStack clickedItem, Material clickedMaterial, int clickedSlot) {
        Bukkit.broadcastMessage("clicked the itemName: "+(event.getClickedInventory().getItem(2) != null ? event.getClickedInventory().getItem(2).getItemMeta().getDisplayName() : "(no item name)"));
        if (clickedSlot == 2) {
            if (event.getClickedInventory() instanceof AnvilInventory) {
                AnvilInventory anvilInventory = (AnvilInventory) event.getClickedInventory();

                String newKitName = anvilInventory.getRenameText();
                Bukkit.broadcastMessage("You hit the slot " + clickedSlot + ", your text is: " + newKitName);
            }
        }
    }

}
