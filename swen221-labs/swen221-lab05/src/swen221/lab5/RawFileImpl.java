package swen221.lab5;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;

public class RawFileImpl implements RowFile {

	// Map<Identifier,int[]> rowsMap;
	// 2 list one containing identifiers and one containing int[]
	Map<Identifier, int[]> dataMap;
	List<Identifier> ids;

	public RawFileImpl() {
		this.ids = new ArrayList<Identifier>();
		this.dataMap = new HashMap<Identifier, int[]>();
	}

	@Override
	public List<Identifier> getIdentifiers() {
		return ids;
	}

	@Override
	public void addRow(Identifier id, int[] data) throws DuplicateIdException {
		if (ids.contains(id)) {
			throw new DuplicateIdException();
		} else {
			ids.add(id);
			Collections.sort(ids);
			this.dataMap.put(id, data);
		}
	}

	@Override
	public int[] getRow(Identifier id) throws MissingDataException {
		if (ids.contains(id)) {
			int[] row = dataMap.get(id);
			return row;
		} else {
			throw new MissingDataException();
		}
	}

	@Override
	public Identifier getRowId(int index) throws IndexOutOfBoundsException {
//		if (index > 0 && index < ids.size()) {
			return ids.get(index);
//		} else {
//			throw new IndexOutOfBoundsException();
//		}
	}

	@Override
	public int getRowTotal(Identifier id) throws MissingDataException {
		int[] data = dataMap.get(id);
		if (data != null) {
			int tot = 0;
			for (int i = 0; i < data.length; i++) {
				tot = tot + data[i];
			}
			return tot;
		} else {
			throw new MissingDataException();
		}
	}

	@Override
	public int getRowAverage(Identifier id) throws MissingDataException {
		int[] data = this.dataMap.get(id);
		if (data == null) {
			throw new MissingDataException();
		} else {
			int sum = 0;
			for (int i = 0; i < data.length; i++) {
				sum = sum + data[i];
			}
			return sum / data.length;
		}
	}

	@Override
	public String toHtmlTable() {
//		String html = "";
		StringBuilder html = new StringBuilder("");
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.html"), "utf-8"))) {

			html.append("<html><body><table border=\"1\"><tr><td>Name</td><td>dept</td><td>dd</td><td>yu</td><td>total</td><td>average</td></tr>");

			for (Identifier id : ids){
				try {
					html.append("<tr>");
					html.append("<td>"+id.getName()+"</td>"+"<td>"+id.getDept()+"</td>"+"<td>"+getRow(id).length+"</td>"+"<td>"+getRow(id).hashCode()+"</td>"+"<td>"+getRowTotal(id)+"</td>"+"<td>"+getRowAverage(id)+"</td>");
					html.append("</tr>");
				} catch (MissingDataException e) {
					e.printStackTrace();
				}
			}
			html.append("</table>");
			System.out.println(html.toString());
			return html.toString();
		} catch (IOException ex) {
			System.out.println("Error with file");
		}
		return "";

	}
}
