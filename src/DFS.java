
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
		
		processor.print(sudoku);

		GridSquare square = this.processor.nextEmptySquare(currentSquare);
		for (int i = 1; i <= 9; i++) {
			this.processor.nextState(this.sudoku, square.getRow(), square.getColumn(), i);

			if (search(square)) {
				return true;
			}
		}

		return false;
	}

}
