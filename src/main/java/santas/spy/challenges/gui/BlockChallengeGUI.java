package santas.spy.challenges.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import santas.spy.challenges.SantasChallenges;
import santas.spy.challenges.challenges.Challenge;
import santas.spy.challenges.challenges.goal.BlockGoal;

public class BlockChallengeGUI extends GUI{
    private Challenge challenge;
    public BlockChallengeGUI(Player player, Challenge challenge) {
        super(player, challenge.name, 27);
        this.challenge = challenge;
        open();
    }

    @Override
    protected void build() {
        addItem(Material.COBBLESTONE, 4, 1, "Type: Block Goal");
        addItem(Material.LIME_CONCRETE, 26, 1, "Confirm");
        BlockGoal goal = (BlockGoal)challenge.goal;
        addItem(goal.goal, 17, 1, "Goal: Place " + goal.goal.toString() + " at ~" + goal.relativePos[0] + ", ~" + goal.relativePos[1] + ", ~" + goal.relativePos[2]);
    }

    @Override
    protected void click(int slot) {
        if (slot == 26) {
            player.sendMessage("Creating new challenge: " + challenge.name);
            SantasChallenges.PLUGIN.newChallenge(player, challenge.name);
            close();
        }
    }
    
}
