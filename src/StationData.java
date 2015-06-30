/**
 * Class that represents a single Weather Station
 * @author Andre Stockling
 *
 */
public class StationData {
	//Station ID
	private String id;
	private float latitude;
	private float longitude;
	private float elevation;
	private String state;
	private String name;
	
	/**
	 * When passed an appropriate String,
	 * This method fills out all of the station's fields
	 * @param data
	 */
	public void setStationData(String data) {
		try {
		id = data.substring(0,11);
		latitude = Float.valueOf(data.substring(12,20).trim()); 
		longitude = Float.valueOf(data.substring(21,30).trim());
		elevation = Float.valueOf(data.substring(31,37).trim());
		state = data.substring(38,40);
		name = data.substring(41,71);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Displays this objects attributes as a string
	 * @return String
	 */
	@Override
	public String toString() {
		return "id=" + id + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", elevation=" + elevation
				+ ", state=" + state + ", name=" + name;
	}

	/**
	 * Returns the station ID
	 * @return String
	 */
	public String getID() {
		return id;
	}
}
