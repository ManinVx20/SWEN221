class A {
	public static int x=1;
	public int m(A a, B b){
		return a.m(b,b)+x;
	}
}

class B extends A {
	public int m(A a1,B a2){
		if (a1==a2) return x;
		return super.m(a1,a2);
	}
}

class D extends B {
	public D(int f){
		super();
		x=3;
	}
}

public class Q4_c {
	public static void main(String args[]){
		D test = new D(3);
	}
}