package org.simplelisp.interpreter.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import org.simplelisp.interpreter.*;

public class LispVectorTest {

	@Test
	public void testConstructor() {
		try {
			LispVector lisp = new LispVector();
			LispExpr lisp2 = lisp.get(0);
			fail("should not work");
		} catch (Exception e) {
			fail("Exception occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void testConstructor2() {
		ArrayList<LispExpr> list = new ArrayList<LispExpr>();
		list.add(new LispInteger(1));
		LispVector lisp = new LispVector(list);
		assertEquals(1, lisp.size());
		assertEquals(1, ((LispInteger) lisp.get(0)).value());
		// assertEquals("1", (lisp.toString()));
	}

	@Test
	public void testToString() {
		// System.out.println(new LispVector(new ArrayList<LispExpr>(Arrays.asList(new LispInteger(1), new LispInteger(10)))).toString());
		assertEquals("#(1 10)", new LispVector(new ArrayList<LispExpr>(Arrays.asList(new LispInteger(1), new LispInteger(10)))).toString());
	}

	@Test
	public void testEquals() {
		LispVector lisp1 = new LispVector(new ArrayList<LispExpr>(Arrays.asList(new LispInteger(1), new LispInteger(10))));
		LispVector lisp2 = new LispVector(new ArrayList<LispExpr>(Arrays.asList(new LispInteger(1), new LispInteger(10))));
		LispVector lisp3 = new LispVector(new ArrayList<LispExpr>(Arrays.asList(new LispInteger(1), new LispInteger(30))));
		assertTrue(lisp1.equals(lisp2));
		assertFalse(lisp1.equals(lisp3));
	}

	@Test
	public void testSize() {
		LispVector lisp1 = new LispVector(new ArrayList<LispExpr>(Arrays.asList(new LispInteger(4), new LispInteger(7))));
		assertEquals(2, lisp1.size());
	}

	@Test
	public void testGet() {
		LispVector lisp1 = new LispVector(new ArrayList<LispExpr>(Arrays.asList(new LispInteger(1), new LispInteger(10))));
		assertEquals("10", lisp1.get(1).toString());
		assertEquals("10", lisp1.elt(1).toString());
	}

	@Test
	public void testAdd() {
		LispVector lisp1 = new LispVector(new ArrayList<LispExpr>(Arrays.asList(new LispInteger(1), new LispInteger(10))));
		lisp1.add(new LispInteger(33));
		assertEquals(3, lisp1.size());
	}

	@Test
	public void testIterator() {
		LispVector lisp1 = new LispVector(new ArrayList<LispExpr>(Arrays.asList(new LispInteger(1), new LispInteger(10))));
		assertTrue(lisp1.iterator() instanceof Iterator);
	}

	@Test
	public void testLength() {
		LispVector lisp1 = new LispVector(new ArrayList<LispExpr>(Arrays.asList(new LispInteger(1), new LispInteger(10))));
		assertEquals(2, lisp1.length().value());
	}

	@Test
	public void testEvaluate() {
		assertEquals(new LispVector().evaluate(new HashMap(), new HashMap()), new LispVector());
	}

	@Test
	public void testReverse() {
		LispVector lisp1 = new LispVector(new ArrayList<LispExpr>(Arrays.asList(new LispInteger(1), new LispInteger(10), new LispInteger(100))));
		assertEquals("#(100 10 1)", lisp1.reverse().toString());
	}
}
