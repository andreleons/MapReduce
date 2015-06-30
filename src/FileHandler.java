import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Reads in and begins the processing of the weather files
 * Uses futures and callables along with a thread pool to
 * process large files through a psuedo map reduce algorithm
 * @author Andre Stockling
 *
 */
public class FileHandler {

	BufferedReader bufferedReader = null;
	//Holds All of the Station Data objects
	ArrayList<StationData> stationData = new ArrayList<StationData>();

	/**
	 * Constructor reads in the Station Data
	 * and then begins the first pass through the data
	 */
	FileHandler() {
		readStationData();
		firstRun();
	}

	/**
	 * Reads the station data files and stores the objects in an ArrayList
	 */
	private void readStationData() {
		try {
			bufferedReader = new BufferedReader(new FileReader("ghcnd-stations.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String thisLine;

		try {
			//Whiles there are more lines to read, add each line as a Station
			while ((thisLine = bufferedReader.readLine()) != null) {
				StationData sd = new StationData(); 
				sd.setStationData(thisLine);
				stationData.add(sd);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * First pass through the data, Creates a single future for every file
	 * and executes them using a thread pool
	 */
	private void firstRun() {
		//Get ExecutorService from Executors utility class, thread pool size is 15
		ExecutorService executor = Executors.newFixedThreadPool(15);
		//create a list to hold the Future object associated with Callable
		List<Future<ResultsTracker>> list = new ArrayList<Future<ResultsTracker>>();
		//Queue that will hold all of the files
		ConcurrentLinkedQueue<File> files = new ConcurrentLinkedQueue<File>();
		//Folder that holds all of the files we wish to process
		File directory = new File("ghcnd_hcn");

		//For each subfile, add that file to the queue
		for (File file : directory.listFiles()) {
			files.add(file);
		}
		//Create MyCallable instance
		Callable<ResultsTracker> callable = new MyCallable(files);

		//For each subfile create a single future
		for(int i=0; i< directory.listFiles().length; i++){
			//submit Callable tasks to be executed by thread pool
			Future<ResultsTracker> future = executor.submit(callable);
			//add Future to the list, we can get return value using Future
			list.add(future);
		}

		//Queue that holds the results of the previous futures
		ConcurrentLinkedQueue<ResultsTracker> secondQueue = new ConcurrentLinkedQueue<ResultsTracker>();
		//For each future, add its results to our "secondQueue"
		for (Future<ResultsTracker> newFuture : list) {
			try {
				secondQueue.add(newFuture.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		//Shutdown the threadpool
		executor.shutdown();
		//Call method that executes our second pass through the data
		secondRun(secondQueue);
	}

	/**
	 * Does second pass through the data
	 * Takes all previous future results and slims them down to 4 futures
	 * each holding the top 5 results out of all the data they processed
	 * @param queue
	 */
	private void secondRun(ConcurrentLinkedQueue<ResultsTracker> queue) {
		//Create new thread pool of size 4
		ExecutorService executor = Executors.newFixedThreadPool(4);
		//List to hold our 4 futures
		List<Future<ResultsTracker>> list = new ArrayList<Future<ResultsTracker>>();
		//Creates an instance of our second Callable class
		Callable<ResultsTracker> secondCallable = new MyCallable2(queue);
		//Create for 4 futures
		for(int i = 0; i < 4; i++){
			//submit Callable tasks to be executed by thread pool
			Future<ResultsTracker> future = executor.submit(secondCallable);
			//add Future to the list, we can get return value using Future
			list.add(future);
		}
		//Queue that will hold the results of the previous 4 futures, to be parsed in the third pass
		ConcurrentLinkedQueue<WeatherData[]> thirdQueue = new ConcurrentLinkedQueue<WeatherData[]>();
		//For each future, add it's results to "thirdQueue"
		for (Future<ResultsTracker> newFuture : list) {
			try {
				thirdQueue.add(newFuture.get().getValues());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		//Shutdown the thread pool
		executor.shutdown();
		//Call the method that controls the third pass through the data
		thirdRun(thirdQueue);
	}

	/**
	 * Controls the third pass through the data and gets the final results
	 * @param finalQueue
	 */
	private void thirdRun(ConcurrentLinkedQueue<WeatherData[]> finalQueue) {
		//results tracker that holds the final 5 Data Points we wanted
		ResultsTracker finalResults = new ResultsTracker();
		//For each previous future, attmept to add it's weather data to the final result
		for (int i = 0; i < 4; i++) {
			WeatherData[] temp = finalQueue.remove();
			for (int j = 0; j < temp.length; j++) {
				//Only the 5 highest will be kept
				finalResults.add(temp[j]);
			}
		}
		printResults(finalResults);
	}

	/**
	 * Prints the final Results
	 * @param results
	 */
	private void printResults(ResultsTracker results) {
		System.out.println(results.getWd1());
		printMatch(results.getWd1().getID());
		System.out.println(results.getWd2());
		printMatch(results.getWd2().getID());
		System.out.println(results.getWd3());
		printMatch(results.getWd3().getID());
		System.out.println(results.getWd4());
		printMatch(results.getWd4().getID());
		System.out.println(results.getWd5());
		printMatch(results.getWd5().getID());
	}

	/**
	 * Helper method that matches a weather Data ID 
	 * With a station, and prints the station data
	 * @param id
	 */
	private void printMatch(String id) {
		boolean found = false;
		int i = 0;
		while (!found && i < stationData.size()) {
			if (stationData.get(i).getID().equals(id)) {
				System.out.println(stationData.get(i) + "\n");
				found = true;
			}
			i++;
		}
	}
}
