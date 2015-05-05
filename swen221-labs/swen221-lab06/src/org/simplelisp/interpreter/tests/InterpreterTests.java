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
			new Interpreter().type_check("test2", new LispExpr[]{new LispInteger(3),new LispInteger(0), new LispInteger(1), new LispInteger(10), new LispInteger(1), new LispInteger(1), new LispInteger(1)},LispExpr.class);
			LispInteger li = new LispInteger(0);
			li.value();
			li.equals(0);
			li.toString();
			LispExpr li2 = new LispNil();
			LispExpr li3 = new LispChar('d');
			
//			new Interpreter().type_check("testX", new LispExpr[]{},LispExpr.class);			
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
