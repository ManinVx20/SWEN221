class InnerClass {
	public static void main(String[] args){
		System.out.println("Test for inner class");
		TestClass t = new TestClass("Outer");
		TestClass.InnerTestClass ti = new t.InnerTestClass("foo");
		System.out.println(ti.name);
		System.out.println(t.name);
	}
}

class TestClass {

	String name;

	TestClass(String name){
		this.name = name;
	}

	public class InnerTestClass {
		
		String name;

		InnerTestClass(String name){
			this.name = name;
		}
	}
}