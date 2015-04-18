package assignment4.shapes;

import java.util.*;

public class Interpreter {

	private int index;
	private Map<String, Shape> shapes;
	private List<String> cmdList;
	private String[] lines;

	public Interpreter(String input) {

		// this.input = input;
		this.shapes = new HashMap<String, Shape>();
		lines = input.split("\n");
		// for (String line : lines) {
		// System.out.println(line);
		// }
		// System.out.println("-----------------------");
	}

	/**
	 * This method tokenizes and cleans up from white-spaces an array list which is then used to evaluate each cmd
	 * 
	 * @return a canvas that shows the result of the input.
	 */
	public Canvas run() {

		Canvas canvas = new Canvas();
		for (String line : lines) {
			index = 0;
			cmdList = tokenizeLine(line);
			evaluateLine(canvas);
		}
		return canvas;
	}

	/**
	 * This methods tokenizes the commands parsed in each line, cleans them from white spaces and assumes a logic for which the elements contains in the array should be part of the same string
	 * 
	 * @param line
	 * @return a clean list of commands
	 */
	private List<String> tokenizeLine(String line) {

		List<String> tokenList = new ArrayList<String>();
		List<String> tokenListClean = new ArrayList<String>();

		// tokenize the elements in each line and adds the to a list of cmd
		StringTokenizer tokens = new StringTokenizer(line, " =+-[]&()", true);
		while (tokens.hasMoreElements()) {
			String token = tokens.nextToken().trim();
			if (!token.isEmpty()) {
				tokenList.add(token);
			}
		}

		// reformats the array conained in the square brakets as one entry
		int index = 0;
		int subIndx = 0;
		while (index < tokenList.size()) {
			if (tokenList.get(index).equals("[")) {
				tokenListClean.add(tokenList.get(index)); // adds [
				subIndx = index + 1;
				String arrayString = tokenList.get(subIndx); // add first element of array
				while (!tokenList.get(++subIndx).equals("]")) {
					arrayString = arrayString.concat(tokenList.get(subIndx));
				}
				tokenListClean.add(arrayString);
				index = subIndx;
			} else {
				tokenListClean.add(tokenList.get(index));
				index++;
			}
		}
		return tokenListClean;
	}

	private void evaluateLine(Canvas canvas) {

		if (cmdList.get(0).equals("fill")) { // if is a filled shape

			if (cmdList.get(1).equals("[") && cmdList.get(3).equals("]")) {
				index = 1;
				Shape shape = readShape();
				Color color = readColor();
				fillShape(shape, color, canvas); // draw the canvas
				index = 4; // move the pointer
			} else if (shapes.containsKey(cmdList.get(1))) { // fill is assigned to a variable
				// System.out.println("Reading fill form a variable");
				String var = cmdList.get(1); // read variable
				index = 2;
				Color color = readColor();
				fillShape(shapes.get(var), color, canvas);// fillShape to variable
				index = 3;
			} else {
				throw new IllegalArgumentException();
			}
		}

		else if (cmdList.get(0).equals("draw")) { // if is a draw line shape
			if (cmdList.get(1).equals("[") && cmdList.get(3).equals("]")) {
				index = 1;
				Shape shape = readShape();
				Color color = readColor();
				drawShape(shape, color, canvas); // draw the canvas
				index = 4; // move the pointer
			} else if (shapes.containsKey(cmdList.get(1))) { // fill is assigned to a variable
				String var = cmdList.get(1); // read variable
				index = 2;
				Color color = readColor();
				drawShape(shapes.get(var), color, canvas);// fillShape to variable
				index = 3;
			} else {
				throw new IllegalArgumentException();
			}

		} else if (cmdList.get(0).matches("[a-zA-Z]*") && cmdList.get(1).equals("=")) { // re-declare a new or existing variable

			index = 2;
			Shape newShape = evaluate();
			if (newShape != null) { // if the shape evaluation was successfull reassign the new shape otherwise terminate
				shapes.put(cmdList.get(0), newShape);
			} else
				throw new IllegalArgumentException();
		} else {
			throw new IllegalArgumentException();
		}

	}

	private Shape evaluate() {

		// index here must be the first position past the = sign
		String lookahead = cmdList.get(index);
		Shape newShape = null;
		if (lookahead.equals("(")) {
			newShape = evaluateBracketed();
		} else {
			newShape = readShape();
		}
		if (newShape == null) {
			throw new IllegalArgumentException();
		}

		if (index < cmdList.size() && cmdList.get(index).matches("^[-+&]$")) {
			lookahead = cmdList.get(index);
			if (lookahead.equals("+")) { // attempt union operation
				// System.out.println("Do Shape Union");
				index++;
				newShape = new ShapeUnion(newShape, evaluate()); // updates the current shape
			} else if (lookahead.equals("&")) {
				// System.out.println("Do Shape Intersection");
				index++;
				newShape = new ShapeIntersection(newShape, evaluate());
			} else if (lookahead.equals("-")) {
				// System.out.println("Do Shape Difference");
				index++;
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

		index++; // moves past the "(" char
		Shape shape = evaluate();
		if (index < cmdList.size() && cmdList.get(index).equals(")")) { // make sure there is a closing braket and the index is not outOfBounds
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
	 *            the canvas on which to draw
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
	 * this methods reads a token containing an array and returns a shape. In case the size or format of the array is not valid will throw an illegalArgument excpetion
	 * 
	 * @return return a new shape with the dimensions specified in the brackets
	 */
	private Shape readShape() {

		if (shapes.containsKey(cmdList.get(index))) {
			Shape newShape = shapes.get(cmdList.get(index));
			index++; // advance index cursor
			return newShape;
		} else if (cmdList.get(index).equals("[") && cmdList.get(index + 1).split(",").length == 4 && cmdList.get(index + 2).equals("]")) { // is a new shape
			String[] shapeArray = cmdList.get(index + 1).split(",");
			int x = Integer.parseInt(shapeArray[0]);
			int y = Integer.parseInt(shapeArray[1]);
			int width = Integer.parseInt(shapeArray[2]);
			int heigth = Integer.parseInt(shapeArray[3]);
			if (x >= 0 && y >= 0 && width >= 0 && heigth >= 0) {
				index = index + 3; // advance index cursor past the array's tokens
				return new Rectangle(x, y, width, heigth);
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

		if (cmdList.get(index).charAt(0) == '#' && cmdList.get(index).length() == 7) {
			return new Color(cmdList.get(index));
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Advances the index cursor though all the white spaces in the provided line
	 *
	 * @param line
	 */
	private void skipWhiteSpace(String line) {

		while (index < line.length() && (line.charAt(index) == ' ')) {
			index++;
		}
	}

}
