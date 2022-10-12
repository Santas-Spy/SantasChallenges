package santas.spy.challenges.challenges;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import santas.spy.challenges.SantasChallenges;
import santas.spy.challenges.challenges.goal.Goal;

public class ActiveChallenge {
    public Location corner1;
    public Player player;
    public String challengeName;
    public Goal goal;
    public Location startLocation;
    private InventoryListener listener;

    public ActiveChallenge(Location corner1, Player player, Challenge challenge)
    {
        this.corner1 = corner1;
        this.player = player;
        this.challengeName = challenge.name;
        this.goal = challenge.goal.copy();
        this.goal.start(corner1);
        this.startLocation = getStartLocation(challenge.spawnOffset, corner1);
        listener = new InventoryListener();
        listener.open();
    }

    /**
     * Saves all data about the state of the challenge to a file
     * @param folder The parent folder to save to
     * */
    public void save(File folder)
    {
        File file = new File(folder, player.getName());
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.createSection("Location");
        yaml.createSection("Challenge");
        yaml.set("Location", corner1);
        yaml.set("Challenge", challengeName);
        try {
            yaml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the goal is satisfied and trigger any effects if it has been
     * */
    public void checkWin()
    {
        if (goal.isSatisfied()) {
            player.sendMessage("Congrats! You beat the challenge");
            listener.close();
        } else {
            player.sendMessage("The goal as not satisfied");
        }
    }

    /**
     * A listener to automatically check if the challenge is complete
     * */
    private class InventoryListener implements Listener
    {
        @EventHandler
        public void onHopperEvent(InventoryMoveItemEvent event)
        {
            SantasChallenges.PLUGIN.getLogger().info("Hopper event found");
            if (goal.isGoalBlock(event.getDestination().getLocation())) {
                checkWin();
            }
        }

        @EventHandler
        public void onPlayerEvent(InventoryInteractEvent event)
        {
            event.getWhoClicked().sendMessage("You moved an item");
            if (goal.isGoalBlock(event.getInventory().getHolder().getInventory().getLocation())) {
                checkWin();
            }
        }

        public void open()
        {
            SantasChallenges.PLUGIN.getServer().getPluginManager().registerEvents(this, SantasChallenges.PLUGIN);
        }

        public void close()
        {
            InventoryInteractEvent.getHandlerList().unregister(this);
            InventoryMoveItemEvent.getHandlerList().unregister(this);
        }
    }

    /**
     * Get the spawn location of the challenge
     * @param offset The spawn offset of the challenge
     * @param origin The min corner of the structure
     * @return The location to teleport the player to
     * */
    private Location getStartLocation(int[] offset, Location origin)
    {
        Location start = new Location(origin.getWorld(), origin.getBlockX(), origin.getBlockY(), origin.getBlockZ());
        System.out.println(start.toString());
        start.add(offset[0], offset[1], offset[2]);
        System.out.println(start.toString());
        return start;
    }
}
