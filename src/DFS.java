import java.util.ArrayList;
import java.util.Arrays;

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
		
//		processor.print(state.getSudoku());
		
		if (!this.processor.hasEmptySquares(state)) {
			return new SearchResult(state.getSudoku(), processor.isValid(state.getSudoku()));
		}
		
		GridSquare square = this.processor.nextEmptySquare(state, this.forwardChecking);
		Short[] possibleValues = processor.getPossibleValues(state.getSudoku(), square, this.forwardChecking);
		
//		square.print();
//		System.out.println(Arrays.toString(possibleValues));
		
		for (short i = 0; i < possibleValues.length; i++) {
			
			short[][] nextSudoku = this.processor.clone(state.getSudoku());
			ArrayList<GridSquare> nextEmptySquares = this.processor.nextEmptySquares(state, this.forwardChecking);
//			System.out.println("------");
//			System.out.println(state.getEmptySquares().size());
//			System.out.println(nextEmptySquares.size());
//			System.out.println("------");
			SudokuState nextState = new SudokuState(nextSudoku, square, nextEmptySquares);
			
			nextSudoku[square.getRow()][square.getColumn()] = possibleValues[i];

			SearchResult search = search(nextState);
			if (search.getResult()) {
				return new SearchResult(search.getSudoku(), true);
			}
		}

		return new SearchResult(state.getSudoku(), false);
	}

}
