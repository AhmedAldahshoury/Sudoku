
public class SearchResult {

	private short[][] sudoku;
	private boolean result;

	public SearchResult(short[][] sudoku, boolean result) {
		this.sudoku = sudoku;
		this.result = result;
	}

	public short[][] getSudoku() {
		return this.sudoku;
	}

	public boolean getResult() {
		return this.result;
	}

}
