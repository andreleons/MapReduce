/**
 * Class that Represents a single point of data at a location
 * @author Andre Stockling
 *
 */
public class WeatherData {
	private String id;
	private int year;
	private int month;
	//The type of data being tracked
	private String element;
	//Value related to the element
	private int value = -1;
	private int day;
	private String qflag1;

	//When Passed a string and a value, 
	//sets all of the appropriate fields in a Weather Data Object
	public void setWeatherData(String data, int newValue, int newDay) {
		try {
			id = data.substring(0,11);
			year = Integer.valueOf(data.substring(11,15).trim());
			month = Integer.valueOf(data.substring(15,17).trim());
			element = data.substring(17,21);
			value = newValue;
			qflag1 = data.substring(27,28);
			day = newDay;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Returns the flag string for this WeatherData
	 * @return String
	 */
	public String getFlag() {
		return qflag1;
	}
	
	/**
	 * Returns the value of this WeatherData
	 * @return int
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns the year of this data point
	 * @return int
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Returns the element type for this data point
	 * @return String
	 */
	public String getElement() {
		return element;
	}

	/**
	 * Returns the associated Station ID
	 * @return String
	 */
	public String getID() {
		return id;
	}

	/**
	 * Converts this object to a string
	 * @return String
	 */
	@Override
	public String toString() {
		return "id=" + id + ", year=" + year + ", month=" + month + ", day=" + (day + 1)
				+ ", element=" + element + ", value=" + value + ", qflag1="
				+ qflag1;
	}


}
