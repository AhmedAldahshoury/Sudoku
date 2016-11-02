import java.io.IOException;

public class Sudoku {

	public static void main(String[] args) throws IOException {
		SudokuFile sr = new SudokuFile();
		SudokuProcessor processor = new SudokuProcessor();

		short[][] sudoku = sr.read("4");

		System.out.println("Original state");
		processor.print(sudoku);

		DFS dfs = new DFS(processor, sudoku, false);
		SearchResult dfSearch = dfs.search(new SudokuState(sudoku, null, processor.clone(sr.emptySquares)));
		if (dfSearch.getResult()) {
			System.out.println("Solution found.");
			processor.print(dfSearch.getSudoku());
			processor.changes(dfSearch.getSudoku(), sr.emptySquares);
			sr.write("4", sudoku, dfSearch.getSudoku());
		} else {
			System.out.println("No solution found.");
		}

		// BFS bfs = new BFS(processor, sudoku,
		// processor.clone(sr.emptySquares));
		// SearchResult bfSearch = bfs.search();
		// if (bfSearch.getResult()) {
		// System.out.println("Solution found.");
		// processor.changes(bfSearch.getSudoku(), sr.emptySquares);
		// } else {
		// System.out.println("No solution found.");
		// }
	}

}
