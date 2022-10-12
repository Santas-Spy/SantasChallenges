package santas.spy.challenges.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;

import santas.spy.challenges.SantasChallenges;
public class StructureIO {

    /**
     * Loads a structure from a file
     * @param file The name of the file
     * @return The structure or null if it could not be found
     * */
    public static Structure load(File file)
    {
        Structure structure = null;
        StructureManager manager = Bukkit.getServer().getStructureManager();
        try {
            try { 
                structure = manager.loadStructure(file);
            } catch (IOException e) {
                SantasChallenges.PLUGIN.getLogger().warning("Could not load structure " + file.getName());
                e.printStackTrace();
            }
        } catch (IndexOutOfBoundsException e) {
            SantasChallenges.PLUGIN.getLogger().warning("Error on loading " + file.getName());
            e.printStackTrace();
        }

        return structure;
    }

    /**
     * Saves a structure to file
     * @param structure The structure
     * @param name The name of the file
     * */
    public static void save(Structure structure, String name) {
        StructureManager manager = Bukkit.getServer().getStructureManager();
        try {
            manager.saveStructure(new File(SantasChallenges.PLUGIN.getDataFolder(), "structures"), structure);
        } catch (IOException e) {
            SantasChallenges.PLUGIN.getLogger().warning("Could not save structure " + name);
        }
    }
}
