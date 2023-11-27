package de.CypDasHuhn.Kit.file_manager.yml.items;

import de.CypDasHuhn.Kit.file_manager.yml.CustomFiles;
import de.CypDasHuhn.Kit.shared.Finals;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;

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

    public static List<String> getClasses() {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration userProvidedConfig = customFiles[0].getFileConfiguration(Finals.USER_PROVIDED_CONFIG,"");
        return null;

    }
}
