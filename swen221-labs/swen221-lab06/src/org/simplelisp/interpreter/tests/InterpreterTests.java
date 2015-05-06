package org.simplelisp.interpreter.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.simplelisp.interpreter.*;

/**
 * test the interpreter class
 * 
 * @author Nick
 *
 */
public class InterpreterTests {

	@Test
	public void testType_check() {
		try {
			new Interpreter().type_check("test", new LispExpr[] {});
			new Interpreter().type_check("test 2", new LispExpr[] { new LispInteger(1), new LispChar('c') }, LispExpr.class);
		} catch (Error e) {
			fail("Error in type checking: " + e.getMessage());
		}
	}

	@Test
	public void testType_inValid() {
		try {
			new Interpreter().type_check("test 2", new LispExpr[] { new LispInteger(1), new LispChar('c') }, LispExpr.class, LispNil.class);
			fail("This should fial");
		} catch (Error e) {
			// ok this was expected
		}
	}

	@Test
	public void testType_inValid02() {
		try {
			new Interpreter().type_check("test 2", new LispExpr[] { new LispInteger(1) }, LispExpr.class, LispNil.class);
			fail("This should fial");
		} catch (Error e) {
			// ok this iwas expected
		}
	}

	// test the evaluate method
	@Test
	public void testEvaluate() {
		try {
			LispExpr result = new Interpreter().evaluate(new LispNil());
			assertEquals(new LispNil(), result);
		} catch (Error e) {
			fail("Error in evaluation: " + e.getMessage());
		}
		// TODO test evaluating other programs
	}
}
