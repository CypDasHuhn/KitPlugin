package de.CypDasHuhn.Kit.listeners;

import de.CypDasHuhn.Kit.KitPluginMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class EntityDamageListener implements Listener {
    static final int emptyTime = 10*20; // 10 secs
    @EventHandler
    public static void entity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getDamager() instanceof Player attackingPlayer) {
                setAndClearMetadata(player, attackingPlayer.getName(), emptyTime);
            } else if (event.getDamager() instanceof Projectile projectile) {
                Entity attackingEntity = (Entity) projectile.getShooter();
                if (attackingEntity instanceof Player attackingPlayer) {
                    setAndClearMetadata(player, attackingPlayer.getName(), emptyTime);
                } else if (attackingEntity != null) {
                    List<MetadataValue> metadata = attackingEntity.getMetadata("player");
                    String playerName = metadata.size() > 0 ? (String) metadata.get(0).value() : null;
                    if (playerName != null) setAndClearMetadata(player, playerName, emptyTime);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.REEL_IN) {
            Player player = event.getPlayer();
            if (event.getCaught() instanceof Player caughtPlayer) {
                setAndClearMetadata(caughtPlayer, player.getName(), emptyTime);
            }
        }
    }

    private static void setAndClearMetadata(Player player, String value, int ticks) {
        player.setMetadata("player", new FixedMetadataValue(KitPluginMain.getPlugin(), value));

        new BukkitRunnable() {
            @Override
            public void run() {
                player.removeMetadata("player", KitPluginMain.getPlugin());
            }
        }.runTaskLater(KitPluginMain.getPlugin(), ticks);
    }
}
