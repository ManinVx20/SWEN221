public class ArraySet implements SimpleSet {

	private Object[] items;
private int count; // counts number of elements currently used.

public ArraySet() {
	this.items = new Object[2];
	this.count = 0;
}

public void add(Object item) {
	if (item == null) {
		throw new IllegalArgumentException("Cannot add null!");
	}
	if (count == items.length){
		Object[] tmp = new Object[count*2];
		System.arraycopy(items,0,tmp,0,count);
		items = tmp;
	}
	items[count] = item;
	count = count + 1;
}

public boolean contains(Object o) {
	for (int i = 0; i != this.count; ++i) {
		if (items[i].equals(o)) {
			return true;
		}
	}
	return false;
}
}