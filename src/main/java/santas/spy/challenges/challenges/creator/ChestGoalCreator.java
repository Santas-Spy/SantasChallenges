package santas.spy.challenges.challenges.creator;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import santas.spy.challenges.SantasChallenges;
import santas.spy.challenges.challenges.goal.ChestGoal;
import santas.spy.challenges.challenges.goal.Goal;
import santas.spy.challenges.gui.GUI;

public class ChestGoalCreator {
    private List<ItemStack> items;
    private Player player;
    private ChallengeCreator creator;

    public ChestGoalCreator(Player player, ChallengeCreator creator)
    {
        this.creator = creator;
        this.player = player;
        GUI gui = createGUI();
        gui.open();
    }

    /**
     * Build the custom gui
     * @return the gui
     * */
    private GUI createGUI()
    {
        return new GUI(player, "Create Challenge", 27) {
            
            @Override
            protected void build() {
                addItem(Material.PAPER, 4, 1, "Add Items To Checklist");
                addItem(Material.LIME_CONCRETE, 26, 1, "Confirm");

                for (int i = 0; i < 9; i++) {
                    if (i != 4) {
                        addItem(Material.GRAY_STAINED_GLASS_PANE, i, 1, " ");
                    }
                }

                for (int i = 18; i < 26; i++) {
                    addItem(Material.GRAY_STAINED_GLASS_PANE, i, 1, " ");
                }
            }

            @Override
            protected void click(int slot) {
                if (slot >= 9 && slot < 18)  {
                    allowInteractions();    // allow players to add/remove/arrange items in the goal slots
                } else {
                    blockInteractons();     // disallow interaction with the control items
                }

                if (slot == 26) {           // confirm item
                    List<ItemStack> itemList = new ArrayList<>();
                    this.close();
                    for (int i = 8; i < 18; i++) {                          //get all the items that were in the gui
                        if (gui.getItem(i) != null) {
                            ItemStack item = new ItemStack(gui.getItem(i)); //copy them
                            itemList.add(item);                             //add them to the list
                        }
                    }
                    setItems(itemList);                                     //set this list to be the goals items
                }
            }
        };
    }

    /**
     * Sets the goals item list
     * @param items the items to set
     * */
    protected void setItems(List<ItemStack> items) {
        this.items = items;
        createChestFinder();    // move to the next step in the process
    }

    /**
     * Create a listener that will get the location of the chest on the island
     * */
    private void createChestFinder()
    {
        player.sendMessage("Right click the chest you want to be the goal chest");
        SantasChallenges.PLUGIN.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onInteractEvent(PlayerInteractEvent event)
            {
                if (event.getPlayer().equals(player)) {
                    Material blockType = event.getClickedBlock().getType();
                    if (blockType == Material.CHEST && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        setChest(event.getClickedBlock().getLocation());        //move to the next step
                        PlayerInteractEvent.getHandlerList().unregister(this);  //deregister this listener
                        event.setCancelled(true);                               //cancel opening the chest
                    }
                }
            }
        }, SantasChallenges.PLUGIN);
    }

    /**
     * Gets the offset of the chest from the corner
     * @param chest The location of the chest
     * */
    protected void setChest(Location chest)
    {
        int[] offset = ChallengeCreator.getOffset(chest, creator);
        Goal goal = new ChestGoal(items, offset[0], offset[1], offset[2]);
        creator.setGoal(goal);  //Hand the process back to the creator
    }
}
