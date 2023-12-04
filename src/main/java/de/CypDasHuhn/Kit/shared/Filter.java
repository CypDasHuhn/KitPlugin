package de.CypDasHuhn.Kit.shared;

import de.CypDasHuhn.Kit.DTO.ConditionDTO;
import de.CypDasHuhn.Kit.DTO.KitDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Filter {

    public static <T> Predicate<KitDTO> createPredicate(Function<KitDTO, T> valueExtractor, String operator, T value) {
        BiPredicate<T, T> operatorLogic = getOperatorLogic(operator, value.getClass());
        return kit -> operatorLogic.test(valueExtractor.apply(kit), value);
    }

    private static <T> BiPredicate<T, T> getOperatorLogic(String operator, Class<?> type) {
        return switch (operator) {
            case "IS" -> (a, b) -> a.equals(b);
            case "IS_NOT" -> (a, b) -> !a.equals(b);
            case "HAS" -> (a, b) -> ((List<String>) a).contains(b);
            case "HAS_NOT" -> (a, b) -> !((List<String>) a).contains(b);
            case "<" -> (a, b) -> Integer.compare((Integer) a, (Integer) b) < 0;
            case "<=" -> (a, b) -> Integer.compare((Integer) a, (Integer) b) <= 0;
            case ">" -> (a, b) -> Integer.compare((Integer) a, (Integer) b) > 0;
            case ">=" -> (a, b) -> Integer.compare((Integer) a, (Integer) b) >= 0;
            // Add other cases as needed
            default -> (a, b) -> false; // Default case if operator is not recognized
        };
    }
    private static Function<KitDTO, Object> getFieldExtractor(String key) {
        return switch (key) {
            case "tag" -> kit -> kit.tags;
            case "complexity" -> kit -> kit.complexity;
            case "class" -> kit -> kit.kitClass;
            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }

    public static HashMap<String, String[]> fieldOperatorMap = new HashMap<String, String[]>() {{
        put("tag", new String[]{"HAS", "HAS_NOT"});
        put("class", new String[]{"IS", "IS_NOT"});
        put("complexity", new String[]{"IS", "IS_NOT", "<", "<=", ">", ">="});
    }};

    public static HashMap<String, Class<?>> fieldClassMap = new HashMap<String, Class<?>>() {{
        put("tag", String.class);
        put("class", String.class);
        put("complexity", Integer.class);
    }};

    public static List<KitDTO> filterKitsByCondition(List<KitDTO> kits, String key, String operator, Object value) {
        Function<KitDTO, Object> valueExtractor = getFieldExtractor(key);
        Predicate<KitDTO> predicate = createPredicate(valueExtractor, operator, value);
        return kits.stream().filter(predicate).toList();
    }

    public static List<KitDTO> filterKitsByEquality(List<KitDTO> kitsA, List<KitDTO> kitsB) {
        return kitsA.stream()
                .filter(kitsB::contains)
                .collect(Collectors.toList());
    }

    public static List<KitDTO> filterKitsByAddition(List<KitDTO> kitsA, List<KitDTO> kitsB) {
        return Stream.concat(kitsA.stream(), kitsB.stream())
                .distinct() // Optional, if you want to remove duplicates
                .collect(Collectors.toList());
    }

    public static boolean isValidCondition(String field, String operator, String value) {
        if (!(fieldClassMap.containsKey(field))) {
            // no such field
            return false;
        } else if (!(Arrays.stream(fieldOperatorMap.get(field)).toList().contains(operator))) {
            // no such operator
            return false;
        } else {
            boolean isInteger = isInteger(value);
            if (fieldClassMap.get(field).equals(String.class)) {
                if (isInteger) {
                    // string not int
                    return false;
                }
            } else if (fieldClassMap.get(field).equals(Integer.class)) {
                if (!isInteger) {
                    // int not string
                    return false;
                }
            }
        }

        return true;
    }

    public static String[] logicalOperators = new String[]{"(", ")", ""};

    public static String[] correctifyCommands(String[] args) {
        for
    }

    public static void parseFilters(String[] args) {
        List<ConditionDTO> conditions = new ArrayList<ConditionDTO>();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if ()
        }
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
