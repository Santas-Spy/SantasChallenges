package santas.spy.challenges.challenges.creator;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;

import santas.spy.challenges.SantasChallenges;
import santas.spy.challenges.challenges.Challenge;
import santas.spy.challenges.challenges.goal.Goal;
import santas.spy.challenges.gui.CreateChallengeGUI;

public class ChallengeCreator {
    Player player;
    String name;
    Location corner1 = null;
    Location corner2 = null;
    AreaSelector areaSelector;
    BlockSelector blockSelector;
    Structure structure;
    Goal goal;
    int[] spawnOffset;
    private Stage stage;

    public ChallengeCreator(Player player, String name)
    {
        stage = Stage.AREA;
        this.player = player;
        this.name = name;
    }

    /**
     * Performs the next step in the process based on the current stage
     * */
    public void nextStep()
    {
        SantasChallenges.PLUGIN.getLogger().info("Doing step: " + stage);
        switch (stage) {
            case AREA:
                areaSelector = new AreaSelector(player);
                break;
            case GOAL:
                System.out.println("Corner1: " + corner1.toString());
                System.out.println("Corner2: " + corner2.toString());
                new CreateChallengeGUI(player, this);
                break;
            case SPAWN:
                blockSelector = new BlockSelector(player, this);
                break;
            case FINISHED:
                Challenge challenge = new Challenge(name, structure, goal, spawnOffset);
                SantasChallenges.PLUGIN.challenges.save(name, challenge);
                SantasChallenges.PLUGIN.getLogger().info("Made new challenge:\n" +  challenge.toString());
                break;
            default:
                break;
        }
    }

    public void cancel()
    {
        // TODO
    }

    /**
     * Gets signal from commandListener that the player has confirmed an action
     * */
    public boolean confirm()
    {
        boolean success = false;
        if (stage == Stage.AREA) {
            Location[] corners = areaSelector.getLocation();
            if (corners != null) {
                StructureManager manager = SantasChallenges.PLUGIN.getServer().getStructureManager();
                corner1 = corners[0]; //most positive corner
                corner2 = corners[1]; //most negative corner
                structure = manager.createStructure();  //get a new empty structure
                structure.fill(corner1, corner2, true); //fill it
                success = true;
                stage = stage.advance();    //move to the next stage
                nextStep();                 //trigger the next stage
            }
        }

        if (stage == Stage.SPAWN) {
            spawnOffset = blockSelector.getBlock();
            if (spawnOffset != null) {
                stage = stage.advance();    //move to the next stage
                nextStep();                 //trigger the next stage
                success = true;
            }
        }

        return success;
    }

    /**
     * Sets the goal for the challenge then triggers the next stage
     * @param goal The goal
     * */
    public void setGoal(Goal goal)
    {   
        this.goal = goal;
        stage = stage.advance();
        nextStep();
    }

    public static int[] getOffset(Location location, ChallengeCreator instance)
    {
        int x = location.getBlockX() - instance.corner2.getBlockX();  // use corner2 because corner2 will be the min corner
        int y = location.getBlockY() - instance.corner2.getBlockY();  // and the min corner will be the one the island is generated at
        int z = location.getBlockZ() - instance.corner2.getBlockZ();
        return new int[] {x, y, z};
    }

    private enum Stage {
        AREA,
        GOAL,
        SPAWN,
        FINISHED;

        public Stage advance()
        {
            switch(this) {
                case AREA:
                    return GOAL;
                case GOAL:
                    return SPAWN;
                case SPAWN:
                    return FINISHED;
                case FINISHED:
                    return FINISHED;
                default:
                    //if something goes wrong, default to finished. This will cause errors that cancel the process
                    return FINISHED;
            }
        }
    }
}
