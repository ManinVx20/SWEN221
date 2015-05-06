package org.simplelisp.interpreter.tests;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import org.simplelisp.interpreter.*;

public class BlackBoxTests {

	@Test
	public void testType_sum() {
		try {
			Interpreter interpreter = new Interpreter();
			LispExpr root = Parser.parse("(+ 2 3)");
			LispExpr result = interpreter.evaluate(root);
			assertEquals("5", result.toString());
		} catch (Error e) {
			fail("Error in type checking: " + e.getMessage());
		}
	}

	@Test
	public void testType_divisionByZero() {
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

	@Test
	public void testType_print() {
		try {
			Interpreter interpreter = new Interpreter();
			LispExpr root = Parser.parse("'(+ 1 2)");
			LispExpr result = interpreter.evaluate(root);
			assertEquals("(+ 1 2)", result.toString());
		} catch (Error e) {
			fail("Error in type checking: " + e.getMessage());
		}
	}

	@Test
	public void testType_car() {
		try {
			Interpreter interpreter = new Interpreter();
			LispExpr root = Parser.parse("(car '(1 2 3))");
			LispExpr result = interpreter.evaluate(root);
			assertEquals("1", result.toString());
		} catch (Error e) {
			fail("Error in type checking: " + e.getMessage());
		}
	}
	
	@Test
	public void testType_cdr() {
		try {
			Interpreter interpreter = new Interpreter();
			LispExpr root = Parser.parse("(cdr '(1 2 3))");
			LispExpr result = interpreter.evaluate(root);
			assertEquals("(2 3)", result.toString());
		} catch (Error e) {
			fail("Error in type checking: " + e.getMessage());
		}
	}
	
	@Test
	public void testType_expr() {
		try {
			Interpreter interpreter = new Interpreter();
			LispExpr root = Parser.parse("(cons 'A '(B C))");
			LispExpr result = interpreter.evaluate(root);
			assertEquals("(A B C)", result.toString());
		} catch (Error e) {
			fail("Error in type checking: " + e.getMessage());
		}
	}

//	@Test
//	public void testType_load() {
//		try {
//			Interpreter interp = new Interpreter();
//			interp.load("fibonacci.sl");
////			interp.load("hello.sl");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}

	@Test
	public void testEvaluate() {
		try {
			LispExpr result = new Interpreter().evaluate(new LispNil());
			assertEquals(new LispNil(), result);
		} catch (Error e) {
			fail("Error in evaluation: " + e.getMessage());
		}
	}
}
