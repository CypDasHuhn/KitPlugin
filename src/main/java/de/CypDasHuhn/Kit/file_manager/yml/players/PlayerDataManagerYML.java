package de.CypDasHuhn.Kit.file_manager.yml.players;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.DTO.ShopDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.ConfirmationContextDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.KitContextDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.OverviewContextDTO;
import de.CypDasHuhn.Kit.DTO.interface_context.ShopContextDTO;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;
import de.CypDasHuhn.Kit.file_manager.yml.CustomFiles;
import de.CypDasHuhn.Kit.file_manager.yml.items.ShopManagerYML;
import de.CypDasHuhn.Kit.shared.Finals;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerDataManagerYML {
    public static final String dataFileDir = "Players";
    public static final String inventoryDir = "Inventory";
    public static final String rowDir = "Row";
    public static final String confirmationTypeDir = "ConfirmationType";
    public static final String kitDir = "Kit";

    public static void setInventory(Player player, String inventory) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(UUID,dataFileDir);
        // set
        if (inventory == null) inventory = Finals.EMPTY;
        playerDataConfig.set(inventoryDir, inventory);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static String getInventory(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(UUID,dataFileDir);
        // get
        String inventory = playerDataConfig.getString(inventoryDir);
        if (inventory == null) inventory = Finals.EMPTY;
        return inventory;
    }

    public static void setRow(Player player, int row) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(UUID,dataFileDir);
        // set
        playerDataConfig.set(rowDir, row);
        // save
        CustomFiles.saveArray(customFiles);
    }

    public static int getRow(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(UUID,dataFileDir);
        // get
        int row = playerDataConfig.getInt(rowDir);
        if (row == 0) {
            row = 1;
            setRow(player, row);
        }
        return row;
    }

    public static OverviewContextDTO getOverviewContext(Player player) {
        int row = getRow(player);

        return new OverviewContextDTO(row);
    }
    public static void setOverviewContext(Player player, OverviewContextDTO data) {
        setRow(player, data.row);
    }

    public static ConfirmationContextDTO getConfirmingContext(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(UUID,dataFileDir);

        KitContextDTO kitContext = getKitContext(player);
        String editingType = playerDataConfig.getString(confirmationTypeDir);

        return new ConfirmationContextDTO(kitContext, editingType);
    }

    public static void setConfirmationContext(Player player, ConfirmationContextDTO context) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(UUID,dataFileDir);

        playerDataConfig.set(confirmationTypeDir, context.confirmationType);

        CustomFiles.saveArray(customFiles);

        setKitContext(player, context.kitContext);
    }

    public static void setKitContext(Player player, KitContextDTO context) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(UUID,dataFileDir);

        playerDataConfig.set(kitDir,context.kit.kitName);

        CustomFiles.saveArray(customFiles);
    }

    public static KitContextDTO getKitContext(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(UUID,dataFileDir);

        String kitName = playerDataConfig.getString(kitDir);
        KitDTO kit = KitManager.getKit(kitName);

        return new KitContextDTO(kit);
    }

    public static void setShopContext(Player player, ShopContextDTO context) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(UUID,dataFileDir);

        playerDataConfig.set("Playing", context.playing);
        playerDataConfig.set("Editing", context.editing);
        playerDataConfig.set("Moving", context.moving);
        playerDataConfig.set("FromInterface", context.fromInterface);
        playerDataConfig.set("Money", context.money);
        playerDataConfig.set("KitName", context.shop.kitName);

        CustomFiles.saveArray(customFiles);

        ShopManagerYML.setShop(context.shop);
    }

    public static ShopContextDTO getShopContext(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(UUID,dataFileDir);

        boolean playing = playerDataConfig.getBoolean("Playing");
        boolean editing = playerDataConfig.getBoolean("Editing");
        boolean moving = playerDataConfig.getBoolean("Moving");
        boolean fromInterface = playerDataConfig.getBoolean("FromInterface");
        int money = playerDataConfig.getInt("Money");
        String kitName = playerDataConfig.getString("KitName");

        ShopDTO shop = ShopManagerYML.getShop(kitName);

        return new ShopContextDTO(shop, playing, editing, moving, fromInterface,money);
    }

    public static int getMoney(Player player) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(UUID,dataFileDir);

        return playerDataConfig.getInt("Money");
    }

    public static void setMoney(Player player, int money) {
        // Prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        String UUID = player.getUniqueId().toString();
        FileConfiguration playerDataConfig = customFiles[0].getFileConfiguration(UUID,dataFileDir);

        playerDataConfig.set("Money",money);

        CustomFiles.saveArray(customFiles);
    }
}
