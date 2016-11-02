import java.util.ArrayList;

public class SearchResult {

	private short[][] sudoku;
	private boolean result;
	private ArrayList<String> placements = new ArrayList<>();

	public SearchResult(short[][] sudoku, boolean result, ArrayList<String> placements) {
		this.sudoku = sudoku;
		this.result = result;
		this.placements = placements;
	}

	public short[][] getSudoku() {
		return this.sudoku;
	}

	public boolean getResult() {
		return this.result;
	}
	
	public ArrayList<String> getPlacements() {
		return this.placements;
	}

}
