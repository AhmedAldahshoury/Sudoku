import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {

	SudokuProcessor processor;
	short[][] sudoku;
	Queue<SudokuState> unVisited = new LinkedList<SudokuState>();

	public BFS(SudokuProcessor processor, short[][] sudoku, ArrayList<GridSquare> emptySquares) {
		this.processor = processor;
		this.sudoku = processor.clone(sudoku);
		
		this.unVisited.add(new SudokuState(this.sudoku, null, emptySquares));
	}

	public SearchResult search() {
		
		System.out.println(unVisited.size());
		
		SudokuState state = unVisited.poll();
		
		if (state == null) {
			return new SearchResult(null, false);
		}

		GridSquare square = state.getCurrentSquare();

		if (!this.processor.hasEmptySquares(state)) {
			if (this.processor.isValid(state.getSudoku())) {
				return new SearchResult(state.getSudoku(), true);
			}
		} 
		else {
			square = this.processor.nextEmptySquare(state, false);
			for (short i = 1; i <= 9; i++) {
				short[][] nextSudoku = this.processor.clone(state.getSudoku());
				nextSudoku[square.getRow()][square.getColumn()] = i;
				ArrayList<GridSquare> nextEmptySquares = this.processor.nextEmptySquares(state, false);
				unVisited.add(new SudokuState(nextSudoku, square, nextEmptySquares));
			}
		}

		return search();
	}
}
