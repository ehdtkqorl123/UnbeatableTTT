package ttt;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines and represents the characteristics of the computer and comes up with its moves. 
 * It calculates where to place its symbol and tries to:
 * - win the game
 * - make a fork when given a chance
 * - block the opposite player's fork
 * - stops the opposite player to win 
 */
public class ComputerPlayerMinMax {
	private int rows = Grid.ROWS;
	private int cols = Grid.COLS;

	private GridLocation[][] cells;

	private CellType type;
	private CellType oppositeType;

	public ComputerPlayerMinMax(Grid grid) {
		cells = grid.cell;
	}

	void setType(CellType type) {
		this.type = type;
		this.oppositeType = (type == CellType.CROSS) ? CellType.CIRCLE : CellType.CROSS;
	}

	/**
	 * calls the minMax() and returns the best moves in terms of an array which contains row and column position
	 * @return
	 */
	public int[] move() {
		// looking forward by only 3 levels.
		int[] result = minMax(2, type);
		return new int[] { result[1], result[2] };
	}

	/**
	 * This is the brain of the computer. 
	 * In this method the computer looks ahead 3 levels and calculates the best possible move using a heuristic. 
	 * It makes a decision tree not literally though.
	 */
	private int[] minMax(int depth, CellType player) {

		List<int[]> allMoves = generateMoves();

		// maximizing for computer and minimizing for opposition
		// hence for computer comparison has to be made with a min value and  vice versa for opposition.
		int bestScore = (player == type) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		int currentScore;
		int bestRow = -1, bestCol = -1;

		if (allMoves.isEmpty() || depth == 0)
			bestScore = evaluateScore();
		else {
			// making moves and calculating the score and looking forward
			for (int[] moves : allMoves) {
				cells[moves[0]][moves[1]].cellType = player;

				if (player == type) {
					currentScore = minMax(depth - 1, oppositeType)[0];
					if (currentScore > bestScore) {
						bestScore = currentScore;
						bestRow = moves[0];
						bestCol = moves[1];
					}
				} else {
					currentScore = minMax(depth - 1, type)[0];
					if (currentScore < bestScore) {
						bestScore = currentScore;
						bestRow = moves[0];
						bestCol = moves[1];
					}
				}

				cells[moves[0]][moves[1]].clear();
			}
		}
		return new int[] { bestScore, bestRow, bestCol };
	}

	/**
	 * It evaluates score of the players if they place their symbols at a particular place. It is 
	 * based on a heuristic. The computer's score has to be maximized and opposition's score should
	 * be minimized. The move that gives the optimum score is taken.
	 */
	private int evaluateScore() {
		int score = 0;
		// evaluateLines(int row1, int col1, int row2, int col2, int row3, int col3)
		score += evaluateLines(0, 0, 0, 1, 0, 2); // first row
		score += evaluateLines(1, 0, 1, 1, 1, 2); // second row
		score += evaluateLines(2, 0, 2, 1, 2, 2); // third row
		score += evaluateLines(0, 0, 1, 0, 2, 0); // first column
		score += evaluateLines(0, 1, 1, 1, 2, 1); // second column
		score += evaluateLines(0, 2, 1, 2, 2, 2); // third column
		score += evaluateLines(0, 0, 1, 1, 2, 2); // \ diagonal
		score += evaluateLines(0, 2, 1, 1, 2, 0);// / diagonal

		return score;
	}

	/**
	 * The heuristic evaluation function for the given line of 3 cells
	 * This method is a kind of helper method to the evaluateScore() as it calculates the score of each line in the grid. 
	 * There are total 8 lines.
	 * 
	 * @return +100, +10, +1 for 3-, 2-, 1-in-a-line for computer. -100, -10, -1 for 3-, 2-, 1-in-a-line for opponent. 0 otherwise
	 */
	private int evaluateLines(int row1, int col1, int row2, int col2, int row3, int col3) {
		int score = 0;
		if (cells[row1][col1].cellType == type) {
			score = 1;
		} else if (cells[row1][col1].cellType == oppositeType) {
			score = -1;
		}

		if (cells[row2][col2].cellType == type) {
			if (score == 1) {
				score = 10;
			} else if (score == -1) {
				return 0;
			} else {
				// cell1 is empty
				score = 1;
			}
		} else if (cells[row2][col2].cellType == oppositeType) {
			if (score == -1) {
				score = -10;
			} else if (score == 1) {
				return 0;
			} else {
				// cell1 is empty
				score = -1;
			}
		}

		if (cells[row3][col3].cellType == type) {
			if (score > 0) {
				score *= 10;
			} else if (score < 0) {
				return 0;
			} else {
				// cell1 and cell2 are empty
				score = 1;
			}
		} else if (cells[row3][col3].cellType == oppositeType) {
			if (score < 0) {
				score *= 10;
			} else if (score > 1) {
				return 0;
			} else {
				// cell1 and cell2 are empty
				score = -1;
			}
		}
		return score;
	}

	/**
	 * It generates all the possible moves that the player can take. It evaluates the empty spaces
	 * and makes a list of the positions of those empty spaces and returns this list.
	 * @return
	 */
	private List<int[]> generateMoves() {
		List<int[]> possibleMoves = new ArrayList<int[]>();

		if (hasWon(type) || hasWon(oppositeType))
			return possibleMoves; // empty list, because game is over
		else {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if (cells[i][j].cellType == CellType.EMPTY)
						possibleMoves.add(new int[] { i, j });
				}
			}
			return possibleMoves;
		}

	}

	/**
	 * This checks if the current player has won or not. 
	 */
	boolean hasWon(CellType cellType) {              
        boolean forRows = false, forCols = false, forDiagonals = false;
        
        for(int i=0 ; i<rows ; i++)
        {
            // checking the rows for victory
                if(cells[i][0].cellType == cellType &&
                   cells[i][1].cellType == cellType &&
                   cells[i][2].cellType == cellType)
                    forRows = true;
            // checking the columns for victory
                if(cells[0][i].cellType == cellType &&
                   cells[1][i].cellType == cellType &&
                   cells[2][i].cellType == cellType)
                    forCols = true;
                
                if(forRows || forCols)
                    return true;
        }
        // checking the diagonal '\' for victory
        if((cells[0][0].cellType == cellType &&
            cells[1][1].cellType == cellType &&
            cells[2][2].cellType == cellType)
            ||  // checking the diagonal '/' for victory
           (cells[0][2].cellType == cellType &&
            cells[1][1].cellType == cellType &&
            cells[2][0].cellType == cellType))
            {
                forDiagonals = true;
                return forDiagonals;
            }
        return false;
    }
}
