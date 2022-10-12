package santas.spy.challenges;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import santas.spy.challenges.challenges.ActiveChallenge;
import santas.spy.challenges.challenges.ActiveChallengeList;
import santas.spy.challenges.challenges.ChallengeList;
import santas.spy.challenges.config.ChallengeLoader;
import santas.spy.challenges.listeners.CommandListener;
import santas.spy.challenges.worldgen.IslandGenerator;
import santas.spy.challenges.worldgen.WorldLoader;

/*
TO DO
- Needs to create challenge from in game (ideally)
    ✅ Players can save an island as a structure from in game
    - Players can generate a blank challange map and create their own to post to a challenge board
    - people can upvote challenges
    - you can toggle if the original block position is locked (ie cant move/break pistons or blocks to increase the difficulty)
- Be able to check that a challenge has been completed
- Be able to setup player in a world to complete the challenge
    ✅ Have a super large world that the challenges take place in.
    - All challenges must be completed within a 100 x 100 area (max)
    ✅ Upon creating a new challenge generate a new area 1000 blocks away from the last area in a grid pattern 
    - When a challenge is completed remove that area from the world and mark it as blank
    - Players can only undertake 1 challenge at a time
        - Their island co ords can be tied to their UUID in a map
- Be able to reward the player

- Player can end a running challenge
- Player can teleport to their current challenge
- Ending a challenge wipes that island

*/

public class SantasChallenges extends JavaPlugin
{
    public static SantasChallenges PLUGIN;
    public IslandGenerator islandMap;
    public ChallengeList challenges;
    public ActiveChallengeList actives;

    @Override
    public void onEnable()
    {
        System.out.println("Hello World!");
        PLUGIN = this;
        registerListeners();
        load();
        WorldLoader.loadWorld();
    }

    @Override
    public void onDisable()
    {
        save();
        System.out.println("Bravo 6. Going Dark o7");
    }

    private void registerListeners()
    {
        this.getCommand("santaschallenges").setExecutor(new CommandListener());
    }

    /**
    * Load all data from save files
    * */
    private void load()
    {
        islandMap = new IslandGenerator();
        challenges = new ChallengeList();
        ChallengeLoader loader = new ChallengeLoader();
        loader.load();
    }

    /**
     * Save all data to save files
     * */
    private void save()
    {
        challenges.writeToFile();
    }

    /**
     * Start a new active challenge
     * @param player The player starting the challenge
     * @param name The name key for the challenge
     * */
    public void newChallenge(Player player, String name)
    {
        ActiveChallenge challenge = islandMap.generateIsland(player, challenges.get(name));
        player.teleport(challenge.startLocation);
    }
}
