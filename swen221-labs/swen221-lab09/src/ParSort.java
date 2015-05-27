import java.io.*;
import java.util.*;
import swen221.concurrent.Job;
import swen221.concurrent.ThreadPool;

public class ParSort {
	
	// =======================================================================
	// SWEN221: Look at this
	// =======================================================================

	/**
	 * The following implements a sequential quick sort.
	 * 
	 * @param list
	 *            --- data to be sorted.
	 * @param start
	 *            --- start position within data for sort.
	 * @param end
	 *            --- end position within data for sort.
	 * @return 
	 */
	public static List<Integer> sequentialSort(List<Integer> list, int start, int end) {		
		if(start >= end) { 
			return list; 
			} // done 
		// recursive case		
		// now sort into two sections so stuff in lower section is less than
		// pivot, and remainder is in upper section.
		int pivot = list.get((start + end) / 2);
		int lower = start;
		int upper = end-1;
		
		while(lower < upper) {
			int lowerItem = list.get(lower); 
			int upperItem = list.get(upper);
			if(lowerItem < pivot) {
				// this item is not out of place
				lower++;
			} else if(upperItem > pivot) {
				// this item is not out of place
				upper--;
			} else {
				// both items are out of place --- so swap them.
				list.set(lower, upperItem);
				list.set(upper, lowerItem);
				if(upperItem != pivot) { lower++; }
				if(lowerItem != pivot) { upper--; }
			}
		}
		
		list.set(lower,pivot);
		
		pause(list,100); // to make animation in display mode better
		
		// A this point, lower == upper.
		sequentialSort(list,start,lower);
		sequentialSort(list,lower+1,end);	
		
		return list;
	}

	/**
	 * The following implements a parallel quick sort.
	 * 
	 * @param list
	 *            --- data to be sorted
	 */
	public static void parallelSort(List<Integer> data, int numProcessors) {
		// Create thread pool and Job List
		numProcessors = numProcessors/4;
		ThreadPool pool = new ThreadPool(numProcessors);
		SortJob[] jobs = new SortJob[numProcessors];
		
		// Each job will process jobSize elements
		int jobSize = data.size() / numProcessors;
		
		// Create the jobs and start them
		List<Integer> indexes = new ArrayList<Integer>();
		int index = 0;
		indexes.add(index);
		for (int i = 0; i != numProcessors; ++i) {
			if ((i + 1) != numProcessors) {
				jobs[i] = new SortJob(data, index, index + jobSize);
				indexes.add(index);
			} else {
				// for the very last job, we allocate all remaining elements
				jobs[i] = new SortJob(data, index, data.size());
				indexes.add(index);
			}
			pool.submit(jobs[i]);
			index = index + jobSize;
			indexes.add(index);
		}
		
		// Finally, gather up results and return
//		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i != numProcessors; ++i) {
			SortJob job = jobs[i];
			job.waitUntilFinished();
			// add one by one then sort ?? 
//			result.addAll(job.result);
		}
		// TEST WITH COLLECTION SORT
		Collections.sort(data);
		// INSERTION SORT
//		for (int i=0; i<indexes.size()-1; i++){
//			insertionSort(data, indexes.get(i), indexes.get(i+1));
//		}
		
//		insertionSort(data);
		// BUBBLE SORT
//		bubbleSort(data);
		
	}
	
	public static class SortJob extends Job {
		private final List<Integer> data;
		private final int start;
		private final int end;
//		private volatile List<Integer> result;
		
		public SortJob(List<Integer> data, int start, int end) {
			this.data = data;
			this.start = start;
			this.end = end;				
		}
		
		public void run() {
			sequentialSort(data,start,end);
		}
	}
	
	/**
	 * Insertion sort on nearly ordered data
	 * @param data
	 */
	public static void insertionSort(List<Integer> data){

        // insertion sort starts from second element
        for (int i = 1; i < data.size(); i++) {
            int numberToInsert = data.get(i);

            int compareIndex = i;
            while (compareIndex > 0 && data.get(compareIndex-1) > numberToInsert) {
            	data.set(compareIndex, data.get(compareIndex - 1)); // shifting element
                compareIndex--; // moving backwards, towards index 0
            }

            // compareIndex now denotes proper place for number to be sorted
            data.set(compareIndex,numberToInsert);
        }
    }
	
	public static void insertionSort(List<Integer> data ,int start, int end){

        // insertion sort starts from second element
        for (int i = start; i < end; i++) {
            int numberToInsert = data.get(i);

            int compareIndex = i;
            while (compareIndex > 0 && data.get(compareIndex-1) > numberToInsert) {
            	data.set(compareIndex, data.get(compareIndex - 1)); // shifting element
                compareIndex--; // moving backwards, towards index 0
            }

            // compareIndex now denotes proper place for number to be sorted
            data.set(compareIndex,numberToInsert);
        }
    }
	
	
	
	public static void bubbleSort(List<Integer> data) {
		
		int j;
		boolean flag = true;   // set flag to true to begin first pass
		int temp;   //holding variable

		while ( flag )
		{
			flag= false;    //set flag to false awaiting a possible swap
			for( j=0;  j < data.size()-1;  j++ )
			{
				if ( data.get(j) > data.get(j+1) )   // change to > for ascending sort
				{
					temp = data.get(j); //swap elements
					data.set(j, data.get(j+1));
					data.set(j+1, temp);
					flag = true; //shows a swap occurred  
				} 
			} 
		} 
	} 
	
	

	
	// =======================================================================
	// SWEN221: No need to look at code below this!
	// =======================================================================
	
	public static void main(String[] args) {
		boolean displayMode = false;
		boolean parallelMode = false;
		String filename = null;

		// First, some rudimentary command-line argument processing.
		if (args.length == 0) {
			System.out.println("Usage: java Main [-gui] input.dat");
			System.exit(1);
		} 
		int index = 0;
		
		while(args[index].startsWith("--")) {
			String arg = args[index++];
			if (arg.equals("--gui")) {
				displayMode = true;
			} else if(arg.equals("--parallel")){
				parallelMode = true;
			} else {
				System.out.println("Unrecognised argument encountered: " + arg);
				System.exit(1);
			}
		}
		filename = args[index];
		
		// Second, read in the data and sort it.
		try {
			int numProcessors = Runtime.getRuntime().availableProcessors();
			System.out.println("Executing on machine with "
					+ numProcessors + " processor(s).");
			List<Integer> data = readInput(new FileReader(filename));
			System.out.println("Read " + data.size() + " data items.");

			if (displayMode) {
				System.out
						.println("Running in Display Mode (so ignore timings).");
				data = new DisplayList<Integer>(data);
			}

			long start = System.currentTimeMillis();

			if(parallelMode) {
				// do a parallel quick sort
				System.out.println("Performing a PARALLEL quicksort...");
				parallelSort(data, numProcessors);
			} else {
				// perform a sequential quick sort
				System.out.println("Performing a SEQUENTIAL quicksort...");
				sequentialSort(data, 0, data.size());
			}

			long time = System.currentTimeMillis() - start;

			System.out.println("Sorted data in " + time + "ms");
			if(checkValid(data)) {
				System.out.println("Data was sorted correctly!");
			} else {
				System.out.println("!!! ERROR: data not sorted correctly");
			}
		} catch (IOException e) {
			System.out.println("I/O error: " + e.getMessage());
		}
	}

	public static boolean checkValid(List<Integer> data) {
		for(int i=1;i!=data.size();++i) {
			int previous = data.get(i-1);
			int current = data.get(i);
			if(previous > current) {
				System.out.println("PREVIOUS " + previous);
				System.out.println("CURRENT " + current);
				// invalid sort
				return false;
			}
		}
		return true;
	}
	
	public static ArrayList<Integer> readInput(Reader input) throws IOException {		
		BufferedReader reader = new BufferedReader(input);
		
		String line;
		ArrayList<Integer> data = new ArrayList<Integer>();
		while((line = reader.readLine()) != null) {
			if(line.equals("")) { continue; } // skip blank lines
			data.add(Integer.parseInt(line));
		}
		return data;
	}
	
	// don't worry about what this method does.
	public static void pause(List<Integer> data, int delay) {
		if(data instanceof DisplayList) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			}
		}
	}
}
