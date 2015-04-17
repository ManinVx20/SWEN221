import org.junit.Test;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CalculatorTests {
	private void test(String input, String output) {
		BigDecimal out = new Calculator(input).evaluate();
		BigDecimal num = new BigDecimal(output);
		if(num.compareTo(out) != 0) {
			fail(input + " => " + out + ", not " + output);
		}
	}

	private void test(String input) {
 		test(input, input);
	}

	@Test public void numberTests() {
		test("3");
		test("    12387", "12387");
	}

	@Test public void realNumberTests() {
		test("0.00137");
		test("0.6789723");
		test("1.22345");
	}

	@Test public void addTests() {
		test("1+1", "2");
		test("2+0.387", "2.387");
		test("3+47", "50");
		test("4.01+7723.089", "7727.099");
		test("0.5+4783", "4783.5");
		test("0.0 + 12387", "12387.0");
		test("0.00123+ 1", "1.00123");
	}

	@Test public void subTests() {
		test("1-1", "0");
		test("2-0.387", "1.613");
		test("3-47", "-44");
		test("4.01-7723.089", "-7719.079");
		test("0.5-4783", "-4782.5");
		test("0.0 - 12387", "-12387.0");
		test("0.00123- 1", "-0.99877");
	}	

	@Test public void mulTests() {
		test("3*3", "9");
		test("2*0.387", "0.774");
		test("3*47", "141");
		test("4.01*7723.089", "30969.58689");
		test("0.5*4783", "2391.5");
		test("0.0 * 12387", "0.0");
		test("0.00123*1", "0.00123");
	}
	
	@Test public void divTests() {
		test("1/1", "1");
		test("2/0.5", "4");
		test("3/4", "0.75");
		test("400/7", "57.1428571429");
		test("0.5/4783", "0.0001045369");
		test("0.00923/1", "0.00923");
	}
}
