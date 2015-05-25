package com.bytebach.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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

	private List<List<Value>> parent;
	private List<Value> values;

	public ValueList(List<List<Value>> parent) {
		this.parent = parent;
		this.values = new ArrayList<Value>();
	}

	@Override
	public int size() {
		throw new InvalidOperation(null);
	}

	@Override
	public Value set(int index, Value element) {
		System.out.println("######");
		// check if value is same type ... 
		// check if key is clashing with other keys
		Value tmpVal = values.get(index);
//		parent.set(index, element);
		values.set(index, element);
		return tmpVal;
//		throw new InvalidOperation(null);
	}

	@Override
	public Value remove(int index) {
		System.out.println("welfsjfskjsdlfskjl");
		throw new InvalidOperation(null);
	}

	@Override
	public boolean isEmpty() {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean contains(Object o) {
		throw new InvalidOperation(null);
	}

	@Override
	public Iterator<Value> iterator() {
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
	public boolean add(Value e) {
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
		System.out.println("ADD ALL");
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
		throw new InvalidOperation(null);
	}

	@Override
	public void add(int index, Value element) {
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

}
