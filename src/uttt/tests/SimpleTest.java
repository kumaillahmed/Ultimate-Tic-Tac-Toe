package uttt.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uttt.UTTTFactory;
import uttt.game.BoardInterface;
import uttt.game.MarkInterface;
import uttt.game.SimulatorInterface;
import uttt.utils.Symbol;

public class SimpleTest {

	// verifies correct symbol
	@Test
	public void getSymbolMarkTest() {
		Symbol testSymbol;

		// set testSymbol to CROSS
		testSymbol = Symbol.CROSS;

		// creates object MarkInterface with testSymbol and position 0
		MarkInterface mark = UTTTFactory.createMark(testSymbol, 0);

		// puts symbol from mark to inputSymbol
		Symbol inputSymbol = mark.getSymbol();

		// checks if testSymbol = inputSymbol
		assertEquals("Symbol must be a CROSS.", testSymbol, inputSymbol);
	}

	// verifies correct position
	@Test
	public void getPositionMarkTest() {

		// sets position to 4
		int pos = 4;

		// creates object MarkInterface with Symbol.CROSS and pos value
		MarkInterface mark = UTTTFactory.createMark(Symbol.CROSS, pos);

		// puts position from mark to inputPosition
		int inputPosition = mark.getPosition();

		//// checks if pos = inputPosition
		assertEquals("Position must be 4.", pos, inputPosition);
	}

	// throws exception if given null
	@Test
	public void setSymbolMarkTest() {
		MarkInterface mark = UTTTFactory.createMark(Symbol.CIRCLE, 0);

		assertThrows(IllegalArgumentException.class, () -> {
			mark.setSymbol(null);
		});
	}

	// checks if getMarks returns non-null array of length 9
	@Test
	public void getMarksFirstTest() {
		BoardInterface board = UTTTFactory.createBoard();

		// calls getMarks on board object
		MarkInterface[] arraymarks = board.getMarks();

		// checks if returned arraymarks is not null
		assertNotNull("Error: Mark array is null.", arraymarks);

		// checks if length of arraymarks is 9
		assertEquals("Error: Array Length is not equal to 9.", arraymarks.length, 9);
	}

	// checks if each mark is not null and has symbol
	@Test
	public void getMarksSecondTest() {
		BoardInterface board = UTTTFactory.createBoard();

		// calls getMarks on board object
		MarkInterface[] arraymarks = board.getMarks();

		// runs loop and checks if each arraymarks[i] is not null and is empty
		for (int i = 0; i < 9; i++) {
			assertNotNull("Error: Mark must not be null.", arraymarks[i]);
			assertEquals("Error: Mark must be initialized to EMPTY.", arraymarks[i].getSymbol(), Symbol.EMPTY);
		}
	}

	// checks if setMarks correctly sets marks on board
	@Test
	public void testSetMarks() {
		BoardInterface board = UTTTFactory.createBoard();

		// calls getMarks on board object
		MarkInterface[] arraymarks = board.getMarks();

		// uses setMarks of board object to set arraymarks obtained previously
		board.setMarks(arraymarks);

		// checks if original arraymarks = updated one
		assertArrayEquals("Error: Marks on board are not set correctly.", arraymarks, board.getMarks());
	}

	// checks if method returns true for correct mark placement and correct symbol
	@Test
	public void testTRUESetMarkAt() {
		BoardInterface board = UTTTFactory.createBoard();

		// checks if setMarkAt returns true if mark with CROSS is set at pos 3
		assertTrue("TRUE: CROSS at box 3.", board.setMarkAt(Symbol.CROSS, 3));

		// checks if setMarkAt returns false if mark with CIRCLE is set at same pos
		assertFalse("FALSE: Setting CIRCLE at box 3.", board.setMarkAt(Symbol.CIRCLE, 3));

		// checks if symbol is CROSS
		assertEquals("Error: Symbol must be CROSS.", board.getMarks()[3].getSymbol(), Symbol.CROSS);
	}

	// throws exception if mark set at >=9 position
	@Test
	public void testInvalid1SetMarkAt() {
		BoardInterface board = UTTTFactory.createBoard();

		assertThrows("ERROR: Position >= 9 for mark.", IllegalArgumentException.class, () -> {
			board.setMarkAt(Symbol.CROSS, 20);
		});
	}

	// throws exception if mark set at -ve position
	@Test
	public void testInvalid2SetMarkAt() {
		BoardInterface board = UTTTFactory.createBoard();

		assertThrows("ERROR: Negative position for mark.", IllegalArgumentException.class, () -> {
			board.setMarkAt(Symbol.CROSS, -3);
		});
	}

	// tests when board is closed due to win/tie
	@Test
	public void testisClosed1() {
		BoardInterface board = UTTTFactory.createBoard();

		// board must not be closed at start
		assertFalse("Error: Initially, board must not be closed.", board.isClosed());
	}

	@Test
	public void testisClosed2() {
		BoardInterface board = UTTTFactory.createBoard();
		// create a win
		board.setMarkAt(Symbol.CIRCLE, 0);
		board.setMarkAt(Symbol.CIRCLE, 1);
		board.setMarkAt(Symbol.CIRCLE, 2);
		assertTrue("Error: Board must be closed when a player wins.", board.isClosed());
	}

	@Test
	public void testisClosed3() {
		BoardInterface board = UTTTFactory.createBoard();

		// create a tie
		board.setMarkAt(Symbol.CIRCLE, 0);
		board.setMarkAt(Symbol.CROSS, 1);
		board.setMarkAt(Symbol.CIRCLE, 2);
		board.setMarkAt(Symbol.CROSS, 3);
		board.setMarkAt(Symbol.CROSS, 4);
		board.setMarkAt(Symbol.CIRCLE, 5);
		board.setMarkAt(Symbol.CIRCLE, 6);
		assertFalse("Error: Board is still incomplete.", board.isClosed());
	}

	@Test
	public void testisClosed4() {
		BoardInterface board = UTTTFactory.createBoard();

		// create a tie
		board.setMarkAt(Symbol.CIRCLE, 0);
		board.setMarkAt(Symbol.CROSS, 1);
		board.setMarkAt(Symbol.CIRCLE, 2);
		board.setMarkAt(Symbol.CROSS, 3);
		board.setMarkAt(Symbol.CROSS, 4);
		board.setMarkAt(Symbol.CIRCLE, 5);
		board.setMarkAt(Symbol.CIRCLE, 6);
		board.setMarkAt(Symbol.CIRCLE, 7);
		board.setMarkAt(Symbol.CROSS, 8);
		assertTrue("Error: Board must be closed when it's a tie.", board.isClosed());
	}

	// tests whe move is possible or throws exception
	@Test
	public void testisMovePossible1() {
		BoardInterface board = UTTTFactory.createBoard();

		board.setMarkAt(Symbol.EMPTY, 5);

		// position 5 must be possible for a move
		assertTrue("Error: Initially, Move must be possible.", board.isMovePossible(5));
	}

	@Test
	public void testisMovePossible2() {
		BoardInterface board = UTTTFactory.createBoard();

		board.setMarkAt(Symbol.CIRCLE, 4);

		// position 4 must not be possible for a move
		assertFalse("Error: Move must not be possible.", board.isMovePossible(4));
	}

	@Test
	public void testisMovePossible3() {
		BoardInterface board = UTTTFactory.createBoard();

		assertThrows(IllegalArgumentException.class, () -> {
			board.isMovePossible(10);
		});
	}

	@Test
	public void testisMovePossible4() {
		BoardInterface board = UTTTFactory.createBoard();

		assertThrows(IllegalArgumentException.class, () -> {
			board.isMovePossible(-4);
		});
	}

	// tests if returns winner/returns empty for unfinished/tie
	@Test
	public void testgetWinner1() {
		BoardInterface board = UTTTFactory.createBoard();

		// initially, board must have no winner
		assertEquals("ERROR: Initially, board must has no winner.", Symbol.EMPTY, board.getWinner());
	}

	@Test
	public void testgetWinner2() {
		BoardInterface board = UTTTFactory.createBoard();

		// create a win for CROSS
		board.setMarkAt(Symbol.CROSS, 0);
		board.setMarkAt(Symbol.CROSS, 1);
		board.setMarkAt(Symbol.CROSS, 2);

		// winner must be CROSS
		assertEquals("ERROR: Winner must be CROSS.", Symbol.CROSS, board.getWinner());
	}

	@Test
	public void testgetWinner3() {
		BoardInterface board = UTTTFactory.createBoard();

		// create a tie
		board.setMarkAt(Symbol.CROSS, 0);
		board.setMarkAt(Symbol.CROSS, 1);
		board.setMarkAt(Symbol.CIRCLE, 2);
		board.setMarkAt(Symbol.CIRCLE, 3);
		board.setMarkAt(Symbol.CIRCLE, 4);
		board.setMarkAt(Symbol.CROSS, 5);
		board.setMarkAt(Symbol.CROSS, 6);
		board.setMarkAt(Symbol.CROSS, 7);
		board.setMarkAt(Symbol.CIRCLE, 8);

		// result must be a tie
		assertEquals("ERROR: Result must be a tie.", Symbol.EMPTY, board.getWinner());
	}

	@Test
	public void testgetWinner4() {

		BoardInterface board = UTTTFactory.createBoard();

		// create a tie
		board.setMarkAt(Symbol.CIRCLE, 0);
		board.setMarkAt(Symbol.CROSS, 1);
		board.setMarkAt(Symbol.CIRCLE, 2);
		board.setMarkAt(Symbol.CROSS, 3);
		board.setMarkAt(Symbol.EMPTY, 4);
		board.setMarkAt(Symbol.EMPTY, 5);
		board.setMarkAt(Symbol.EMPTY, 6);
		board.setMarkAt(Symbol.EMPTY, 7);
		board.setMarkAt(Symbol.EMPTY, 8);

		// unfinished board must return empty
		assertEquals("ERROR: Unfinished board must return empty.", Symbol.EMPTY, board.getWinner());
	}

	@Test
	public void testgetWinner5() {
		BoardInterface board = UTTTFactory.createBoard();

		board.setMarkAt(Symbol.CIRCLE, 4);

		// unfinished board must return empty
		assertEquals("ERROR: Unfinished board must return empty.", Symbol.EMPTY, board.getWinner());
	}

	@Test
	public void testgetWinner6() {
		BoardInterface board = UTTTFactory.createBoard();

		// create a win for CROSS
		board.setMarkAt(Symbol.CROSS, 0);
		board.setMarkAt(Symbol.CROSS, 3);
		board.setMarkAt(Symbol.CROSS, 6);

		// winner must be CROSS
		assertEquals("ERROR: Winner must be CROSS.", Symbol.CROSS, board.getWinner());
	}

	@Test
	public void testgetWinner7() {
		BoardInterface board = UTTTFactory.createBoard();

		// create a win for CROSS
		board.setMarkAt(Symbol.CROSS, 0);
		board.setMarkAt(Symbol.CROSS, 4);
		board.setMarkAt(Symbol.CROSS, 8);

		// winner must be CROSS
		assertEquals("ERROR: Winner must be CROSS.", Symbol.CROSS, board.getWinner());
	}

	// tests if board conditions are correct
	@Test
	public void testgetBoards1() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		BoardInterface[] notNullBoards = simulator1.getBoards();

		assertNotNull(notNullBoards);

		for (BoardInterface eachboard : notNullBoards) {
			assertNotNull("Error: Boards array is not null.", eachboard);
		}
	}

	@Test
	public void testgetBoards2() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		BoardInterface[] boards = simulator1.getBoards();

		for (BoardInterface eachboard : boards) {
			assertNotNull("Error: Boards array is not null.", eachboard);
		}

		assertEquals("Error: Total number of boards is not 9.", 9, boards.length);
	}

	// tests if board set is correct
	@Test
	public void testsetBoards() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();
		BoardInterface[] boards = new BoardInterface[9];

		// sets test boards
		for (int i = 0; i < 9; i++) {
			boards[i] = UTTTFactory.createBoard();
		}

		// sets boards in simulator
		simulator1.setBoards(boards);

		// gets boards from simulator
		BoardInterface[] returnedBoards = simulator1.getBoards();

		assertNotNull(returnedBoards[0]);
		// checks if returned boards match original boards
		assertArrayEquals("Error: Boards are not set correctly in simulator.", boards, returnedBoards);
		for (BoardInterface eachBoard : returnedBoards) {
			assertNotNull(eachBoard);
		}
	}

	// throws exception if setBoards() is invalid
	@Test
	public void testInvalidsetBoards1() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();
		BoardInterface[] invalidBoards = new BoardInterface[10];

		assertThrows("ERROR: Invalid number of boards.", IllegalArgumentException.class, () -> {
			simulator1.setBoards(invalidBoards);
		});
	}

	@Test
	public void testInvalidsetBoards2() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();
		BoardInterface[] invalidBoards = null;

		assertThrows(IllegalArgumentException.class, () -> {
			simulator1.setBoards(invalidBoards);
		});
	}

	@Test
	public void testInvalidsetBoards3() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();
		BoardInterface[] incompleteBoards = new BoardInterface[9];

		incompleteBoards[0] = UTTTFactory.createBoard();
		incompleteBoards[1] = UTTTFactory.createBoard();
		incompleteBoards[2] = null;
		incompleteBoards[3] = UTTTFactory.createBoard();
		incompleteBoards[4] = null;
		incompleteBoards[5] = UTTTFactory.createBoard();
		incompleteBoards[6] = UTTTFactory.createBoard();
		incompleteBoards[7] = null;
		incompleteBoards[8] = UTTTFactory.createBoard();

		assertThrows("ERROR: Incomplete boards.", IllegalArgumentException.class, () -> {
			simulator1.setBoards(incompleteBoards);
		});
	}

	// checks if it gets symbol of current player
	@Test
	public void testgetCurrentPlayerSymbol1() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);

		assertEquals(Symbol.CROSS, simulator1.getCurrentPlayerSymbol());
	}

	@Test
	public void testgetCurrentPlayerSymbol2() {

		SimulatorInterface simulator1 = UTTTFactory.createSimulator();
		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);
		simulator1.setMarkAt(Symbol.CROSS, 0, 0);
		assertEquals(Symbol.CROSS, simulator1.getCurrentPlayerSymbol());

	}

	// checks if current player symbol is set correctly
	@Test
	public void testsetCurrentPlayerSymbol() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// sets current player symbol to CROSS
		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);

		// checks if current player symbol is set correctly
		assertEquals(Symbol.CROSS, simulator1.getCurrentPlayerSymbol());
	}

	// throws exception if current player symbol is null
	@Test
	public void testInvalidsetCurrentPlayerSymbol() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		assertThrows(IllegalArgumentException.class, () -> {
			simulator1.setCurrentPlayerSymbol(null);
		});
	}

	// tests if symbol is set correctly
	@Test
	public void testsetMarkAt1() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// sets current player symbol to CROSS
		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);

		// sets a mark at boardIndex 0, markIndex 4
		assertTrue("TRUE: CROSS at board 0, box 7.", simulator1.setMarkAt(Symbol.CROSS, 0, 7));
	}

	public void testsetMarkAt2() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// sets current player symbol to CIRCLE
		simulator1.setCurrentPlayerSymbol(Symbol.CIRCLE);
		simulator1.setMarkAt(Symbol.CIRCLE, 0, 0);

		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);
		simulator1.setMarkAt(Symbol.CROSS, 0, 3);

		simulator1.setCurrentPlayerSymbol(Symbol.CIRCLE);

		assertTrue("Error: Mark is added incorrectly.", simulator1.setMarkAt(Symbol.CIRCLE, 3, 0));
	}

	public void testsetMarkAt3() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// sets current player symbol to CIRCLE
		simulator1.setCurrentPlayerSymbol(Symbol.CIRCLE);
		simulator1.setMarkAt(Symbol.CIRCLE, 0, 0);

		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);
		assertTrue("Error: Mark is added incorrectly.", simulator1.setMarkAt(Symbol.CROSS, 0, 4));
	}

	@Test
	public void invalidFirstSetMarkAt() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);
		simulator1.setMarkAt(Symbol.CROSS, 0, 0);

		simulator1.setCurrentPlayerSymbol(Symbol.CIRCLE);
		simulator1.setMarkAt(Symbol.CIRCLE, 0, 3);
		// sets current player symbol to CROSS
		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);

		assertFalse("Error: Box is already occupied.", simulator1.setMarkAt(Symbol.CROSS, 4, 6));
	}

	@Test
	public void invalidSecondSetMarkAt() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// sets current player symbol to CROSS
		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);

		simulator1.setMarkAt(Symbol.CROSS, 0, 0);
		simulator1.setCurrentPlayerSymbol(Symbol.CIRCLE);

		assertFalse("Error: Box is already occupied.", simulator1.setMarkAt(Symbol.CIRCLE, 0, 0));
	}

	// throws exception if symbol set at wrong mark index
	@Test
	public void testInvalidMarkIndexsetMarkAt() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// sets current player symbol to CROSS
		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);

		// tries to set mark at boardIndex 0, markIndex 20 (invalid markIndex)
		assertThrows(IllegalArgumentException.class, () -> {
			simulator1.setMarkAt(Symbol.CROSS, 0, 20);
		});
	}

	@Test
	public void testInvalidNegMarkIndexsetMarkAt() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// sets current player symbol to CROSS
		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);

		// tries to set mark at boardIndex 0, markIndex -20 (invalid markIndex)
		assertThrows(IllegalArgumentException.class, () -> {
			simulator1.setMarkAt(Symbol.CROSS, 0, -20);
		});
	}

	// throws exception if symbol set at wrong board index
	@Test
	public void testInvalidBoardIndexsetMarkAt() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// sets current player symbol to CROSS
		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);

		// tries to set mark at boardIndex 15, markIndex 5 (invalid markIndex)
		assertThrows(IllegalArgumentException.class, () -> {
			simulator1.setMarkAt(Symbol.CROSS, 15, 5);
		});
	}

	// throws exception if symbol set at wrong board index
	@Test
	public void testInvalidNegBoardIndexsetMarkAt() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// sets current player symbol to CROSS
		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);

		// tries to set mark at boardIndex -15, markIndex 5 (invalid markIndex)
		assertThrows(IllegalArgumentException.class, () -> {
			simulator1.setMarkAt(Symbol.CROSS, -15, 5);
		});
	}

	// throws exception if symbol is null
	@Test
	public void testInvalidNullsetMarkAt() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// tries to set mark (invalid)
		assertThrows(IllegalArgumentException.class, () -> {
			simulator1.setMarkAt(null, 0, 5);
		});
	}

	// checks if next index of board can be get
	@Test
	public void testgetIndexNextBoard() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// tests setting a valid index within board boundaries
		int validIndex = 3;

		simulator1.setIndexNextBoard(validIndex);

		int actualIndex = simulator1.getIndexNextBoard();

		assertEquals("Error: The index of the next board should be 3.", validIndex, actualIndex);
	}

	// checks if next index of board can be set
	@Test
	public void testsetIndexNextBoard() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// tests setting a valid index within board boundaries
		int validIndex = 2;

		simulator1.setIndexNextBoard(validIndex);

		int actualIndex = simulator1.getIndexNextBoard();

		assertEquals("Error: The index of the next board should be 2.", validIndex, actualIndex);
	}

	@Test
	public void testsetIndexNextBoard1() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// tests setting -1 as index (unrestricted)
		int unrestrictedIndex = -1;

		int actualUnrestrictedIndex = simulator1.getIndexNextBoard();

		assertEquals("Error: The index of the next board should be -1 (unrestricted)", unrestrictedIndex,
				actualUnrestrictedIndex);
	}

	@Test
	public void testsetIndexNextBoardInvalid() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();
		int outIndex = -1;

		simulator1.setIndexNextBoard(outIndex);

		int realIndex = simulator1.getIndexNextBoard();
		assertEquals(-1, realIndex);
	}

	// +ve exception case
	@Test
	public void testsetIndexPosNextBoardInvalid() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		assertThrows(IllegalArgumentException.class, () -> {
			simulator1.setIndexNextBoard(15);
		});
	}

	// -ve exception case
	@Test
	public void testsetIndexNegNextBoardInvalid() {
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		assertThrows(IllegalArgumentException.class, () -> {
			simulator1.setIndexNextBoard(-5);
		});
	}

	// tests gameover
	@Test
	public void isGameOver1() {

		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// creates object BoardInterface
		BoardInterface[] boards = new BoardInterface[9];

		// runs loop for i
		for (int i = 0; i <= 2; i++) {

			// creates object BoardInterface
			BoardInterface firstBoard = UTTTFactory.createBoard();
			MarkInterface[] newBoard = new MarkInterface[9];

			newBoard[0] = UTTTFactory.createMark(Symbol.CIRCLE, 0);
			newBoard[1] = UTTTFactory.createMark(Symbol.CIRCLE, 1);
			newBoard[2] = UTTTFactory.createMark(Symbol.CIRCLE, 2);
			newBoard[3] = UTTTFactory.createMark(Symbol.EMPTY, 3);
			newBoard[4] = UTTTFactory.createMark(Symbol.EMPTY, 4);
			newBoard[5] = UTTTFactory.createMark(Symbol.EMPTY, 5);
			newBoard[6] = UTTTFactory.createMark(Symbol.EMPTY, 6);
			newBoard[7] = UTTTFactory.createMark(Symbol.EMPTY, 7);
			newBoard[8] = UTTTFactory.createMark(Symbol.EMPTY, 8);

			firstBoard.setMarks(newBoard);
			boards[i] = firstBoard;

		}

		boards[3] = UTTTFactory.createBoard();
		boards[4] = UTTTFactory.createBoard();
		boards[5] = UTTTFactory.createBoard();
		boards[6] = UTTTFactory.createBoard();
		boards[7] = UTTTFactory.createBoard();
		boards[8] = UTTTFactory.createBoard();

		simulator1.setBoards(boards);

		// asserts true
		assertTrue(simulator1.isGameOver());
	}

	@Test
	public void isGameOver2() {

		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// creates object BoardInterface
		BoardInterface[] boards = new BoardInterface[9];

		// runs loop for i
		for (int i = 0; i <= 8; i++) {
			// creates object BoardInterface
			BoardInterface initialBoard = UTTTFactory.createBoard();
			MarkInterface[] newBoard = new MarkInterface[9];

			newBoard[0] = UTTTFactory.createMark(Symbol.CIRCLE, 0);
			newBoard[1] = UTTTFactory.createMark(Symbol.CIRCLE, 1);
			newBoard[2] = UTTTFactory.createMark(Symbol.CROSS, 2);
			newBoard[3] = UTTTFactory.createMark(Symbol.EMPTY, 3);
			newBoard[4] = UTTTFactory.createMark(Symbol.EMPTY, 4);
			newBoard[5] = UTTTFactory.createMark(Symbol.EMPTY, 5);
			newBoard[6] = UTTTFactory.createMark(Symbol.EMPTY, 6);
			newBoard[7] = UTTTFactory.createMark(Symbol.CROSS, 7);
			newBoard[8] = UTTTFactory.createMark(Symbol.CIRCLE, 8);

			initialBoard.setMarks(newBoard);
			boards[i] = initialBoard;

		}
		simulator1.setBoards(boards);

		// asserts false
		assertFalse(simulator1.isGameOver());
	}

	@Test
	public void isGameOver3() {

		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// creates object BoardInterface
		BoardInterface[] boards = new BoardInterface[9];

		// runs loop for i
		for (int i = 0; i < 9; i++) {
			// creates object BoardInterface
			BoardInterface initialBoard = UTTTFactory.createBoard();
			MarkInterface[] newBoard = new MarkInterface[9];

			newBoard[0] = UTTTFactory.createMark(Symbol.CROSS, 0);
			newBoard[1] = UTTTFactory.createMark(Symbol.CROSS, 1);
			newBoard[2] = UTTTFactory.createMark(Symbol.CIRCLE, 2);
			newBoard[3] = UTTTFactory.createMark(Symbol.CIRCLE, 3);
			newBoard[4] = UTTTFactory.createMark(Symbol.CIRCLE, 4);
			newBoard[5] = UTTTFactory.createMark(Symbol.CROSS, 5);
			newBoard[6] = UTTTFactory.createMark(Symbol.CROSS, 6);
			newBoard[7] = UTTTFactory.createMark(Symbol.CIRCLE, 7);
			newBoard[8] = UTTTFactory.createMark(Symbol.CROSS, 8);

			initialBoard.setMarks(newBoard);
			boards[i] = initialBoard;

		}
		simulator1.setBoards(boards);

		// asserts true
		assertTrue(simulator1.isGameOver());
	}

	@Test
	// checks isMovePossible
	public void testSimisMovePossible1() {

		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		simulator1.setCurrentPlayerSymbol(Symbol.CIRCLE);
		simulator1.setMarkAt(Symbol.CIRCLE, 0, 0);

		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);
		simulator1.setMarkAt(Symbol.CROSS, 0, 8);

		// asserts false
		assertFalse(simulator1.isMovePossible(0));
	}

	@Test
	public void testSimisMovePossible2() {

		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		simulator1.setCurrentPlayerSymbol(Symbol.CIRCLE);
		simulator1.setMarkAt(Symbol.CIRCLE, 0, 0);

		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);
		simulator1.setMarkAt(Symbol.CROSS, 0, 6);

		// asserts true
		assertTrue(simulator1.isMovePossible(6));
	}

	@Test
	public void testSimisMovePossible3() {

		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		simulator1.setCurrentPlayerSymbol(Symbol.CIRCLE);
		simulator1.setMarkAt(Symbol.CIRCLE, 0, 0);

		// asserts false
		assertFalse(simulator1.isMovePossible(5));
	}

	@Test
	public void testSimisMovePossible4() {

		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		simulator1.setCurrentPlayerSymbol(Symbol.CIRCLE);
		simulator1.setMarkAt(Symbol.CIRCLE, 0, 0);

		// asserts true
		assertTrue(simulator1.isMovePossible(0));
	}

	// throws an exception if board > 8
	@Test
	public void testSimisMovePossible5() {

		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		assertThrows(IllegalArgumentException.class, () -> {
			simulator1.isMovePossible(15);
		});
	}

	// throws an exception if board < 0
	@Test
	public void testSimisMovePossible6() {
		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		assertThrows(IllegalArgumentException.class, () -> {
			simulator1.isMovePossible(-15);
		});
	}

	@Test
	public void testSimisMovePossible7() {
		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// asserts true
		assertTrue(simulator1.isMovePossible(0));
	}

	@Test
	public void testSimisMovePossible8() {

		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		simulator1.setCurrentPlayerSymbol(Symbol.CIRCLE);
		simulator1.setMarkAt(Symbol.CIRCLE, 0, 0);

		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);
		simulator1.setMarkAt(Symbol.CROSS, 0, 5);

		// asserts true
		assertTrue(simulator1.isMovePossible(5, 5));
	}

	@Test
	public void testSimisMovePossible9() {

		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// asserts true
		assertTrue(simulator1.isMovePossible(0, 3));
	}

	@Test
	public void testSimisMovePossible10() {

		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		simulator1.setCurrentPlayerSymbol(Symbol.CIRCLE);
		simulator1.setMarkAt(Symbol.CIRCLE, 0, 0);

		simulator1.setCurrentPlayerSymbol(Symbol.CROSS);
		simulator1.setMarkAt(Symbol.CROSS, 0, 4);

		// asserts false
		assertFalse(simulator1.isMovePossible(7, 4));
	}

	@Test
	public void testSimisMovePossible11() {

		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		simulator1.setCurrentPlayerSymbol(Symbol.CIRCLE);
		simulator1.setMarkAt(Symbol.CIRCLE, 0, 0);

		// asserts true
		assertTrue(simulator1.isMovePossible(0, 5));
	}

	// throws an exception if box > 8
	@Test
	public void testSimisMovePossible12() {
		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		assertThrows(IllegalArgumentException.class, () -> {
			simulator1.isMovePossible(5, 15);
		});
	}

	// throws an exception if board > 8
	@Test
	public void testSimisMovePossible13() {
		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		assertThrows(IllegalArgumentException.class, () -> {
			simulator1.isMovePossible(15, 3);
		});
	}

	// throws an exception if box < 0
	@Test
	public void testSimisMovePossible14() {
		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		assertThrows(IllegalArgumentException.class, () -> {
			simulator1.isMovePossible(5, -10);
		});
	}

	// throws an exception if board < 0
	@Test
	public void testSimisMovePossible15() {
		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		assertThrows(IllegalArgumentException.class, () -> {
			simulator1.isMovePossible(-10, 6);
		});
	}

	// tests for winner symbol of game
	@Test
	public void testSimulatorgetWinner1() {

		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// creates object BoardInterface
		BoardInterface[] boards = new BoardInterface[9];

		// runs loop for i
		for (int i = 0; i <= 8; i++) {
			// creates object BoardInterface
			BoardInterface initialBoard = UTTTFactory.createBoard();
			MarkInterface[] newBoard = new MarkInterface[9];

			newBoard[0] = UTTTFactory.createMark(Symbol.CROSS, 0);
			newBoard[1] = UTTTFactory.createMark(Symbol.CIRCLE, 1);
			newBoard[2] = UTTTFactory.createMark(Symbol.CROSS, 2);
			newBoard[3] = UTTTFactory.createMark(Symbol.CIRCLE, 3);
			newBoard[4] = UTTTFactory.createMark(Symbol.CROSS, 4);
			newBoard[5] = UTTTFactory.createMark(Symbol.CIRCLE, 5);
			newBoard[6] = UTTTFactory.createMark(Symbol.CROSS, 6);
			newBoard[7] = UTTTFactory.createMark(Symbol.CIRCLE, 7);
			newBoard[8] = UTTTFactory.createMark(Symbol.CROSS, 8);

			initialBoard.setMarks(newBoard);
			boards[i] = initialBoard;
		}
		simulator1.setBoards(boards);

		// asserts equal
		assertEquals(Symbol.CROSS, simulator1.getWinner());
	}

	@Test
	public void testSimulatorgetWinner2() {

		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// creates object BoardInterface
		BoardInterface[] boards = new BoardInterface[9];

		// creates object BoardInterface
		BoardInterface initialBoard = UTTTFactory.createBoard();
		MarkInterface[] newBoard = new MarkInterface[9];

		newBoard[0] = UTTTFactory.createMark(Symbol.CIRCLE, 0);
		newBoard[1] = UTTTFactory.createMark(Symbol.CROSS, 1);
		newBoard[2] = UTTTFactory.createMark(Symbol.CIRCLE, 2);
		newBoard[3] = UTTTFactory.createMark(Symbol.CROSS, 3);
		newBoard[4] = UTTTFactory.createMark(Symbol.CIRCLE, 4);
		newBoard[5] = UTTTFactory.createMark(Symbol.CROSS, 5);
		newBoard[6] = UTTTFactory.createMark(Symbol.CIRCLE, 6);
		newBoard[7] = UTTTFactory.createMark(Symbol.CROSS, 7);
		newBoard[8] = UTTTFactory.createMark(Symbol.CIRCLE, 8);

		boards[1] = UTTTFactory.createBoard();
		boards[2] = UTTTFactory.createBoard();
		boards[3] = UTTTFactory.createBoard();
		boards[4] = UTTTFactory.createBoard();
		boards[5] = UTTTFactory.createBoard();
		boards[6] = UTTTFactory.createBoard();
		boards[7] = UTTTFactory.createBoard();
		boards[8] = UTTTFactory.createBoard();

		initialBoard.setMarks(newBoard);
		boards[0] = initialBoard;

		simulator1.setBoards(boards);

		// asserts equal
		assertEquals(Symbol.EMPTY, simulator1.getWinner());
	}

	@Test
	public void testSimulatorgetWinner3() {

		// creates object SimulatorInterface
		SimulatorInterface simulator1 = UTTTFactory.createSimulator();

		// creates object BoardInterface
		BoardInterface[] boards = new BoardInterface[9];

		// runs loop for i
		for (int i = 0; i <= 2; i++) {
			BoardInterface initialBoard = UTTTFactory.createBoard();
			MarkInterface[] newBoard = new MarkInterface[9];

			newBoard[0] = UTTTFactory.createMark(Symbol.CIRCLE, 0);
			newBoard[1] = UTTTFactory.createMark(Symbol.CIRCLE, 1);
			newBoard[2] = UTTTFactory.createMark(Symbol.CIRCLE, 2);
			newBoard[3] = UTTTFactory.createMark(Symbol.EMPTY, 3);
			newBoard[4] = UTTTFactory.createMark(Symbol.EMPTY, 4);
			newBoard[5] = UTTTFactory.createMark(Symbol.EMPTY, 5);
			newBoard[6] = UTTTFactory.createMark(Symbol.EMPTY, 6);
			newBoard[7] = UTTTFactory.createMark(Symbol.EMPTY, 7);
			newBoard[8] = UTTTFactory.createMark(Symbol.EMPTY, 8);

			initialBoard.setMarks(newBoard);
			boards[i] = initialBoard;

		}

		// runs loop to make rest of boards empty
		for (int j = 3; j <= 8; j++) {
			BoardInterface initialBoard = UTTTFactory.createBoard();
			MarkInterface[] newBoard = new MarkInterface[9];

			// Set all marks to EMPTY in rest of boards
			for (int k = 0; k < 9; k++) {
				newBoard[k] = UTTTFactory.createMark(Symbol.EMPTY, j);
			}

			initialBoard.setMarks(newBoard);
			// assign the board to the array
			boards[j] = initialBoard;

		}
		simulator1.setBoards(boards);

		// asserts equal
		assertEquals(Symbol.CIRCLE, simulator1.getWinner());
	}
}
