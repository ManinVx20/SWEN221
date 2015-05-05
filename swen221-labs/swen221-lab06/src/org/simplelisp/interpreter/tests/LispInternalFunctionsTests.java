package org.simplelisp.interpreter.tests;

import org.junit.Test;
import org.simplelisp.interpreter.InternalFunctions;
import org.simplelisp.interpreter.Interpreter;
import org.simplelisp.interpreter.LispFunction;


public class LispInternalFunctionsTests {
	
	@Test
	public void  testConstructors(){
		Interpreter interp = new Interpreter();
		InternalFunctions internalF = new InternalFunctions();
		internalF.setup_internals(interp);
//		interp.setGlobalExpr("print", new LispFunction());
	}
	
}
