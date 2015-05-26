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
import com.bytebach.model.Table;
import com.bytebach.model.Value;

/**
 * MyRow contains a list of values
 * 
 * @author diego
 *
 */
public class ValueList implements List<Value> {

	private ListRows parent;
	private List<Value> values;

	public ValueList(ListRows parent) {
		this.parent = parent;
		this.values = new ArrayList<Value>();
	}

	@Override
	public int size() {
		return values.size();
//		throw new InvalidOperation(null);
	}

	@Override
	public Value set(int index, Value element) {
		// CHECK IF NEW VALUE IS CLASHING WITH KEY-VALUES
		// is the field of the element a key ? 
		Field fieldName = parent.parent.fields().get(index);
		// get keyMap
		Map<Field, List<Value>> keyMap = parent.keyValues;
		// check if field is contined in the map
		if (keyMap.containsKey(fieldName)){
			// grab all the key vlaues of this field
			List<Value> tmpValues = keyMap.get(fieldName);
			// now check if element is contained in this list
			if (tmpValues.contains(element)){
				throw new InvalidOperation("Key element clashing");
			}
		}
		// finally, if permitted, set the new element in the row, and return old element
		Value tmpVal = values.get(index);
		// CHECK IF SAME TYPE
		if (tmpVal.getClass() == element.getClass()){
			// further check if this String field is of type TEXT or TEXTAREA
			if (tmpVal.getClass().getSimpleName().equals("StringValue")){
				// now check if tmpVal doesn't has any \n => don't allow new types with \n
				if (element.toString().contains("\n") && !tmpVal.toString().contains("\n")){
					throw new InvalidOperation("Field to value of incorrect type");
				}
			}
			this.values.set(index, element);
			return tmpVal;
		} else {
			throw new InvalidOperation("Key elements of different type");
		} 
	}

	@Override
	public void add(int index, Value element) {
		throw new InvalidOperation(null);
	}

	@Override
	public Value remove(int index) {
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
	public boolean contains(Object o) {
		// check o is instance of ValueList
		if (o instanceof ValueList) {
		// check they are same size
			if (((ValueList) o).size() == values.size()){
				boolean contains = true;
				for (int i = 0; i < values.size(); i++){
					if (!values.get(i).equals(((ValueList) o).get(i))) {
						contains = false;
					}
					return contains;
				}
			}
		}
		return false;
//		throw new InvalidOperation(null);
	}

	@Override
	public Iterator<Value> iterator() {
		return new MyIterator<Value>();
//		throw new InvalidOperation(null);
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
	public boolean addAll(Collection<? extends Value> c) {
		for (Value val : c){
			values.add(val);
		}
		return true;
		//		throw new InvalidOperation(null);
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
	public Value get(int index) {
		return values.get(index);
//		throw new InvalidOperation(null);
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
