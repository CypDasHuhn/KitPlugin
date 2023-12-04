package de.CypDasHuhn.Kit;

import de.CypDasHuhn.Kit.command.general.CustomCommand;
import de.CypDasHuhn.Kit.command.general.CustomTabCompleter;
import de.CypDasHuhn.Kit.listeners.InventoryClickListener;
import de.CypDasHuhn.Kit.listeners.InventoryCloseListener;
import de.CypDasHuhn.Kit.listeners.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class KitPluginMain extends JavaPlugin {
    private static KitPluginMain plugin;
    public static String pluginName;
    private static final String[] COMMANDS = {"kit", "kitSet", "kitDelete", "kitEdit", "kitRandom"};
    private static final Listener[] LISTENERS = {new InventoryClickListener(), new InventoryCloseListener(), new PlayerJoinListener()};

    public void onEnable(){
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        plugin = this;
        pluginName = getDescription().getName();

        for (String a : COMMANDS) {
            getCommand(a).setExecutor(new CustomCommand());
            getCommand(a).setTabCompleter(new CustomTabCompleter());
        }

        PluginManager pluginManager = Bukkit.getPluginManager();
        for (Listener listener : LISTENERS) {
            pluginManager.registerEvents(listener, this);
        }

    }

    public static KitPluginMain getPlugin(){
        return plugin;
    }
}
