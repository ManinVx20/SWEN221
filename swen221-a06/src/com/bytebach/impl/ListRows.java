package com.bytebach.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import com.bytebach.model.InvalidOperation;
import com.bytebach.model.Table;
import com.bytebach.model.Value;
import com.bytebach.model.Field;

public class ListRows implements List<List<Value>> {

	protected Table parent;
	private List<ValueList> internalRows;
	protected Map<Field, List<Value>> keyValues = new HashMap<Field, List<Value>>();

	public ListRows(Table parent) {
		this.parent = parent;
		this.internalRows = new ArrayList<ValueList>();
	}

//	public Map<Field, List<Value>> getKeyMap(){
//		return keyValues;
//	}
	
	@Override
	public boolean add(List<Value> valueList) {
		ValueList valueListToAdd = isOkToAdd(valueList);
		System.out.println("-------------");
		System.out.println(valueListToAdd);
		if (valueListToAdd != null) {
			// Convert the list of values into a new valuelist.... ask why we cannot cast. Is it because List<Value> is not subtype of ValueList?
			internalRows.add(valueListToAdd);
			return true;
		}
		throw new InvalidOperation("Cannot add row with same key field as another");
	}
	
	/**
	 * Converts a list of List<Value> into valueList. This can be used for checking valididy of elements to be added
	 * @param values
	 * @return
	 */
	private ValueList toValueList(List<Value> values){
		isOkToAdd(values);
		ValueList tmpValList = new ValueList(this);
		tmpValList.addAll(values);
		return tmpValList;
		
	}

	@Override
	public List<Value> set(int index, List<Value> valueList) {
		System.out.println("SET ON LISTROW");
		List<Value> tmp = internalRows.get(index);
		ValueList valueListToAdd = isOkToAdd(valueList);
		if (valueListToAdd != null) {
			// Convert the list of value into a vlaueList
			// can add checks here for valuList set checking
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
	private ValueList isOkToAdd(List<Value> valueList) {
		if (parent.fields().size() == valueList.size()) {
			// cycle though each field and check type and if is key. If is key, check if clashing values
			for (int i = 0; i < valueList.size(); i++) {
				Field currentField = this.parent.fields().get(i);
				if (currentField.isKey()) {
					// if list of keyValues is empty or if doesn't contain this value -> add to keyValues
					if (keyValues.get(currentField) == null || !keyValues.get(currentField).contains(valueList.get(i))) {
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
						return toValueList(valueList);
					} else if (keyValues.get(currentField).contains(valueList.get(i))) { // if keyValues already contains this value
						return null;
					}
				} else { // currentField is not key -> add is ok provided is same type
					// finally return keyvalues converted into a ValueList
					return toValueList(valueList);
				}
			}
		}
		return null;
	}

	@Override
	public int size() {
		System.out.println("SIZE");
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
		
//		System.out.println("ListRows contains Object ... ");
//		// check o is instance of ValueList
//		if (o instanceof ListRows) {
//		// check they are same size
//			if (((ListRows) o).size() == internalRows.size()){
//				boolean contains = true;
//				for (int i = 0; i < internalRows.size(); i++){
//					if (!internalRows.get(i).equals(((ListRows) o).get(i))) {
//						contains = false;
//					}
//					return contains;
//				}
//			}
//		}
//		return false;
		throw new InvalidOperation(null);
		
		
		
		
		
		
//		throw new InvalidOperation(null);
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
		System.out.println("REMOVE LISTROW WITH OBJ");
		// First check if o is instance of ValueList
		if (o instanceof ValueList) {
			// then check if o content matches any of the rows
			for (int index = 0; index < internalRows.size(); index++) {
				// if match found remove that row
				if (internalRows.get(index).contains((ValueList) o)) {
					internalRows.remove(index);
					return true;
				}
			}
		}
		return false;
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
