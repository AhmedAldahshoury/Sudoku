import java.util.ArrayList;

public class DFS {

	SudokuProcessor processor;
	short[][] sudoku;
	boolean forwardChecking, arcConsistency;
	ArrayList<String> placements = new ArrayList<>();

	public DFS(SudokuProcessor processor, short[][] sudoku, boolean forwardChecking, boolean arcConsistency) {
		this.processor = processor;
		this.sudoku = this.processor.clone(sudoku);
		this.forwardChecking = forwardChecking;
		this.arcConsistency = arcConsistency;
	}

	public SearchResult search(SudokuState state) {

		if (!this.processor.hasEmptySquares(state)) {
			return new SearchResult(state.getSudoku(), processor.isValid(state.getSudoku()), this.placements);
		}

		GridSquare square = this.processor.nextEmptySquare(state, this.forwardChecking, this.arcConsistency);
		Short[] possibleValues = square.getDomain(this.processor, state, this.forwardChecking, this.arcConsistency);

		for (short i = 0; i < possibleValues.length; i++) {

			if (possibleValues[i] == -1) {
				continue;
			}

			short[][] nextSudoku = this.processor.clone(state.getSudoku());
			ArrayList<GridSquare> nextEmptySquares = this.processor.nextEmptySquares(state, this.forwardChecking,
					this.arcConsistency);
			SudokuState nextState = new SudokuState(nextSudoku, square, nextEmptySquares);

			nextSudoku[square.getRow()][square.getColumn()] = possibleValues[i];

			SearchResult search = search(nextState);
			if (search.getResult()) {
//				System.out.println(square.getRow() + " " + square.getColumn() + " " + possibleValues[i]);
				this.placements.add(square.getRow() + " " + square.getColumn() + " " + possibleValues[i]);
				return new SearchResult(search.getSudoku(), true, this.placements);
			}
		}

		return new SearchResult(state.getSudoku(), false, this.placements);
	}

}
