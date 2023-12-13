package de.CypDasHuhn.Kit.shared;

import de.CypDasHuhn.Kit.DTO.Filter.ConditionDTO;
import de.CypDasHuhn.Kit.DTO.Filter.ConditionSetDTO;
import de.CypDasHuhn.Kit.DTO.Filter.OperatorDTO;
import de.CypDasHuhn.Kit.DTO.Filter.ParenthesesDTO;
import de.CypDasHuhn.Kit.DTO.KitDTO;
import org.bukkit.Bukkit;

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
        BiPredicate<T, T> operatorLogic = getOperatorLogic(operator);
        return kit -> operatorLogic.test(valueExtractor.apply(kit), value);
    }

    private static <T> BiPredicate<T, T> getOperatorLogic(String operator) {
        return switch (operator) {
            case "IS" -> (a, b) -> a.equals(b);
            case "IS_NOT" -> (a, b) -> !a.equals(b);
            case "HAS" -> (a, b) -> ((List<String>) a).contains(b);
            case "HAS_NOT" -> (a, b) -> !((List<String>) a).contains(b);
            case "<" -> (a, b) -> (Integer) a < (Integer) b;
            case "<=" -> (a, b) -> (Integer) a <= (Integer) b;
            case ">" -> (a, b) -> (Integer) a > (Integer) b;
            case ">=" -> (a, b) -> (Integer) a >= (Integer) b;
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

    public static HashMap<String, String[]> fieldOperatorMap = new HashMap<>() {{
        put("tag", new String[]{"HAS", "HAS_NOT"});
        put("class", new String[]{"IS", "IS_NOT"});
        put("complexity", new String[]{"IS", "IS_NOT", "<", "<=", ">", ">="});
    }};

    public static HashMap<String, Class<?>> fieldClassMap = new HashMap<>() {{
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
            boolean isInteger = SpigotMethods.isInteger(value);
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

    public static Object[] convertIntoObjectArray(String[] args) {
        List<Object> objects = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            boolean isParentheses = arg.equals("(") || arg.equals(")");
            boolean isOpenParentheses = arg.equals("(");

            boolean isOperator = arg.equalsIgnoreCase("AND") || arg.equalsIgnoreCase("OR");
            boolean isAndOperator = arg.equalsIgnoreCase("AND");

            if (isParentheses) {
                objects.add(new ParenthesesDTO(isOpenParentheses));
            } else if (isOperator) {
                objects.add(new OperatorDTO(isAndOperator));
            } else {
                boolean enoughArguments = args.length > i + 2;
                if (!enoughArguments) {
                    Bukkit.broadcastMessage("// ### missing condition args");
                    return null;
                }

                String field = arg;
                String operator = args[i + 1];
                String value = args[i + 2];

                if (!isValidCondition(field, operator, value)) {
                    Bukkit.broadcastMessage("// ### condition not valid");
                    return null;
                }

                ConditionDTO condition = new ConditionDTO(field, operator, value);

                objects.add(condition);

                // Increment by 2 to move to the next set of three arguments
                i += 2;
            }
        }

        return objects.toArray();
    }

    /*
        Bukkit.broadcastMessage("Test-2");
        if (!(sender instanceof Player player)) {
            Bukkit.broadcastMessage("KEIN SPILER");
            return;
        }

        player.sendMessage("Los!");

        Object[] objects = Filter.convertIntoObjectArray(testCommand);

        for (Object o : objects) {
            if (o instanceof ConditionDTO c) {
                System.out.println(c.field);
                System.out.println(c.operator);
                System.out.println(c.value);
            } else if (o instanceof ParenthesesDTO p) {
                if (p.isOpening) System.out.println("(");
                else System.out.println(")");
            } else if (o instanceof OperatorDTO t) {
                if (t.isAnd) System.out.println("AND");
                else {
                    player.sendMessage("§cEINFACH EIN OPERATOR");
                    System.out.println("OR");
                }
            }
            System.out.println("---");
        }

        ConditionSetDTO test = Filter.convertIntoConditionSet(objects);

        Bukkit.broadcastMessage("erfolgreich??");
        */

    public static ConditionSetDTO convertIntoConditionSet(Object[] objects) {
        ConditionSetDTO main = new ConditionSetDTO(null, new ArrayList<>(), null, null);
        ConditionSetDTO currentConditionSet = main;

        boolean conditionTurn = true;

        for (int i = 0; i < objects.length; i++) {
            Object object = objects[i];

            boolean isParentheses = object instanceof ParenthesesDTO;
            ParenthesesDTO parentheses = isParentheses ? (ParenthesesDTO) object : null;

            boolean isOperator = object instanceof OperatorDTO;
            if (isOperator) {
                Bukkit.broadcastMessage("operator detected");
            }
            OperatorDTO operator = isOperator ? (OperatorDTO) object : null;

            ConditionDTO condition = (!isParentheses && !isOperator) ? (ConditionDTO) object : null;

            ConditionSetDTO currentConditionSetCopy = currentConditionSet;
            while (currentConditionSetCopy.parentConditionSet != null) {
                currentConditionSetCopy = currentConditionSetCopy.parentConditionSet;
            }
            main = currentConditionSetCopy;

            if (isOperator) {
                Bukkit.broadcastMessage("operator detected");
                if (conditionTurn) {
                    Bukkit.broadcastMessage("// ### two operators without condition");
                    return null;
                }

                if (operator.isAnd) {
                    if (currentConditionSet.operatorType != null && currentConditionSet.operatorType.equals("OR")) {
                        Bukkit.broadcastMessage("// ### no 2 operator types / missing parentheses");
                        return null;
                    } else {
                        currentConditionSet.operatorType = "AND";
                    }
                } else { // Or Operator
                    if (currentConditionSet.operatorType != null && currentConditionSet.operatorType.equals("AND")) {
                        Bukkit.broadcastMessage("// ### no 2 operator types / missing parentheses");
                        return null;
                    } else {
                        currentConditionSet.operatorType = "OR";
                    }
                }
                Bukkit.broadcastMessage("condition should be true now");
                conditionTurn = true;

            } else if (isParentheses) {
                if (parentheses.isOpening) {
                    if (!conditionTurn) {
                        Bukkit.broadcastMessage("// ### opening parentheses illegal after a condition");
                        return null;
                    }

                    currentConditionSet.conditionSets.add(new ConditionSetDTO(null, new ArrayList<>(), null, currentConditionSet));
                    currentConditionSet = currentConditionSet.conditionSets.get(currentConditionSet.conditionSets.size() - 1);
                } else { // closed parentheses
                    if (conditionTurn) {
                        Bukkit.broadcastMessage("// ### closing parentheses illegal after an operator");
                        return null;
                    }

                    currentConditionSet = currentConditionSet.parentConditionSet;
                }
            } else { // Condition
                if (!conditionTurn) {
                    Bukkit.broadcastMessage("// ### two conditions without a connector");
                    System.out.println(condition.field+"."+condition.operator+"."+condition.value);
                    return null;
                }

                boolean isFirstCondition = currentConditionSet.condition == null;
                boolean isSecondCondition = !isFirstCondition && currentConditionSet.conditionSets.size() == 0;

                if (isSecondCondition) {
                    ConditionSetDTO firstCondition = new ConditionSetDTO(currentConditionSet.condition, new ArrayList<>(), null, currentConditionSet);
                    currentConditionSet.conditionSets.add(firstCondition);
                    currentConditionSet.condition = null;
                }

                if (isFirstCondition) {
                    currentConditionSet.condition = condition;
                } else { // 2nd/2+th Condition
                    ConditionSetDTO newCondition = new ConditionSetDTO(condition, new ArrayList<>(), null, currentConditionSet);
                    currentConditionSet.conditionSets.add(newCondition);
                }

                i += 2; // skip forward
                conditionTurn = false;
            }
        }

        while (currentConditionSet.parentConditionSet != null) {
            currentConditionSet = currentConditionSet.parentConditionSet;
        }
        main = currentConditionSet;

        return main;
    }


    public static void parseFilters(String[] args) {
        List<ConditionDTO> conditions = new ArrayList<ConditionDTO>();

        boolean repeat = true;
        while (repeat) {
            repeat = false;

            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                boolean noChanges = true;

                arg = arg.trim();

                if (arg.isEmpty()) {
                    args = removeElement(args, i);
                    repeat = true;
                    break;
                }

                if (arg.startsWith("(") && arg.length() > 1) {
                    args[i] = "(" + arg.substring(1);
                    noChanges = false;
                }

                if (arg.endsWith(")") && arg.length() > 1) {
                    args[i] = arg.substring(0, arg.length() - 1) + ")";
                    noChanges = false;
                }

                if (!noChanges) {
                    repeat = true;
                }
            }
        }

        /*
        List<Object> formattedCommand = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (Arrays.asList(objects).contains(arg)) {
                formattedCommand.add(arg);
            } else {
                if (args.length <= i + 2) {
                    return;
                }

                String field = arg;
                String operator = args[i + 1];
                String value = args[i + 2];

                if (!isValidCondition(field, operator, value)) {
                    return;
                }

                ConditionDTO condition = new ConditionDTO(field, operator, value);
                formattedCommand.add(condition);

                i += 2;
            }
        }*/

    }

    private static String[] removeElement(String[] array, int index) {
        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, array.length - index - 1);
        return newArray;
    }
}
