import java.io.BufferedReader;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Mycallable2 class implements callable
 * call() method parses the top 5 results from a queue of ResultsTracker
 * @author Andre Stockling
 *
 */
public class MyCallable2 implements Callable<ResultsTracker>{
	int filesRead = 1;
	//List of Results Tracker to parse
	ConcurrentLinkedQueue<ResultsTracker> files;
	
	/**
	 * Constructor
	 * @param newFiles List of Results Trackers to be parsed
	 */
	MyCallable2(ConcurrentLinkedQueue<ResultsTracker> newFiles) {
		files = newFiles;
	}
	
	/**
	 * Call() method pulls a results tracker off queue and compares the values to it's own
	 * Returns a single tracker with the top 5 results parsed
	 */
    @Override
    public ResultsTracker call() throws Exception {
    	ResultsTracker temp;
    	ResultsTracker topResults = new ResultsTracker();
    	while (files.size() != 0) {
    		try {
               	temp = files.remove();
            	readResultsData(temp, topResults);
    		} catch (NoSuchElementException e) {

    		}
    	}
    	return topResults;
    }

    /**
     * Compares previous results to current Tracker
     * @param previousResults The results tracker being parsed
     * @param results the call() methods results tracker that will be returned
     */
    private void readResultsData(ResultsTracker previousResults, ResultsTracker results) {
    	BufferedReader bufferedReader = null;

    	for (int i = 0;  i < previousResults.getValues().length; i++) {
    		results.add(previousResults.getValues()[i]);
    	}
    }
    
}