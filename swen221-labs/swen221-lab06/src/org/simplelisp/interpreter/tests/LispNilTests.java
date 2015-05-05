package org.simplelisp.interpreter.tests;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;
import org.simplelisp.interpreter.*;

/**
 * Test the lispNil class. Should have 100% coverage.
 * 
 * @author Nick
 *
 */
public class LispNilTests {
	@Test
	public void testToString() {
		assertEquals("nil", new LispNil().toString());
	}

	@Test
	public void testEquals() {
		assertTrue(new LispNil().equals(new LispNil()));
		assertFalse(new LispNil().equals(new LispTrue()));
	}

	@Test
	public void testEvaluate() {
		assertEquals(new LispNil().evaluate(new HashMap(), new HashMap()), new LispNil());
	}
}
