interface SimpleInterface {
	String foo = "10";
	String getName();
}

class SimpleInterfaceClass implements SimpleInterface{
	
	public String getName(){
		return "Bla";
	}

	public static void main(String args[]){
		System.out.println(foo);
		System.out.println("dd");
	}
}