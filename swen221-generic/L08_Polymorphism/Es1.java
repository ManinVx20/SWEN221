abstract class Parent {
	int x=100;
	
	Parent (int x){
		this.x = x;
	}
	abstract int m(int x);
	
	static Parent k (Parent x){
		return x;
	}
	
	int f(int x){
		return m(x+1);
	}
}

class Heir extends Parent{
	static int x = 200;

	Heir(int x){
		super(x+1);
	}

	Heir(){
		this(3); // points to constructor in this class
	}

	int m(int x){
		return x+2;
	}

	int n(int x){
		return this.x + x;
	}
}

public class Es1 {
	public static void main(String[] argv){
		Parent p = new Heir();
		Heir h = new Heir();
		// System.out.println(p.f(h.x)); // heir.x -> 200 // p.f(200) -> m(200+1) // 201+2 = 203
		// DOESNT COMPILE System.out.println((p.k(new Parent())).x);
		// System.out.println((p.k(h)).x); // h is initialised with x=4 // p.k(4) then calls .x -> 4
		// DOESNT COMPILES System.out.println((p.n(5)).x);
		// System.out.println(new Heir().x=5); // new heir -> this.x = 4; .x=5 -> 5
		// System.out.println((h.n(p.x))); // new heir -> this.x = 4 this.x is 200 --> 204 ?
		// System.out.println((h.n(p.x))); // new heir -> this.x = 4 this.x is 200 --> 204 ?
	}
}
