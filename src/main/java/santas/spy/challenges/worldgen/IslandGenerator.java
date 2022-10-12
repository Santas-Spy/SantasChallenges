package santas.spy.challenges.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Player;
import org.bukkit.structure.Structure;

import santas.spy.challenges.SantasChallenges;
import santas.spy.challenges.challenges.ActiveChallenge;
import santas.spy.challenges.challenges.Challenge;
import santas.spy.challenges.config.Constants;

public class IslandGenerator {
    private static List<Player> locations;

    public IslandGenerator()
    {
        locations = new ArrayList<>();
    }

    /**
     * Creates a new island with an active challenge instance running on it
     * @param player The player doing the challenge
     * @param challenge The challenge type being started
     * @return The ActiveChallenge that was created at this island
     * */
    public ActiveChallenge generateIsland(Player player, Challenge challenge)
    {
        int[] pos = getFreeIsland(player);
        Location location = generateIsland(pos, challenge.structure);
        return new ActiveChallenge(location, player, challenge);
    }

    /**
     * Gets the index of the next available area for a challenge island to be spawned
     * @param player The player doing the challenge
     * @return an int[2] representing the x and y index of the island. This is an index and not a scaled value
     * */
    private int[] getFreeIsland(Player player)
    {
        int i = 0;
        int travelled = 0;
        int length = 1;
        boolean increase = false;
        boolean found = false;
        int[] pos = new int[] {0, 0};
        Direction dir = Direction.UP;

        if (locations.isEmpty()) {
            locations.add(i, player);
        } else {
            while (i < Constants.MAX_ISLANDS && i < locations.size() && !found) {                   //if i >= locations.size all current islands are already full so just generate a new one here
                if (locations.get(i) == null) {                                                     //empty slot means a challenge was here but has since been removed. This is a valid spot
                    found = true;
                    locations.add(i, player);                                           
                    System.out.println("Inside loop: " + locations.toString());                     //TODO: this is a debug message. Change to proper debug system once implemented
                } else {
                    SantasChallenges.PLUGIN.getLogger().info("[" + pos[0] + "," + pos[1] + "]");    //TODO: this is a debug message. Change to proper debug system once implemented
                    pos[0] += dir.move()[0];
                    pos[1] += dir.move()[1];

                    travelled++;                    // keep track of how far along this edge we are
                    if (travelled >= length) {      // if at the end of the edge
                        dir = dir.rotate();         // change direction
                        if (increase) {             // every 2 rotations increase the length by 1 to give a spiral pattern
                            length++;
                        }
                        travelled = 0;              // reset side length
                        increase = !increase;       // flip increase to keep track of number of lengths rotated
                    }
                    i++;
                }
            }

            if (!found) {
                // got to the end of size, nothing was found, add a new island here
                locations.add(i, player);
            }
        }

        return pos;
    }

    /**
     * Place a challenges structure in the world at a given index
     * @param pos The index of the island
     * @param structure The structure to generate
     * @return The location the island was generated at
     * */
    private Location generateIsland(int[] pos, Structure structure)
    {
        //use index to ensure predictable and customisable distace between of islands
        Location generateLocation = new Location(WorldLoader.loadWorld(), pos[0] * Constants.DIST_BETWEEN_ISLANDS, 64, pos[1] * Constants.DIST_BETWEEN_ISLANDS);

        structure.place(generateLocation, true, StructureRotation.NONE, Mirror.NONE, 0, 1, new Random());
        System.out.println("Made new island at " + generateLocation.toString());    //TODO: This is a debug message to be removed or adapted
        return generateLocation;
    }

    private enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;

        /**
         * increments the array based on the direction
         * */
        public int[] move()
        {
            switch (this) {
                case UP: 
                    return new int[] {0, -1};
                case DOWN:
                    return new int[] {0, 1};
                case LEFT:
                    return new int[] {-1, 0};
                case RIGHT:
                    return new int[] {1, 0};
                default:
                    return new int[] {0, 0};
            }
        }

        /**
         * rotates the direction counterclockwise
         * */
        public Direction rotate() {
            switch (this) {
                case UP: 
                    return LEFT;
                case DOWN:
                    return RIGHT;
                case LEFT:
                    return DOWN;
                case RIGHT:
                    return UP;
                default:
                    return null;
            }
        }
    }
}
