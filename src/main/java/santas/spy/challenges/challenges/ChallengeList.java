package santas.spy.challenges.challenges;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import santas.spy.challenges.SantasChallenges;

public class ChallengeList {
    private Map<String, Challenge> structures;

    public ChallengeList()
    {
        structures = new HashMap<>();
    }

    /**
     * retrives a challenge from the list
     * */
    public Challenge get(String name)
    {
        return structures.get(name);
    }

    /**
     * Saves a new challenge to the list under the name
     * @param name The name of the challenge
     * @param data The challenge
     * */
    public void save(String name, Challenge data)
    {
        structures.put(name, data);
    }

    /**
     * Gets the set of all challenge names
     * */
    public Set<String> getAll()
    {
        return structures.keySet();
    }

    /**
     * Saves all challenges in the list to file
     * @return true if there were no issues, or false if a challenge could not be saved
     * */
    public boolean writeToFile()
    {
        boolean success = true;
        File folder = new File(SantasChallenges.PLUGIN.getDataFolder(), "challenges");
        for (Challenge challenge : structures.values()) {
            try {
                challenge.save(folder);
            } catch (IOException e) {
                SantasChallenges.PLUGIN.getLogger().warning("Could not save challenge " + challenge.name);
                e.printStackTrace();
                success = false;
            }
        }
        return success;
    }
}
