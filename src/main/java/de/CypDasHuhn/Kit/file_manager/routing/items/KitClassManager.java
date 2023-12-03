package de.CypDasHuhn.Kit.file_manager.routing.items;

import de.CypDasHuhn.Kit.KitPluginMain;
import de.CypDasHuhn.Kit.file_manager.sql.items.KitClassManagerSQL;
import de.CypDasHuhn.Kit.file_manager.yml.items.KitClassManagerYML;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;

import java.util.HashMap;

public class KitClassManager {
    public static final HashMap<String, ChatColor> colorMap = new HashMap<String, ChatColor>(){{
        put("DEFAULT", ChatColor.of("#956595"));
        put("WHITE", ChatColor.of("#E0E5E5"));
        put("LIGHT_GRAY", ChatColor.of("#898980"));
        put("GRAY", ChatColor.of("#3D4145"));
        put("BLACK", ChatColor.of("#1E1E21"));
        put("BROWN", ChatColor.of("#6E4526"));
        put("RED", ChatColor.of("#962321"));
        put("ORANGE", ChatColor.of("#F17210"));
        put("YELLOW", ChatColor.of("#F8C423"));
        put("LIME", ChatColor.of("#6BB618"));
        put("GREEN", ChatColor.of("#52681E"));
        put("CYAN", ChatColor.of("#16848F"));
        put("LIGHT_BLUE", ChatColor.of("#3AB1D8"));
        put("BLUE", ChatColor.of("#323499"));
        put("PURPLE", ChatColor.of("#7225A6"));
        put("MAGENTA", ChatColor.of("#B73EAC"));
        put("PINK", ChatColor.of("#EF89A7"));
    }};
    public static final HashMap<String, Material> materialMap = new HashMap<String, Material>(){{
        put("DEFAULT", Material.SHULKER_BOX);
        put("WHITE", Material.WHITE_SHULKER_BOX);
        put("LIGHT_GRAY", Material.LIGHT_GRAY_SHULKER_BOX);
        put("GRAY", Material.GRAY_SHULKER_BOX);
        put("BLACK", Material.BLACK_SHULKER_BOX);
        put("BROWN", Material.BROWN_SHULKER_BOX);
        put("RED", Material.RED_SHULKER_BOX);
        put("ORANGE", Material.ORANGE_SHULKER_BOX);
        put("YELLOW", Material.YELLOW_SHULKER_BOX);
        put("LIME", Material.LIME_SHULKER_BOX);
        put("GREEN", Material.GREEN_SHULKER_BOX);
        put("CYAN", Material.CYAN_SHULKER_BOX);
        put("LIGHT_BLUE", Material.LIGHT_BLUE_SHULKER_BOX);
        put("BLUE", Material.BLUE_SHULKER_BOX);
        put("PURPLE", Material.PURPLE_SHULKER_BOX);
        put("MAGENTA", Material.MAGENTA_SHULKER_BOX);
        put("PINK", Material.PINK_SHULKER_BOX);
    }};

    public static HashMap<String, String> getClasses() {
        return KitPluginMain.usesSQL ? KitClassManagerSQL.getClasses() : KitClassManagerYML.getClasses();
    }
}
