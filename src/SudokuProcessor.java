import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SudokuProcessor {

	private ArrayList<GridSquare> emptySquares;
	private EmptySquareIterator esi;

	public SudokuProcessor(ArrayList<GridSquare> emptySquares) {
		this.emptySquares = emptySquares;
		this.esi = new EmptySquareIterator(this.emptySquares);
	}

	public boolean hasEmptySquares(GridSquare square) {
		if (square == null) {
			return this.emptySquares.size() > 0;
		}
		GridSquare lastEmptySquare = this.emptySquares.get(this.emptySquares.size() - 1);
		return !(square.getRow() == lastEmptySquare.getRow() && square.getColumn() == lastEmptySquare.getColumn());
	}

	public GridSquare nextEmptySquare(GridSquare currentSquare) {
		this.esi.reset();
		while (this.esi.hasNext() && currentSquare != null) {
			GridSquare square = this.esi.next();
			if (square.getRow() == currentSquare.getRow() && square.getColumn() == currentSquare.getColumn()) {
				break;
			}
		}
		return this.esi.next();
	}

	public EmptySquareIterator getEmptySquares() {
		return this.esi;
	}

	public static Set<Short> getPossibleMoves(short[][] grid, int x, int y) {

		Set<Short> possibleValues = new HashSet<Short>();
		for (short i = 0; i < 9; i++) {
			possibleValues.add((short) (i + 1));
		}

		boolean eights = false;
		boolean nines = false;

		for (int i = 0; i < grid[x].length; i++) {
			if (grid[x][i] != 0) {
				possibleValues.remove(grid[x][i]);
			}

			if (grid[x][i] == 8) {
				eights = true;
			} else if (grid[x][i] == 9) {
				nines = true;
			}
		}

		for (int i = 0; i < grid.length; i++) {
			if (grid[i][y] != 0) {
				possibleValues.remove(grid[i][y]);
			}

			if (grid[x][i] == 8) {
				eights = true;
			} else if (grid[x][i] == 9) {
				nines = true;
			}
		}

		int calculatedX = (x / 3) * 3;
		int calculatedY = (y / 3) * 3;

		for (int i = calculatedX; i < calculatedX + 3; i++) {
			for (int j = calculatedY; j < calculatedY + 3; j++) {
				if (grid[i][j] != 0) {
					possibleValues.remove(grid[i][j]);
				}

				if (grid[i][j] == 8) {
					eights = true;
				} else if (grid[i][j] == 9) {
					nines = true;
				}
			}
		}

		if (eights && !nines) {
			possibleValues.add((short) 8);
		} else if (!eights && nines) {
			possibleValues.add((short) 9);
		}

		return possibleValues;
	}

	public Set<Short> fillSet(int first, int last) {
		Set<Short> possibleValues = new HashSet<Short>();
		for (int i = first; i <= last; i++) {
			possibleValues.add((short) i);
		}
		return possibleValues;
	}

	public boolean isValidRow(short[][] grid, int row) {
		Set<Short> values = fillSet(1, 7);
		for (int i = 0; i < grid[row].length; i++) {
			if (grid[row][i] != 8 && grid[row][i] != 9) {
				if (values.contains(grid[row][i])) {
					values.remove(grid[row][i]);
				} else {
					return false;
				}
			}
		}

		return values.isEmpty();
	}

	public boolean isValidColumn(short[][] grid, int column) {
		Set<Short> values = fillSet(1, 7);
		for (int i = 0; i < grid.length; i++) {
			if (grid[i][column] != 8 && grid[i][column] != 9) {
				if (values.contains(grid[i][column])) {
					values.remove(grid[i][column]);
				} else {
					return false;
				}
			}
		}

		return values.isEmpty();
	}

	public boolean isValidGrid(short[][] grid, int row, int column) {
		Set<Short> values = fillSet(1, 7);

		int calculatedRow = (row / 3) * 3;
		int calculatedColumn = (column / 3) * 3;

		for (int i = calculatedRow; i < calculatedRow + 3; i++) {
			for (int j = calculatedColumn; j < calculatedColumn + 3; j++) {
				if (grid[i][j] != 8 && grid[i][j] != 9) {
					if (values.contains(grid[i][j])) {
						values.remove(grid[i][j]);
					} else {
						return false;
					}
				}
			}
		}

		return values.isEmpty();
	}

	// TODO Optimize
	public boolean isValid(short[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if ((isValidRow(grid, i) && isValidColumn(grid, j) && isValidGrid(grid, i, j)) == false) {
					return false;
				}
			}
		}
		return true;
	}

	public short[][] nextState(short[][] grid, int row, int column, int value) {
		grid[row][column] = (short) value;
		return grid;
	}

	public void print(short[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public short[][] clone(short[][] sudoku) {
		short[][] result = new short[sudoku.length][];
		for (int i = 0; i < sudoku.length; i++)
			result[i] = sudoku[i].clone();
		return result;
	}
}
