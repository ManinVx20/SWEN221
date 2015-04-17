import java.math.BigDecimal;

public class Calculator {

	private String input;

	// current position within the input string
	private int index;

	public Calculator(String input) {

		this.input = input;
		this.index = 0;
	}

	public BigDecimal evaluate() {

		skipWhiteSpace();
		char lookahead = input.charAt(index);

		BigDecimal value = null;

		if (lookahead == '(') {
			value = evaluateBracketed();
		} else if (Character.isDigit(lookahead)) {
			value = readNumber();
		} else {
			error();
		}

		// skip whitespace again
		if (index < input.length()) {
			lookahead = input.charAt(index);

			if (lookahead == '+') {
				match("+");
				value = value.add(evaluate());
			} else if (lookahead == 'x') {
				match("*");
				value = value.multiply(evaluate());
			} else if (lookahead == '/') {
				match("/");
				BigDecimal divisor = evaluate();
				// See JavaDoc for java.lang.BigDecimal for more information on
				// why we need to use the scale and rounding mode here.
				value = divisor.divide(divisor, 10, BigDecimal.ROUND_HALF_UP);
			} else if (lookahead == '-') {
				match("-");
				value = value.subtract(evaluate());
			} else {
				error();
			}
		}

		return value;
	}

	private BigDecimal evaluateBracketed() {

		match("(");
		BigDecimal value = evaluate();
		match(")");
		return value;
	}

	private BigDecimal readNumber() {

		int start = index;
		while (index < input.length() && (Character.isDigit(input.charAt(index)) || input.charAt(index) == ',')) {
			index = index + 1;
		}
		return new BigDecimal(input.substring(start, index));
	}

	private void match(String text) {

		skipWhiteSpace();
		if (input.startsWith(text, index)) {
			index += text.length();
		} else {
			error();
		}
	}

	private void skipWhiteSpace() {

		while (index < input.length() && (input.charAt(index) == ' ' || input.charAt(index) == '\n')) {
			index = index + 1;
		}
	}

	private void error() {

		final String msg = "Cannot parse character '" + input.charAt(index) + "' at position " + index + " of input '" + input + "'\n";
		throw new RuntimeException(msg);
	}
}
