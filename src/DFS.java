import java.util.ArrayList;

public class DFS {

	SudokuProcessor processor;
	short[][] sudoku;
	boolean forwardChecking;

	public DFS(SudokuProcessor processor, short[][] sudoku, boolean forwardChecking) {
		this.processor = processor;
		this.sudoku = this.processor.clone(sudoku);
		this.forwardChecking = forwardChecking;
	}

	public SearchResult search(SudokuState state) {

		if (!this.processor.hasEmptySquares(state)) {
			return new SearchResult(state.getSudoku(), processor.isValid(state.getSudoku()));
		}

		GridSquare square = this.processor.nextEmptySquare(state, this.forwardChecking);
		Short[] possibleValues = processor.getPossibleValues(state.getSudoku(), square, this.forwardChecking);

		for (short i = 0; i < possibleValues.length; i++) {

			short[][] nextSudoku = this.processor.clone(state.getSudoku());
			ArrayList<GridSquare> nextEmptySquares = this.processor.nextEmptySquares(state, this.forwardChecking);
			SudokuState nextState = new SudokuState(nextSudoku, square, nextEmptySquares);

			nextSudoku[square.getRow()][square.getColumn()] = possibleValues[i];

			SearchResult search = search(nextState);
			if (search.getResult()) {
				System.out.println(square.getRow() + " " + square.getColumn() + " " + possibleValues[i]);
				return new SearchResult(search.getSudoku(), true);
			}
		}

		return new SearchResult(state.getSudoku(), false);
	}

}
