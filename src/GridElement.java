//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Set;
//
//public class GridElement {
//
//	private int row, column;
//	private Short[] domain ;
//	private ArrayList<GridElement> constraints = new ArrayList<GridElement>();
//	
//	public GridElement(int row, int column) {
//		this.row = row;
//		this.column = column;
//	}
//	
//	public int getColumn() {
//		return column;
//	}
//
//	public void setColumn(int column) {
//		this.column = column;
//	}
//
//	public int getRow() {
//		return row;
//	}
//
//	public void setRow(int row) {
//		this.row = row;
//	}
//	
//	public void setDomain(Short[] domain){
//		this.domain = domain.clone();
//	}
//	
//	public Short[] getDomain(){
//		return domain;
//	}
//	
//	public ArrayList<GridElement> getConstraints(){
//		return constraints;
//	}
//	
//	public void setConstraints(ArrayList<GridElement> constraints){
//		this.constraints = constraints;
//	}
//	
//	public void addConstraint(GridElement e){
//		this.constraints.add(e);
//	}
//	
//	public void print() {
//		System.out.println("row: " + this.row + " ------ column: " + this.column);
//	}
//	
//}
