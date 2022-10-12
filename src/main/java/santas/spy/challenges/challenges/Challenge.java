package santas.spy.challenges.challenges;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.structure.Structure;

import santas.spy.challenges.SantasChallenges;
import santas.spy.challenges.challenges.goal.Goal;

public class Challenge {
    public String name;
    public Structure structure;
    public Goal goal;
    public int[] spawnOffset;

    public Challenge(String name, Structure structure, Goal goal, int[] spawnOffset)
    {
        this.name = name;
        this.structure = structure;
        this.goal = goal;
        this.spawnOffset = spawnOffset;
    }

    /**
     * Writes the challenge to file
     * @param folder The parent folder to save to
     * */
    public void save(File folder) throws IOException
    {
        File strucFile = new File(folder, name + ".nbt");
        File dataFile = new File(folder, name + ".data");

        strucFile.createNewFile();
        SantasChallenges.PLUGIN.getServer().getStructureManager().saveStructure(strucFile, structure);

        YamlConfiguration data = new YamlConfiguration();
        data = goal.save(data);
        data = saveLocation(data);
        data.save(dataFile);
    }

    /**
     * Add the spawn offset to the yaml
     * */
    private YamlConfiguration saveLocation(YamlConfiguration yaml)
    {
        ConfigurationSection data = yaml.createSection("Start_Location");
        data.set("x", spawnOffset[0]);
        data.set("y", spawnOffset[1]);
        data.set("z", spawnOffset[2]);
        return yaml;
    }

    public String toString()
    {
        String string = String.format("Name: %s\nStructure Size: %d x %d x %d\nGoal Type: %s\nSpawn Offset: [%d, %d, %d]",
            name,
            structure.getSize().getBlockX(), structure.getSize().getBlockY(), structure.getSize().getBlockZ(),
            goal.type().toString(),
            spawnOffset[0], spawnOffset[1], spawnOffset[2]);

        return string;
    }
}