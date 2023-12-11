package de.CypDasHuhn.Kit;

import de.CypDasHuhn.Kit.command.general.CustomCommand;
import de.CypDasHuhn.Kit.command.general.CustomTabCompleter;
import de.CypDasHuhn.Kit.file_manager.yml.CustomFiles;
import de.CypDasHuhn.Kit.file_manager.yml.UserConfigManagerYML;
import de.CypDasHuhn.Kit.listeners.InventoryClickListener;
import de.CypDasHuhn.Kit.listeners.InventoryCloseListener;
import de.CypDasHuhn.Kit.listeners.PlayerJoinListener;
import de.CypDasHuhn.Kit.shared.Finals;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class KitPluginMain extends JavaPlugin {
    private static KitPluginMain plugin;
    public static String pluginName;
    public static boolean usesSQL;
    private static final Listener[] LISTENERS = {new InventoryClickListener(), new InventoryCloseListener(), new PlayerJoinListener()};

    public void onEnable(){
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        plugin = this;
        pluginName = getDescription().getName();

        for (String a : CustomCommand.aliasesMap.keySet()) {
            getCommand(a).setExecutor(new CustomCommand());
            getCommand(a).setTabCompleter(new CustomTabCompleter());
        }

        PluginManager pluginManager = Bukkit.getPluginManager();
        for (Listener listener : LISTENERS) {
            pluginManager.registerEvents(listener, this);
        }


        if (!UserConfigManagerYML.isInitialized()) {
            UserConfigManagerYML.initializeConfig();
        }

        usesSQL = UserConfigManagerYML.usesSQL();
    }

    public static KitPluginMain getPlugin(){
        return plugin;
    }
}
