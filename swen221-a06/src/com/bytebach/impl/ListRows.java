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

	private Table parent;
	private List<List<Value>> rows;
	private Map<Field, List<Value>> keyValues = new HashMap<Field, List<Value>>();

	public ListRows(Table parent) {
		this.parent = parent;
		this.rows = new ArrayList<List<Value>>();
	}

	@Override
	public boolean add(List<Value> valueList) {
		List<Value> valueListToAdd = isOkToAdd(valueList);
		System.out.println("-------------");
		System.out.println(valueListToAdd);
		if (valueListToAdd != null) {
			rows.add(valueListToAdd);
			return true;
		}
		throw new InvalidOperation("Cannot add row with same key field as another");
	}

	// // IS OK TO ADD ?:
	// if (parent.fields().size() == valueList.size()) {
	// // cycle though each field and check type and if is key. If is key, check if clashing values
	// for (int i = 0; i < valueList.size(); i++) {
	// Field currentField = this.parent.fields().get(i);
	// if (currentField.isKey()) {
	// // if list of keyValues is empty or if doesn't contain this value -> add to keyValues
	// if (keyValues.get(currentField) == null || !keyValues.get(currentField).contains(valueList.get(i))) {
	// List<Value> tmpValList;
	// if (keyValues.get(currentField) == null){
	// tmpValList = new ArrayList<Value>();
	// } else {
	// tmpValList = keyValues.get(currentField);
	// }
	// // add value to the tmpList and then to the valueListMap
	// tmpValList.add(valueList.get(i));
	// keyValues.put(currentField, tmpValList);
	// } else { // if keyValues already contains this value -> throw exception
	// throw new InvalidOperation("Cannot add row with same key field as another");
	// }
	// // finally add to the list of keyvalues
	// rows.add(valueList);
	// return true;
	// }
	// }
	// }
	// throw new InvalidOperation("Cannot add row with same key field as another");
	// }

	@Override
	public List<Value> set(int index, List<Value> valueList) {
		List<Value> tmp = rows.get(index);
		List<Value> valueListToAdd = isOkToAdd(valueList);
		if (valueListToAdd != null) {
			rows.set(index, valueListToAdd);
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
	private List<Value> isOkToAdd(List<Value> valueList) {
		// IS OK TO ADD ?:
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
						// finally return keyvalues
						return valueList;
					} else if (keyValues.get(currentField).contains(valueList.get(i))) { // if keyValues already contains this value
						return null;
					}
				} else { // currentField is not key -> add is ok provided is same type
					// IMPLEMENT CHECK FOR SAME TYPE
					return valueList;
				}
			}
		}
		return null;
	}

	@Override
	public int size() {
		System.out.println("SIZE");
		return rows.size();
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
		throw new InvalidOperation(null);
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
		System.out.println((rows.contains(o)));
		if (o instanceof ValueList && rows.contains((ValueList) o)) { 
			return rows.remove(o);
		} else {
			throw new InvalidOperation(null);
		}
	}

	@Override
	public List<Value> remove(int index) {
		if (rows.size() >= index) {
			return rows.remove(index);
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
		List<Value> row = rows.get(index);
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

	public class MyIterator<T> implements Iterator<T> {

		private int currentIndex = 0;

		public boolean hasNext() {
			return this.currentIndex < rows.size();
		}

		public T next() {
			if (this.hasNext()) {
				int current = currentIndex;
				currentIndex++;
				return (T) rows.get(current);
			}
			throw new NoSuchElementException();
		}

		public void remove() {
			throw new InvalidOperation(null);
		}
	}

}
