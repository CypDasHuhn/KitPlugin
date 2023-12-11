package de.CypDasHuhn.Kit.file_manager.routing.items;

import de.CypDasHuhn.Kit.DTO.KitDTO;
import de.CypDasHuhn.Kit.KitPluginMain;
import de.CypDasHuhn.Kit.file_manager.sql.items.KitListManagerSQL;
import de.CypDasHuhn.Kit.file_manager.yml.items.KitListManagerYML;
import de.CypDasHuhn.Kit.shared.Finals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KitListManager {
    public static List<String> getKits() {
        return KitPluginMain.usesSQL ? KitListManagerSQL.getKits() : KitListManagerYML.getKits();
    }

    public static List<String> sortKits(List<String> kits) {
        return KitPluginMain.usesSQL ? KitListManagerSQL.sortKits(kits) : KitListManagerYML.sortKits(kits);
    }

    public static boolean exists(String kitName) {
        int id = KitPluginMain.usesSQL ? KitListManagerSQL.findId(kitName) : KitListManagerYML.findId(kitName);
        return id != Finals.NULL_INT;
    }

    public static KitDTO getRandomKit(List<String> kits) {
        int randomInt = new Random().nextInt(kits.size());
        String kitName = kits.get(randomInt);
        return KitManager.getKit(kitName);
    }

    public static KitDTO getFirstKit(List<String> kits) {
        String kitName = kits.get(0);
        return KitManager.getKit(kitName);
    }

    public static KitDTO getLastKit(List<String> kits) {
        String kitName = kits.get(kits.size()-1);
        return KitManager.getKit(kitName);
    }

    public static final List<String> specialNames = new ArrayList<String>(){{
        add("@r");
        add("@f");
        add("@l");
    }};

    public static KitDTO getKitSpecial(List<String> kits, String specialCommand) {
        return switch (specialCommand){
            case "@r" -> getRandomKit(kits);
            case "@f" -> getFirstKit(kits);
            case "@l" -> getLastKit(kits);
            default -> null;
        };
    }
}
