package santas.spy.challenges.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.structure.Structure;

import santas.spy.challenges.SantasChallenges;
import santas.spy.challenges.challenges.Challenge;
import santas.spy.challenges.challenges.goal.ChestGoal;
import santas.spy.challenges.challenges.goal.Goal;

public class ChallengeLoader {

    /**
     * Loads all challenges from save files
     * */
    public void load()
    {
        File folder = new File(SantasChallenges.PLUGIN.getDataFolder(), "challenges");
        folder.mkdirs();
        File[] structures = folder.listFiles();

        for (File file : structures) {
            SantasChallenges.PLUGIN.getLogger().info("Loading " + file.getName());
            String fileName = file.getName();
            String[] data = fileName.split("\\.");

            if (data.length > 1) {
                String name = data[0];
                String extension = data[1];

                if (extension.equals("nbt")) {
                    Challenge challenge = loadChallenge(folder, name);
                    if (challenge == null) {
                        SantasChallenges.PLUGIN.getLogger().warning("Challenge " + name + " has an error. Structure or Goal file is missing");
                    } else {
                        SantasChallenges.PLUGIN.challenges.save(name, challenge);
                    }
                } else {
                    SantasChallenges.PLUGIN.getLogger().info("Found file " + fileName + " with extension: " + extension);
                }
            } else {
                SantasChallenges.PLUGIN.getLogger().warning("Found file " + fileName + " in folder which did not belong");
            }
        }
    }

    /**
     * Creates a challenge object from a save file
     * @param folder The parent folder where the save file is stored
     * @param name The name of the challenge
     * @return The challenge or null if there was an error
     * */
    private Challenge loadChallenge(File folder, String name)
    {
        SantasChallenges.PLUGIN.getLogger().info("Loading challenge: " + name);
        File structFile = new File(folder, name + ".nbt");
        File dataFile = new File(folder, name + ".data");
        Challenge challenge = null;

        //load data
        Structure structure = StructureIO.load(structFile);
        Goal goal = loadGoal(dataFile);
        int[] spawnOffset = getOffset(dataFile);

        //validate data
        if (structure != null && goal != null && spawnOffset != null) {
            SantasChallenges.PLUGIN.getLogger().info("Successfully loaded challenge: " + name);
            challenge = new Challenge(name, structure, goal, spawnOffset);
        } else {
            //error messages
            SantasChallenges.PLUGIN.getLogger().warning("Failed to load challenge: " + name);
            if (structure == null) { SantasChallenges.PLUGIN.getLogger().warning("\t" +  name + ": Structure could not be found"); }
            if (goal == null) { SantasChallenges.PLUGIN.getLogger().warning("\t" +  name + ": Goal could not be found"); }
            if (spawnOffset == null) { SantasChallenges.PLUGIN.getLogger().warning("\t" + name + ": Start Location could not be found"); }
        }
        return challenge;
    }

    /**
     * Creates the goal object from the data file
     * @param goalFile The file to load from
     * @return The goal, or null if it could not be loaded
     * */
    private Goal loadGoal(File goalFile)
    {
        Goal goal = null;
        try {
            YamlConfiguration yaml = new YamlConfiguration();
            yaml.load(goalFile);

            switch (yaml.getString("Type")) {
                case "CHEST":
                    int x = yaml.getInt("Goal_Pos.x");
                    int y = yaml.getInt("Goal_Pos.y");
                    int z = yaml.getInt("Goal_Pos.z");
                    int i = 1;  //not an array index. Item storage index starts at 1

                    List<ItemStack> items = new ArrayList<>();
                    ConfigurationSection itemSection = yaml.getConfigurationSection("Goal_Items");
                    ItemStack item = itemSection.getItemStack("" +  i);

                    while (item != null) {
                        items.add(item);
                        i++;
                        item = itemSection.getItemStack("" + i);
                    }

                    goal = new ChestGoal(items, x, y, z);
                    SantasChallenges.PLUGIN.getLogger().info("Loaded Chest Goal");
                    break;

                case "BLOCK":
                    // not yet implemented
                    break;
                default:
                    SantasChallenges.PLUGIN.getLogger().warning("Error in " + goalFile + ". Type was not valid");
                    break;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e2) {
            e2.printStackTrace();
        }

        return goal;
    }

    /**
     * Gets the spawn offset from the file
     * @param file The file to load from
     * @return the int[] offset or null if it could not be found
     * */
    private int[] getOffset(File file)
    {
        YamlConfiguration yaml = new YamlConfiguration();
        int[] offset = null;
        try {
            yaml.load(file);
            ConfigurationSection data = yaml.getConfigurationSection("Start_Location");
            offset = new int[3];
            offset[0] = data.getInt("x");
            offset[1] = data.getInt("y");
            offset[2] = data.getInt("z");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e2) {
            e2.printStackTrace();
        }
        return offset;
    }
}
