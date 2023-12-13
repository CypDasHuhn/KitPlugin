package de.CypDasHuhn.Kit.interfaces;

import de.CypDasHuhn.Kit.DTO.interface_context.OverviewContextDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.KitContextDTO;
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
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreviewInterface extends SkeletonInterface {
    public static final String interfaceName = "Preview";

    public static final int RETURN_SLOT = 0;
    @Override
    public Inventory getInterface(Player player, Object... vars) {
        KitContextDTO context = (KitContextDTO) vars[0];

        Inventory inventory = Bukkit.createInventory(null, 6*9, "§6§l"+context.kit.kitName+" Preview");

        int lastRow = 5*9;

        ItemStack backgroundGlassPane = SpigotMethods.createItem(Material.GRAY_STAINED_GLASS_PANE," ",false, null, null);
        for (int i = 0; i < 6*9; i++) {
            inventory.setItem(i, backgroundGlassPane);
        }
        inventory.setItem(lastRow+RETURN_SLOT, SpigotMethods.createItem(Material.PLAYER_HEAD, "return", false, null, Finals.CustomHeads.RETURN.texture));

        for (int i = 0; i <= Finals.OFF_HAND_INDEX; i++) {
            ItemStack item = context.kit.inventory[i];
            if (Arrays.asList(Finals.HOT_BAR_INDEXES).contains(i)) {
                inventory.setItem(i+4*9,item);
            } else if (Arrays.asList(Finals.REST_INVENTORY_INDEXES).contains(i)) {
                inventory.setItem(i-9,item);
            } else if (Arrays.asList(Finals.ARMOR_INDEXES).contains(i)) {
                inventory.setItem(i+5-9, item);
            } else if (Finals.OFF_HAND_INDEX == i) {
                inventory.setItem(i-9, item);
            }
        }

        List<String> effectList = new ArrayList<>();
        for (PotionEffect potionEffect : context.kit.effects) {
            String effectString = "§5"+potionEffect.getType().getName()+" "+(potionEffect.getAmplifier()+1);
            effectList.add(effectString);
        }

        ItemStack potionItem = SpigotMethods.createItem(Material.POTION,"effects",true,effectList,null);
        inventory.setItem(3*9,potionItem);

        return inventory;
    }

    @Override
    public void listener(InventoryClickEvent event, Player player, ItemStack clickedItem, Material clickedMaterial, int clickedSlot) {
        if (clickedSlot == 5*9+RETURN_SLOT) {
            OverviewContextDTO context = PlayerDataManager.getOverviewInformation(player);
            Interface.openTargetInterface(player, OverviewInterface.interfaceName, context);
        }
    }
}
