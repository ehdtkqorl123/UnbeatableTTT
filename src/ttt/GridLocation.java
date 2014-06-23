package ttt;

/**
 * Sets the properties for a cell
 */
public class GridLocation {
	CellType cellType;
	int row, col;

	public GridLocation(int row, int col) {
		this.row = row;
		this.col = col;
		clear();
	}

	void clear() {
		cellType = CellType.EMPTY;
	}

	/**
	 * Print cell with appropriate type
	 */
	void drawCell() {
		switch (cellType) {
		case CROSS:
			System.out.print(" X ");
			break;
		case CIRCLE:
			System.out.print(" O ");
			break;
		case EMPTY:
			System.out.print("   ");
			break;
		}
	}
}