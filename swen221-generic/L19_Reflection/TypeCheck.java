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
		// make an instance of the secret method in testClass
		TestClass t = new TestClass();
		
		// t.secretMethod(); // this doesn't compile because the method is private
		
		// bu we can get the type of the class ... then hack it
  		
		// find out what methods the class has 
		Method[] methods = tClass.getDeclaredMethods();
		for (Method m : methods){
			System.out.println(m);
		}
		
		// find out which field has
		Field[] fields = tClass.getDeclaredFields();
		for (Field f : fields){
			System.out.println(f);
		}
		
		// grab the first field, the private one
		Field f = tClass.getDeclaredField("secretField");

		// set accessible true (necessary for private fields)
		f.setAccessible(true);
		
		System.out.println("Value of secret field: "+f.get(t));
		// change the vlaue of private field
		f.set(t,10);
		System.out.println("Value of secret field after hack: "+f.get(t));
		System.out.println("-------");
	}
}