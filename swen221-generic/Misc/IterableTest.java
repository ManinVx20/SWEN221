import java.util.*;

public class IterableTest {

	public static void main(String args[]){
		SomeList list = new SomeList();
		list.run();
	}
}

class SomeList<T> implements Iterable<T> {

	private List<Integer> t = new ArrayList<Integer>();

	public Iterator<T> iterator(){
		return new MyIterator<T>();
	}

	public void run(){
		// creates a test list
		for (int i=0;i<10;i++){
			t.add(i);
		}

		// make an iterator
		Iterator<Integer> iterator = t.iterator();
		// Iterator<Integer> it = 

		for (Integer i : t){
			System.out.println(i);
		}
	}

	private class MyIterator<T> implements Iterator<T>{

		int index;

		MyIterator(){
			index = 0;
		}

		public boolean hasNext(){
			return true;
		}

		@SuppressWarnings("unchecked")
		public T next() {
			return (T) t.get(index);
		}

		public void remove(){
			//
		}
	}
}