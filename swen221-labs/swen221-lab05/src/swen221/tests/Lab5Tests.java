package swen221.tests;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import swen221.lab5.DuplicateIdException;
import swen221.lab5.Identifier;
import swen221.lab5.MissingDataException;
import swen221.lab5.RowFile;
import swen221.lab5.RowFileReader;
import static org.junit.Assert.*;

/**
 * Tests for your solution. The solution should be in a class called Solution and should initialise itself in the constructor. </p> <b>Do not modify the tests!</b> </p>
 *
 * @author ncameron
 *
 */
public class Lab5Tests {

	@Test
	public void testAddRow() throws IOException {
		RowFile rf = new RowFileReader(new File("input.dat")).read();
		try {
			rf.addRow(new Identifier("Lynne", "b"), new int[] { 11, 17, 67, 5, 39, 60, 82, 49, 16 });
			assertArrayEquals(rf.getRow(new Identifier("Lynne", "b")), new int[] { 11, 17, 67, 5, 39, 60, 82, 49, 16 });
			rf.addRow(new Identifier("Ella", "c"), new int[] { 0, 17, 37, 5, 39, 62, 82, 49, 16 });
			assertArrayEquals(rf.getRow(new Identifier("Ella", "c")), new int[] { 0, 17, 37, 5, 39, 62, 82, 49, 16 });
		} catch (DuplicateIdException e) {
			assertTrue(false);
		} catch (MissingDataException e) {
			assertTrue(false);
		}
		try {
			rf.addRow(new Identifier("Angelo", "a"), new int[] {});
			assertTrue(false);
		} catch (DuplicateIdException e) {
		}
		try {
			rf.addRow(new Identifier("Rongo", "d"), new int[] {});
			assertTrue(false);
		} catch (DuplicateIdException e) {
		}
	}

	@Test
	public void testGetRow() throws IOException {
		RowFile rf = new RowFileReader(new File("input.dat")).read();
		try {
			int[] ret = rf.getRow(new Identifier("Ella", "b"));
			assertArrayEquals(ret, new int[] { 11, 17, 67, 65, 39, 60, 81, 49, 86 });
			ret = rf.getRow(new Identifier("Daniel", "a"));
			assertArrayEquals(ret, new int[] { 57, 47, 25, 87, 3, 42, 6, 62, 57 });
			ret = rf.getRow(new Identifier("Chang", "d"));
			assertArrayEquals(ret, new int[] { 52, 58, 73, 22, 42, 50, 2, 14, 17 });
		} catch (MissingDataException e) {
			assertTrue(false);
		}
		try {
			int[] ret = rf.getRow(new Identifier("Ella", "c"));
			assertTrue(false);
		} catch (MissingDataException e) {
		}
		try {
			int[] ret = rf.getRow(new Identifier("fsdgsdfg", ""));
			assertTrue(false);
		} catch (MissingDataException e) {
		}
		try {
			int[] ret = rf.getRow(new Identifier("Amelia", "e"));
			assertTrue(false);
		} catch (MissingDataException e) {
		}
	}

	@Test
	public void testGetRowId() throws IOException {
		RowFile rf = new RowFileReader(new File("input.dat")).read();
		try {
			Identifier ret = rf.getRowId(0);
			assertTrue(ret.equals(new Identifier("Ali", "a")));
			ret = rf.getRowId(17);
			assertTrue(ret.equals(new Identifier("Liam", "b")));
			ret = rf.getRowId(38);
			assertTrue(ret.equals(new Identifier("William", "d")));
		} catch (IndexOutOfBoundsException e) {
			assertTrue(false);
		}
		try {
			Identifier ret = rf.getRowId(-1);
			assertTrue(false);
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			Identifier ret = rf.getRowId(Integer.MAX_VALUE);
			assertTrue(false);
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			Identifier ret = rf.getRowId(39);
			assertTrue(false);
		} catch (IndexOutOfBoundsException e) {
		}
	}

	@Test
	public void testGetRowTotal() throws IOException {
		RowFile rf = new RowFileReader(new File("input.dat")).read();
		try {
			int ret = rf.getRowTotal(new Identifier("Liam", "a"));
			assertEquals(ret, 468);
			ret = rf.getRowTotal(new Identifier("Benjamin", "b"));
			assertEquals(ret, 669);
			ret = rf.getRowTotal(new Identifier("Taonga", "a"));
			assertEquals(ret, 429);
		} catch (MissingDataException e) {
			assertTrue(false);
		}
		try {
			int ret = rf.getRowTotal(new Identifier("Taonga", "c"));
			assertTrue(false);
		} catch (MissingDataException e) {
		}
	}

	@Test
	public void testGetRowAverage() throws IOException {
		RowFile rf = new RowFileReader(new File("input.dat")).read();
		try {
			int ret = rf.getRowAverage(new Identifier("Vladimir", "b"));
			assertEquals(ret, 64);
			ret = rf.getRowAverage(new Identifier("Isabella", "c"));
			assertEquals(ret, 25);
			ret = rf.getRowAverage(new Identifier("Amit", "a"));
			assertEquals(ret, 43);
		} catch (MissingDataException e) {
			assertTrue(false);
		}
		try {
			int ret = rf.getRowAverage(new Identifier("Bo", "a"));
			assertTrue(false);
		} catch (MissingDataException e) {
		}
	}
}
