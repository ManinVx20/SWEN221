import java.util.*;

public class BiHashMap<T> implements Map<T,T> {

	private HashMap<T, T> keyToValues = new HashMap<T, T>();
	private HashMap<T, List<T>> valueToKeys = new HashMap<T, List<T>>();

	/**
	 * Associates the specified value with the specified key in this map. If the map previously contained a mapping for this key, the old value is replaced. Returns
	 * the previous value associated with key, or null if there was no mapping for key. (A null return can also indicate that the map previously associated null with
	 * key).
	 */
	public T put(T key, T value) {
		// first, update the value associated with this key
		T rv = keyToValues.put(key, value);
		// now, update the set of keys associated with this value
		List<T> keys = valueToKeys.get(value);
		if (keys == null) {
			keys = new ArrayList<T>();
			valueToKeys.put(value, keys);
		}
		keys.add(key);
		// finally, return the original mapping in order to ad here to Map
		// interface.
		return rv;
	}
	
	//	@Override
	//	public int size() {
	//		return keyToValues.size();
	//		}
	

	/**
	 * Copies all of the mappings from the specified map to this map (optional operation). The effect of this call is equivalent to that of calling put(k, v) on this
	 * map once for each mapping from key k to value v in the specified map. The behavior of this operation is unspecified if the specified map is modified while the
	 * operation is in progress.
	 */
	@Override
	public void putAll(Map<? extends T, ? extends T> m) {
		System.out.println("copy copy");
		// need to do something here! -- kk, let's do something !
		keyToValues.putAll(m);
		for (T key : m.keySet()){
			valueToKeys.put(m.get(key), valueToKeys.get(key));
		}
	}

	/**
	 * Removes the mapping for this key from this map if present. Returns the previous value associated with key, or null if there was no mapping for key. (A null
	 * return can also indicate that the map previously associated null with key).
	 */
	public T remove(Object key) {
		// first, update the value associated with this key
		T value = keyToValues.remove(key);
		if (value != null) {
			// now, update the set of keys associated with this value
			T keyToRemove = keyToValues.get(value);
//			List<T> keysToRemove = valueToKeys.get(keyToRemove);
			// Note, keys should not be null!
			valueToKeys.remove(value);
			// also remove the word HELLO --> form values to Keys
		}
		// finally, return the original mapping in order to ad here to Map
		// interface.
		return value;
	}

	/**
	 * Returns the value to which the specified key is mapped in this identity hash map, or null if the map contains no mapping for this key. A return value of null
	 * does not necessarily indicate that the map contains no mapping for the key; it is also possible that the map explicitly maps the key to null. The containsKey
	 * method may be used to distinguish these two cases.
	 * 
	 * @param key
	 * @return
	 */
	public T get(Object key) {
		// System.out.println(keyToValues.containsKey(key));
		if (keyToValues.containsKey(key)) {
			return keyToValues.get(key);
		}
		return null;
	}

	/**
	 * Get the set of keys associated with a particular value
	 */
	public List<T> getKeys(Object value) {
		List<T> rList = new ArrayList<T>();
		if (valueToKeys.containsKey(value)) {
			for (Map.Entry<T, T> e : keyToValues.entrySet()) {
				if (e.getValue().equals(value)) {
					rList.add((T) e.getKey());
				}
			}
		}
		return rList;
//		return null;
	}

	/**
	 * Clears both hashmaps: keyToValues and valuesToKeys
	 */
	public void clear() {
		keyToValues.clear();
		valueToKeys.clear();
	}

	/**
	 * Returns a collection view of the mappings contained in this map. Each element in the returned collection is a Map.Entry. The collection is backed by the map, so
	 * changes to the map are reflected in the collection, and vice-versa. The collection supports element removal, which removes the corresponding mapping from the
	 * map, via the Iterator.remove, Collection.remove, removeAll, retainAll, and clear operations. It does not support the add or addAll operations.
	 * 
	 * @return
	 */
	public Set<Map.Entry<T, T>> entrySet() {
		Set<Map.Entry<T, T>> entrySet = new HashSet<Map.Entry<T,T>>();
		for (Map.Entry<T, T> e : keyToValues.entrySet()) {
			entrySet.add(e);
		}
		return entrySet;
	}

	/**
	 * Returns a set view of the keys contained in this map. The set is backed by the map, so changes to the map are reflected in the set, and vice-versa. If the map
	 * is modified while an iteration over the set is in progress (except through the iterator's own remove operation), the results of the iteration are undefined. The
	 * set supports element removal, which removes the corresponding mapping from the map, via the Iterator.remove, Set.remove, removeAll retainAll, and clear
	 * operations. It does not support the add or addAll operations.
	 */
	public Set keySet() {
		return keyToValues.entrySet();
	}

	/**
	 * Returns a collection view of the values contained in this map. The collection is backed by the map, so changes to the map are reflected in the collection, and
	 * vice-versa. If the map is modified while an iteration over the collection is in progress (except through the iterator's own remove operation), the results of
	 * the iteration are undefined. The collection supports element removal, which removes the corresponding mapping from the map, via the Iterator.remove,
	 * Collection.remove, removeAll, retainAll and clear operations. It does not support the add or addAll operations.
	 * 
	 * @return
	 */
	public Collection values() {
		return keyToValues.values();
	}

	/**
	 * Returns true if this map contains no key-value mappings.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return keyToValues.isEmpty();
	}

//	@Override
//	public int size() {
//		return keyToValues.size();
//		}

	/**
	 * Returns the hash code value for this map. The hash code of a map is defined to be the sum of the hashCodes of each entry in the map's entrySet view. This
	 * ensures that t1.equals(t2) implies that t1.hashCode()==t2.hashCode() for any two maps t1 and t2, as required by the general contract of Object.hashCode.
	 * 
	 */
	public int hashCode() {
		return keyToValues.hashCode();
	}

	@Override
	public boolean containsKey(Object key) {
		return keyToValues.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return valueToKeys.containsKey(value);
	}

	/**
	 * This is some sample code to illustrate the current usage.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BiHashMap map = new BiHashMap();
		map.put("Dave", "ENGR202");
		map.put("Alex", "COMP205");
		map.put("James", "ENGR202");
		// for (String x : (List<String>) map.getKeys("ENGR202")) {
		// System.out.println("GOT: " + x);
		// }
	}

	@Override
	public int size() {
		return keyToValues.size();
	}

//	@Override
//	public T get(Object key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public T remove(Object key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void putAll(Map<? extends T, ? extends T> m) {
//		// TODO Auto-generated method stub
//		
//	}
//	*/
}
