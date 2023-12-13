package de.CypDasHuhn.Kit.interfaces;

import de.CypDasHuhn.Kit.Actions.KitAction;
import de.CypDasHuhn.Kit.DTO.interface_context.KitContextDTO;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitClassManager;
import de.CypDasHuhn.Kit.file_manager.routing.players.PlayerDataManager;
import de.CypDasHuhn.Kit.interfaces.general.Interface;
import de.CypDasHuhn.Kit.interfaces.general.SkeletonInterface;
import de.CypDasHuhn.Kit.shared.SpigotMethods;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassInterface extends SkeletonInterface {
    public static final String interfaceName = "class";

    public static final int RETURN_SLOT = 0;

    @Override
    public Inventory getInterface(Player player, Object... vars) {
        KitContextDTO context = (KitContextDTO) vars[0];

        Inventory inventory = Bukkit.createInventory(null, 3*9, "§6§lSelect Class");

        int lastRow = 2*9;

        ItemStack backgroundGlassPane = SpigotMethods.createItem(Material.GRAY_STAINED_GLASS_PANE," ",false, null, null);
        for (int i = 0; i < 3*9; i++) {
            inventory.setItem(i, backgroundGlassPane);
        }

        HashMap<String, String> classMap = new HashMap<>();
        for (String className : KitClassManager.getClasses().keySet()) {
            String classColor = KitClassManager.getClasses().get(className);
            classMap.put(classColor,className);
        }

        int i = 0;
        for (String classColor : KitClassManager.materialMap.keySet()) {
            Material shulkerMaterial = KitClassManager.materialMap.get(classColor);
            String className = classMap.get(classColor);
            if (className != null) {
                ChatColor classChatColor = KitClassManager.colorMap.get(classColor);
                boolean isSelected = context.kit.kitClass.equals(className);
                List<String> lore = isSelected ? new ArrayList<>(){{add("§5currently selected");}} : null;
                ItemStack shulker = SpigotMethods.createItem(shulkerMaterial, classChatColor+className,false,lore,null);
                inventory.setItem(i, shulker);
                i++;
            }

        }

        inventory.setItem(lastRow+RETURN_SLOT, SpigotMethods.createItem(Material.PLAYER_HEAD, "return", false, null, Finals.CustomHeads.RETURN.texture));

        return inventory;
    }

    @Override
    public void listener(InventoryClickEvent event, Player player, ItemStack clickedItem, Material clickedMaterial, int clickedSlot) {
        KitContextDTO context = PlayerDataManager.getKitContext(player);

        int lastRow = 2*9;

        boolean returnItem = clickedSlot == lastRow+RETURN_SLOT;
        if (returnItem) {
            Interface.openTargetInterface(player, EditInterface.interfaceName, context);
            return;
        }

        if (clickedSlot < 2*9 && !clickedMaterial.equals(Material.GRAY_STAINED_GLASS_PANE) && clickedItem.getItemMeta().getLore() == null) {
            HashMap<String, String> classMap = new HashMap<>();
            for (String className : KitClassManager.getClasses().keySet()) {
                String classColor = KitClassManager.getClasses().get(className);
                classMap.put(classColor,className);
            }

            String className = "";
            for (String classColor : KitClassManager.materialMap.keySet()) {
                Material shulkerMaterial = KitClassManager.materialMap.get(classColor);
                if (shulkerMaterial.equals(clickedMaterial)) {
                    className = classMap.get(classColor);
                    break;
                }
            }

            KitAction.changeClass(player, context.kit, className);
            context.kit.kitClass = className;
            Interface.openCurrentInterface(player, context);
        }
    }
}
