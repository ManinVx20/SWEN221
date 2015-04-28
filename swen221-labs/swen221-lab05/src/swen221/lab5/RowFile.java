package swen221.lab5;

import java.util.List;

/**
 * Your solution should implement this interface
 * @author ncameron
 *
 */
public interface RowFile {
	/**
	 * Return a list of the identifiers contained in this file.
	 * 
	 * @return
	 */
	List<Identifier> getIdentifiers();
	
	/**
	 * Add a row to the data set
	 * @param id the row's identifier
	 * @param data the data for the row
	 * @throws DuplicateIdException if a row with the given identifier already exists
	 */
	void addRow(Identifier id, int[] data) throws DuplicateIdException;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws MissingDataException
	 */
	int[] getRow(Identifier id) throws MissingDataException;
	/**
	 * 
	 * @param index
	 * @return the index-th row of the ordered data, where the first row is row 0
	 * @throws IndexOutOfBoundsException if the row at index is not present in the data
	 */
	Identifier getRowId(int index) throws IndexOutOfBoundsException;
	/**
	 * 
	 * @param id
	 * @return the total of all data for the given id
	 * @throws MissingDataException if the id is not present in the data
	 */
	int getRowTotal(Identifier id) throws MissingDataException;
	/**
	 * 
	 * @param id
	 * @return the average of all data for the given id
	 * @throws MissingDataException if the id is not present in the data
	 */
	int getRowAverage(Identifier id) throws MissingDataException;
	
	/**
	 * Generate an HTML file containing the sorted summary data in an HTML table.
	 * 
	 * @return
	 */
	String toHtmlTable();
	
}
