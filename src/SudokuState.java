
public class SudokuState {
	short[][] sudoku;
	GridSquare currentSquare;

	public SudokuState(short[][] sudoku, GridSquare currentSquare) {
		this.sudoku = sudoku;
		this.currentSquare = currentSquare;
	}
	
	public short[][] getSudoku() {
		return sudoku;
	}
	
	public GridSquare getCurrentSquare() {
		return currentSquare;
	}
}
