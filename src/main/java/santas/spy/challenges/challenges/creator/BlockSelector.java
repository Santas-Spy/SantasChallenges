package santas.spy.challenges.challenges.creator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import santas.spy.challenges.SantasChallenges;

public class BlockSelector {
    private Location block;
    private int[] offset = null;
    private Player player;
    private ClickListener listener;
    private ChallengeCreator creator;

    public BlockSelector(Player player, ChallengeCreator creator)
    {
        this.player = player;
        this.creator = creator;
        selectLocation();
    }

    /**
     * Create a listener that gets a single block
     * */
    private void selectLocation()
    {
        listener = new ClickListener();
        SantasChallenges.PLUGIN.getServer().getPluginManager().registerEvents(listener, SantasChallenges.PLUGIN);
        player.sendMessage("Using a stick select the block for the player to spawn on.");
    }
    
    /**
     * A custom listener that trigger if a player clicks while holding a stick
     * */
    private class ClickListener implements Listener
    {
        @EventHandler
        public void onClick(PlayerInteractEvent event)
        {
            if (event.getPlayer().equals(player) && event.hasItem() && event.getItem().getType() == Material.STICK) {
                switch (event.getAction()) {
                    case LEFT_CLICK_BLOCK:
                        click(event.getClickedBlock());
                        event.setCancelled(true);
                        break;
                    case RIGHT_CLICK_BLOCK:
                        click(event.getClickedBlock());
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
     * Set the offset of the block clicked
     * */
    private void click(Block clicked)
    {
        block = clicked.getLocation();
        block.setY(block.getY() + 1.2);
        offset = ChallengeCreator.getOffset(block, creator);
        player.sendMessage("Type /santaschallenges confirm to save this challenge");
    }

    /**
     * Gets the block selected. If a choice has been made it will close the listener
     * @return the offset of the block clicked from the corner
     * */
    public int[] getBlock()
    {
        if (offset != null) {
            listener.close();
        }
        return offset;
    }
}
