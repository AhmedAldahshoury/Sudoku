import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ArcConsistency {
	
	SudokuProcessor processor;
	short[][] sudoku;
	public static ArrayList<GridSquare> emptyElements = new ArrayList<>();
	
	public ArcConsistency(SudokuProcessor processor, short[][] sudoku) {
		this.processor = processor;
		this.sudoku = processor.clone(sudoku);
		getEmptyElements(sudoku);
		for(int i = 0; i<emptyElements.size(); i++){
			GridSquare e = emptyElements.get(i);
			GridSquare s = new GridSquare(e.getRow(), e.getColumn());
			e.setDomain(getPossibleValues(sudoku, s, true));
			System.out.println(Arrays.toString(e.getDomain()));
			
			int rowE = e.getRow();
			int columnE = e.getColumn();			
			for(int j = 0; j <emptyElements.size(); j++){
				GridSquare g = emptyElements.get(j);
				int rowG = g.getRow();
				int columnG = g.getColumn();
				if(rowE == rowG && columnE == columnG)
					continue;
				else if((rowG == rowE && columnG != columnE) || (rowG != rowE && columnG == columnE) || (((rowG / 3) * 3) == ((rowE / 3) * 3) && ((columnG / 3) * 3) == ((columnE / 3) * 3) )){
//					System.out.println(rowE +"  " + columnE);
					e.addConstraint(g);
				}
				}
			}
		}
	
	public /*SearchResult*/void search(){
		for(int i = 0; i < emptyElements.size(); i++){
			arcCons(emptyElements.get(i));
		}
		for(int i = 0; i < emptyElements.size(); i++){
			System.out.println(Arrays.toString(emptyElements.get(i).getDomain()));
		}
	}
	
	public static void arcCons(GridSquare e){
		Short[] domainBefore = e.getDomain().clone();
		pruneDomain(e);
		Short[] domainAfter = e.getDomain();
		if(Arrays.equals(domainBefore, domainAfter))
			return;
		else{
			ArrayList<GridSquare> constraints = e.getConstraints();
			for(int i =0; i < constraints.size(); i++){
				arcCons(constraints.get(i));
			}
		}
	}
	
	public static void pruneDomain(GridSquare e){
		Short[] domainE = e.getDomain();
		//System.out.println(domainE);
		int rowE = e.getRow();
		int columnE = e.getColumn();
//		for(int i =0; i<emptyElements.size(); i++){
//			GridElement g = emptyElements.get(i);
//			Short[] domainG = g.getDomain();
//			int rowG = g.getRow();
//			int columnG = g.getColumn();
//			if(rowE == rowG && columnE == columnG)
//				continue;
//			else if((rowG == rowE && columnG != columnE) || (rowG != rowE && columnG == columnE) || (((rowG / 3) * 3) == ((rowE / 3) * 3) && ((columnG / 3) * 3) == ((columnE / 3) * 3) )){
////				System.out.println(rowG +" "+ columnG + "   " + rowE +" "+ columnE);
//				if(domainG.length == 1){
////					System.out.println(rowG +" "+ columnG + "   " + rowE +" "+ columnE);
//					for(int j = 0; j < domainE.length; j++){
////						System.out.println(domainE[i] +" "+ domainG[0] );
//						if(domainE[j] == domainG[0] && domainE[j] != 8 && domainE[j] != 9)
//							domainE[j] = -1;
//					}
//				}
//			}
//		}
		
		
		
		for(int i = 0; i < e.getConstraints().size();i++){
			GridSquare g = e.getConstraints().get(i);
			Short[] domainG = g.getDomain();
			if(domainG.length == 1){
				for(int j = 0; j < domainE.length; j++){
//				System.out.println(domainE[i] +" "+ domainG[0] );
				if(domainE[j] == domainG[0] && domainE[j] != 8 && domainE[j] != 9)
					domainE[j] = -1;
			}
			}
		}
		
//		System.out.println(Arrays.toString(domainE));
	}
	
	public static ArrayList<GridSquare> getEmptyElements(short[][] sudoku){
		emptyElements = new ArrayList<>();
		for(int i = 0; i<sudoku.length; i++){
			for(int j = 0; j<sudoku[i].length; j++){
				if(sudoku[i][j] == 0)
					emptyElements.add(new GridSquare(i,j));
			}
		}
		return emptyElements;
	}
	
	public Short[] getPossibleValues(short[][] sudoku, GridSquare square, boolean forwardChecking) {

		Set<Short> possibleValues = new HashSet<Short>();
		for (short i = 0; i < 9; i++) {
			possibleValues.add((short) (i + 1));
		}

		if (!forwardChecking) {
			return possibleValues.toArray(new Short[0]);
		}

		int x = square.getRow();
		int y = square.getColumn();

		boolean eights = false;
		boolean nines = false;

		for (int i = 0; i < sudoku[x].length; i++) {
			if (sudoku[x][i] != 0) {
				possibleValues.remove(sudoku[x][i]);
			}

			if (sudoku[x][i] == 8) {
				eights = true;
			} else if (sudoku[x][i] == 9) {
				nines = true;
			}
		}

		for (int i = 0; i < sudoku.length; i++) {
			if (sudoku[i][y] != 0) {
				possibleValues.remove(sudoku[i][y]);
			}

			if (sudoku[x][i] == 8) {
				eights = true;
			} else if (sudoku[x][i] == 9) {
				nines = true;
			}
		}

		int calculatedX = (x / 3) * 3;
		int calculatedY = (y / 3) * 3;

		for (int i = calculatedX; i < calculatedX + 3; i++) {
			for (int j = calculatedY; j < calculatedY + 3; j++) {
				if (sudoku[i][j] != 0) {
					possibleValues.remove(sudoku[i][j]);
				}

				if (sudoku[i][j] == 8) {
					eights = true;
				} else if (sudoku[i][j] == 9) {
					nines = true;
				}
			}
		}

		if (eights && !nines) {
			possibleValues.add((short) 8);
		} else if (!eights && nines) {
			possibleValues.add((short) 9);
		}

		return possibleValues.toArray(new Short[0]);
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}