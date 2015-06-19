public class Point { 
	
	private int x; private int y;
	
	public Point(int x, int y) { 
		this.x = x;
		this.y = y; 
	}

	@Override
	public boolean equals(Object o){
		if (o != null && (this.getClass() == o.getClass())){
			Point other = (Point) o;
			return (this.x==other.x && this.y==other.y);
		}
		return false;
	}

	public int hashcode(){
		return x*y;
	}

}