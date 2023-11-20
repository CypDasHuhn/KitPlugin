package de.CypDasHuhn.TP.file_manager.player_manager;

import de.CypDasHuhn.TP.file_manager.CustomFiles;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class LocaleManager {
    public static final List<String> locales = Arrays.asList("en", "de");
    public static final String defaultLocale = locales.get(0); // en

    public static boolean isLocale(String language) {
        return locales.contains(language);
    }

    public static void setLanguage(Player player, String language) {
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playerConfig = customFiles[0].getFileConfiguration("PlayerData", "");
        playerConfig.set("Players." + player.getUniqueId() + ".language", language);
        CustomFiles.saveArray(customFiles);
    }

    public static String getLanguage(Player player) {
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playerConfig = customFiles[0].getFileConfiguration("PlayerData", "");
        String language = playerConfig.getString("Players." + player.getUniqueId() + ".language");
        if (language == null) {
            language = getLanguageGlobal();
        }
        return language;
    }

    public static void setLanguageGlobal(String language) {
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playerConfig = customFiles[0].getFileConfiguration("PlayerData", "");
        playerConfig.set("GlobalLanguage", language);
        CustomFiles.saveArray(customFiles);
    }

    public static String getLanguageGlobal() {
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration playerConfig = customFiles[0].getFileConfiguration("PlayerData", "");
        String language = playerConfig.getString("GlobalLanguage");
        if (language == null) {
            language = defaultLocale;
            setLanguageGlobal(defaultLocale);
        }
        return language;
    }

    public static java.util.Locale getLocale(Player player) {
        return java.util.Locale.forLanguageTag(getLanguage(player));
    }
}
