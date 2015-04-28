package swen221.lab5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RowFileReader {
	private Scanner input;
	
	public RowFileReader(File file) throws FileNotFoundException {
		input = new Scanner(file);
	}
	
	public RowFile read() {
		RowFile file = new RawFileImpl();
		
		// First, read title row
		String titleLine = input.nextLine();
//		System.out.println(titleLine);
//		int numberOfColumns = titleLine.split(",").length;
		
		// Second, read data rows
		while(input.hasNext()) {			
			String dataLine = input.nextLine();
//			System.out.println(dataLine);
			String[] dataItems = dataLine.split(",");
			
			// create a new identifier
			Identifier identifier = new Identifier(dataItems[0], dataItems[1]);
			// creates int entries
			int[] data = new int[dataItems.length-2];
			for (int i=0; i<data.length; i++){
				data[i] = Integer.parseInt(dataItems[i+2]);
			}
			try {
				file.addRow(identifier, data);
			} catch (DuplicateIdException e) {
				System.out.println("Duplicate ID exception");
				e.printStackTrace();
			}
		}
		
		return file;
	}
}
