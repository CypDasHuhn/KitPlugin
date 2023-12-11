package de.CypDasHuhn.Kit.Actions;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitListManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;
import de.CypDasHuhn.Kit.shared.SpigotMethods;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class KitAction {
    public static void getKit(Player player, KitDTO kit) {
        player.getInventory().setContents(kit.inventory);

        for (PotionEffectType potionEffectType : PotionEffectType.values()) {
            player.removePotionEffect(potionEffectType);
        }
        player.addPotionEffects(kit.effects);

        player.sendMessage("§ayou §5got §athe kit §6"+kit.kitName);
    }

    public static void renameKit(Player player, KitDTO kit, String newKitName) {
        KitManager.renameKit(kit, newKitName);

        player.sendMessage("§ayou have §5renamed §6the kit §5"+kit.kitName+" to §6"+newKitName);
    }

    public static void updateKitInventory(Player player, KitDTO kit) {
        kit.inventory = SpigotMethods.inventoryToArray(player.getInventory());
        KitManager.setInventory(kit);
        player.sendMessage("§ayou have updated the §5inventory §aof the kit §6"+kit.kitName);
    }

    public static void updateKitEffects(Player player, KitDTO kit) {
        kit.effects = player.getActivePotionEffects();
        KitManager.setKit(kit);
        player.sendMessage("§ayou have updated the §5effects §aof the kit §6"+kit.kitName);
    }

    public static void changeClass(Player player, KitDTO kit, String kitClass) {
        kit.kitClass = kitClass;
        KitManager.setKit(kit);

        player.sendMessage("§athe kit §6"+kit.kitName+" §anow has the §5class "+kit.kitClass);
    }

    public static void kitCreate(Player player, String kitName, String kitClass) {
        ItemStack[] inventory = SpigotMethods.inventoryToArray(player.getInventory());
        Collection<PotionEffect> effects = player.getActivePotionEffects();

        KitDTO kit = new KitDTO(kitName, kitClass,inventory, effects, null,0, null);

        KitManager.registerKit(kit);

        player.sendMessage("§aYou §5created §athe kit §6"+kit.kitName);
    }

    public static void deleteKit(Player player, KitDTO kit) {
        KitManager.deleteKit(kit);

        player.sendMessage("§ayou §5deleted §athe kit §6"+kit.kitName);
    }
}
