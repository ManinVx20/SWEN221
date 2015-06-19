import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.*;


class ParallelTaskII {
	public static void main(String args[]){
		System.out.println("----");
		// create a pool executorService
		final ExecutorService pool = Executors.newFixedThreadPool(10);
		Counter c = new Counter();
		// pass a new callable to pool
		for (int i=0;i<100000;i++){
			pool.submit(new Runnable(){
				public void run(){
					c.inc();
				}
			});
		}
		pool.shutdown();
	}
}

class Counter{
	private int count;
	public synchronized void inc(){
		count = count + 1;
	}
	public synchronized int get(){
		return count;
	}
}