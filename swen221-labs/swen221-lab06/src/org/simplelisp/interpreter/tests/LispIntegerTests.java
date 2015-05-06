package org.simplelisp.interpreter.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import org.junit.Test;
import org.simplelisp.interpreter.LispExpr;
import org.simplelisp.interpreter.LispInteger;

public class LispIntegerTests {

	@Test
	public void testValue() {
		LispInteger lisp = new LispInteger(200);
		assertEquals(200, lisp.value());
	}

	@Test
	public void testToString() {
		assertEquals("200", new LispInteger(200).toString());
	}

	@Test
	public void testEquals() {
		assertTrue(new LispInteger(10).equals(new LispInteger(10)));
		assertFalse(new LispInteger(10).equals(new LispInteger(20)));
	}

	@Test
	public void testEvaluate() {
		// what excactly this one do ?
		assertEquals(new LispInteger(2).evaluate(new HashMap<String, LispExpr>(), new HashMap<String, LispExpr>()), new LispInteger(2));
	}
}
