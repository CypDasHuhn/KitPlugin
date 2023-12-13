package de.CypDasHuhn.Kit.command.kit_plugin;

import de.CypDasHuhn.Kit.command.general.SkeletonCommand;
import de.CypDasHuhn.Kit.file_manager.routing.players.PlayerDataManager;
import de.CypDasHuhn.Kit.shared.SpigotMethods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitPluginMoneyCommand extends SkeletonCommand {
    public static final String MONEY_COMMAND = "money";

    @Override
    public void command(CommandSender sender, String[] args, String label) {
        if (args.length < 1) {
            if (sender instanceof Player player) player.sendMessage("§cno target given");
            return;
        }
        String target = args[0];

        boolean targetExists = SpigotMethods.getPossibleTargets().contains(target);
        if (!targetExists) {
            System.out.println(target);
            for (String possibleTarget : SpigotMethods.getPossibleTargets()) {
                System.out.println(possibleTarget);
            }

            if (sender instanceof Player player) player.sendMessage("§ctarget not found");
            return;
        }

        Location location = (sender instanceof BlockCommandSender blockCommandSender) ? blockCommandSender.getBlock().getLocation() : (sender instanceof Player player) ? player.getLocation() : null;
        if (location == null || (target.equals("s") && !(sender instanceof Player)) ) return;
        Player targetPlayer = target.equals("@s") ? (Player) sender : SpigotMethods.getPlayer(target, location);
        int money = PlayerDataManager.getMoney(targetPlayer);

        if (args.length < 2) {
            if (sender instanceof Player player) player.sendMessage("§cno mode given");
            return;
        }
        String mode = args[1];

        boolean modeExists = mode.equals("set") || mode.equals("give") || mode.equals("remove") || mode.equals("show");
        if (!modeExists) {
            if (sender instanceof Player player) player.sendMessage("§cmode not found");
            return;
        }

        if (mode.equals("show")) {
            if (sender instanceof Player player) player.sendMessage("§6"+targetPlayer.getName()+"§a's money: §5"+money);
            return;
        }

        if (args.length < 3) {
            if (sender instanceof Player player) player.sendMessage("§cnumber not given");
            return;
        }
        String numberStr = args[2];

        boolean isNumber = SpigotMethods.isInteger(numberStr);
        if (!isNumber) {
            if (sender instanceof Player player) player.sendMessage("§cargument not a number");
            return;
        }
        int number = Integer.parseInt(numberStr);

        Location blockLocation = sender instanceof BlockCommandSender blockCommandSender ? blockCommandSender.getBlock().getLocation() : null;
        String commandPerformer = sender instanceof Player player ? player.getName() : "Command Block ("+blockLocation.getX()+","+blockLocation.getY()+","+blockLocation.getZ()+")";
        switch (mode) {
            case "give" -> {
                int newMoney = money + number;
                if (newMoney < 0) newMoney = 0;
                PlayerDataManager.setMoney(targetPlayer, newMoney);
                Bukkit.broadcastMessage("§6" + commandPerformer + " §5gave§6 " + targetPlayer.getName() + " §6" + number + "œœ §aand now has §6" + newMoney + "œœ");
                break;
            }
            case "remove" -> {
                int newMoney = money - number;
                if (newMoney < 0) newMoney = 0;
                PlayerDataManager.setMoney(targetPlayer, newMoney);
                Bukkit.broadcastMessage("§6" + commandPerformer + " §5removed§6 " + targetPlayer.getName() + " §6" + number + "œœ §aand now has §6" + newMoney + "œœ");
                break;
            }
            case "set" -> {
                if (number < 1) number = 1;
                PlayerDataManager.setMoney(targetPlayer, number);
                Bukkit.broadcastMessage("§6" + commandPerformer + " §5set §athe money of§6 " + targetPlayer.getName() + " §ato§6 " + number + "œœ");
            }
        }
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<>();

        if (args.length == 1) {
            List<String> players = SpigotMethods.getPossibleTargets();
            arguments.addAll(players);
        }
        else if (args.length == 2) {
            arguments.add("show");
            arguments.add("set");
            arguments.add("give");
            arguments.add("remove");
        }
        else if (args.length == 3 && !args[1].equals("show")) {
            arguments.add("[Number]");
        }

        return arguments;
    }
}
