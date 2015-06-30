/**
 * Each Results Tracker holds 5 WeatherData
 * Added data gets dropped if it's not one of the top 5 results
 * @author Andre Stockling
 *
 */
public class ResultsTracker {
	//Top 5 Weather Data
	private WeatherData wd1 = new WeatherData();
	private WeatherData wd2 = new WeatherData();
	private WeatherData wd3 = new WeatherData();
	private WeatherData wd4 = new WeatherData();
	private WeatherData wd5 = new WeatherData();

	/**
	 * Tests if the WeatherData is bigger than any of the results
	 * in the current top 5, and adds if it does
	 * Automatically pushes down the other results
	 * @param wd WeatherData to test and add
	 */
	public void add(WeatherData wd) {
		if (wd.getValue() > wd1.getValue()) {
			replace1(wd);
		}
		else if (wd.getValue() > wd2.getValue()) {
			replace2(wd);
		}
		else if (wd.getValue() > wd3.getValue()) {
			replace3(wd);
		}
		else if (wd.getValue() > wd4.getValue()) {
			replace4(wd);
		}
		else if (wd.getValue() > wd5.getValue()) {
			replace5(wd);
		}
	}

	/**
	 * Returns the #1 Result
	 * @return WeatherData
	 */
	public WeatherData getWd1() {
		return wd1;
	}

	/**
	 * Sets the #1 Result
	 * @param wd1 WeatherData
	 */
	public void setWd1(WeatherData wd1) {
		this.wd1 = wd1;
	}

	/**
	 * Returns the #2 Result
	 * @return WeatherData
	 */
	public WeatherData getWd2() {
		return wd2;
	}

	/**
	 * Sets the #2 Result
	 * @param wd1 WeatherData
	 */
	public void setWd2(WeatherData wd2) {
		this.wd2 = wd2;
	}

	/**
	 * Returns the #3 Result
	 * @return WeatherData
	 */
	public WeatherData getWd3() {
		return wd3;
	}

	/**
	 * Sets the #3 Result
	 * @param wd1 WeatherData
	 */
	public void setWd3(WeatherData wd3) {
		this.wd3 = wd3;
	}

	/**
	 * Returns the #4 Result
	 * @return WeatherData
	 */
	public WeatherData getWd4() {
		return wd4;
	}

	/**
	 * Sets the #4 Result
	 * @param wd1 WeatherData
	 */
	public void setWd4(WeatherData wd4) {
		this.wd4 = wd4;
	}

	/**
	 * Returns the #5 Result
	 * @return WeatherData
	 */
	public WeatherData getWd5() {
		return wd5;
	}

	/**
	 * Sets the #5 Result
	 * @param wd1 WeatherData
	 */
	public void setWd5(WeatherData wd5) {
		this.wd5 = wd5;
	}

	/**
	 * Replaces the #1 Results
	 * Pushes down lower results
	 * @param newData The New #1 WeatherData
	 */
	public void replace1(WeatherData newData) {
		replace2(wd1);
		wd1 = newData;
	}

	/**
	 * Replaces the #2 Results
	 * Pushes down lower results
	 * @param newData The New #2 WeatherData
	 */
	public void replace2(WeatherData newData) {
		replace3(wd2);
		wd2 = newData;
	}

	/**
	 * Replaces the #3 Results
	 * Pushes down lower results
	 * @param newData The New #3 WeatherData
	 */
	public void replace3(WeatherData newData) {
		replace4(wd3);
		wd3 = newData;
	}

	/**
	 * Replaces the #4 Results
	 * Pushes down lower results
	 * @param newData The New #4 WeatherData
	 */
	public void replace4(WeatherData newData) {
		replace5(wd4);
		wd4 = newData;
	}

	/**
	 * Replaces the #5 Results
	 * @param newData The New #1 WeatherData
	 */
	public void replace5(WeatherData newData) {
		wd5 = newData;
	}

	/**
	 * Creates a string that represents the objects attributes
	 */
	@Override
	public String toString() {
		return  wd1 + "\n" + wd2 + "\n" + wd3
				+ "\n" + wd4 + "\n" + wd5;
	}

	/**
	 * Creates a string that represents the values in the top 5 Weather Results
	 * @return
	 */
	public String toStringValue() {
		return "ResultsTracker [wd1=" + wd1.getValue() + ", wd2=" + wd2.getValue() + ", wd3=" + wd3.getValue()
				+ ", wd4=" + wd4.getValue() + ", wd5=" + wd5.getValue() + "]";
	}

	/**
	 * Returns the top 5 results in an Array
	 * @return WeatherData[]
	 */
	public WeatherData[] getValues() {
		WeatherData[] results = new WeatherData[]{wd1, wd2, wd3, wd4, wd5};
		return results;
	}
}
