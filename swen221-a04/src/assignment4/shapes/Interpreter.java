package assignment4.shapes;

import java.util.*;

/**
 * This version of the interpreter not only allows multiple operations between shapes utilising recursion, also, is now implemented so that it will not rely on single lines and white spaces to
 * tokenise the commands. It also ignore the case of the variables and commands, and has an additional logic to allow commands such as fillx (which contain no spaces between command and variable)
 * 
 * @author diego
 * @version 3.0
 *
 */
public class Interpreter {

	/**
	 * {@inheritDoc}
	 */
	private int index;
	private Map<String, Shape> shapes;
	private String input;
	private List<String> keyCmd = new ArrayList<String>();
	private boolean debug = false;

	public Interpreter(String input) {
		this.shapes = new HashMap<String, Shape>();
		this.input = input;
		this.index = 0;
		this.keyCmd.add("fill");
		this.keyCmd.add("draw");
	}

	/**
	 * main method to run the parsing and generate a canvas
	 * 
	 * @return a canvas utilised by awt for drawing the recatngles
	 */
	public Canvas run() {
		Canvas canvas = new Canvas();
		while (index < input.length()) {
			evaluateNextCommand(canvas);
		}
		return canvas;
	}

	/**
	 * This method evaluates any valid command to fill or draw a new rectangle shape. It relies on eavluate method
	 * 
	 * @param canvas
	 *            Takes a canvas to draw on
	 */
	private void evaluateNextCommand(Canvas canvas) {
		skipWhiteSpace();
		String cmd = readWord();
		if (cmd != null) {
			if (cmd.equalsIgnoreCase("fill")) { // if is a filled shape
				if (debug) System.out.println("Phill's command"); // read next word then read shape and color
				Shape shape = readShape(); // try to read a shape
				Color color = readColor();
				fillShape(shape, color, canvas); // fill the canvas
			} else if (cmd.equalsIgnoreCase("draw")) {
				if (debug) System.out.println("Draw's command");
				Shape shape = readShape(); // try to read a shape
				Color color = readColor();
				drawShape(shape, color, canvas); // draw the canvas
			} else if (cmd.matches("[a-zA-Z]*")) {
				if (debug) System.out.println("New variable : " + cmd); // re-declare a new or existing variable
				if (input.charAt(index) == '=') { // check lookahead is '=', then eavluate new shape
					advanceIndex(1);
					Shape newShape = evaluate();
					if (newShape != null) { // if the shape evaluation was successfull reassign the new shape otherwise terminate
						shapes.put(cmd, newShape);
					} else
						throw new IllegalArgumentException();
				}
			}
		} else {
			advanceIndex(1);
		}
	}

	/**
	 * Evaluates the operations between shapes. It uses recursion to solve multiple operators and support bracketing. Entry point of index must be the first position past the '=' sign
	 */
	private Shape evaluate() {
		skipWhiteSpace();
		String lookahead = String.valueOf(input.charAt(index));
		Shape newShape = null;
		if (lookahead.equals("(")) {
			newShape = evaluateBracketed();
		} else {
			newShape = readShape();
		}
		if (newShape == null) {
			throw new IllegalArgumentException();
		}
		skipWhiteSpace();
		if (index < input.length() && String.valueOf(input.charAt(index)).matches("^[-+&]$")) { // if the value of index is either one of : [ ] + - &
			lookahead = String.valueOf(input.charAt(index));
			if (lookahead.equals("+")) { // attempt union operation
				if (debug) System.out.println("Do Shape Union");
				advanceIndex(1);
				newShape = new ShapeUnion(newShape, evaluate()); // updates the current shape
			} else if (lookahead.equals("&")) {
				if (debug) System.out.println("Do Shape Intersection");
				advanceIndex(1);
				newShape = new ShapeIntersection(newShape, evaluate());
			} else if (lookahead.equals("-")) {
				if (debug) System.out.println("Do Shape Difference");
				advanceIndex(1);
				newShape = new ShapeDifference(newShape, evaluate());
			} else {
				throw new IllegalArgumentException();
			}
		}
		return newShape;
	}

	/**
	 * This method recursively evaluates whatever is inside the brackets then returns a shape containing the result
	 * 
	 * @return the new shape
	 */
	private Shape evaluateBracketed() {
		advanceIndex(1); // moves past the "(" char
		Shape shape = evaluate();
		if (index < input.length() && input.charAt(index) == ')') { // make sure there is a closing braket and the index is not outOfBounds
			return shape;
		} else {
			return null;
		}
	}

	/**
	 * Fill shape get boundingbox from shapes then draw on the canvas if contains is true apparently canvas draws in dots
	 * 
	 * @param shape
	 *            the shape to be processed
	 * @param col
	 *            the color of the shape
	 * @param can
	 *            åthe canvas on which to draw
	 */
	private void fillShape(Shape shape, Color col, Canvas can) {
		for (int i = 0; i < shape.boundingBox().getWidth() + shape.boundingBox().getX(); i++) {
			for (int j = 0; j < shape.boundingBox().getHeight() + shape.boundingBox().getY(); j++) {
				if (shape.contains(i, j)) {
					can.draw(i, j, col);
				}
			}
		}
	}

	/**
	 * Draw shape draws an outline of the current shape. The outline is determined by using an horizontal and vertical scanline algorithm
	 * 
	 * @param shape
	 *            the shape to be processed
	 * @param col
	 *            the color of the shape
	 * @param can
	 *            the canvas on which to draw
	 */
	private void drawShape(Shape shape, Color col, Canvas can) {
		for (int j = 0; j < shape.boundingBox().getHeight() + shape.boundingBox().getY(); j++) {
			for (int i = 0; i < shape.boundingBox().getWidth() + shape.boundingBox().getX(); i++) {
				if (shape.contains(i, j) && !shape.contains(i + 1, j) || !shape.contains(i - 1, j) && shape.contains(i, j) || shape.contains(i, j) && !shape.contains(i, j + 1)
						|| !shape.contains(i, j - 1) && shape.contains(i, j)) {
					can.draw(i, j, col);
				}
			}
		}
	}

	/**
	 * Read char sequence of word
	 * 
	 * @return string containing the next word
	 */
	private String readWord() {
		skipWhiteSpace();
		int start = index;
		String word = null;
		while (index < input.length() && Character.isLetter(input.charAt(index))) {
			word = input.substring(start, index + 1); // add 1: silly substring doesn't include the last index
			if (debug) System.out.println("Checking for words..." + word);
			if (keyCmd.contains(word) || shapes.keySet().contains(word)) { // check if is a key word or a variable
				if (debug) System.out.println("Wooha dude, I found a match. Word, bro! : " + word);
				advanceIndex(1); // increases the index to return the position past the end of the word
				skipWhiteSpace();
				return word;
			} else {
				advanceIndex(1);
			}
		}
		skipWhiteSpace();
		return word; // a new word
	}

	/**
	 * this methods reads a token containing an array and returns a shape. In case the size or format of the array is not valid will throw an illegalArgument excpetion
	 * 
	 * @return return a new shape with the dimensions specified in the brackets
	 */
	private Shape readShape() {
		skipWhiteSpace();
		if (input.charAt(index) == '[') { // is a new array
			if (debug) System.out.println("New array shape");
			int startArray = ++index;
			while (index < input.length() && input.charAt(index) != ']') { // advance index cursor to the find ']' then try to split the array in 4
				advanceIndex(1);
			}
			String strArray = input.substring(startArray, index);
			if (strArray.split(",").length == 4) { // check that the array has four entries comma separated
				String[] shapeArray = strArray.split(","); // all good --> convert string into an array, and trim out all the empty spaces
				int x = Integer.parseInt(shapeArray[0].trim());
				int y = Integer.parseInt(shapeArray[1].trim());
				int width = Integer.parseInt(shapeArray[2].trim());
				int heigth = Integer.parseInt(shapeArray[3].trim());
				if (x >= 0 && y >= 0 && width >= 0 && heigth >= 0) {
					if (debug) System.out.println("Successfully created a new shape from array");
					advanceIndex(1); // move the index pointer passed the ]
					return new Rectangle(x, y, width, heigth);
				}
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			String var = readWord(); // read word then check if is a walid word
			if (shapes.containsKey(var)) {
				if (debug) System.out.println("Found a valid shape stored as variable to fill..");
				Shape newShape = shapes.get(var);
				return newShape;
			} else {
				throw new IllegalArgumentException();
			}
		}
		return null;
	}

	/**
	 * This method reads the hexadecimal color starting from the hashtag #
	 * 
	 * @return the Color of the Shape
	 */
	private Color readColor() {
		skipWhiteSpace();
		if (debug) System.out.println("Reading colors ... rainbows and flying unicors");
		String strColor;
		if (debug) System.out.println(input.charAt(index));
		if (input.charAt(index) == '#') { // read the next 6 digits
			int startIndx = index;
			advanceIndex(6);
			strColor = input.substring(startIndx, index + 1); // reads next 6 chars
			if (strColor.matches("^#(?:[0-9a-fA-F]{3}){1,2}$")) { // now check if is a valid hex color
				if (debug) System.out.println("This is a valid HEX color : " + strColor);
				advanceIndex(1);
				return new Color(strColor);
			}
		} else {
			throw new IllegalArgumentException();
		}
		return null; // this bother me: if the color is not valid, I throw an exception, why do I still need to return null here to close the if case ? ??
	}

	/**
	 * This method allows to check if the index is out of bounds, but is not currently used as we rely on falling out of bounds to terminate the while loop in the run method
	 * @param steps
	 */
	private void advanceIndex(int steps) {
		for (int i = 0; i < steps; i++) {
			// if ((index) < (input.length())) {
			index++;
			// }
		}
	}

	private void skipWhiteSpace() {
		while (index < input.length() && (input.charAt(index) == ' ' || input.charAt(index) == '\n')) {
			index = index + 1;
		}
	}
}
