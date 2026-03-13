package uttt.game;

import uttt.UTTTFactory;
import uttt.utils.Move;
import uttt.utils.Symbol;

public class SimulatorImplement implements SimulatorInterface {

    private BoardInterface[] boards;
    private Symbol currentPlayerSymbol;
    private int indexNextBoard;

    public SimulatorImplement() {

        // creates boards array with 9 boards
        boards = new BoardInterface[9];

        for (int i = 0; i < 9; i++) {
            boards[i] = UTTTFactory.createBoard();
        }
        currentPlayerSymbol = Symbol.CROSS;
        indexNextBoard = -1;
    }

    // returns array of boards
    @Override
    public BoardInterface[] getBoards() {
        return boards;
    }

    // sets array of boards
    @Override
    public void setBoards(BoardInterface[] boards) throws IllegalArgumentException {
        // checks if boards = null
        if (boards == null) {
            throw new IllegalArgumentException("Error: Invalid boards. Boards cannot be null.");
        }

        // checks if length of boards != 9
        if (boards.length != 9) {
            throw new IllegalArgumentException("Error: Invalid number of boards. Expected 9.");
        }

        for (int i = 0; i < boards.length; i++) {
            // checks if board at index i = null
            if (boards[i] == null) {
                throw new IllegalArgumentException("Error: Invalid board at index " + i + ". Board cannot be null.");
            }
        }

        this.boards = boards;
    }

    // returns current player symbol
    @Override
    public Symbol getCurrentPlayerSymbol() {
        return currentPlayerSymbol;
    }

    // sets current player symbol
    @Override
    public void setCurrentPlayerSymbol(Symbol symbol) throws IllegalArgumentException {
        // checks if symbol = null
        if (symbol == null) {
            throw new IllegalArgumentException("Error: Symbol cannot be null.");
        }
        currentPlayerSymbol = symbol;
    }

    @Override
    public boolean setMarkAt(Symbol symbol, int boardIndex, int markIndex) throws IllegalArgumentException {
        // checks if boardIndex is valid
        if (boardIndex < 0 || boardIndex >= 9) {
            throw new IllegalArgumentException("Error: Invalid board index.");
        }

        // checks if markIndex is valid
        if (markIndex < 0 || markIndex >= 9) {
            throw new IllegalArgumentException("Error: Invalid mark index.");
        }

        // checks if symbol = null
        if (symbol == null) {
            throw new IllegalArgumentException("Error: Symbol cannot be null.");
        }

        BoardInterface board = boards[boardIndex];
        MarkInterface[] marks = board.getMarks();

        // checks if mark at 'markIndex' = EMPTY
        if (marks[markIndex].getSymbol() == Symbol.EMPTY) {
            // creates a new mark with specified symbol & markIndex
            marks[markIndex] = UTTTFactory.createMark(symbol, markIndex);

            // marks[markIndex].setSymbol(symbol);

            // returns true if mark is successfully set
            return true;
        }

        // returns false if mark is not set
        return false;

    }

    // returns next board index
    @Override
    public int getIndexNextBoard() {
        return indexNextBoard;
    }

    @Override
    public void setIndexNextBoard(int index) throws IllegalArgumentException {
        // checks if index is in valid range (-1 to 8)
        if (index < -1 || index >= 9) {
            throw new IllegalArgumentException("Error: Invalid next board index.");
        }

        // checks if board at index = closed
        if (this.boards[index].isClosed()) {
            // If board = closed, set index to -1 so player can choose any board
            index = -1;
        }

        indexNextBoard = index;
    }

    @Override
    public boolean isGameOver() {
        boolean isGameFinished = true;

        // checks if any board is still not closed
        for (BoardInterface board : boards) {
            if (!board.isClosed()) {
                isGameFinished = false;
                break;
            }
        }

        // if all boards = closed, game is finished
        if (isGameFinished) {
            return true;
        }

        // checks if entire game is finished by checking for winning combinations

        // checks rows
        for (int i = 0; i < 9; i += 3) {
            if (boards[i].getWinner() != Symbol.EMPTY && boards[i].getWinner() == boards[i + 1].getWinner()
                    && boards[i].getWinner() == boards[i + 2].getWinner()) {
                return true;
            }
        }

        // checks columns
        for (int i = 0; i < 3; i++) {
            if (boards[i].getWinner() != Symbol.EMPTY && boards[i].getWinner() == boards[i + 3].getWinner()
                    && boards[i].getWinner() == boards[i + 6].getWinner()) {
                return true;
            }
        }

        // checks diagonals
        if (boards[0].getWinner() != Symbol.EMPTY && boards[0].getWinner() == boards[4].getWinner()
                && boards[0].getWinner() == boards[8].getWinner()) {
            return true;
        }

        if (boards[2].getWinner() != Symbol.EMPTY && boards[2].getWinner() == boards[4].getWinner()
                && boards[2].getWinner() == boards[6].getWinner()) {
            return true;
        }

        // if no winning condition is met, game is not finished
        return false;

    }

    @Override
    public boolean isMovePossible(int boardIndex) throws IllegalArgumentException {
        // checks if board index is valid
        if (boardIndex < 0 || boardIndex >= 9) {
            throw new IllegalArgumentException("Error: Invalid board index.");
        }
        // checks if move is possible based on next board index & current board index
        if (boardIndex != this.indexNextBoard && this.indexNextBoard != -1) {
            return false;
        }
        return boards[boardIndex].getWinner() == Symbol.EMPTY && !boards[boardIndex].isClosed();

    }

    @Override
    public boolean isMovePossible(int boardIndex, int markIndex) throws IllegalArgumentException {
        // checks if board index is valid
        if (boardIndex < 0 || boardIndex >= 9) {
            throw new IllegalArgumentException("Error: Invalid board index.");
        }
        // checks if mark index is valid
        if (markIndex < 0 || markIndex >= 9) {
            throw new IllegalArgumentException("Error: Invalid mark index.");
        }
        // checks if move is possible based on next board index & current board index
        if (boardIndex != this.indexNextBoard && this.indexNextBoard != -1) {
            return false;
        }
        // checks if board is not closed & mark index = EMPTY
        return !boards[boardIndex].isClosed() && boards[boardIndex].getMarks()[markIndex].getSymbol() == Symbol.EMPTY;
    }

    @Override
    public Symbol getWinner() {
        // checks rows for a winner
        for (int i = 0; i < 9; i += 3) {
            if (boards[i].getWinner() != Symbol.EMPTY && boards[i].getWinner() == boards[i + 1].getWinner()
                    && boards[i].getWinner() == boards[i + 2].getWinner()) {
                return boards[i].getWinner();
            }
        }

        // checks columns for a winner
        for (int i = 0; i < 3; i++) {
            if (boards[i].getWinner() != Symbol.EMPTY && boards[i].getWinner() == boards[i + 3].getWinner()
                    && boards[i].getWinner() == boards[i + 6].getWinner()) {
                return boards[i].getWinner();
            }
        }

        // checks diagonals for a winner
        if (boards[0].getWinner() != Symbol.EMPTY && boards[0].getWinner() == boards[4].getWinner()
                && boards[0].getWinner() == boards[8].getWinner()) {
            return boards[0].getWinner();
        }

        if (boards[2].getWinner() != Symbol.EMPTY && boards[2].getWinner() == boards[4].getWinner()
                && boards[2].getWinner() == boards[6].getWinner()) {
            return boards[2].getWinner();
        }

        // returns symbol EMPTY if there is no winner
        return Symbol.EMPTY;

    }

    @Override
    public void run(PlayerInterface playerOne, PlayerInterface playerTwo, UserInterface ui)
            throws IllegalArgumentException {

        SimulatorInterface uttt = UTTTFactory.createSimulator();

        // checks if ui is not null
        if (ui == null) {
            throw new IllegalArgumentException("Error: UI is null.");
        }

        // checks if game is not over
        if (this.isGameOver()) {
            throw new IllegalArgumentException("Error: Game is over.");
        }

        // gets symbols for player 1 & 2
        Symbol player1 = playerOne.getSymbol();
        Symbol player2 = playerTwo.getSymbol();

        // puts symbol of current player into currentsymbol
        Symbol currentsymbol = player1;
        // set symbol of current player
        setCurrentPlayerSymbol(currentsymbol);

        // puts move of current player into currentmove
        Move currentmove = playerOne.getPlayerMove(this, ui);

        // runs loop until isGameOver() = true
        while (!isGameOver()) {

            int marksIndex;
            int boardsIndex;

            // throws exception if move of current player = null
            if (currentmove == null) {
                throw new IllegalArgumentException("Error: Current player move is null.");
            }

            boardsIndex = currentmove.getBoardIndex();
            marksIndex = currentmove.getMarkIndex();

            // runs if condition to check if move is possible
            if (!this.isMovePossible(boardsIndex, marksIndex)) {

                // runs loop until user puts valid move
                while (!this.isMovePossible(boardsIndex, marksIndex)) {

                    currentmove = ui.getUserMove();
                    boardsIndex = currentmove.getBoardIndex();
                    marksIndex = currentmove.getMarkIndex();
                }
            }

            // sets mark of current player at board, mark
            this.setMarkAt(currentsymbol, boardsIndex, marksIndex);

            // sets index of next board = mark index of previous player
            setIndexNextBoard(marksIndex);

            // updates ui screen
            ui.updateScreen(this);

            // if player won/tie, screen shows prompt
            if (isGameOver()) {
                break;
            }

            // switches symbol of current player
            currentsymbol = (currentsymbol == player1) ? player2 : player1;

            // updates current player symbol in uttt
            setCurrentPlayerSymbol(currentsymbol);

            // switches move of current player
            currentmove = (currentsymbol == player1) ? playerOne.getPlayerMove(uttt, ui)
                    : playerTwo.getPlayerMove(this, ui);

            // updates ui screen
            ui.updateScreen(this);
        }

        // shows winner once loop ends
        ui.showGameOverScreen(getWinner());
    }
}
