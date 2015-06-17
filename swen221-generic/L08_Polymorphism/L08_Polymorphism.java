class Cat{
	public void action(Cat c){
		System.out.println("1");
	}
}
class Kitten extends Cat {
	public void action(Kitten k){
		System.out.println("2");
	}

}

class L08_Polymorphism {
	public static void main(String[] args){
		Cat gypsy = new Cat();
		Cat spike = new Kitten();
		Kitten teddy = new Kitten();
		gypsy.action(teddy);
		spike.action(teddy);
		teddy.action(teddy);
	}
}
