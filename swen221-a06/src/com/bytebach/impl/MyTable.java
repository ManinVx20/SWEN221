package com.bytebach.impl;

import java.util.ArrayList;
import java.util.List;
import com.bytebach.model.Field;
import com.bytebach.model.InvalidOperation;
import com.bytebach.model.Table;
import com.bytebach.model.Value;

public class MyTable implements Table {

	private String name;
	private List<Field> fields;
	private List<Field> keys;
	private List<List<Value>> rows;

	public MyTable(String name, List<Field> fields) {
		this.name = name;
		this.fields = fields;
		this.keys = new ArrayList<Field>();
		for (Field f : fields) {
			if (f.isKey()) keys.add(f);
		}
		this.rows = new ArrayList<List<Value>>();
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
			if (foundIt) return r;
		}
		return null;
	}
	
	@Override
	public void delete(Value... keys) {
		List<Value> toDelete = row(keys);
		rows.remove(toDelete);
	}
	
//	private void deleteByIndex()
}