import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SudokuReader {

	ArrayList<GridSquare> emptySquares = new ArrayList<>();

	public short[][] read(String sudoku) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(this.sudokuPath() + sudoku));
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

	/**
	 * 
	 * @return The system's absolute path on disk.
	 */
	public String sudokuPath() {
		try {
			return URLDecoder.decode(ClassLoader.getSystemClassLoader().getResource(".").getPath(), "UTF-8")
					+ "sudokus/";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
