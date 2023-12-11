package de.CypDasHuhn.Kit.shared;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class SpigotMethods {
    public static Player getPlayer(String playerString, Location location) {
        switch (playerString) {
            case "@p" -> {
                return getNearestPlayer(location);
            }
            case "@r" -> {
                return getRandomPlayer();
            }
            default -> {
                Player player = Bukkit.getPlayer(playerString);
                return player;
            }
        }
    }

    public static List<String> getPossibleTargets() {
        List<String> players = new ArrayList<String>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            players.add(player.getName());
        }
        players.add("@p");
        players.add("@r");
        return players;
    }

    public static Player getRandomPlayer() {
        Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[0]);
        if (players.length == 0) return null;
        int randomIndex = new Random().nextInt(players.length);
        Player randomPlayer = players[randomIndex];
        return randomPlayer;
    }


    public static Player getNearestPlayer(Location location) {
        double shortestDistance = Double.MAX_VALUE;
        Player nearestPlayer = null;

        for (Player player : Bukkit.getOnlinePlayers()) {
            double playerDistance = player.getLocation().distance(location);
            if (playerDistance < shortestDistance) {
                shortestDistance = playerDistance;
                nearestPlayer = player;
            }
        }

        return nearestPlayer;
    }

    public static ItemStack createItem(Material material, String name, boolean enchanted, List<String> lore, String base64Texture) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (name != null) {
            itemMeta.setDisplayName(name);
        }

        itemMeta.setLore(lore);

        if (enchanted) {
            itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 0, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }


        if (base64Texture != null) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), "null");
            profile.getProperties().put("textures", new Property("textures", base64Texture));

            try {
                Field profileField = itemMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(itemMeta, profile);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack createItemFromItem(ItemStack itemStack, String name, boolean enchanted, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (name != null) {
            itemMeta.setDisplayName(name);
        }

        itemMeta.setLore(lore);

        if (enchanted) {
            itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 0, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            for (Enchantment enchantment : Enchantment.values()) {
                itemMeta.removeEnchant(enchantment);
            }
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack[] inventoryToArray(PlayerInventory inventory) {
        int size = inventory.getSize();
        ItemStack[] items = new ItemStack[size];

        // Get main inventory items
        for (int i = 0; i < inventory.getSize(); i++) {
            items[i] = inventory.getItem(i);
        }

        return items;
    }

    public static List<PotionEffect> getEffects(Player player) {
        List<PotionEffect> effects = new ArrayList<PotionEffect>();

        for (PotionEffectType potionType : PotionEffectType.values()) {
            effects.add(player.getPotionEffect(potionType));
        }

        return effects;
    }

    public static boolean hasSlotLeft(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) return true;
        }
        return false;
    }

    public static ItemStack[] insertItem(PlayerInventory inventory, ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, item);
                return inventoryToArray(inventory);
            }
        }
        return null;
    }
}
