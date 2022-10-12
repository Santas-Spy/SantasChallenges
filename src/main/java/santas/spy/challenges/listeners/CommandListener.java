package santas.spy.challenges.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import santas.spy.challenges.SantasChallenges;
import santas.spy.challenges.challenges.creator.ChallengeCreator;
import santas.spy.challenges.gui.ChallengeListGUI;

public class CommandListener implements CommandExecutor {
    Map<Player, ChallengeCreator> currentlySaving = new HashMap<>();
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
        boolean success = true;
        Player player = null;
        if (sender instanceof Player) {
            player = (Player)sender;
        }

        if (args.length == 0) {
            help(sender);
        } else {
            switch(args[0]) {
                case "new":
                    newChallenge(player, args);
                    break;
                case "list":
                    list(sender);
                    break;
                case "gui":
                    if (player != null) {
                        new ChallengeListGUI(player);
                    }
                    break;
                case "create":
                    create(player, args);
                    break;
                case "confirm":
                    confirm(player);
                    break;
                case "backup":
                    SantasChallenges.PLUGIN.challenges.writeToFile();
                    break;
                case "help": 
                    help(sender);
                    break;
                default:
                    help(sender);
                    break;
            }
        }
        return success;
    }

    private void help(CommandSender sender)
    {
        sender.sendMessage("/santaschallenges new <name>: Spawns a new challenge from the name");
        sender.sendMessage("/santaschallenges list: Lists all available challenges");
        sender.sendMessage("/santaschallenges create <name>: Saves the challenge your creating under the name");
        sender.sendMessage("/santaschallenges backup: Save all challenges to file");
        sender.sendMessage("/santaschallenges help: shows this list");
    }

    /**
     * Creates a new challenge for the player
     * */
    private void newChallenge(Player player, String[] args)
    {
        if (player == null) {
            return;
        }

        if (args.length > 1) {
            SantasChallenges.PLUGIN.newChallenge(player, args[1]);
        } else {
            player.sendMessage("Please select which island you would like to go to. Use /santaschallenges list to list all islands");
        }
    }

    /**
     * Lists all available challenges to the command sender
     * */
    private void list(CommandSender sender)
    {
        for (String name : SantasChallenges.PLUGIN.challenges.getAll()) {
            sender.sendMessage(name);
        }

        if (SantasChallenges.PLUGIN.challenges.getAll().isEmpty()) {
            sender.sendMessage("There were no structures");
        }
    }

    /**
     * Triggers the process to create a new challenge
     * */
    private void create(Player player, String[] args)
    {
        if (player != null) {
            if (args.length < 2) {
                player.sendMessage("Incorrect usage. Please specify a name for the challenge like this /santaschallenges save <name>");
            } else {
                if (currentlySaving.get(player) == null) {
                    player.sendMessage("Type /santaschallenges save to cancel saving");
                    ChallengeCreator saver = new ChallengeCreator(player, args[1]);
                    currentlySaving.put(player, saver); //used for cancelling challenge creation
                    saver.nextStep();                   //triggers the ChallengeCreator to begin the process
                } else {
                    player.sendMessage("Saving cancelled");
                    currentlySaving.get(player).cancel();
                    currentlySaving.remove(player);
                }
            }
        }
    }

    /**
     * Used when the player is being asked to confirm an action
     * TODO: This could be done cleaner with enums and states that can be set seperately
     * */
    private void confirm(Player player)
    {
        if (player != null) { 
            if (currentlySaving.get(player) != null) {
                //ChallengeCreator is asking player for confirmation
                if (currentlySaving.get(player).confirm()) {
                    player.sendMessage("Success");
                } else {
                    player.sendMessage("Error. Did not save challenge");
                }
            } else {
                player.sendMessage("You dont have any active saves");
            }
        }
    }
}
