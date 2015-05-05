package org.simplelisp.interpreter.tests;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
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
//			LispInteger li = new LispInteger(0);
//			li.value();
//			li.equals(0);
//			li.toString();
//			LispExpr li2 = new LispNil();
//			LispExpr li3 = new LispChar('d');
//			new Interpreter().type_check("testX", new LispExpr[]{},LispExpr.class);			
		} catch (Error e) {
			fail("Error in type checking: " + e.getMessage());
		} 
		//TODO test for non-empty input
	}
	
	@Test public void testType_sum() {
		try {
			Interpreter interpreter = new Interpreter();
			LispExpr root = Parser.parse("(+ 2 3)");
			LispExpr result = interpreter.evaluate(root);
			assertEquals("5", result.toString());
		} catch (Error e) {
			fail("Error in type checking: " + e.getMessage());
		} 
	}
	
	@Test public void testType_divisionByZero() {
		try {
			Interpreter interpreter = new Interpreter();
			LispExpr root = Parser.parse("(/ 2 0)");
			LispExpr result = interpreter.evaluate(root);
			System.out.println(result.toString());
			assertEquals("5", result.toString());
			fail("this should have errored");
		} catch (ArithmeticException e) {
			// the super-hero catches the monster
		} 
	}
	
	@Test public void testType_print() {
		try {
			Interpreter interpreter = new Interpreter();
			LispExpr root = Parser.parse("'(+ 1 2)");
			LispExpr result = interpreter.evaluate(root);
			assertEquals("(+ 1 2)", result.toString());
		} catch (Error e) {
			fail("Error in type checking: " + e.getMessage());
		} 
	}
	
	@Test public void testType_car() {
		try {
			Interpreter interpreter = new Interpreter();
			LispExpr root = Parser.parse("(car '(1 2 3))");
			LispExpr result = interpreter.evaluate(root);
			assertEquals("1", result.toString());
		} catch (Error e) {
			fail("Error in type checking: " + e.getMessage());
		} 
	}

	
	@Test public void testType_load() {
		try {
			Interpreter interp = new Interpreter();
			interp.load("hello.sl");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
