public interface SimpleSet {
	/**
	 * Add a new item into this SimpleSet.  If item is already
	 * stored in this SimpleSet, then this method does nothing.
	 * Otherwise, it stores item in this SimpleSet.
	 */
	public void add(Object item);
	/**
	 * Check whether an object is currently stored in this SimpleSet
	 * which equals() the given item.
	 */
	public boolean contains(Object item);
}

	