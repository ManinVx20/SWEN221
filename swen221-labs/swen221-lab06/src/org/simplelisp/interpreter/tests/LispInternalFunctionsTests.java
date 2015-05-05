package org.simplelisp.interpreter.tests;

import org.junit.Test;
import org.simplelisp.interpreter.InternalFunctions;
import org.simplelisp.interpreter.Interpreter;


public class LispInternalFunctionsTests {
	
	@Test
	public void  testConstructors(){
		InternalFunctions internalF = new InternalFunctions();
		internalF.setup_internals(new Interpreter());
	}
	
}
