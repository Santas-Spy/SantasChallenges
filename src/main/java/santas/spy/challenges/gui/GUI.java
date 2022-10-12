package santas.spy.challenges.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import santas.spy.challenges.SantasChallenges;

public abstract class GUI {
    protected Player player;
    protected String name;
    protected Inventory gui;
    protected InventoryListener listener;
    protected boolean blockInteractons = true;

    /**
     * Create a new GUI with no stored functions
     * @param player The player who will use the GUI
     * @param name The name at the top of the GUI
     * @param size The amount of slots in the GUI
     * */
    public GUI(Player player, String name, int size) {
        this.player = player;
        this.name = name;
        gui = Bukkit.getServer().createInventory(player, size);
        listener = new InventoryListener();
    }

    /**
     * Will be called upon opening the GUI. Indended use is to build the layout with itemstacks
     * */
    protected abstract void build();

    /**
     * Will be called when the player clicks a slot in the inventory
     * @param slot The slot number that was clicked
     * */
    protected abstract void click(int slot);
    
    /**
     * Close the inventory and deregister the listener
     * */
    public void close()
    {
        player.closeInventory();
        InventoryClickEvent.getHandlerList().unregister(listener);
    }

    /**
     * build and open the inventory and register its listener
     * */
    public void open()
    {
        build();
        SantasChallenges.PLUGIN.getServer().getPluginManager().registerEvents(listener, SantasChallenges.PLUGIN);
        player.openInventory(gui);
    }

    /**
     * Allow the player from moving items in the inventory.
     * This will apply after click() has been called
     * */
    protected void allowInteractions()
    {
        blockInteractons = false;
    }

    /**
     * Stop the player from moving items in the inventory.
     * This will apply after click() has been called
     * */
    protected void blockInteractons()
    {
        blockInteractons = true;
    }

    private class InventoryListener implements Listener {
        @EventHandler
        public void onInvEvent(InventoryClickEvent event) {
            if (event.getWhoClicked().equals(player) && event.getClickedInventory() != null && event.getClickedInventory().equals(gui)) {
                click(event.getSlot());
                event.setCancelled(blockInteractons);
            }
        }
    }

    /**
     * Add an item into the inventory
     * @param type The item type
     * @param slot The slot for the item to sit in
     * @param amount The count on the itemStack
     * @param info The name of the item
     * */
    protected void addItem(Material type, int slot, int amount, String info)
    {
        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(info);
        item.setItemMeta(meta);
        item.setAmount(amount);
        gui.setItem(slot, item);
    }

    /**
     * Add an item into the inventory
     * @param type The item type
     * @param slot The slot for the item to sit in
     * @param amount The count on the itemStack
     * */
    protected void addItem(Material type, int slot, int amount)
    {
        ItemStack item = new ItemStack(type);
        item.setAmount(amount);
        gui.setItem(slot, item);
    }
}
