public class DFS {

	SudokuProcessor processor;
	short[][] sudoku;

	public DFS(SudokuProcessor processor, short[][] sudoku) {
		this.processor = processor;
		this.sudoku = sudoku;
	}

	public boolean search(GridSquare currentSquare) {
		if (!this.processor.hasEmptySquares(currentSquare)) {
			return processor.isValid(this.sudoku);
		}
		
		boolean forwardChecking = false;

		GridSquare square = this.processor.nextEmptySquare(currentSquare, forwardChecking);
		Short[] possibleValues = this.processor.getPossibleValues(this.sudoku, square, forwardChecking);
		
		for (int i = 0; i < possibleValues.length; i++) {
			this.processor.nextState(this.sudoku, square.getRow(), square.getColumn(), possibleValues[i]);

			if (search(square)) {
				return true;
			}
		}

		return search(square);
	}

}
