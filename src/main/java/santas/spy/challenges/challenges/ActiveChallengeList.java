package santas.spy.challenges.challenges;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import santas.spy.challenges.SantasChallenges;

public class ActiveChallengeList {
    private Map<Player, ActiveChallenge> actives;

    public ActiveChallengeList()
    {
        actives = new HashMap<>();
    }

    public void add(Player player, ActiveChallenge challenge)
    {
        actives.put(player, challenge);
    }

    public void end(Player player)
    {
        ActiveChallenge challenge = actives.get(player);
        /**
         * TODO:
         *  Remove old island
         *  Check if player cancelled or won
         *      If won, reward player
         *  Mark island as complete
         *  Teleport player to safety/hub
         * */
    }

    /**
     * Saves this challenge to a file
     * TODO
     * */
    public void save()
    {
        File folder = new File(SantasChallenges.PLUGIN.getDataFolder(), "active challenges");
        for (ActiveChallenge challenge : actives.values()) {
            challenge.save(folder);
        }
    }
}
