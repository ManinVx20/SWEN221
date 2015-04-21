package assignment4.shapes;

public class Interpreter {
    private String input;
    private int index;

    public Interpreter(String input) {
    	this.input = input;
    	this.index = 0;
    }

    /**
     * This method should return a canvas to which the input commands have been applied.
     * @return a canvas that shows the result of the input.
     */
	public Canvas run() {
		Canvas canvas = new Canvas();
		while (index < input.length()) {
			evaluateNextCommand(canvas);
        }
		return canvas;
 	}

	private void evaluateNextCommand(Canvas canvas) {
		skipWhiteSpace();
		String cmd = readWord();
		skipWhiteSpace();
		if (cmd.equals("fill")) {
			Shape shape = readShape();
			Color color = readColor();
			fillShape(shape, color, canvas);
		}
	}

    private String readWord() {
        int start = index;
        while(index < input.length() && Character.isLetter(input.charAt(index))) {
                index++;
        }
        return input.substring(start,index);
    }

    private void skipWhiteSpace() {         
        while (index < input.length()
                        && (input.charAt(index) == ' ' || input.charAt(index) == '\n')) {
                index = index + 1;
        }
    }       
}
