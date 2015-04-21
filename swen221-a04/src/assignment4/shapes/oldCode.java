import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

// OLD METHODS USED IN V02
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
		if (cmdList.get(0).equalsIgnoreCase("fill")) { // if is a filled shape
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
		} else if (cmdList.get(0).equalsIgnoreCase("draw")) { // if is a draw line shape
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

	private Shape evaluateOld() {
		skipWhiteSpace();
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
	 * This method reads the hexadecimal color starting from the hashtag #
	 * 
	 * @return the Color of the Shape
	 */
	private Color readColorOLD() {
		if (cmdList.get(index).charAt(0) == '#' && cmdList.get(index).length() == 7) {
			return new Color(cmdList.get(index));
		} else {
			throw new IllegalArgumentException();
		}
	}
}