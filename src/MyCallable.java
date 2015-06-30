import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Mycallable class implements callable
 * call() method parses the top 5 results from a single file
 * @author Andre Stockling
 *
 */
public class MyCallable implements Callable<ResultsTracker>{
	int filesRead = 1;
	ConcurrentLinkedQueue<File> files;
	
	/**
	 * Constructor
	 * @param newFiles A queue of Files to be parsed
	 */
	MyCallable(ConcurrentLinkedQueue<File> newFiles) {
		files = newFiles;
	}
	
	/**
	 * Call method takes a file off the queue and parse the data
	 */
    @Override
    public ResultsTracker call() throws Exception {
    	File temp = files.remove();
    	ResultsTracker topResults = new ResultsTracker();
    	readWeatherData(temp, topResults);
    	return topResults;
    }

    /**
     * Reads the file and generates the Weather Data
     * @param file The file to be parsed
     * @param results The ResultTracker associate with the call()
     */
    private void readWeatherData(File file, ResultsTracker results) {
    	BufferedReader bufferedReader = null;
    	
    		try {
    			bufferedReader = new BufferedReader(new FileReader(file));
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		}
    		String thisLine;

    		try {
    			while ((thisLine = bufferedReader.readLine()) != null) {
    				int counter = 0;
    				//Incremented to make sure the right substring is parsed for each day
    				int j = 0;
    				//Get all 31 Days of values from each line
    				while (counter < 31) {
        				WeatherData wd = new WeatherData(); 
        				wd.setWeatherData(thisLine, Integer.valueOf(thisLine.substring(j + 21, j + 26).trim()), counter);
        				//Only count the data if the element is snow
        				if (wd.getElement().equals("SNOW")) {
        					//Only count the data if the year is 2011
        					if (wd.getYear() == 2011) {
        						if (wd.getFlag().equals(" ") || wd.getFlag().equals("")) {
                    				results.add(wd);
        						}
        					}
        				}
        				j += 8;
        				counter++;
    				}
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	
    }
    
}
     




