/**
* QUESTION 2
*/

public class Parent {
	protected String[] items;

	public Parent(String[] items){
		this.items = items;
	}

	public String get(int index){
		return items[index];
	}

	public static void main(String[] args){
		String[] items = {"Pizza", "Lasagne", "Pesto"};
		String[] items0 = new String[0]; // empty array
		String[] items00 = {}; // empty array as well
		// ------------------------------------
		// Parent parent = new Parent(items00);
		// String str = parent.get(0);
		// ------------------------------------
		// Child child = new Child(items00);
		// String str = child.get(0);
		// System.out.println(str); // null, because exception is caught
		// ------------------------------------
		// Parent fakeParent = new Child(items00);
		// String str = fakeParent.get(0);
		// System.out.println(str);
		// ------------------------------------
		String[] itemsNull = null;
		Parent parent = new Parent(itemsNull);
		String str = parent.get(0); // getting the value of a null array @_@! 
		System.out.println(str);
	}
}

class Child extends Parent {

	public Child(String[] items){
		super(items);
	}

	public String get(int index){
		try { 
			return super.get(index);
		} catch (ArrayIndexOutOfBoundsException e){
			return null;
		}
	}
}


