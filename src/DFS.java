
public class DFS {

	SudokuProcessor processor;
	short[][] sudoku;

	public DFS(SudokuProcessor processor, short[][] sudoku) {
		this.processor = processor;
		this.sudoku = this.processor.clone(sudoku);
	}

	public SearchResult search(SudokuState state) {
		
		if (!this.processor.hasEmptySquares(state.getCurrentSquare())) {
			return new SearchResult(state.getSudoku(), processor.isValid(state.getSudoku()));
		}

		GridSquare square = this.processor.nextEmptySquare(state.getCurrentSquare());
		for (short i = 1; i <= 9; i++) {
			
			short[][] nextSudoku = this.processor.clone(state.getSudoku());
			SudokuState nextState = new SudokuState(nextSudoku, square);
			
			nextSudoku[square.getRow()][square.getColumn()] = i;

			SearchResult search = search(nextState);
			if (search.getResult()) {
				return new SearchResult(search.getSudoku(), true);
			}
		}

		return new SearchResult(state.getSudoku(), false);
	}

}
