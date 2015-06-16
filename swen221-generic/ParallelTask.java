import java.util.ArrayList;
import java.util.concurrent.*;

public class ParallelTask {
	
	public static boolean isPrime(int n){
		for(int i=2;i<n;i++) {
			if(n%i==0)
				return false;
		}
		return true;
	}

	public static final ExecutorService pool = Executors.newFixedThreadPool(10);
	// In most cases should be 2-3 times the processors numbers

	public static void main(String[] args) throws Throwable{
		// Make a new list
		ArrayList<Future<Boolean>> results = new ArrayList<>();
		// Loop though numbers and add to list
		for (int i=0;i<100;i++){
			final int currentNumber = i;
			results.add(pool.submit(new Callable<Boolean>(){
				public Boolean call() throws Exception {
					return isPrime(currentNumber);
				}
			} ));
		}
		// Important to have 2 loops
		for (int i=0;i<10;i++){
			if(results.get(i).get()){
				System.out.println(i);
			}
		}
	}

}
