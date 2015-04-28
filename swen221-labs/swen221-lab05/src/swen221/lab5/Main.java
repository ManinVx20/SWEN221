package swen221.lab5;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException, MissingDataException, DuplicateIdException {
		// First, read the "input.dat" file in		
		RowFileReader reader = new RowFileReader(new File("/Users/diego/Documents/Victoria/SWEN221_2015T1/GitHub/swen221-labs/swen221-lab05/handout/input.dat"));
		RowFile rf = reader.read();
		
//		Identifier ret = rf.getRowId(0);
//		assertTrue(ret.equals(new Identifier("Ali", "a")));
//		System.out.println(rf.getRowId(17).getName());
//		System.out.println(rf.getRowId(17).hashCode());
//		System.out.println((new Identifier("Liam", "b")).getName());
//		System.out.println((new Identifier("Liam", "b")).hashCode());
		
//		rf.addRow(new Identifier("Lynne", "b"), new int[]{11,17,67,5,39,60,82,49,16});
		
//		int[] c = new int[]{11,17,67,5,39,60,82,49,16};
//		System.out.println(c);
//		System.out.println(new int[]{11,17,67,5,39,60,82,49,16});
//		System.out.println(new int[]{11,17,67,5,39,60,82,49,16});
//		System.out.println(new int[]{11,17,67,5,39,60,82,49,16});
//		System.out.println(new int[]{11,17,67,5,39,60,82,49,16});
//		System.out.println(new int[]{11,17,67,5,39,60,82,49,16});
		
//		System.out.println((new int[]{0,17,37,5,39,62,82,49,16}).equals(new int[]{0,17,37,5,39,62,82,49,16}));
//		assertArrayEquals(new int[]{0,17,37,5,39,62,82,49,16}, new int[]{0,17,37,5,39,62,82,49,16});
		
//		System.out.println(rf.getRow(new Identifier("Lynne", "b")));
//		int ret = rf.getRowTotal(new Identifier("Liam", "a"));
//		System.out.println(ret);
		
		rf.toHtmlTable();
//		System.out.println(new int[]{11,17,67,5,39,60,82,49,16});
		
//		for (Identifier id : rf.getIdentifiers()){
//			if (id.getName().equals("Lynne")){
//			System.out.println(id.getName());
//			System.out.println(id.getDept());
//			System.out.println(rf.getRow(id));
//			for (int i : rf.getRow(id)){
//				System.out.println(i);
//			}
//			}
//		}
		

		/*
		// Second, print its contents to the console
		for(Identifier id : rf.getIdentifiers()) {
			System.out.print(id.getName() + " " + id.getDept());
			try {
				for(int item : rf.getRow(id)) {
					System.out.print(item + " ");
				}
				System.out.println(); // add empty line
			} catch(MissingDataException e) {
				// this should be impossible!
				System.err.println("Error: " + e.getMessage());
			}
		}*/
	}
}
