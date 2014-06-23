package ttt;

/**
 * This is the board where the game is played. It 
 * - maintains and update the states
 * - updates the grid
 * - switches the player according to their turns.
 * - handles the moves of the players
 * - decides who will play first based on user input
 */

import java.util.Scanner;

public class TTTBoard {
	private Grid grid; // The grid on which the game will be played
	private GameState currentState; // The current state of the game after every move
	private CellType currentPlayer; // The current player's symbol
	private Scanner input = new Scanner(System.in); // For taking the input from the player about their moves.

	public TTTBoard() {
		grid = new Grid();
		ComputerPlayerMinMax computer = new ComputerPlayerMinMax(grid);
		computer.setType(CellType.CROSS);
		
		int firstPlayer = -1;
		System.out.println("Your symbol is O and computer's symbol is X.");
		System.out.println("Enter 0 to play first; 1 to let computer play first.");
		try {
			firstPlayer = input.nextInt();
		} catch (Exception e) {
			System.out.println("Invalid input. Please enter either 0 or 1.");
		}
		while (firstPlayer < 0 || firstPlayer > 1) {
			System.out.println("Please enter either 1 or 0.");
			while (!input.hasNextInt()) {
				System.out.println("That's not a number!");
				input.next();
			}
			firstPlayer = input.nextInt();
		}
		
		gameInit(firstPlayer);
		System.out.println("Board grid before starting the game.");
		grid.drawGrid();
		do {
			playerMove(currentPlayer, computer);
			grid.drawGrid();
			updateGameState(currentPlayer);

			if (currentState == GameState.CROSS_WON) {
				System.out.println("COMPUTER WON! TRY AGAIN!");
				System.out.println("============================================");
			}
			else if (currentState == GameState.CIRCLE_WON) {
				System.out.println("YOU WON! GOOD JOB!");
				System.out.println("============================================");
			}
			else if (currentState == GameState.DRAW) {
				System.out.println("DRAW! NICE TRY!");
				System.out.println("============================================");
			}

			currentPlayer = (currentPlayer == CellType.CROSS) ? CellType.CIRCLE : CellType.CROSS;
		}
		// keep on playing until one player wins or the game is drawn
		while (currentState == GameState.PLAYING);
	}

	public GameState getState() {
		return this.currentState;
	}

	private void gameInit(int first) {
		grid.init();
		currentState = GameState.PLAYING;
		if (first == 0)
			currentPlayer = CellType.CIRCLE;
		else
			currentPlayer = CellType.CROSS;
	}

	private void updateGameState(CellType type) {
		if (grid.hasWon(type)) {
			if (type == CellType.CROSS)
				currentState = GameState.CROSS_WON;
			if (type == CellType.CIRCLE)
				currentState = GameState.CIRCLE_WON;
		} else if (grid.isDraw())
			currentState = GameState.DRAW;
	}

	private void playerMove(CellType type, ComputerPlayerMinMax comp) {
		boolean validInput = true;
		do {
			int row = -1, col = -1;

			if (type == CellType.CROSS) {
				int[] computerMoves = comp.move();
				row = computerMoves[0];
				col = computerMoves[1];
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) { }
				System.out.println("Computer placed its " + type + " at row " + (row + 1) + " column " + (col + 1));
			} else if (type == CellType.CIRCLE) {
				System.out.println("Please enter the position to place your " + type
						+ ".\nThe input should be (row[1-3] , column[1-3]) WITHOUT commas, and ONLY SPACES between two digits.");

				try {
					row = input.nextInt() - 1;
				} catch (Exception e) {
					System.out.println("Invalid row input.");
				}
				col = input.nextInt() - 1;
				System.out.println("");
			}

			if (row >= 0 && row < 3 && col >= 0 && col < 3 && grid.cell[row][col].cellType == CellType.EMPTY) {
				grid.cell[row][col].cellType = type;
				grid.currentRow = row;
				grid.currentCol = col;
				grid.remainingEmptyCells--;
				validInput = true;
			}
			else {
				System.out.println("");
				System.out.println("The entered input is invalid. Please enter a valid position.");
				validInput = false;
			}
		} while (!validInput);
	}
}
