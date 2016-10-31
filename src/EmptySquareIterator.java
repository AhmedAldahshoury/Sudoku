//import java.util.ArrayList;
//
//public class EmptySquareIterator {
//
//	private int currentIndex;
//	private ArrayList<GridSquare> emptySquares;
//
//	public EmptySquareIterator(ArrayList<GridSquare> emptySquares) {
//		this.emptySquares = emptySquares;
//	}
//
//	public void reset() {
//		this.currentIndex = 0;
//	}
//
//	public boolean hasNext() {
//		return this.emptySquares.size() != 0 && this.currentIndex < this.emptySquares.size();
//	}
//
//	public GridSquare next() {
//		if (hasNext()) {
//			return this.emptySquares.get(this.currentIndex++);
//		}
//		return null;
//	}
//
//}
