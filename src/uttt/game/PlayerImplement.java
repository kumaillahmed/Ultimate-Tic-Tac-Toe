package uttt.game;

import uttt.utils.Move;
import uttt.utils.Symbol;

public class PlayerImplement implements PlayerInterface {

    private Symbol symbol;

    public PlayerImplement(Symbol symbol) {
        this.symbol = symbol;
    }

    // returns symbol
    @Override
    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public Move getPlayerMove(SimulatorInterface game, UserInterface ui) throws IllegalArgumentException {
        // throws exception if player = AI
        if (ui == null) {
            throw new IllegalArgumentException("Error: UserInterface cannot be null for human player.");
        }

        // if player = human, get move from UI
        if (ui instanceof UserInterface) {
            return ui.getUserMove();
        }

        return null;
    }
}
