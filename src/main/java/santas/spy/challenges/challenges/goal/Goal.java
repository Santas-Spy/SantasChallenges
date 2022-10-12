package santas.spy.challenges.challenges.goal;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public abstract class Goal {
    
    /**
     * @return true if this goal is satisfied, or false otherwise
     * */
    public abstract boolean isSatisfied();

    /**
     * @return The GoalType of this goal
     * */
    public abstract GoalType type();

    /**
     * @return The yaml configuration with this goals save data loaded into it
     * */
    public abstract YamlConfiguration saveHook(YamlConfiguration section);

    /**
     * Trigger this goal to become active and set its parameters based on the corner.
     * @param corner1 The start corner of the challenge
     * @return True if the goal was started or false otherwise
     * */
    public abstract boolean start(Location corner1);

    /**
     * @param block The block to test
     * @return true if the block matched the goal state, or false otherwise
     * */
    public abstract boolean isGoalBlock(Location block);

    /**
     * @return a deactivated copy of this goal
     * */
    public abstract Goal copy();

    /**
     * @return true if this goal has been started or false otherwise
     * */
    public abstract boolean isActive();

    /**
     * Add this goals save data to the yaml
     * */
    public YamlConfiguration save(YamlConfiguration yaml)
    {
        //general information
        yaml.createSection("Type");
        yaml.set("Type", type().toString());
        
        //type specific information
        yaml = saveHook(yaml);
        return yaml;
    }
    
    /**
     * A list of all goal types
     * */
    public enum GoalType 
    {
        CHEST,
        BLOCK;
    }
}

