package com.bytebach.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.bytebach.model.Database;
import com.bytebach.model.Field;
import com.bytebach.model.InvalidOperation;
import com.bytebach.model.Table;
import com.bytebach.model.Value;


public class RowList<V extends Value> extends ArrayList<Value> {

	private static final long serialVersionUID = 1L;
	
//	private Database db;
//	private String tableName;
	
	public RowList(Table table) {
//		this.db = db;
//		this.tableName = tableName;
	}
	
	
//	@Override
	public boolean add(Value row) {
		// a row is a list of values
//		System.out.println(row);
//		System.out.println("#########");
//		row.
		return super.add(row);
		
	}
	
//	public boolean add(List<Value> row) {
//		// Check if arrayList has same key-field.value as another:
//		// For each value in row, check if field[position] is a key. If is a key, check if value of key is duplicate or unique. 
//		// As long as there is one unique value form the key-vlaues allow adding
//		
//		boolean allowAdding = false;
//		
//		System.out.println("ADDDING : " + row);
//		for (int i = 0; i < row.size(); i++) { // for each entry in the row
//			System.out.println(row.get(i));
//			
//			Field currentField = fields.get(i);
//			if (currentField.isKey()) { // check if relative there is one key field or more... 
//				System.out.println("KEY FIELD : " + currentField.title());
//				System.out.println("----");
//				System.out.println(keyFieldValues.containsKey(currentField));
//				
//				if (keyFieldValues.containsKey(currentField)) { // if keyFieldValues already conatins this key field
//					System.out.println("----");
//					
//					if (!keyFieldValues.get(i).contains(row.get(i))){ // if values contains this field but not this value, ok to add  
//
//						List<Value> keyValueList = keyFieldValues.get(fields.get(i));
//						keyValueList.add(row.get(i)); // add the value to key values list
//						// then add this value to map list 
//						keyFieldValues.put(currentField, keyValueList); // put back the updated key values list onto the key field map
//						// Finally set allow adding true
//						allowAdding = true;
//					} else { // keyFieldValues contain this key field with same value
//						allowAdding = false;
//					}
//				} else { // keyFieldValues doesn't contain key-field, need to add key-field and value
//					List<Value> values = new ArrayList<Value>();
//					values.add(row.get(i));
//					keyFieldValues.put(currentField, values);
//					allowAdding = true;
//				} 
//				
//				System.out.println("%%%%%%");
//				if (!allowAdding){
//					throw new InvalidOperation("");
//				}
//			}
//		}
//		return super.add(row);
//	}
//};

}