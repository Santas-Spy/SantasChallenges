package santas.spy.challenges.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import santas.spy.challenges.SantasChallenges;
import santas.spy.challenges.challenges.Challenge;
import santas.spy.challenges.challenges.goal.ChestGoal;

public class ChestChallengeGUI extends GUI {
    Challenge challenge;
    
    public ChestChallengeGUI(Player player, Challenge challenge) {
        super(player, challenge.name, 27);
        this.challenge = challenge;
        open();
    }

    @Override
    protected void build() {
        addItem(Material.CHEST, 4, 1, "Type: Chest Goal");
        
        //top row of glass panes
        for (int i = 0; i < 9; i++) {
            if (i != 4) {
                addItem(Material.GRAY_STAINED_GLASS_PANE, i, 1, " ");
            }
        }

        //The goal items 
        ChestGoal goal = (ChestGoal)challenge.goal;
        int i = 9;
        for (ItemStack item : goal.goalItems) {
            addItem(item.getType(), i, item.getAmount());
            i++;
        }

        //the bottom row of panes
        for (int j = 19; j < 26; j++) {
            addItem(Material.GRAY_STAINED_GLASS_PANE, j, 1, " ");
        }

        addItem(Material.RED_CONCRETE, 18, 1, "Cancel");
        addItem(Material.LIME_CONCRETE, 26, 1, "Confirm");
    }

    @Override
    protected void click(int slot) {
        if (slot == 26) {   //confirm
            player.sendMessage("Creating new challenge: " + challenge.name);
            SantasChallenges.PLUGIN.newChallenge(player, challenge.name);
            close();
        }

        if (slot == 18) {   //cancel
            close();
            new ChallengeListGUI(player);
        }
    }
}
