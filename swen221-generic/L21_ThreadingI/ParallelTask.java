	import java.util.concurrent.Callable;
	import java.util.concurrent.ExecutorService;
	import java.util.concurrent.Executors;
	import java.util.concurrent.Future;
	import java.util.*;

	public class ParallelTask{
		// Method we want to be threaded
		protected static Integer counter(int number){
			return number;
		}
		
		public static void main(String[] args) throws Throwable{
			
			ExecutorService executor = Executors.newFixedThreadPool(10);
			List<Future<Integer>> future = new ArrayList<>();  
			
			for (int i=0;i<1000;i++){
				// the vlaue passd to the executor must be final or effectively final
				final int r = counter(i);
				future.add(executor.submit(new Callable<Integer>(){
					public Integer call() throws Exception {
						return r;
					}
			
				}));
			}
			executor.shutdown();
			for (int i=0;i<1000;i++){
				System.out.println(future.get(i));
			}
		}

	}