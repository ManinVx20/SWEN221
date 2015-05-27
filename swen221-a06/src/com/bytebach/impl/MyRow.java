package com.bytebach.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import com.bytebach.model.Field;
import com.bytebach.model.InvalidOperation;
import com.bytebach.model.ReferenceValue;
import com.bytebach.model.Table;
import com.bytebach.model.Value;

/**
 * MyRow contains a list of values
 * 
 * @author diego
 *
 */
public class MyRow implements List<Value> {

	private MyRowList parent;
	private List<Value> values;

	public MyRow(MyRowList parent) {
		this.parent = parent;
		this.values = new ArrayList<Value>();
	}

	/**
	 * Check if values in row provided are ok to add/set into ListRows
	 * 
	 * @param valueList
	 * @return
	 */
	private boolean isOkToAdd(int index, Value element) {

		System.out.println("ISOKTOADD");
		// CHECK IF NEW VALUE IS CLASHING WITH KEY-VALUES
		// is the field of the element a key ?
		Field fieldName = parent.parent.fields().get(index);
		// get keyMap
		Map<Field, List<Value>> keyMap = parent.keyValues;
		// check if field is contined in the map
		if (keyMap.containsKey(fieldName)) {
			// grab all the key vlaues of this field
			List<Value> tmpValues = keyMap.get(fieldName);
			// now check if element is contained in this list
			if (tmpValues.contains(element)) {
				throw new InvalidOperation("Key element clashing");
			}
		}
		// finally, if permitted, set the new element in the row, and return old element
		Value tmpVal = values.get(index);
		// CHECK IF SAME TYPE
//		System.out.println(tmpVal.getClass().getSimpleName());
//		System.out.println(element.getClass().getSimpleName());
		if (tmpVal.getClass() == element.getClass()) {
			// further check if this String field is of type TEXT or TEXTAREA
			if (tmpVal.getClass().getSimpleName().equals("StringValue")) {
				// now check if tmpVal doesn't has any \n => don't allow new types with \n
				if (element.toString().contains("\n") && !tmpVal.toString().contains("\n")) {
					throw new InvalidOperation("Field to value of incorrect type");
				}
			} else if (element.getClass().getSimpleName().equals("ReferenceValue")){
				System.out.println("INSTANCE OF REFERENCE");
				ReferenceValue referenceVal = (ReferenceValue) element;
				Table referenceTable = parent.parent.parent.table(referenceVal.table());
				if (referenceTable != null) {
					List<Value> match = referenceTable.row(referenceVal.keys());
					if (match == null) {
						throw new InvalidOperation("Key " + referenceVal + " does not exist");
					}
				} else {
					throw new InvalidOperation("Table " + referenceVal.table() + " does not exist");
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public Value get(int index) {
		return values.get(index);
	}

	@Override
	public boolean addAll(Collection<? extends Value> c) {
		for (Value val : c) {
			values.add(val);
		}
		return true;
//		throw new InvalidOperation(null);
	}

	@Override
	public boolean contains(Object o) {
		if (o instanceof MyRow) {
			if (((MyRow) o).size() == values.size()) {
				boolean contains = true;
				for (int i = 0; i < values.size(); i++) {
					if (!values.get(i).equals(((MyRow) o).get(i))) {
						contains = false;
					}
					return contains;
				}
			}
		}
		return false;
	}

	@Override
	public Value set(int index, Value element) {
		Value tmpVal = values.get(index);
		if (isOkToAdd(index, element)) {
			this.values.set(index, element);
			return tmpVal;
		}
		throw new InvalidOperation(null);
	}
		
	@Override
	public int size() {
		return values.size();
	}

	@Override
	public Iterator<Value> iterator() {
		return new MyIterator<Value>();
	}

	// //////// METHODS NOT IMPLEMENTED

	@Override
	public Value remove(int index) {
		throw new InvalidOperation(null);
	}

	@Override
	public void add(int index, Value element) {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean isEmpty() {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean add(Value e) {
		throw new InvalidOperation(null);
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
		throw new InvalidOperation(null);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Value> c) {
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
	public int indexOf(Object o) {
		throw new InvalidOperation(null);
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new InvalidOperation(null);
	}

	@Override
	public ListIterator<Value> listIterator() {
		throw new InvalidOperation(null);

	}

	@Override
	public ListIterator<Value> listIterator(int index) {
		throw new InvalidOperation(null);
	}

	@Override
	public List<Value> subList(int fromIndex, int toIndex) {
		throw new InvalidOperation(null);
	}

	private class MyIterator<T> implements Iterator<T> {

		private int currentIndex = 0;

		public boolean hasNext() {
			return this.currentIndex < values.size();
		}

		public T next() {
			if (this.hasNext()) {
				int current = currentIndex;
				currentIndex++;
				return (T) values.get(current);
			}
			throw new NoSuchElementException();
		}

		public void remove() {
			throw new InvalidOperation(null);
		}
	}

}
