import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ArcConsistency {

	SudokuProcessor processor;
	short[][] sudoku;
	public ArrayList<GridSquare> emptySquares = new ArrayList<>();

	public ArcConsistency(SudokuProcessor processor, short[][] sudoku, ArrayList<GridSquare> emptySquares) {
		this.processor = processor;
		this.sudoku = processor.clone(sudoku);
		this.emptySquares = emptySquares;
		for (int i = 0; i < emptySquares.size(); i++) {
			GridSquare e = emptySquares.get(i);
			GridSquare s = new GridSquare(e.getRow(), e.getColumn());
			e.setDomain(getPossibleValues(sudoku, s, true));

			int rowE = e.getRow();
			int columnE = e.getColumn();
			for (int j = 0; j < emptySquares.size(); j++) {
				GridSquare g = emptySquares.get(j);
				int rowG = g.getRow();
				int columnG = g.getColumn();
				if (rowE == rowG && columnE == columnG)
					continue;
				else if ((rowG == rowE && columnG != columnE) || (rowG != rowE && columnG == columnE)
						|| (((rowG / 3) * 3) == ((rowE / 3) * 3) && ((columnG / 3) * 3) == ((columnE / 3) * 3))) {
					e.addConstraint(g);
				}
			}
		}
	}

	public ArrayList<GridSquare> search() {
		for (int i = 0; i < emptySquares.size(); i++) {
			arcCons(emptySquares.get(i));
		}

		return emptySquares;
	}

	public static void arcCons(GridSquare e) {
		Short[] domainBefore = e.getDomain(null, null, false, true).clone();
		pruneDomain(e);
		Short[] domainAfter = e.getDomain(null, null, false, true);
		if (Arrays.equals(domainBefore, domainAfter))
			return;
		else {
			ArrayList<GridSquare> constraints = e.getConstraints();
			for (int i = 0; i < constraints.size(); i++) {
				arcCons(constraints.get(i));
			}
		}
	}

	public static void pruneDomain(GridSquare e) {
		Short[] domainE = e.getDomain(null, null, false, true);

		for (int i = 0; i < e.getConstraints().size(); i++) {
			GridSquare g = e.getConstraints().get(i);
			Short[] domainG = g.getDomain(null, null, false, true);
			if (domainG.length == 1) {
				for (int j = 0; j < domainE.length; j++) {
					if (domainE[j] == domainG[0] && domainE[j] != 8 && domainE[j] != 9)
						domainE[j] = -1;
				}
			}
		}
	}

	public ArrayList<GridSquare> getEmptyElements(short[][] sudoku) {
		emptySquares = new ArrayList<>();
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku[i].length; j++) {
				if (sudoku[i][j] == 0)
					emptySquares.add(new GridSquare(i, j));
			}
		}
		return emptySquares;
	}

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

}
