import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Sudoku {

	public static void main(String[] args) throws IOException {
		SudokuFile sf = new SudokuFile();
		SudokuProcessor processor = new SudokuProcessor();
		
		if (!(new File(sf.sudokuPath()).exists())) {
			new File(sf.sudokuPath()).mkdir();
			System.out.println("Please add test files in (bin/sudokus) before proceeding.");
			return;
		}

		String fileName = "4";
		short[][] sudoku = sf.read(fileName);

		// Mutually Exclusive
		boolean forwardChecking = true;
		boolean arcConsistent = false;

		ArrayList<GridSquare> arcSqaures;
		if (arcConsistent) {
			ArcConsistency a = new ArcConsistency(processor, sudoku, processor.clone(sf.emptySquares));
			arcSqaures = processor.clone(a.search());
		} else {
			arcSqaures = processor.clone(sf.emptySquares);
		}

		DFS dfs = new DFS(processor, sudoku, forwardChecking, arcConsistent);
		SearchResult dfSearch = dfs.search(new SudokuState(sudoku, null, arcSqaures));
		if (dfSearch.getResult()) {
			System.out.println("Solution found.");
			Collections.reverse(dfSearch.getPlacements());
			sf.write(fileName, sudoku, dfSearch.getSudoku(), dfSearch.getPlacements());
		} else {
			System.out.println("No solution found.");
		}

		// BFS bfs = new BFS(processor, sudoku,
		// processor.clone(sr.emptySquares));
		// SearchResult bfSearch = bfs.search();
		// if (bfSearch.getResult()) {
		// System.out.println("Solution found.");
		// processor.changes(bfSearch.getSudoku(), sr.emptySquares);
		// sr.write(fileName, sudoku, bfSearch.getSudoku(), null);
		// } else {
		// System.out.println("No solution found.");
		// }
	}

}
