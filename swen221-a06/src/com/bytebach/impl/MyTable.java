package com.bytebach.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.bytebach.model.Field;
import com.bytebach.model.InvalidOperation;
import com.bytebach.model.Table;
import com.bytebach.model.Value;

public class MyTable implements Table {

	private String name;
	private List<Field> fields;
	private List<Field> keys;
	private List<List<Value>> rows;
	private Map<Field, List<Value>> keyFieldValues = new HashMap<Field, List<Value>>();

	public MyTable(String name, List<Field> fields) {
		this.name = name;
		this.fields = fields;
		this.keys = new ArrayList<Field>();
		for (Field f : fields) {
			if (f.isKey()) keys.add(f);
		}
		
		// EXTENDS ATTAYLIST to check for duplicate keys
		this.rows = new RowList<List<Value>>();
	}

	@Override
	public String name() {

		return name;
	}

	@Override
	public List<Field> fields() {

		return fields;
	}

	@Override
	public List<List<Value>> rows() {
		// check if row with same key field as another
		return rows;
	}

	@Override
	public List<Value> row(Value... keys) {
		if (keys.length != this.keys.size()) throw new InvalidOperation(keys.length + " keys provided but there are" + this.keys.size() + " key fields.");
		for (List<Value> r : rows) {
			boolean foundIt = true;
			for (int i = 0; i < keys.length; i++) {
				if (!(keys[i]).equals(r.get(i))) foundIt = false;
			}
			if (foundIt) {
				// copies the found List<Value> into a new ValueList<Value> then returns it ... @_@! Castin ghere wouldn't work. WHY? 
				ValueList<Value> tmpRow = new ValueList<Value>();
				tmpRow.addAll(r);
				return tmpRow;
			};
		}
		return null;
	}

	@Override
	public void delete(Value... keys) {
		List<Value> toDelete = row(keys);
		rows.remove(toDelete);
	}
	
	private class RowList<E extends List<Value>> extends ArrayList<List<Value>>{

		private static final long serialVersionUID = 4292514328680205758L;

		@Override
		public boolean add(List<Value> e) {
			System.out.println("Adding new RowList");
				for (int i = 0; i < e.size(); i++) { // for each entry in the row
					Field currentField = fields.get(i);
					if (currentField.isKey()) { // check if relative there is one key field or more...
						if (keyFieldValues.containsKey(currentField) && keyFieldValues.get(currentField).contains(e.get(i))) { // if keyFieldValues already conatins this key field
							throw new InvalidOperation("");
						} else if (!keyFieldValues.containsKey(currentField)) {
							/// make new type of list<Value>
							ValueList<Value> keyVals = new ValueList<Value>();
//							List<Value> keyVals = new ArrayList<Value>();
							keyVals.add(e.get(i)); // add the value to key values list
							keyFieldValues.put(currentField, keyVals); // put back the updated key values list onto the key field map
							return super.add(e);
						}
					}
					return super.add(e);
				}
				return false;
			}
		}

	private class ValueList<Value> extends ArrayList<Value>{
		
		private static final long serialVersionUID = -3382912876372169860L;

		@Override
		public Value set(int index, Value element) {
			// we have a list of Values, we need to check is the lavlue at index is key ... 
			System.out.println("ValueList: set()");
			if (fields.get(index).isKey()){
				System.out.println("Is a key field");
				throw new InvalidOperation("Key fields cannot be set. Invalid operation");
			} else {
				System.out.println("Is not a key field, valid call to set()");
				Value tmpVal = super.set(index, element);
				System.out.println(element);
				System.out.println(tmpVal);
				return tmpVal;
//				I tried to duplicate the super method, but is declared as transient. What is a transient declaration ?? 
//				Value oldValue = super.elementData(index);
//		        elementData[index] = element;
//		        return oldValue;
			}
		}	
	}
}
//	
//	private class ValueList extends AbstractList<Value>{
//
//		@Override
//		public Value get(int index) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public int size() {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//}
//}
	
	
	
	
//	// Check if arrayList has same key-field.value as another:
//	// For each value in row, check if field[position] is a key. If is a key, check if value of key is duplicate or unique. 
//	// As long as there is one unique value form the key-vlaues allow adding
//	
//	boolean allowAdding = false;
//	
//	System.out.println("ADDDING : " + row);
//	for (int i = 0; i < row.size(); i++) { // for each entry in the row
//		System.out.println(row.get(i));
//		
//		Field currentField = fields.get(i);
//		if (currentField.isKey()) { // check if relative there is one key field or more... 
//			System.out.println("KEY FIELD : " + currentField.title());
//			System.out.println("----");
//			System.out.println(keyFieldValues.containsKey(currentField));
//			
//			if (keyFieldValues.containsKey(currentField)) { // if keyFieldValues already conatins this key field
//				System.out.println("----");
//				
//				if (!keyFieldValues.get(i).contains(row.get(i))){ // if values contains this field but not this value, ok to add  
//
//					List<Value> keyValueList = keyFieldValues.get(fields.get(i));
//					keyValueList.add(row.get(i)); // add the value to key values list
//					// then add this value to map list 
//					keyFieldValues.put(currentField, keyValueList); // put back the updated key values list onto the key field map
//					// Finally set allow adding true
//					allowAdding = true;
//				} else { // keyFieldValues contain this key field with same value
//					allowAdding = false;
//				}
//			} else { // keyFieldValues doesn't contain key-field, need to add key-field and value
//				List<Value> values = new ArrayList<Value>();
//				values.add(row.get(i));
//				keyFieldValues.put(currentField, values);
//				allowAdding = true;
//			} 
//			
//			System.out.println("%%%%%%");
//			if (!allowAdding){
//				throw new InvalidOperation("");
//			}
//		}
//	}
//	return super.add(row);