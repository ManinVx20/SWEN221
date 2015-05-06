package org.simplelisp.interpreter.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.simplelisp.interpreter.InternalFunctions;
import org.simplelisp.interpreter.Interpreter;
import org.simplelisp.interpreter.LispExpr;
import org.simplelisp.interpreter.LispTrue;
import org.simplelisp.interpreter.Parser;


public class LispInternalFunctionTests {
	
	@Test
	public void testConstructors(){
		Interpreter interp = new Interpreter();
//		InternalFunctions internalF = new InternalFunctions();
		InternalFunctions.setup_internals(interp);
//		interp.setGlobalExpr("print", new LispFunction());
	}
	
	@Test
	public void testInternals02(){
		Interpreter interpreter = new Interpreter();
		LispExpr root = Parser.parse("(+ 2 1)");
		LispExpr result = interpreter.evaluate(root);
		assertEquals("3", result.toString());
	}
	
	@Test
	public void testInternals03(){
		Interpreter interpreter = new Interpreter();
		LispExpr root = Parser.parse("(- 3 2)");
		LispExpr result = interpreter.evaluate(root);
		assertEquals("1", result.toString());
	}
	
	@Test
	public void testInternals04(){
		Interpreter interpreter = new Interpreter();
		LispExpr root = Parser.parse("(- 3 2)");
		LispExpr result = interpreter.evaluate(root);
		assertEquals("1", result.toString());
	}

	@Test
	public void testInternals05(){
		Interpreter interpreter = new Interpreter();
		LispExpr root = Parser.parse("(* 3 2)");
		LispExpr result = interpreter.evaluate(root);
		assertEquals("6", result.toString());
	}
	
	@Test
	public void testInternals06(){
		Interpreter interpreter = new Interpreter();
		LispExpr root = Parser.parse("(/ 10 2)");
		LispExpr result = interpreter.evaluate(root);
		assertEquals("5", result.toString());
	}
	
	@Test
	public void testInternals07(){
		Interpreter interpreter = new Interpreter();
		LispExpr root = Parser.parse("(% 3 2)");
		LispExpr result = interpreter.evaluate(root);
		assertEquals("1", result.toString());
	}
	
	@Test
	public void testInternals08(){
		Interpreter interpreter = new Interpreter();
		LispExpr root = Parser.parse("(car '(1 2 3))");
		LispExpr result = interpreter.evaluate(root);
		assertEquals("1", result.toString());
	}
	
//	@Test
//	public void testInternals09(){
//		try {
//			Interpreter interp = new Interpreter();
//			interp.load("hello.sl");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void testInternals10(){
		Interpreter interpreter = new Interpreter();
		LispExpr root = Parser.parse("(equal 3 3)");
		LispExpr result = interpreter.evaluate(root);
		assertEquals(new LispTrue(), result);
	}
	
	@Test
	public void testInternals11(){
		Interpreter interpreter = new Interpreter();
		LispExpr root = Parser.parse("(> 3 2)");
		LispExpr result = interpreter.evaluate(root);
		assertEquals(new LispTrue(), result);
	}
	
	@Test
	public void testInternals12(){
		Interpreter interpreter = new Interpreter();
		LispExpr root = Parser.parse("(< 2 3)");
		LispExpr result = interpreter.evaluate(root);
		assertEquals(new LispTrue(), result);
	}
	
	@Test
	public void testInternals13(){
		Interpreter interpreter = new Interpreter();
		LispExpr root = Parser.parse("(<= 2 3)");
		LispExpr result = interpreter.evaluate(root);
		assertEquals(new LispTrue(), result);
	}
	
	@Test
	public void testInternals14(){
		Interpreter interpreter = new Interpreter();
		LispExpr root = Parser.parse("(>= 3 2)");
		LispExpr result = interpreter.evaluate(root);
		assertEquals(new LispTrue(), result);
	}
	
}
