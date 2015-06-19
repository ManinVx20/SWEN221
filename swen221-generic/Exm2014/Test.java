import java.util.ArrayList;

class IA {
	interface A{
		int m();
		void add(ArrayList<A> array);
	}
}

static class AA implements A{

	int n;

	public static void add(ArrayList<A> array){
		n = array.size();
	}

	public static int m(){
		return n;
	}
}

public class Test {
	public static void main(String[] arg){
		ArrayList<A> a = new ArrayList<AA>();
		for(int i=0;i<10;i++){
			add(a);
		}
		assert a.get(0).m()==0;
		assert a.get(1).m()==1;
		assert a.get(7).m()==7;
		assert a.get(9).m()==9;
	}
	
	
}