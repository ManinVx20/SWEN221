package org.simplelisp.interpreter.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.simplelisp.interpreter.*;

/**
 * test the interpreter class
 * @author Nick
 *
 */
public class InterpreterTests {
	
	//test the type_check method
	@Test public void testType_check() {
		//test an empty input
		try {
			new Interpreter().type_check("test", new LispExpr[]{});
		} catch (Error e) {
			fail("Error in type checking: " + e.getMessage());
		}
		
		//TODO test for non-empty input
	}
	
	//test the evaluate method
	@Test public void testEvaluate() {
		try {
			LispExpr result = new Interpreter().evaluate(new LispNil());
			assertEquals(new LispNil(), result);
		} catch (Error e) {
			fail("Error in evaluation: " + e.getMessage());
		}
		
		//TODO test evaluating other programs
	}
}
