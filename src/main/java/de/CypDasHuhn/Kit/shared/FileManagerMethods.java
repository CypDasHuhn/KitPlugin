package de.CypDasHuhn.TP.shared;

import de.CypDasHuhn.TP.file_manager.CustomFiles;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class FileManagerMethods {
    static List<String> illegalCharacters = new ArrayList<String>(){{
        add("/");
        add("\\");
        add(":");
        add("*");
        add("?");
        add("\"");
        add("<");
        add(">");
        add("|");
    }};
    static List<String> illegalName = new ArrayList<String>(){{
        add(Finals.EMPTY);
        add(Finals.DEFAULT_PARENT);
        add(Finals.Attributes.GLOBAL.label);
        add(Finals.Attributes.PROPOSE.label);
        add(Finals.Attributes.FIND.label);
    }};
    public static boolean illegalName(String name) {
        for (String character : illegalCharacters) {
            if (name.contains(character)) return false;
        }
        return illegalName.contains(name);
    }
}
