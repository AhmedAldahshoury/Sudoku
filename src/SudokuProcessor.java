import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SudokuProcessor {

	public SudokuProcessor() {
	}

	public boolean hasEmptySquares(SudokuState state) {
		return state.getEmptySquares() != null && state.getEmptySquares().size() > 0;
	}

	private GridSquare nextEmptySquare(SudokuState state) {
		return state.getEmptySquares().get(0);
	}

	private GridSquare nextMostConstrainedSquare(SudokuState state) {
		GridSquare mostConstrainedSquare = state.getEmptySquares().get(0);
		short[][] sudoku = state.getSudoku();

		int minimumDomainLength = getPossibleValues(sudoku, mostConstrainedSquare, true).length;

		for (GridSquare square : state.getEmptySquares()) {
			int domainLength = getPossibleValues(sudoku, square, true).length;
			if (domainLength < minimumDomainLength) {
				minimumDomainLength = domainLength;
				mostConstrainedSquare = square;
			}
		}

		return mostConstrainedSquare;
	}

	public GridSquare nextEmptySquare(SudokuState state, boolean forwardChecking) {
		if (forwardChecking) {
			return nextMostConstrainedSquare(state);
		}

		return nextEmptySquare(state);
	}

	public ArrayList<GridSquare> nextEmptySquares(SudokuState state, boolean forwardChecking) {
		ArrayList<GridSquare> clone = clone(state.getEmptySquares());

		if (forwardChecking) {
			clone.remove(nextMostConstrainedSquare(state));
			return clone;
		}
		clone.remove(0);
		return clone;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------

	public Short[] getPossibleValues(short[][] sudoku, GridSquare square, boolean forwardChecking) {

		Set<Short> possibleValues = new HashSet<Short>();
		for (short i = 0; i < 9; i++) {
			possibleValues.add((short) (i + 1));
		}

		if (!forwardChecking) {
			return possibleValues.toArray(new Short[0]);
		}

		int x = square.getRow();
		int y = square.getColumn();

		boolean eights = false;
		boolean nines = false;

		for (int i = 0; i < sudoku[x].length; i++) {
			if (sudoku[x][i] != 0) {
				possibleValues.remove(sudoku[x][i]);
			}

			if (sudoku[x][i] == 8) {
				eights = true;
			} else if (sudoku[x][i] == 9) {
				nines = true;
			}
		}

		for (int i = 0; i < sudoku.length; i++) {
			if (sudoku[i][y] != 0) {
				possibleValues.remove(sudoku[i][y]);
			}

			if (sudoku[x][i] == 8) {
				eights = true;
			} else if (sudoku[x][i] == 9) {
				nines = true;
			}
		}

		int calculatedX = (x / 3) * 3;
		int calculatedY = (y / 3) * 3;

		for (int i = calculatedX; i < calculatedX + 3; i++) {
			for (int j = calculatedY; j < calculatedY + 3; j++) {
				if (sudoku[i][j] != 0) {
					possibleValues.remove(sudoku[i][j]);
				}

				if (sudoku[i][j] == 8) {
					eights = true;
				} else if (sudoku[i][j] == 9) {
					nines = true;
				}
			}
		}

		if (eights && !nines) {
			possibleValues.add((short) 8);
		} else if (!eights && nines) {
			possibleValues.add((short) 9);
		}

		return possibleValues.toArray(new Short[0]);
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

	public void print(short[][] sudoku) {
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku[i].length; j++) {
				System.out.print(sudoku[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void changes(short[][] fin, ArrayList<GridSquare> squares) {
		for (GridSquare square : squares) {
			System.out.println(
					square.getRow() + " " + square.getColumn() + " " + fin[square.getRow()][square.getColumn()]);
		}
	}

	public short[][] clone(short[][] sudoku) {
		short[][] clone = new short[sudoku.length][];
		for (int i = 0; i < sudoku.length; i++)
			clone[i] = sudoku[i].clone();
		return clone;
	}

	public ArrayList<GridSquare> clone(ArrayList<GridSquare> squares) {
		ArrayList<GridSquare> clone = new ArrayList<GridSquare>(squares.size());
		for (GridSquare square : squares) {
			clone.add((GridSquare) square.clone());
		}
		return clone;
	}
}
