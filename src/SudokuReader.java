import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SudokuReader {
	
	public static void main(String[] args) {
		SudokuReader sr = new SudokuReader();
		
			short[][] numbers = null;
			try {
				numbers = sr.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			for (int j = 0; j < numbers.length; j++) {
				for (int j2 = 0; j2 < numbers[j].length; j2++) {
					System.out.print(numbers[j][j2] + " ");
				}
				System.out.println();
			}
	}
	
	public short[][] read() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("/Users/ahmedabadie/Desktop/1.sud"));
		short[][] numbers = new short[9][9];
		int i = 0;
		for (String line : lines) {
			String[] characters = line.replace("*", "0").split(" ");
			for (int j = 0; j < characters.length; j++) {
				numbers[i][j] = Short.parseShort(characters[j]);
			}
			++i;
		}
		return numbers;
	}

}
