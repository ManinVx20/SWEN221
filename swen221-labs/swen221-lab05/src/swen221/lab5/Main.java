package swen221.lab5;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException, MissingDataException, DuplicateIdException {
		// First, read the "input.dat" file in
//		RowFileReader reader = new RowFileReader(new File("/u/students/trazzidieg/code/GitHub/SWEN221/swen221-labs/swen221-lab05/handout/input.dat"));
		RowFileReader reader = new RowFileReader(new File("input.dat"));
		RowFile rf = reader.read();
		rf.toHtmlTable();
	}
}
