package santas.spy.challenges.challenges.creator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import santas.spy.challenges.SantasChallenges;

public class AreaSelector {
    Location corner1;
    Location corner2;
    ClickListener listener;
    Player player;

    public AreaSelector(Player player)
    {
        this.player = player;
        selectLocation();
    }

    /**
     * @return The two corners of the selection, or null if not chosen
     * */
    public Location[] getLocation()
    {
        Location[] locations = null;
        if (corner1 == null) {
            player.sendMessage("You have not set corner1 yet");
        } else if (corner2 == null) {
            player.sendMessage("You have not set corner2 yet");
        } else {
            locations = standarizeLocations();
            listener.close();
        }

        return locations;
    }

    /**
     * Starts the process of selecting an area
     * */
    private void selectLocation()
    {
        listener = new ClickListener();
        SantasChallenges.PLUGIN.getServer().getPluginManager().registerEvents(listener, SantasChallenges.PLUGIN);
        player.sendMessage("Using a stick select the area for the challenge."); 
        player.sendMessage("Right click for pos1, Left click for pos2");
        player.getInventory().addItem(new ItemStack(Material.STICK));
    }

    /**
     * Prompts the player to confirm if both corners are selected
     * */
    private void attemptSave()
    {
        if (corner1 != null && corner2 != null) {
            player.sendMessage("Type /santaschallenges confirm to confirm");
        }
    }

    private class ClickListener implements Listener
    {
        @EventHandler
        public void onClick(PlayerInteractEvent event)
        {
            if (event.getPlayer().equals(player) && event.hasItem() && event.getItem().getType() == Material.STICK) {
                switch (event.getAction()) {
                    case LEFT_CLICK_BLOCK:
                        leftClick(event.getClickedBlock());
                        event.setCancelled(true);
                        break;
                    case RIGHT_CLICK_BLOCK:
                        rightClick(event.getClickedBlock());
                        event.setCancelled(true);
                        break;
                    default:
                        break;
                }
            }
        }

        public void close()
        {
            PlayerInteractEvent.getHandlerList().unregister(this);
        }
    }

    /**
     * Sets corner1
     * */
    private void leftClick(Block block)
    {
        corner1 = block.getLocation();
        player.sendMessage("Corner 1 at: " + corner1.getBlockX() + ", " + corner1.getBlockY() + ", " + corner1.getBlockZ());
        attemptSave();
    }

    /**
     * Sets corner2
     * */
    private void rightClick(Block block)
    {
        corner2 = block.getLocation();
        player.sendMessage("Corner 2 at: " + corner2.getBlockX() + ", " + corner2.getBlockY() + ", " + corner2.getBlockZ());
        attemptSave();
    }

    /**
     * Creates two corners where c1 +x, +y, +z and c2 is -x, -y, -z relatively
     * @return the two corners
     * */
    private Location[] standarizeLocations()
    {
        int maxX = Math.max(corner1.getBlockX(), corner2.getBlockX());
        int minX = Math.min(corner1.getBlockX(), corner2.getBlockX());
        int maxY = Math.max(corner1.getBlockY(), corner2.getBlockY());
        int minY = Math.min(corner1.getBlockY(), corner2.getBlockY());
        int maxZ = Math.max(corner1.getBlockZ(), corner2.getBlockZ());
        int minZ = Math.min(corner1.getBlockZ(), corner2.getBlockZ());
        Location newC1 = new Location(corner1.getWorld(), maxX, maxY, maxZ);
        Location newC2 = new Location(corner2.getWorld(), minX, minY, minZ);
        return new Location[] {newC1, newC2};
    }
}
