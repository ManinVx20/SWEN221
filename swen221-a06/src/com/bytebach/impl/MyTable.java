package com.bytebach.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.bytebach.model.Database;
import com.bytebach.model.Field;
import com.bytebach.model.InvalidOperation;
import com.bytebach.model.ReferenceValue;
import com.bytebach.model.Table;
import com.bytebach.model.Value;


public class MyTable implements Table {
	
	protected MyDatabase parent;
	private String name;
	protected List<Field> fields;
	private List<Field> keys;
	private MyRowList rows;
	
	public MyTable(MyDatabase db, String name, List<Field> fields) {
		this.parent = db;
		this.name = name;
		this.fields = fields;
		this.rows = new MyRowList(this);
		this.keys = new ArrayList<Field>();
		for (Field f : fields) {
			if (f.isKey()) keys.add(f);
		}
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
			if (foundIt) {
				return r;
			};
		}
		return null;
	}

	@Override
	public void delete(Value... keys) {
		List<Value> toDelete = row(keys);
		rows.remove(toDelete);
	}
	
	public void deleteRef(Value... keys) {
		if (keys.length != this.keys.size()) throw new InvalidOperation(keys.length + " keys provided but there are" + this.keys.size() + " key fields.");
		for (List<Value> r : rows) {
			boolean foundIt = true;
			for (int i = 0; i < keys.length; i++) {
				for (Value v : r){
					if (v instanceof ReferenceValue) {
						ReferenceValue ref = (ReferenceValue) v;
						for (int j = 0 ; j < ref.keys().length; j++){
							if (!(keys[i]).equals(ref.keys()[0])) foundIt = false;
						}
					}
				}
			}
			if (foundIt) {
				rows.remove(r);
			};
		}
	}
}
