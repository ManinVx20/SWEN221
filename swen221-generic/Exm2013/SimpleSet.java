public interface SimpleSet {
	/**
	 * Add a new item into this SimpleSet.  If item is already
	 * stored in this SimpleSet, then this method does nothing.
	 * Otherwise, it stores item in this SimpleSet.
	 */
	public void add(Object item);
	/**
	 * Check whether an object is currently stored in this SimpleSet
	 * which equals() the given item.
	 */
	public boolean contains(Object item); }

public class Point { 
	private int x; private int y;
	public Point(int x, int y) { 
		this.x = x;
		this.y = y; 
	}
}

SimpleSet s = ...;
Point p = new Point(1,1); s.add(p);
if(s.contains(new Point(1,1))) {
	System.out.println("MATCH"); }else{
		System.out.println("NO MATCH");
	}

	