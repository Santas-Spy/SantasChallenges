package santas.spy.challenges.challenges.goal;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

public class BlockGoal extends Goal{
    public int[] relativePos = new int[3];
    public Material goal;
    public Location corner1;
    public boolean isActive = false;

    public BlockGoal(int x, int y, int z, Material goal)
    {
        relativePos[0] = x;
        relativePos[1] = y;
        relativePos[2] = z;
        this.goal = goal;
    }

    @Override
    public BlockGoal copy()
    {
        return new BlockGoal(relativePos[0], relativePos[1], relativePos[2], goal);
    }

    @Override
    public boolean isSatisfied()
    {
        return corner1.add(relativePos[0], relativePos[1], relativePos[2]).getBlock().getType() == goal;
    }

    @Override
    public boolean start(Location corner1)
    {
        this.corner1 = corner1;
        isActive = true;
        return true;
    }

    @Override
    public GoalType type()
    {
        return GoalType.BLOCK;
    }

    @Override
    public YamlConfiguration saveHook(YamlConfiguration yaml)
    {
        return yaml;
    }

    @Override
    public boolean isGoalBlock(Location block) {
        return block.equals(corner1.add(relativePos[0], relativePos[1], relativePos[2]));
    }

    @Override
    public boolean isActive()
    {
        return isActive;
    }
}
