import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.*;
import java.util.Random;

public class ParallelTask{

	public static void main(String args[]) throws Throwable{
		System.out.println("MultiThreading...");
		
		ExecutorService pool = Executors.newFixedThreadPool(19);

		Integer size = 1000000;
		Calculator calc = new Calculator();
		Integer[] fArray = new Integer[size];
		Random randomGenerator = new Random();
		
		for (int i=0;i<size;i++){
			fArray[i] = randomGenerator.nextInt(10);

		}

		ArrayList<Future<Integer>> results = new ArrayList<>();
		for (int i=0; i<size;i++){
			final int num = i;
			results.add(pool.submit(new Callable<Integer>() {
				public Integer call() throws Exception {
					Calculator calc = new Calculator();
					return calc.factorial(fArray[num]);
				}
			}));
		}
		// retrive results form future
		for (int i=0;i<size;i++){
			Integer result = results.get(i).get();
			System.out.printf("%d Factorial of %d is %d\n",i, fArray[i], result);
		}
		pool.shutdown();
	}
}

class Calculator{

	public Integer factorial(int n){
		int factorial = n;
		for(int i=(n-1);i>1;i--){
			factorial = factorial *i;
		}
		return factorial;
	}
}
