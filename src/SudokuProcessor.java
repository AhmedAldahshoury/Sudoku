import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SudokuProcessor {
	public static Set<Short> getPossibleMoves(short[][] grid, int x, int y) {

		Set<Short> possibleValues = new HashSet<Short>();
		for (short i = 0; i < 9; i++) {
			possibleValues.add((short) (i + 1));
		}
		
		boolean eights = false;
		boolean nines = false;
		
		for (int i = 0; i < grid[x].length; i++) {
			if (grid[x][i] != 0) {
				possibleValues.remove(grid[x][i]);
			}
			
			if (grid[x][i] == 8) {
				eights = true;
			} else if (grid[x][i] == 9) {
				nines = true;
			}
		}
		
		for (int i = 0; i < grid.length; i++) {
			if (grid[i][y] != 0) {
				possibleValues.remove(grid[i][y]);
			}
			
			if (grid[x][i] == 8) {
				eights = true;
			} else if (grid[x][i] == 9) {
				nines = true;
			}
		}
		
		int calculatedX = (x/3) * 3;
		int calculatedY = (y/3) * 3;
		
		for(int i = calculatedX; i< calculatedX + 3; i++){
			for(int j = calculatedY; j < calculatedY + 3; j++){
				if (grid[i][j] != 0) {
					possibleValues.remove(grid[i][j]);
				}
				
				if (grid[i][j] == 8) {
					eights = true;
				} else if (grid[i][j] == 9) {
					nines = true;
				}
			}
		}
		
		if (eights && !nines) {
			possibleValues.add((short) 8);
		} else if (!eights && nines) {
			possibleValues.add((short) 9);
		}

		return possibleValues;
	}
    
	public static void main(String[] args) {
		
		SudokuReader sr = new SudokuReader();
		
		try {
			short[][] numbers = sr.read();
						
			System.out.println(getPossibleMoves(numbers, 8, 2));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
