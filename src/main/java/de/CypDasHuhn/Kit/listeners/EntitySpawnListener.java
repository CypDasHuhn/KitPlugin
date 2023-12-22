package de.CypDasHuhn.Kit.listeners;

import de.CypDasHuhn.Kit.KitPluginMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class EntitySpawnListener implements Listener {
    @EventHandler
    public void listener(EntitySpawnEvent event) {
        Location spawnedLocation = event.getLocation();

        boolean spawnedByPlayer = UseItemListener.spawnEggTargetLocation.containsKey(spawnedLocation);
        if (spawnedByPlayer) {
            String playerName = Bukkit.getPlayer(UseItemListener.spawnEggTargetLocation.get(spawnedLocation)).getName();
            Entity entity = event.getEntity();
            entity.setMetadata("player", new FixedMetadataValue(KitPluginMain.getPlugin(), playerName));
            UseItemListener.spawnEggTargetLocation.remove(spawnedLocation);
        }
    }
}
