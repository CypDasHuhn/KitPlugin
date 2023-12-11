package de.CypDasHuhn.Kit.command.kit_plugin;

import de.CypDasHuhn.Kit.command.general.SkeletonCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitPluginHelpCommand extends SkeletonCommand {
    public static final String KIT_PLUGIN_HELP_COMMAND = "help";

    @Override
    public void command(CommandSender sender, String[] args, String label) {
        Player player = (Player) sender;
    }

    @Override
    public List<String> completer(CommandSender sender, String[] args, String label) {
        List<String> arguments = new ArrayList<String>();

        arguments.add("wip");

        return arguments;
    }
}
