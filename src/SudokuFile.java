import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuFile {

	ArrayList<GridSquare> emptySquares = new ArrayList<>();

	public short[][] read(String fileName) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(this.sudokuPath() + fileName + ".sud"));
		short[][] numbers = new short[9][9];
		int i = 0;
		for (String line : lines) {
			String[] characters = line.replace("*", "0").split(" ");
			for (int j = 0; j < characters.length; j++) {
				numbers[i][j] = Short.parseShort(characters[j]);
				if (numbers[i][j] == 0) {
					this.emptySquares.add(new GridSquare(i, j));
				}
			}
			++i;
		}
		return numbers;
	}
	
	public void write(String fileName, short[][] originalSudoku, short[][] finalSudoku, ArrayList<String> placements) {
		List<String> lines = new ArrayList<>();
		lines.add("Original Sudoku");
		
		String[] rows = new String[originalSudoku.length];
		for (int i = 0; i < originalSudoku.length; i++) {
			rows[i] = "";
			for (int j = 0; j < originalSudoku[i].length; j++) {
				if (originalSudoku[i][j] == 0) {
					rows[i] += "*" + " ";
				} else {
					rows[i] += originalSudoku[i][j] + " ";
				}
			}
		}
		lines.addAll(Arrays.asList(rows));
		
		lines.add("");
		lines.add("Final Sudoku");
		for (int i = 0; i < finalSudoku.length; i++) {
			rows[i] = "";
			for (int j = 0; j < finalSudoku[i].length; j++) {
				rows[i] += finalSudoku[i][j] + " ";
			}
		}
		lines.addAll(Arrays.asList(rows));
		
		lines.add("");
		lines.add("Placements");
		if (placements == null || placements.size() == 0) {
			for (GridSquare square : emptySquares) {
				lines.add(square.getRow() + " " + square.getColumn() + " " + finalSudoku[square.getRow()][square.getColumn()]);
			}
		} else {
			lines.addAll(placements);
		}
		
		Path file = Paths.get(sudokuPath() + fileName + ".sol");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return The system's absolute path on disk.
	 */
	public String sudokuPath() {
		try {
			String path = URLDecoder.decode(ClassLoader.getSystemClassLoader().getResource(".").getPath(), "UTF-8")
					+ "sudokus/";
			if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
				return path.substring(1);
			}
			
			return path;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
