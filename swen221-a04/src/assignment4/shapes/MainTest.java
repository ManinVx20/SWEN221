package assignment4.shapes;

public class MainTest {

	public static void main(String[] args) {

		 Interpreter interp = new Interpreter("x = [2,0,5,5]\ny = [0,2,4,5]\ny =  (y-x\nfill y #ff0000\n");
		 Canvas canvas = interp.run();
//		 System.out.print(canvas.toString());
		 canvas.show();

		/*
		String input = "";
		String color = "";
		for (int col = 0; col < 8; col++) {
			for (int row = 0; row < 8; row++) {
				int random = (int) (Math.random() * 6);
				if (random == 0) {
					color = "#458b00";
				} else if (random == 1) {
					color = "#7f9a65";
				} else if (random == 2) {
					color = "#659d32";
				} else if (random == 3) {
					color = "#324f17";
				} else if (random == 4) {
					color = "#c0d9af";
				} else if (random == 5) {
					color = "#397d02";
				} else if (random == 6) {
					color = "#567e3a";
				}
				input = input + "x = [" + row * 10 + "," + col * 10 + ", 10, 10]\n fill x " + color + "\n fill [10,20,20,20] #000000\n" + "fill [50,20,20,20] #000000\n x= [20, 50, 10, 30]\n y = [30, 40, 20, 30]\n"
						+ "z = [50, 50, 10, 30]\n a = x+y+z\nfill a #000000 ";
			}
		}
		System.out.println(input);
		Interpreter i = new Interpreter(input);
		Canvas c = i.run();
		c.show();
	*/
		 
	}
}
