import java.io.IOException;

public class Sudoku {

	public static void main(String[] args) throws IOException {
		SudokuReader sr = new SudokuReader();
		SudokuProcessor processor = new SudokuProcessor(sr.emptySquares);

		short[][] sudoku = sr.read("3.sud");

		processor.print(sudoku);
		DFS dfs = new DFS(processor, sudoku);
		BFS bfs = new BFS(processor, sudoku);

//		if (dfs.search(null)) {
//			processor.print(sudoku);
//		} else {
//			System.out.println("No solution found.");
//		}
		
		SearchResult search = bfs.search();
		if (search.getResult()) {
			processor.print(search.getSudoku());
		} else {
			System.out.println("No solution found.");
		}
	}

}
