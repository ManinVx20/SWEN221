package com.bytebach.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import com.bytebach.model.Field.Type;
import com.bytebach.model.InvalidOperation;
import com.bytebach.model.ReferenceValue;
import com.bytebach.model.Table;
import com.bytebach.model.Value;
import com.bytebach.model.Field;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class MyRowList implements List<List<Value>> {

	protected MyTable parent;
	private List<MyRow> internalRows;
	protected Map<Field, List<Value>> keyValues = new HashMap<Field, List<Value>>();

	public MyRowList(MyTable parent) {
		this.parent = parent;
		this.internalRows = new ArrayList<MyRow>();
	}

	@Override
	public boolean add(List<Value> valueList) {
		// CHECK IF OK TO ADD. IF NOT RETURNS NULL
		MyRow valueListToAdd = isOkToAdd(valueList);
		if (valueListToAdd != null) {
			internalRows.add(valueListToAdd);
			return true;
		}
		throw new InvalidOperation("Invalid operation");
	}

	/**
	 * Converts a list of List<Value> into valueList. This can be used for checking valididy of elements to be added
	 * 
	 * @param values
	 * @return
	 */
	private MyRow toValueList(List<Value> values) {
		MyRow tmpValList = new MyRow(this);
		tmpValList.addAll(values);
		return tmpValList;
	}

	@Override
	public List<Value> set(int index, List<Value> valueList) {
		List<Value> tmp = internalRows.get(index);
		MyRow valueListToAdd = isOkToAdd(valueList);
		if (valueListToAdd != null) {
			internalRows.set(index, valueListToAdd);
			return tmp;
		}
		throw new InvalidOperation(null);
	}

	/**
	 * Check if valueList provided is ok to add/set into ListRows
	 * 
	 * @param valueList
	 * @return
	 */
	private MyRow isOkToAdd(List<Value> valueList) {
		boolean okToAdd = true;
		// CHECK FOR SAME SIZE OF LISTS
		if (parent.fields().size() == valueList.size()) {
			// ITERATE THOUGH VALUES, CHECK IF FIELD IS KEY, THEN CHECK IF IS CLASHING
			for (int i = 0; i < valueList.size(); i++) {
				Field currentField = this.parent.fields().get(i);
				if (currentField.isKey()) {
					// IF KEYVALUES IS EMPTY
					if (keyValues.isEmpty()) {
						okToAdd = true;
						List<Value> tmpValList;
						if (keyValues.get(currentField) == null) {
							tmpValList = new ArrayList<Value>();
						} else {
							tmpValList = keyValues.get(currentField);
						}
						// add value to the tmpList and then to the valueListMap
						tmpValList.add(valueList.get(i));
						keyValues.put(currentField, tmpValList);
						// finally return keyvalues converted into a ValueList
						// return toValueList(valueList);
						// if keyValues is not empty but doesn't have this field yet, or keyValues doesn't contain this value -> ok to add
					} else if (keyValues.get(currentField) == null || !keyValues.get(currentField).contains(valueList.get(i))) {
						// if (keyValues.get(currentField) == null || !keyValues.get(currentField).contains(valueList.get(i))) {
						okToAdd = true;
						List<Value> tmpValList;
						if (keyValues.get(currentField) == null) {
							tmpValList = new ArrayList<Value>();
						} else {
							tmpValList = keyValues.get(currentField);
						}
						// add value to the tmpList and then to the valueListMap
						tmpValList.add(valueList.get(i));
						keyValues.put(currentField, tmpValList);
						// finally return keyvalues converted into a ValueList
						// if (okToAdd) return toValueList(valueList);
						// IF KEYVALUE CONTAINS THE KEY VALUE
					} else if (keyValues.get(currentField).contains(valueList.get(i))) {
						// CHECK IF THERE ARE OTHER KEY VALUES: does keyValues have more than one key ?
						// not ok to add /// but continue
						// return null;
						okToAdd = false;
					}
				} else if (currentField.type().equals(Type.REFERENCE)) {
					System.out.println("FOUND REFERENCE");
					// find the reference value and reference table
					ReferenceValue referenceVal = (ReferenceValue) valueList.get(i);
					Table referenceTable = parent.parent.table(referenceVal.table());
					// check if the reference table is not null, then check the row index from the reference value
					if(referenceTable != null) {
						List<Value> match = referenceTable.row(referenceVal.keys());
						if(match == null) {
							throw new InvalidOperation("Key " + referenceVal + " does not exist");
						}
					} else {
						throw new InvalidOperation("Table " + referenceVal.table() + " does not exist");
					}
					
//					// CREATES A LIST ARRAY WITH KEY REFERENCE VALUES
//					List<Value> keyRefValues = new ArrayList<Value>();
//					for (int x=0; x<((ReferenceValue) valueList.get(x)).keys().length; x++){
//						keyRefValues.add(((ReferenceValue) valueList.get(x)).keys()[x]);
//					}
//					System.out.println(Arrays.toString(((ReferenceValue) valueList.get(i)).keys()));
//					System.out.println(currentField.refTable());
					return toValueList(valueList);
				} else { // currentField is not key -> add is ok provided is same type
							// finally return keyvalues converted into a ValueList
					if (okToAdd) {
						return toValueList(valueList);
					}
				}
			}
		}
		return null;
	}

	@Override
	public int size() {
		return internalRows.size();
	}

	@Override
	public boolean isEmpty() {
		// if (rows.size() == 0){
		// return true;
		// }
		throw new InvalidOperation(null);
	}

	@Override
	public boolean contains(Object o) {

		// System.out.println("ListRows contains Object ... ");
		// // check o is instance of ValueList
		// if (o instanceof ListRows) {
		// // check they are same size
		// if (((ListRows) o).size() == internalRows.size()){
		// boolean contains = true;
		// for (int i = 0; i < internalRows.size(); i++){
		// if (!internalRows.get(i).equals(((ListRows) o).get(i))) {
		// contains = false;
		// }
		// return contains;
		// }
		// }
		// }
		// return false;
		throw new InvalidOperation(null);

		// throw new InvalidOperation(null);
	}

	@Override
	public Iterator<List<Value>> iterator() {
		return new MyIterator<List<Value>>();
		// throw new InvalidOperation(null);
	}

	@Override
	public Object[] toArray() {
		throw new InvalidOperation(null);
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean remove(Object o) {
		// First check if o is instance of MyRow
		if (o instanceof MyRow) {
			MyRow rowToDelete = (MyRow) o;
			// then check if rowToDelete matches any of the rows
			for (int index = 0; index < internalRows.size(); index++) {
				if (internalRows.get(index).contains(rowToDelete)) {
//					System.out.println("FOUND ROW TO DELETE");
					
					// Map used to keep track of what to delete. Delete is performed at the end of the traversal
					Map<Table,List<List<Value>>> toDeleteMap = new HashMap<Table,List<List<Value>>>();
					boolean okToDelete = false;
					List<List<Value>> rowsToDelete = new ArrayList<List<Value>>();
					List<Value> keysToDelete = new ArrayList<Value>();
					
					// CHECK FOR REFERENCE VLAUES
					// if in rowToDelete, there is a value which contains a reference, navigate to the other table and delete the content
					
					// If a table contains a reference to any of the value in the row to remove, also remove the row in that table
					// let's say we remove a row which contains a List<key> 0,1 and in another table/row/value there is a reference to this table/this List<key> 0,1
					// FOR EACH TABLE, FOR EACH ROW, FOR EACH VALUE
					for (Table table : parent.parent.tables()){
						for (List<Value> row : table.rows()){
							for (int vIndx = 0; vIndx < row.size(); vIndx++){
								// if this value is a reference .. check if points to any of the values in the row I am removing...
								if(row.get(vIndx) instanceof ReferenceValue) {
									ReferenceValue refVal = (ReferenceValue) row.get(vIndx); // this is [table:0]
									// check if refVal points to this table name
									if (refVal.table().equals(parent.name())) {
										// System.out.println(refVal.table().equals(parent.name()));
										// now I found a table with same name, I should check among each row of this table if the provided list of keys matches the keys in this row
										Table matchingTable = parent.parent.table(refVal.table());
										// now check each row
										// let's make a Map to keep track of all the matches we find. We store the table name with a (list of (lists of values) <- keys to delete) <- list of rows 
										okToDelete = false;
										for (List<Value> refRow : matchingTable.rows()){
											// now check for each key field
											for (int kIndx = 0; kIndx < refRow.size(); kIndx++){
												// if row contains a key value .. and matches with the reference value 
												if (matchingTable.fields().get(kIndx).isKey()) {
													// if key of this field matches the ReferenceValue.keys[kIndx] --> is ok to delete
													if (refRow.get(kIndx).equals(refVal.keys()[kIndx]) && refRow.get(kIndx).equals(rowToDelete.get(kIndx))) {
														// if value matches .. continue until end of array
														okToDelete = true;
														keysToDelete.add(refRow.get(kIndx));
														
													} else { // key values are not the same so is not ok to delete
														okToDelete = false;
													}
												}
											} 
											if (okToDelete){ // if this row is ok to delete add it to the list of rows to delete
												rowsToDelete.add(keysToDelete);
												keysToDelete = new ArrayList<Value>(); // @_@! finally, after trying .clear() realised the list is passed by reference... can't use it here! 
											}
										}
									}
								}
							}
						}
						// now that we have a list of list<value>
						System.out.println("-------------");
						System.out.println(rowsToDelete);
						System.out.println(table.name());
						if (!rowsToDelete.isEmpty()){
							toDeleteMap.put(table, rowsToDelete);
						}
					}
					// if the map contains some elements, which contains some element -> delete
					if (!toDeleteMap.isEmpty()){
						for (Table table : toDeleteMap.keySet()){
							if (!toDeleteMap.get(table).isEmpty()){
								for (List<Value> keyValList : toDeleteMap.get(table)){
									System.out.println("WAS OK TO DELETE");
									Value[] keyToDeleteArray = new Value[keyValList.size()];
									keyToDeleteArray = keyValList.toArray(keyToDeleteArray);
									table.delete(keyToDeleteArray);
								}
							}
						}
					}
					internalRows.remove(index);
					return true;
				}
			}
		}
		return false;
		
		
		/*
		if(o instanceof MyRow) {
			List<Value> values = (List<Value>) o;
			
			List<Field> fields = parent.fields();
			List<Integer> keys = new ArrayList<Integer>();
			
			for(Field field : fields) {
				if(field.isKey()) {
					keys.add(fields.indexOf(field));
				}
			}
			
			//System.out.println(values);
			Value[] findKeys = new Value[keys.size()];
			
			int count = 0;
			for(Value val : values) {
				if(keys.contains(count)) {
					Field keyField = fields.get(count);
				}
				
				if(val instanceof ReferenceValue) {
					ReferenceValue referenceVal = (ReferenceValue) val;
					Table table = parent.parent.table(referenceVal.table());
					table.delete(referenceVal.keys());
				}
				count++;
			}
			
			return super.remove(o);
		} else {
			throw new InvalidOperation("Object " + o + " is not of the type List<Value>");
		}
	}
	*/
		
		
		
		
		
	}

	@Override
	public List<Value> remove(int index) {
		if (internalRows.size() >= index) {
			return internalRows.remove(index);
		}
		throw new InvalidOperation(null);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean addAll(Collection<? extends List<Value>> c) {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean addAll(int index, Collection<? extends List<Value>> c) {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new InvalidOperation(null);
	}

	@Override
	public void clear() {
		throw new InvalidOperation(null);
	}

	@Override
	public List<Value> get(int index) {
		List<Value> row = internalRows.get(index);
		return row;
	}

	@Override
	public void add(int index, List<Value> element) {
		throw new InvalidOperation(null);
	}

	@Override
	public int indexOf(Object o) {
		throw new InvalidOperation(null);
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new InvalidOperation(null);
	}

	@Override
	public ListIterator<List<Value>> listIterator() {
		throw new InvalidOperation(null);
	}

	@Override
	public ListIterator<List<Value>> listIterator(int index) {
		throw new InvalidOperation(null);
	}

	@Override
	public List<List<Value>> subList(int fromIndex, int toIndex) {
		throw new InvalidOperation(null);
	}

	private class MyIterator<T> implements Iterator<T> {

		private int currentIndex = 0;

		public boolean hasNext() {
			return this.currentIndex < internalRows.size();
		}

		public T next() {
			if (this.hasNext()) {
				int current = currentIndex;
				currentIndex++;
				return (T) internalRows.get(current);
			}
			throw new NoSuchElementException();
		}

		public void remove() {
			throw new InvalidOperation(null);
		}
	}

}
