package com.bytebach.impl;

import com.bytebach.model.InvalidOperation;
import java.util.*;
import com.bytebach.model.*;

public class MyDatabase implements Database {

	private ArrayList<Table> tables;

	public MyDatabase() {

		super();
		tables = new ArrayList<Table>();
	}

	@Override
	public Collection<? extends Table> tables() {
		return tables;
	}

	@Override
	public Table table(String name) {
		for (Table t : tables) {
			if (t.name().equals(name)) return t;
		}
		return null;
	}

	@Override
	public void createTable(String name, List<Field> fields) {
		// check if table already exists
		if (table(name) != null) {
			throw new InvalidOperation("Unable to create table with duplicate name");
		} else {
			tables.add(new MyTable(this, name, fields));
		}
	}

	@Override
	public void deleteTable(String name) {

		Table toDelete = table(name);
		if (toDelete != null) tables.remove(toDelete);
		return;
	}
}