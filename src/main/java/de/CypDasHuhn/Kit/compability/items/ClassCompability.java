package de.CypDasHuhn.Kit.compability.items;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitListManager;
import de.CypDasHuhn.Kit.file_manager.routing.items.KitManager;

import java.util.List;

public class ClassCompability {
    public static void renameClasses(String oldKitClassName, String newKitClassName) {
        List<String> kits = KitListManager.getKits();
        for (String kitName : kits) {
            KitDTO kit = KitManager.getKit(kitName);

            if (kit.kitClass.equals(oldKitClassName)) {
                kit.kitClass = newKitClassName;

                KitManager.setKit(kit);
            }
        }
    }
}
