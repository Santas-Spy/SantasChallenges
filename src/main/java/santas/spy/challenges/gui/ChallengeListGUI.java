package santas.spy.challenges.gui;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import santas.spy.challenges.SantasChallenges;
import santas.spy.challenges.challenges.Challenge;

public class ChallengeListGUI extends GUI {

    public ChallengeListGUI(Player player)
    {
        super(player, "Challenge List", 27);
        open();
    }

    @Override
    protected void build()
    {
        //TODO: This does not handle >9 challenges. Needs multipage support
        Set<String> challenges = SantasChallenges.PLUGIN.challenges.getAll();
        int index = 8;
        for (String challenge : challenges) {   //add a new item for each challenge
            addItem(Material.GRASS_BLOCK, index, 1, challenge);
            index++;
        }
    }

    @Override
    protected void click(int slot)
    {
        ItemStack itemClicked = gui.getItem(slot);
        if (itemClicked != null && itemClicked.getType() != Material.AIR) {
            String name = itemClicked.getItemMeta().getDisplayName();           //Get the challenge name from the item name (This could probably have a smarter solution)
            Challenge challenge = SantasChallenges.PLUGIN.challenges.get(name);
            if (challenge.goal == null) {
                //this should only ever be reached in testing
                close();
                player.sendMessage("That challenge was broken. It did not have a goal and could not be started.");
            } else {
                switch (challenge.goal.type()) {
                    case CHEST:
                        new ChestChallengeGUI(player, challenge);
                        break;
                    case BLOCK:
                        new BlockChallengeGUI(player, challenge);
                        break;
                }
            } 
        }
    }
}
