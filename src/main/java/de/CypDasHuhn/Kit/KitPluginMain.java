package de.CypDasHuhn.Kit;

import de.CypDasHuhn.Kit.command.general.CustomCommand;
import de.CypDasHuhn.Kit.command.general.CustomTabCompleter;
import de.CypDasHuhn.Kit.command.general.TestCommand;
import de.CypDasHuhn.Kit.file_manager.yml.UserConfigManagerYML;
import de.CypDasHuhn.Kit.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class KitPluginMain extends JavaPlugin {
    private static KitPluginMain plugin;
    public static String pluginName;
    public static boolean usesSQL;
    private static final Listener[] LISTENERS = {
            new InventoryClickListener(),
            new InventoryCloseListener(),
            new PlayerJoinListener(),
            new UseItemListener(),
            new PlayerDeathListener(),
            new EntityDamageListener(),
            new EntitySpawnListener()
    };

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

        boolean isTesting = true;
        if (isTesting) {
            getCommand("ckpTest").setExecutor(new TestCommand());
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
