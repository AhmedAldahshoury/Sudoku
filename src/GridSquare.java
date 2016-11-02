import java.util.ArrayList;

public class GridSquare implements Cloneable, Comparable<GridSquare> {
	private int row, column;
	private Short[] domain ;
	private ArrayList<GridSquare> constraints = new ArrayList<>();
	
	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public GridSquare(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public void setDomain(Short[] domain){
		this.domain = domain.clone();
	}
	
	public Short[] getDomain(){
		return domain;
	}
	
	public ArrayList<GridSquare> getConstraints(){
		return constraints;
	}
	
	public void setConstraints(ArrayList<GridSquare> constraints){
		this.constraints = constraints;
	}
	
	public void addConstraint(GridSquare e){
		this.constraints.add(e);
	}
	
	public void print() {
		System.out.println("row: " + this.row + " ------ column: " + this.column);
	}
	
	public GridSquare clone() {
        try {
			return (GridSquare) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
        
        return null;
    }
	
	public int compareTo(GridSquare square) {
		if (this.row == square.getRow() && this.column == square.getColumn()) {
			return 0;
		}
		return -1;
	}
	
	public boolean equals(Object o) {
		GridSquare square = (GridSquare) o;
		return this.compareTo(square) == 0;
		
	}
}
