import java.util.ArrayList;

public class SudokuState {
	private short[][] sudoku;
	private GridSquare currentSquare;
	private ArrayList<GridSquare> emptySquares;

	public SudokuState(short[][] sudoku, GridSquare currentSquare, ArrayList<GridSquare> emptySquares) {
		this.sudoku = sudoku;
		this.currentSquare = currentSquare;
		this.emptySquares = emptySquares;
	}
	
	public short[][] getSudoku() {
		return sudoku;
	}
	
	public GridSquare getCurrentSquare() {
		return currentSquare;
	}
	
	public ArrayList<GridSquare> getEmptySquares() {
		return this.emptySquares;
	}
}
