package ttt;

/**
 * Description:
 * This class stores the grid on which the game is being played. The grid is made up of 9 grid cells. 
 * These cells are stored in an array containing the GridLocation objects.
 */
public class Grid {
	public static final int ROWS = 3;
	public static final int COLS = 3;
	int remainingEmptyCells; // counts the empty cells remaining in the grid
	int currentRow, currentCol; // row/column which has been worked on currently
	GridLocation[][] cell; // stores the cells objects

	public Grid() {
		cell = new GridLocation[ROWS][COLS];
		remainingEmptyCells = ROWS * COLS;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				cell[i][j] = new GridLocation(i, j);
			}
		}
	}

	/**
	 * Initialize the grid with empty values
	 */
	public void init() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				cell[row][col].clear();
			}
		}
	}

	/**
	 * Print out the grid
	 */
	public void drawGrid() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				cell[i][j].drawCell();
				if (j < COLS - 1)
					System.out.print("|");
			}
			System.out.println();
			if (i < ROWS - 1)
				System.out.println("-----------");
		}
	}

	/**
	 * Returns true if there are no more empty cells
	 */
	public boolean isDraw() {
		return remainingEmptyCells == 0;
	}

	/**
	 * Check the winning condition and returns the verdict
	 */
	boolean hasWon(CellType type)
    {               // checking rows for victory
        return (
    		(cell[currentRow][0].cellType == type &&
              cell[currentRow][1].cellType == type &&
              cell[currentRow][2].cellType == type) 
            ||  // checking columns for victory
             (cell[0][currentCol].cellType == type &&
              cell[1][currentCol].cellType == type &&
              cell[2][currentCol].cellType == type)
            ||  // checking the diagonal '\' for win
              (currentRow == currentCol   &&
              cell[0][0].cellType == type &&
              cell[1][1].cellType == type &&
              cell[2][2].cellType == type)
            ||  // checking the diagonal '/' for win
              (currentRow + currentCol == 2 &&
               cell[0][2].cellType == type &&
               cell[1][1].cellType == type &&
               cell[2][0].cellType == type));
    }
}
