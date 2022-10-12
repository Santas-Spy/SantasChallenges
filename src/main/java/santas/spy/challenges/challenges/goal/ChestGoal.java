package santas.spy.challenges.challenges.goal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import santas.spy.challenges.SantasChallenges;

public class ChestGoal extends Goal {
    public List<ItemStack> goalItems;
    public int[] chestOffset = new int[3];
    public Chest chest;
    public boolean isActive = false;

    public ChestGoal(List<ItemStack> items, int x, int y, int z) {
        goalItems = items;
        chestOffset[0] = x;
        chestOffset[1] = y;
        chestOffset[2] = z;
    }

    @Override
    public ChestGoal copy()
    {
        return new ChestGoal(goalItems, chestOffset[0], chestOffset[1], chestOffset[2]);
    }

    @Override
    public boolean isSatisfied()
    {
        boolean satisfied = true;

        if (!isActive) {
            SantasChallenges.PLUGIN.getLogger().warning("WARNING: Trying to check a non active goal");
            satisfied = false;
        } else {
            for (ItemStack item : goalItems) {
                if (!chest.getInventory().contains(item)) { //if the item isnt in the chest, its not satisfied
                    satisfied = false;
                }
            }
        }
        return satisfied;
    }

    @Override
    public GoalType type()
    {
        return GoalType.CHEST;
    }

    @Override
    public YamlConfiguration saveHook(YamlConfiguration yaml)
    {
        //save the chest offset
        Map<String, Integer> goalPos = new HashMap<>();
        goalPos.put("x", chestOffset[0]);
        goalPos.put("y", chestOffset[1]);
        goalPos.put("z", chestOffset[2]);
        yaml.createSection("Goal_Pos", goalPos);
        
        //save the goal items
        ConfigurationSection goalItemsSection = yaml.createSection("Goal_Items");
        int index = 1;
        for (ItemStack item : goalItems) {
            goalItemsSection.set("" + index, item);
            index ++;
        }

        return yaml;
    }

    @Override
    public boolean start(Location corner1)
    {
        boolean found = false;
        //find the block that *should* be a chest
        Block block = new Location(corner1.getWorld(), corner1.getX() + chestOffset[0], corner1.getY() + chestOffset[1], corner1.getZ() + chestOffset[2]).getBlock();

        if (block.getType() == Material.CHEST)
        {
            try {
                this.chest = (Chest)block.getState();
                isActive = true;
                found = true;
                SantasChallenges.PLUGIN.getLogger().info("Found chest for goal");
            } catch (ClassCastException e) {
                //block was a chest but could not be cast. This should never be reached.
                e.printStackTrace();
            }
        } else {
            //block was not a chest, something has gone wrong
            SantasChallenges.PLUGIN.getLogger().warning("Goal Chest did not exist!. Searched at "  + block.getLocation().toString());
        }
        return found;
    }

    @Override
    public boolean isGoalBlock(Location location)
    {
        return location.equals(chest.getBlock().getLocation());
    }

    @Override
    public boolean isActive()
    {
        return isActive;
    }
}
