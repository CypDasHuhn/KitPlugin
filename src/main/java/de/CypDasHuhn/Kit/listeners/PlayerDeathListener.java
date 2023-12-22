package de.CypDasHuhn.Kit.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void listener(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getEntity();

        if (entity instanceof Player murder) {

        }
        // death by player projectile
        if (entity instanceof Projectile projectile && projectile.getShooter() instanceof Player murder) {

        }
    }
}
