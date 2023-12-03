package de.CypDasHuhn.Kit.interfaces.OverviewInterface;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.DTO.OverviewInformationDTO;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitClassManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitListManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;
import de.CypDasHuhn.Kit.file_manager.routing.players.PlayerDataManager;
import de.CypDasHuhn.Kit.interfaces.skeleton.SkeletonInterface;
import de.CypDasHuhn.Kit.shared.SpigotMethods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        OverviewInformationDTO data = (OverviewInformationDTO) vars[0];
        int row = data.row;

        PlayerDataManager.setOverviewInformation(player, data);

        Inventory inventory = Bukkit.createInventory(null, 6*9, "§6§lKits");

        int lastRow = 5*9;

        for (int i = 0; i < lastRow; i++) {
            inventory.setItem(i, SpigotMethods.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE," ",false, null, null));
        }
        for (int i = lastRow; i < 6*9; i++) {
            inventory.setItem(i, SpigotMethods.createItem(Material.GRAY_STAINED_GLASS_PANE," ",false, null, null));
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
}
