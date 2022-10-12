package santas.spy.challenges.worldgen;

import org.bukkit.World;
import org.bukkit.WorldCreator;

import santas.spy.challenges.SantasChallenges;
import santas.spy.challenges.config.Constants;

public class WorldLoader {

    /**
     * Generates a new void world that can be used challenges.
     * World will only be generated if one by the same name does not already exist
     * @return The world that was generated or found
     * */
    public static World loadWorld()
    {
        WorldCreator creator = new WorldCreator(Constants.WORLD_NAME);
        creator.generator(new VoidChunk());
        return SantasChallenges.PLUGIN.getServer().createWorld(creator);
    }
}
