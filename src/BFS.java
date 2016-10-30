import java.util.LinkedList;
import java.util.Queue;

public class BFS {

	SudokuProcessor processor;
	short[][] sudoku;
	Queue<short[][]> unVisited = new LinkedList<short[][]>();

	public BFS(SudokuProcessor processor, short[][] sudoku) {
		this.processor = processor;
		this.sudoku = sudoku;
		this.unVisited.add(this.sudoku);
	}

	public boolean search(GridSquare currentSquare) {
		short[][] state = unVisited.poll();
		if (state == null) {
			return false;
		}

		if (!this.processor.hasEmptySquares(currentSquare)) {
			if (this.processor.isValid(this.sudoku)) {
				return true;
			}
		} else {
			GridSquare square = this.processor.nextEmptySquare(currentSquare);
			for (short i = 1; i <= 9; i++) {
				short[][] nextSudoku = this.processor.clone(state);
				nextSudoku[square.getRow()][square.getColumn()] = i;
				unVisited.add(nextSudoku);
			}
		}

		return search(currentSquare);

	}

	public void BFSConstruction(short[][] state) {

	}

}
