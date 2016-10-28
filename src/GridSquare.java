
public class GridSquare {
	private int row, column;
	
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
	
	public void print() {
		System.out.println("row: " + this.row + " ------ column: " + this.column);
	}
}
