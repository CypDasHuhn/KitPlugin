package de.CypDasHuhn.Kit.file_manager.yml.items;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.file_manager.yml.CustomFiles;
import de.CypDasHuhn.Kit.shared.Finals;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;


public class KitManagerYML {
    public static void registerKit(KitDTO kit) {
        // other calls
        setKit(kit);
        KitListManagerYML.add(kit.kitName);
    }

    public static void setKit(KitDTO kit) {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration kitConfig = customFiles[0].getFileConfiguration(kit.kitName,"Kit");

        kitConfig.set("Class", kit.kitClass);

        for (PotionEffect effect : kit.effects) {
            kitConfig.set("Effects."+effect.getType(), effect.getAmplifier()+1);
        }

        CustomFiles.saveArray(customFiles);

        setInventory(kit);
    }

    public static void setInventory(KitDTO kit) {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration kitConfig = customFiles[0].getFileConfiguration(kit.kitName, "Kit");

        for (int i = 0; i < kit.inventory.length; i++) {
            if (kit.inventory[i] != null) {
                ItemMeta itemMeta = kit.inventory[i].getItemMeta();
                if (itemMeta instanceof SpawnEggMeta) {
                    SpawnEggMeta spawnEggMeta = (SpawnEggMeta) itemMeta;

                    EntityType spawnType = spawnEggMeta.getCustomSpawnedType();
                    if (spawnType != null) {
                        String displayMaterial = kit.inventory[i].getType().name();

                        kitConfig.set("Eggs." + i + ".Spawn", spawnType.toString());
                        kitConfig.set("Eggs." + i + ".Display", displayMaterial);
                    }
                }

                kitConfig.set("Inventory." + i, kit.inventory[i]);
            }
        }

        CustomFiles.saveArray(customFiles);
    }

    public static void deleteKit(KitDTO kit) {
        // delete
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        customFiles[0].delete(kit.kitName, "Kit");
        // remove in list
        KitListManagerYML.remove(kit.kitName);
    }

    public static void renameKit(KitDTO kit, String newName) {
        // rename in list
        KitListManagerYML.replace(kit.kitName, newName);
        // delete original
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        customFiles[0].delete(kit.kitName, "Kit");
        // save new
        kit.kitName = newName;
        setKit(kit);
    }

    public static KitDTO getKit(String kitName) {
        // prework
        CustomFiles[] customFiles = CustomFiles.getCustomFiles(1);
        FileConfiguration kitConfig = customFiles[0].getFileConfiguration(kitName,"Kit");
        // find
        String kitClass = kitConfig.getString("Class");

        ItemStack[] inventory = new ItemStack[Finals.OFF_HAND_INDEX+1];
        for (int i = 0; i < inventory.length; i++) {
            inventory[i] = kitConfig.getItemStack("Inventory."+i);
            if (inventory[i] != null) {
                ItemMeta itemMeta = inventory[i].getItemMeta();
                Material m = inventory[i].getType();
                if (itemMeta instanceof SpawnEggMeta spawnEggMeta) {
                    String spawnType = kitConfig.getString("Eggs."+i+".Spawn");

                    if (spawnType != null) {
                        String displayMaterial = kitConfig.getString("Eggs."+i+".Display");

                        if (displayMaterial != null) {
                            Material egg = Material.matchMaterial(displayMaterial);
                            spawnEggMeta.setCustomSpawnedType(EntityType.valueOf(spawnType));

                            inventory[i].setItemMeta(spawnEggMeta);
                            inventory[i].setType(egg);
                        }
                    }
                }
            }
        }

        List<PotionEffect> effects = new ArrayList<>();
        for (PotionEffectType potionType: PotionEffectType.values()) {
            int amplifier = kitConfig.getInt("Effects."+potionType);
            boolean existingEffect = amplifier > 0;
            if (existingEffect) {
                effects.add(new PotionEffect(potionType, -1, amplifier-1, true, true));
            }
        }

        return new KitDTO(kitName, kitClass, inventory, effects, null, 0, null);
    }
}
