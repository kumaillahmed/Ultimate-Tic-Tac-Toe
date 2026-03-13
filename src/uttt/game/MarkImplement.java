package uttt.game;

import uttt.utils.Symbol;

public class MarkImplement implements MarkInterface {

    private Symbol symbol;
    private int position;

    public MarkImplement(Symbol symbol, int position) {
        this.symbol = symbol;
        this.position = position;

        if (position < 0 || position > 8) {
            throw new IllegalArgumentException("Position is out of bounds.'");
        }
    }

    // returns symbol
    @Override
    public Symbol getSymbol() {
        return symbol;
    }

    // returns position
    @Override
    public int getPosition() {

        // throws exception if position is out of bounds
        if (position >= 0 && position < 9) {
            return position;
        } else {
            throw new IllegalArgumentException("Position is out of bounds.'");
        }
    }

    // sets symbol of mark
    @Override
    public void setSymbol(Symbol symbol) throws IllegalArgumentException {

        // throws exception if symbol = null
        if (symbol == null) {
            throw new IllegalArgumentException("Symbol cannot be null");
        }
        this.symbol = symbol;
    }
}
