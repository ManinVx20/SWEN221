package com.bytebach.impl;

import java.util.ArrayList;
import java.util.List;
import com.bytebach.model.Database;
import com.bytebach.model.Field;
import com.bytebach.model.InvalidOperation;
import com.bytebach.model.Table;
import com.bytebach.model.Value;


public class MyTable implements Table {
	
	private Database parent;
	private String name;
	protected List<Field> fields;
	private List<Field> keys;
	private ListRows rows;
	
	public MyTable(Database db, String name, List<Field> fields) {
		this.parent = db;
		this.name = name;
		this.fields = fields;
		this.rows = new ListRows(this);
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
				// copies the found List<Value> into a new ValueList<Value> then returns it ... @_@! Casting here wouldn't work. WHY? -> Because 
//				ValueList<Value> tmpRow = new ValueList<Value>();
//				tmpRow.addAll(r);
//				return tmpRow;
//				ValueList newRow = new ValueList(rows);
//				newRow.addAll(r);
//				return newRow;
				return r;
			};
		}
		return null;
	}

	@Override
	public void delete(Value... keys) {
		List<Value> toDelete = row(keys);
		rows.remove(toDelete);
//		throw new InvalidOperation(null);
	}

}
