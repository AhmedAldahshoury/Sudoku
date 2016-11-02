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
			System.out.println("Please add test files in (/bin/sudokus) before proceeding.");
			return;
		}

		/**
		 * The file name that should be present in /bin/sudokus. The directory
		 * /bin/sudokus should be automatically created.
		 */
		String fileName = "3";
		short[][] sudoku = sf.read(fileName);

		// Start of DFS
		/**
		 * Please note that [forwardChecking] and [arcConsistent] cannot be both
		 * set true. However, they both can be set to false to fall back to the
		 * normal DFS.
		 * 
		 * They are mutually exclusive.
		 */
		boolean forwardChecking = false; // set to true to enable forward
											// checking.
		boolean arcConsistent = true; // set to true to enable arc consistency

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
		// End of DFS

		// Start of BFS
		BFS bfs = new BFS(processor, sudoku, processor.clone(sf.emptySquares));
		SearchResult bfSearch = bfs.search();
		if (bfSearch.getResult()) {
			System.out.println("Solution found.");
			processor.changes(bfSearch.getSudoku(), sf.emptySquares);
			sf.write(fileName, sudoku, bfSearch.getSudoku(), null);
		} else {
			System.out.println("No solution found.");
		}
		// End of BFS

		/**
		 * After running any of the algorithms, please check /bin/sudokus for
		 * the corresponding .sol file which contains the original Sudoku, the
		 * final (solved) Sudoku, and the placements each algorithm performed.
		 */
	}

}
