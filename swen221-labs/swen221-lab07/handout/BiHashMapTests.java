import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class BiHashMapTests {
	
	@Test public void testPut() {
		BiHashMap map = new BiHashMap();
		map.put("Hello", "World");
		assertTrue(map.get("Hello").equals("World"));
		assertTrue(map.getKeys("World").size() == 1);
		assertTrue(map.getKeys("World").contains("Hello"));
		assertTrue(map.containsKey("Hello"));
		assertTrue(map.containsValue("World"));
	}
	
	@Test public void testRemove() {
		BiHashMap map = new BiHashMap();
		map.put("Hello", "World");
		map.remove("Hello");
		assertTrue(map.get("Hello") == null);
		assertTrue(map.getKeys("World").size() == 0);
		assertFalse(map.containsKey("Hello"));
		assertFalse(map.containsValue("World"));
	}
	
	@Test public void testClear() {
		BiHashMap map = new BiHashMap();
		map.put("Hello", "World");
		map.clear();
		assertTrue(map.get("Hello") == null);
		assertTrue(map.getKeys("World") == null);
		assertFalse(map.containsKey("Hello"));
		assertFalse(map.containsValue("World"));
	}
	
	@Test public void testPutAll() {
		HashMap<String,String> omap = new HashMap<String,String>();		
		omap.put("Hello", "World");
		omap.put("Dave", "World");
		omap.put("Something", "Else");
		
		BiHashMap map = new BiHashMap();
		map.putAll(omap);
		
		for(Map.Entry<String, String> e : omap.entrySet()) {
			assertTrue(map.get(e.getKey()).equals(e.getValue()));			
		}
		
		assertTrue(map.getKeys("World").size() == 2);
		assertTrue(map.getKeys("World").contains("Hello"));
		assertTrue(map.getKeys("World").contains("Dave"));
		assertTrue(map.getKeys("Else").size() == 1);
		assertTrue(map.getKeys("Else").contains("Something"));				
	}
		
	@Test public void testEntrySet() {
		String[][] data = {{"Hello", "World"}, {"Dave", "World"},
				{"Something", "Else"}};
		
		HashMap<String,String> omap = new HashMap<String,String>();		
		HashMap<String,Set<String>> rmap = new HashMap<String,Set<String>>();
		
		for(String[] p : data) {
			omap.put(p[0],p[1]);
			Set<String> r = rmap.get(p[1]);
			if(r == null) {
				r = new HashSet<String>();
				rmap.put(p[1], r);
			}
			r.add(p[0]);
		}
		
		BiHashMap map = new BiHashMap();
		map.putAll(omap);
		for(Map.Entry e : map.entrySet()) {
			assertTrue(omap.get(e.getKey()).equals(e.getValue()));
			// Following line needed to convert set returned by getKeys() into a
			// HashSet for the comparison to work.
			HashSet<String> keys = new HashSet<String>(map.getKeys(e.getValue()));
			assertTrue(rmap.get(e.getValue()).equals(keys));
		}
	}

//Non trivial tests!
  @Test
  public void testUpdate1() {
    BiHashMap b = new BiHashMap();
    b.put("Sam", "Person");
    b.put("Marco", "Person");
    b.put("Mary", "Person");
    assertEquals(3, b.keySet().size());
    assertEquals(3, b.getKeys("Person").size());
    assertTrue(b.getKeys("Person").size() <= b.keySet().size());
    b.put("Sam", "Person2");
    assertEquals(2, b.getKeys("Person").size());
  }
  
  @Test
  public void testUpdate2() {
    BiHashMap b = new BiHashMap();
    b.put("Sam", "Person");
    assertEquals(1, b.keySet().size());
    assertEquals(1, b.getKeys("Person").size());
    assertTrue(b.getKeys("Person").size() <= b.keySet().size());
    b.put("Sam", "Person2");
    assertEquals(0, b.getKeys("Person").size());
  }
  
  @Test
  public void testUpdate3() {
    BiHashMap b = new BiHashMap();
    b.put("Sam", "Person");
    b.put("Sam", "Person");
    assertTrue(b.getKeys("Person").size() <= b.keySet().size());
    assertEquals(1, b.getKeys("Person").size());
  }

}
