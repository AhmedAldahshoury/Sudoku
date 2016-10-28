import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SudokuProcessor {
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

	public static void main(String[] args) {

		SudokuReader sr = new SudokuReader();
		SudokuProcessor sp = new SudokuProcessor();

		try {
			short[][] numbers = sr.read("1.sud");

			sp.print(numbers);

//			EmptySquareIterator it = new EmptySquareIterator(sr.emptySquares);

			// while(it.hasNext()) {
			// it.next().print();
			// System.out.println(sr.emptySquares.get(sr.emptySquares.size() -
			// 1) == it.next());
			// }

			//
			// sp.nextState(numbers, 0, 1, 1);
			// sp.print(numbers);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
