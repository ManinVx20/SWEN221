import java.lang.reflect.Method;
import java.lang.reflect.Field;

class TestClass {

	private final int secretField = 22;

	private void secretMethod(){
		System.out.println("Secret method");
	}
}

public class TypeCheck {
	public static void main(String args[]) throws NoSuchFieldException, IllegalAccessException {
		// String test = new String("ssss");
		// Class<? extends String> c1 = test.getClass();
		// Class<?> c2 = test.getClass();
		// System.out.println(c2);
		// System.out.println(test.getClass());
		// System.out.println(String.class);
		// This is like python, when you call dir(cmd)
		// Object o = test;
		// Class<?> oClass = o.getClass();
		// System.out.println(oClass);
		// System.out.println(String.class);
		// Method ms[] = oClass.getDeclaredMethods();
		// for (Method m:ms){
		// 	System.out.println("Method: "+m.getName());
		// }
		// --------------------------------------------
		// make an instance of the secret method in testClass
		TestClass t = new TestClass();
		// t.secretMethod(); // this doesn't compile because the method is private
		Class<?> tClass = t.getClass(); // get the type of class
		Method[] methods = tClass.getDeclaredMethods();
		for (Method m : methods){
			System.out.println(m);
		}
		Field[] fields = tClass.getDeclaredFields();
		for (Field f : fields){
			System.out.println(f);
		}
		// grab the first field, the private one
		Field f = fields[0];
		f.setAccessible(true);
		System.out.println("Value of secret field: "+f.get(t));
		f.set(t,10); // set the secret field to 10
		System.out.println("Value of secret field after hack: "+f.get(t));
		System.out.println("-------");
		// f.set();
	}
}