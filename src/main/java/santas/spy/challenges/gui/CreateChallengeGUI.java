package santas.spy.challenges.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import santas.spy.challenges.challenges.creator.ChallengeCreator;
import santas.spy.challenges.challenges.creator.ChestGoalCreator;

public class CreateChallengeGUI extends GUI {
    private ChallengeCreator creator;

    public CreateChallengeGUI(Player player, ChallengeCreator creator) {
        super(player, "Create Challenge", 18);
        this.creator = creator;
        open();
    }

    @Override
    public void build() {
        addItem(Material.PAPER, 4, 1, "Choose Goal Type");
        addItem(Material.CHEST, 12, 1, "Item Goal");
        addItem(Material.COBBLESTONE, 14, 1, "Block Goal");
    }

    @Override
    public void click(int slot) {
        switch (slot) {
            case 12: //Player chose to make a new item goal
                this.close();
                new ChestGoalCreator(player, creator);
                break;
            case 14: //Player chose to make a new block goal
                break;
            default: //Nothing usefull was selected
                break;
        }
    }
}
