package santas.spy.challenges.worldgen;

import java.util.Random;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

public class VoidChunk extends ChunkGenerator {
    @Override
    public void generateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkY, ChunkData chunkData)
    {
        // generate nothing for void
    }

    @Override
    public void generateSurface(WorldInfo worldInfo, Random random, int chunkX, int chunkY, ChunkData chunkData)
    {
        // generate nothing for void
    }

    @Override
    public void generateBedrock(WorldInfo worldInfo, Random random, int chunkX, int chunkY, ChunkData chunkData)
    {
        // generate nothing for void
    }

    @Override
    public void generateCaves(WorldInfo worldInfo, Random random, int chunkX, int chunkY, ChunkData chunkData)
    {
        // generate nothing for void
    }
}
