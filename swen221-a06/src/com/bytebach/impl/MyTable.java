package com.bytebach.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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

	@SuppressWarnings("serial")
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
		// CHECK IF THE PROVIDED TYPE IS THE RIGHT ONE
		if (keys.length != this.keys.size()) throw new InvalidOperation(keys.length + " keys provided but there are" + this.keys.size() + " key fields.");
		for (List<Value> r : rows) {
			boolean foundIt = true;
			for (int i = 0; i < keys.length; i++) {
				if (!(keys[i]).equals(r.get(i))) foundIt = false;
			}
			if (foundIt) return r;
		}
		return null;
	}

	@Override
	public void delete(Value... keys) {
		List<Value> toDelete = row(keys);
		rows.remove(toDelete);
	}

	// private void deleteByIndex()
	
	public class RowValue<Value> extends ArrayList<Value>{
		
		@Override
		public boolean add(Value e) {
			// TODO Auto-generated method stub
			return false;
		}
	}
	
	public class RowList<E extends List<Value>> extends ArrayList<List<Value>>{

		@Override
		public boolean add(List<Value> e) {
			System.out.println("AAAAAAAAAAAAAAAAAAA");
				for (int i = 0; i < e.size(); i++) { // for each entry in the row
					Field currentField = fields.get(i);
					if (currentField.isKey()) { // check if relative there is one key field or more...
						if (keyFieldValues.containsKey(currentField) && keyFieldValues.get(currentField).contains(e.get(i))) { // if keyFieldValues already conatins this key field
							throw new InvalidOperation("");
						} else if (!keyFieldValues.containsKey(currentField)) {
							List<Value> keyVals = new ArrayList<Value>();
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

		public List<Value> set(int index, List<Value> element) {
			// TODO Auto-generated method stub
			System.out.println("-----------------");
			return null;
		}
}
	
	
	
	
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