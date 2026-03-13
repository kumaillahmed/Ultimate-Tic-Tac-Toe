package uttt.game;

import uttt.utils.Symbol;

public class BoardImplement implements BoardInterface {

    private MarkInterface[] marks;
    private boolean closed;
    private Symbol winner;

    public BoardImplement() {
        marks = new MarkInterface[9];
        for (int i = 0; i < marks.length; i++) {
            marks[i] = new MarkImplement(Symbol.EMPTY, i);
        }
        closed = false;
        winner = Symbol.EMPTY;
    }

    // returns marks
    @Override
    public MarkInterface[] getMarks() {
        return marks;
    }

    // sets array of marks for board
    @Override
    public void setMarks(MarkInterface[] marks) throws IllegalArgumentException {
        // throws exception if number of marks != 9
        if (marks.length != 9) {
            throw new IllegalArgumentException("Invalid number of marks: The marks array should have a length of 9.");
        }
        this.marks = marks;
    }

    // sets mark at specified index with given symbol
    @Override
    public boolean setMarkAt(Symbol symbol, int markIndex) throws IllegalArgumentException {
        // throws exception if mark index is out of bounds
        if (markIndex < 0 || markIndex >= marks.length) {
            throw new IllegalArgumentException("Invalid mark index: The mark index should be between 0 and 8.");
        }
        if (marks[markIndex].getSymbol() == Symbol.EMPTY) {
            marks[markIndex].setSymbol(symbol);
            // returns true if mark is successfully set
            return true;
        }
        // returns false if mark is already occupied
        return false;
    }

    @Override
    public boolean isClosed() {
        // if board is already won, it is considered closed
        if (winner != Symbol.EMPTY) {
            return true;
        }

        // check for winning combinations on board
        int[][] winningCombinations = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // rows
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // columns
                { 0, 4, 8 }, { 2, 4, 6 } // diagonals
        };

        // checks each winning combination for a match
        for (int[] combination : winningCombinations) {
            MarkInterface mark1 = marks[combination[0]];
            MarkInterface mark2 = marks[combination[1]];
            MarkInterface mark3 = marks[combination[2]];

            // board is already won and considered closed
            if (mark1.getSymbol() != Symbol.EMPTY && mark1.getSymbol() == mark2.getSymbol()
                    && mark1.getSymbol() == mark3.getSymbol()) {
                winner = mark1.getSymbol();
                closed = true;
                return true;
            }
        }

        // check if all positions are filled
        for (MarkInterface mark : marks) {
            if (mark.getSymbol() == Symbol.EMPTY) {
                // if there is an empty position, board is not closed yet
                return false;
            }
        }

        // if all positions are filled, it's a tie and board is considered closed
        closed = true;
        return true;
    }

    @Override
    public boolean isMovePossible(int markIndex) throws IllegalArgumentException {
        // checks if mark index is within range
        if (markIndex < 0 || markIndex >= marks.length) {
            throw new IllegalArgumentException("Invalid mark index: The mark index should be between 0 and 8.");
        }
        // checks if symbol at index = EMPTY, move possible at that index
        return marks[markIndex].getSymbol() == Symbol.EMPTY;
    }

    @Override
    public Symbol getWinner() {
        // if board is already closed, return winner
        if (closed) {
            return winner;
        }

        // checks for winning combinations
        int[][] winningCombinations = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // rows
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // columns
                { 0, 4, 8 }, { 2, 4, 6 } // diagonals
        };

        for (int[] combination : winningCombinations) {
            MarkInterface mark1 = marks[combination[0]];
            MarkInterface mark2 = marks[combination[1]];
            MarkInterface mark3 = marks[combination[2]];

            // checks if there is a winning combination
            if (mark1.getSymbol() != Symbol.EMPTY && mark1.getSymbol() == mark2.getSymbol()
                    && mark1.getSymbol() == mark3.getSymbol()) {
                winner = mark1.getSymbol();
                closed = true;
                return winner;
            }
        }

        // checks if all positions are filled
        boolean allFilled = true;
        for (MarkInterface mark : marks) {
            if (mark.getSymbol() == Symbol.EMPTY) {
                allFilled = false;
                break;
            }
        }

        if (allFilled) {
            // if all positions are filled, it's a tie
            closed = true;
        }

        // board is unfinished or a tie
        return Symbol.EMPTY;

    }

}