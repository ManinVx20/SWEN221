package assignment4.shapes;

public class MainTest {

	public static void main(String[] args) {
		Interpreter interp = new Interpreter("x = [20,20,40,40]\ny = [0,0,30,30]\nz = [30,0,60,30]\ny = y + x + z + x + x +y\ndraw y #00ff00\n");
		Canvas canvas = interp.run();
           canvas.show();
     } 
}
