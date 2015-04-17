package assignment4.shapes;

import java.util.*;

public class Interpreter {

	// private String input;
	private int index;
	private Map<String, Shape> shapes;
	private List<String> cmdList;
	private String[] lines;

	// private List<String> tokenListClean = new ArrayList<String>();

	public Interpreter(String input) {

		// this.input = input;
		this.shapes = new HashMap<String, Shape>();
		lines = input.split("\n");
		for (String line : lines) {
			System.out.println(line);
		}
		System.out.println("-----------------------");
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
		StringTokenizer tokens = new StringTokenizer(line, " =+-[]&", true);
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

		// for (String entry : tokenListClean) {
		// System.out.println(entry);
		// }

		return tokenListClean;

	}

	private void evaluateLine(Canvas canvas) {

		if (cmdList.get(0).equals("fill")) { // if is a filled shape
			if (cmdList.get(1).equals("[") && cmdList.get(3).equals("]")) {
				index=1;
				Shape shape = readShape();
				Color color = readColor();
				fillShape(shape, color, canvas); // draw the canvas
				index = 4; // move the pointer
			} else if (shapes.containsKey(cmdList.get(1))) { // fill is assigned to a variable
				System.out.println("Reading fill form a variable");
				String var = cmdList.get(1); // read variable
				index=2;
				Color color = readColor();
				fillShape(shapes.get(var), color, canvas);// fillShape to variable
				index = 3;
			} else {
				throw new IllegalArgumentException();
			}
		}

		else if (cmdList.get(0).equals("draw")) { // if is a draw line shape
			if (cmdList.get(1).equals("[") && cmdList.get(3).equals("]")) {
				index=1;
				Shape shape = readShape();
				Color color = readColor();
				drawShape(shape, color, canvas); // draw the canvas
				index = 4; // move the pointer
			} else if (shapes.containsKey(cmdList.get(1))) { // fill is assigned to a variable
				System.out.println("Reading draw form a variable");
				String var = cmdList.get(1); // read variable
				index=2;
				Color color = readColor();
				drawShape(shapes.get(var), color, canvas);// fillShape to variable
				index = 3;
			} else {
				throw new IllegalArgumentException();
			}

			// } else if (cmdList.get(index).equals("\\(") || cmdList.get(index).equals("\\)")) { // cmd has a round bracket --> for this implementation just remove
			// index++;

		} else if (cmdList.get(0).matches("[a-zA-Z]") && cmdList.get(1).equals("=")) { // re-declare a new or existing variable

			// if (cmdList.get(2).equals("[") && cmdList.get(4).equals("]")) { // shape from array
			// System.out.println("Shape definition from array");
			// String varName = cmdList.get(0); // fetch the new var name
			// Shape shape = readShape(cmdList.get(3));
			// this.shapes.put(varName, shape);
			// index = 5; // move the pointer

			// not needed --> handled in evaluation
			// } else if (cmdList.size() == 3 && shapes.containsKey(cmdList.get(2))) { // special case, variable re assigned to another
			// System.out.println("SPECIAL CASE: shape re assigned");
			// this.shapes.put(cmdList.get(0), shapes.get(cmdList.get(2))); // takes the shape from index 2 of cmdList and assigns it to shape key as index 1
			// index = 2;

			// THIS IS WHERE THE CALCULATOR CODE AND RECURION KICK IN
			// } else if (cmdList.get(2).matches("[a-zA-Z]") || cmdList.get(2).equals("\\(") && cmdList.get(3).matches("^[-+&]$") && cmdList.get(4).matches("[a-zA-Z]")) { // if the expression has an
			// operator

			// if (cmdList.get(2).matches("[a-zA-Z]") || cmdList.get(2).equals("\\(") || cmdList.get(2).equals("[")) { // if the expression has an operator or a parenthesis
			index = 2;
			Shape newShape = evaluate();
			if (newShape != null) { // if the shape evaluation was successfull reassign the new shape otherwise terminate
				shapes.put(cmdList.get(0), newShape);
			} else
				throw new IllegalArgumentException();
			// }

			// } else if (cmdList.get(index + 2).matches("[a-zA-Z]") && cmdList.get(index + 3).matches("^[-+&]$") && cmdList.get(index + 4).matches("[a-zA-Z]")) { // if the expression has an operator
			// // and two operands or more
			// System.out.println(cmdList.get(2));
			// System.out.println(cmdList.get(3));
			// System.out.println(cmdList.get(4));
			// int xtrIndex = index + 5;
			// while (xtrIndex < cmdList.size() && (cmdList.size() - 1) % 2 == 0) { // check if there are more operators and are even
			// System.out.println(cmdList.get(xtrIndex));
			// System.out.println(cmdList.get(xtrIndex + 1));
			// xtrIndex = xtrIndex + 2;
			// }
			// index = xtrIndex;
			// // System.out.println("Expression with operation");
			// }

			// while (index < cmdList.size()) {
			// if (cmdList.get(index).matches("^[a-zA-Z]$") && cmdList.get(index+1).matches("^[-+&]$"))
			//
			// }

			// for (String entry : cmdList.subList(index+2, cmdList.size())) { // for any of the operand entries..
			// if (entry.matches("^[a-zA-Z]$")) { // check if the operand is a variable and its shape exists
			// if (shapes.containsKey(entry)) {
			// System.out.println("SHAPE IS KNOWN");
			// } else {
			// throw new IllegalArgumentException();
			// }
			// } else if (entry.matches("^[-+&]$")) { // check if the operator is legal
			// System.out.println("SHAPE OPERATION VALID");
			// } else {
			// throw new IllegalArgumentException();
			// }
			// }
			// }
			// } else if (shapes.containsKey(cmdList.get(index+2) ) {

			// for items in expression if there are brackets do those first, then do * and / and then to sums

			// } else {
			// throw new IllegalArgumentException();
			// }
			//
			// } else if (shapes.containsKey(cmdList.get(index))) {// if the shape already exists
			// System.out.println("Shape already exists, redefining...");
			// } else if (shapes.containsKey(cmdList.get(index)) && cmdList.get(index+1).equals("[")) { // new shape with array
			// System.out.println("New shape from array");
			// } else { // new shape from combination of pre-existing shapes
			// System.out.println("New shape from pre-existing shapes");
			// }

			// String var = cmdList.get(index+2); // fetch and store the variable name
			// System.out.println(cmdList.get(index+2));
			// if (cmdList.get(index+1).equals("+")) { // attempt union operation
			// System.out.println("Do Union");
			// System.out.println(var);
			// // this.doBoolean("+", cmd, line); // updates the current shape
			// // index = line.length(); // move pointer to end of line
			// // index++;
			// } else if (cmdList.get(index+1).equals("&")) { // attempt intersection operation
			// System.out.println("Do Intersection");
			// // this.doBoolean("&", cmd, line);
			// // index = line.length(); // move pointer to end of line
			// // index++;
			// } else if (cmdList.get(index+1).equals("-")) { // attempt difference operation
			// System.out.println("Do Difference");
			// // this.doBoolean("-", cmd, line);
			// // index = line.length(); // move pointer to end of line
			// // index++;
			// } else if (cmdList.get(index+1).matches(".*\\[(.*?)\\].*")) { // re-define the shape array
			// System.out.println("Redefine shape array ??");
			// // String shapeName = cmd;
			// // Shape shape = readShape(line);
			// // this.shapes.put(shapeName, shape);
			// // index = line.length(); // move pointer to end of line
			// // index++;
			// } else {
			// throw new IllegalArgumentException();
			// }

			// } else if (cmdList.get(index).matches("^[a-zA-Z]$") && !shapes.containsKey(cmdList.get(index))) { // cmd matches an alphanumeric char, but is not in the cmd list --> is a new shape
			// String var = cmdList.get(index); // fetch the var name
			// if (cmdList.get(index+1).equals("=") && cmdList.get(index+2).equals("[") && cmdList.get(index+4).equals("]")) {
			// Shape shape = readShape(cmdList.get(index+3));
			// this.shapes.put(var, shape);
			// index = index + 4; // move the pointer
			// } else {
			// throw new IllegalArgumentException();
			// }

		} else {
			throw new IllegalArgumentException();
		}

	}

	// }

	private Shape evaluate() {

		// index is the first position past the = sign
		String lookahead = cmdList.get(index);
		System.out.println(lookahead);
		Shape newShape = null;

		if (lookahead == "(") {
			System.out.println("(");
			// newShape = evaluateBracketed();
			// } else if (shapes.containsKey(cmdList.get(index)) && cmdList.get(index+1).matches("^[-+&]$") && shapes.containsKey(cmdList.get(index+2))) {
			// } else if (shapes.containsKey(cmdList.get(index))) {
		} else {
			newShape = readShape();
			System.out.println("READ SHAPE");
		}
		if (newShape == null) {
			throw new IllegalArgumentException();
		}

		// now the lookahead has been increased by the read shape and we expecting an operator
		System.out.println("OPERATION");
		System.out.println(index < cmdList.size());
		if (index < cmdList.size() && cmdList.get(index).matches("^[-+&]$")) {
			System.out.println(cmdList.get(index));
			lookahead = cmdList.get(index);
			if (lookahead.equals("+")) { // attempt union operation
				System.out.println("Do Shape Union");
				index++;
				newShape = new ShapeUnion(newShape, evaluate()); // updates the current shape
			} else if (lookahead.equals("&")) {
				System.out.println("Do Shape Intersection");
				index++;
				newShape = new ShapeIntersection(newShape, evaluate());
			} else if (lookahead.equals("-")) {
				System.out.println("Do Shape Difference");
				index++;
				newShape = new ShapeDifference(newShape, evaluate());
			} else {
				throw new IllegalArgumentException();
			}
		}
		return newShape;
	}

	private Shape evaluateBracketed() {

		// TODO Auto-generated method stub
		return null;
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
				} else {
					if (can.width() < i - 1 && can.height() < j - 1) {
						can.draw(i, j, can.colorAt(i, j));
					}
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

		System.out.println(cmdList.get(index));
		if (cmdList.get(index).charAt(0) == '#' && cmdList.get(index).length() == 7) {
			return new Color(cmdList.get(index));
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Read the next word in the provided line (stops when encounters a white space)
	 * 
	 * @param line
	 * @return
	 */
	private String readWord(String line) {

		skipWhiteSpace(line); // in case there are white spaces
		int start = index;
		while (index < line.length() && Character.isLetter(line.charAt(index))) {
			index++;
		}
		return line.substring(start, index);
	}

	/**
	 * Advanced the Index cursor though the non alphanumeric characters in the provided line
	 * 
	 * @param line
	 */
	private void skipNonAlphaChar(String line) {

		while (index < line.length() && !Character.isLetter(line.charAt(index))) {
			index++;
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

	/**
	 * Executes a union operation between the two shapes
	 * 
	 * @param var
	 *            the name of the shape-variable to whom assign the resulting shape
	 * @param line
	 *            the line containg the command
	 */
//	private void doBoolean(String op, Shape shapeA, Shape shapeB) {
	private void doBoolean(String op, String var, String line) {

		// Shape shapeA = null;
		// Shape shapeB = null;
		//
		// String operation = line.split("=")[1]; // fetch the portion of line with the operation
		//
		// // what if there are more than 2 operands ?
		//
		// String operandA = operation.split("\\" + op + "")[0]; // split again to find the two operands
		// String operandB = operation.split("\\" + op + "")[1];
		//
		// if (readShape(operandA) != null) { // check if there is an array for a shape to be created
		// shapeA = readShape(operandA);
		// } else if (shapes.get(operandA.replaceAll("[^a-zA-Z0-9]", "")) != null) { // is is not a new shape check if the variable is in the map
		// // (removing special char)
		// shapeA = shapes.get(operandA.replaceAll("[^a-zA-Z0-9]", ""));
		// } else {
		// System.out.println("Shape A is not valid");
		// }
		//
		// if (readShape(operandB) != null) { // check if there is an array for a shape to be created
		// shapeB = readShape(operandB);
		// } else if (shapes.get(operandB.replaceAll("[^a-zA-Z0-9]", "")) != null) { // is is not a new shape check if the variable is in the map
		// // (removing special char)
		// shapeB = shapes.get(operandB.replaceAll("[^a-zA-Z0-9]", ""));
		// } else {
		// System.out.println("Shape B is not valid");
		// }
		//
//		 if (op.equals("+")) {
//		 shapes.put(var, new ShapeUnion(shapeA, shapeB)); // generates a new shape form the union of the two input and store in shpaes
//		 } else if (op.equals("&")) {
//		 shapes.put(var, new ShapeIntersection(shapeA, shapeB)); // generates a new shape form the union of the two input and store in shpaes
//		 } else if (op.equals("-")) {
//		 shapes.put(var, new ShapeDifference(shapeA, shapeB)); // generates a new shape form the union of the two input and store in shpaes
//		 } else {
//		 System.out.println("Operator not valid");
//		 }
	}
}
