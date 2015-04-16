package assignment4.shapes;

public class MainTest {

	public static void main(String[] args) {
		Interpreter interp = new Interpreter("x = [2,2,4,4]\ny = [0,0,3,3]\nz = [3,0,6,3]\ny = y + x + z + x + x +y\ndraw y #00ff00\n");
		Canvas canvas = interp.run();
           canvas.show();
     } 
}
