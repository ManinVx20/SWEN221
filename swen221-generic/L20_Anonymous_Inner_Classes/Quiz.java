public class Quiz {

	static int x = 1;
	int y = 2;
	static int z = 0;
	static class Foo {

		static int y = 3;
		int x = 4;

		static int m1() {
			return Foo.y + Quiz.x;
		}

		int m2() {
			return y + x;
		}
		class Bar {

			int x = 5;
			int y = 6;

			int m3() {
				return y + x + m1() + m2();
			}

			int m4() {
				return z + Foo.this.y + Foo.this.x;
			}
		}
	}

	public static void main(String[] args) {
		Foo foo = new Foo();
		Foo.Bar bar = foo.new Bar();
		System.out.println(foo.m1());
		System.out.println(foo.m2());
		System.out.println(bar.m3());
		System.out.println(bar.m4());
	}
}