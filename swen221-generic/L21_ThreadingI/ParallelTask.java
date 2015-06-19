import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.*;
import java.util.Random;

public class ParallelTask{

	public static void main(String args[]) throws Throwable{
		System.out.println("MultiThreading...");
		
		ExecutorService pool = Executors.newFixedThreadPool(24);

		Integer size = 1000000;
		Calculator calc = new Calculator();
		Integer[] fArray = new Integer[size];
		Random randomGenerator = new Random();
		
		for (int i=0;i<size;i++){
			fArray[i] = randomGenerator.nextInt(15);

		}

		ArrayList<Future<Long>> results = new ArrayList();
		
		for (int i=0; i<size;i++){
			final int num = i;
			results.add(pool.submit(new Callable<Long>() {
				public Long call() throws Exception {
					return calc.factorial(fArray[num]);
				}
			}));
		}
		// retrive results form future
		for (int i=0;i<size;i++){
			Long result = results.get(i).get();
			System.out.printf("%d Factorial of %d is %d\n",i, fArray[i], result);
		}
		pool.shutdown();
	}
}

class Calculator{

	public synchronized Long factorial(long n){
		long factorial = n;
		for(long i=(n-1);i>1;i--){
			factorial = factorial * (long) i;
		}
		return factorial;
	}
}
