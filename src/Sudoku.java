import java.io.IOException;

public class Sudoku {

	public static void main(String[] args) throws IOException {
		SudokuReader sr = new SudokuReader();
		SudokuProcessor processor = new SudokuProcessor(sr.emptySquares);

		short[][] sudoku = sr.read("4.sud");

		System.out.println("Original state");
		processor.print(sudoku);
		
//		DFS dfs = new DFS(processor, sudoku);
//		BFS bfs = new BFS(processor, sudoku);

//		SearchResult dfSearch = dfs.search(new SudokuState(sudoku, null)); 
//		if (dfSearch.getResult()) {
//			System.out.println("Solution found.");
//			processor.changes(dfSearch.getSudoku(), sr.emptySquares);
//		} else {
//			System.out.println("No solution found.");
//		}
		
//		SearchResult bfSearch = bfs.search();
//		if (bfSearch.getResult()) {
//		System.out.println("Solution found.");
//			processor.changes(bfSearch.getSudoku(), sr.emptySquares);
//		} else {
//			System.out.println("No solution found.");
//		}
	}

}
