import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
 
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class WeatherFetcher {
	
	// Set a temperature array to hold currentTemperature[0], highTemperature[1], lowTemperature[2]
	public Double [] temperatures = new Double[4];

	public String getObject(String weatherURL) throws IOException 
	{
		// Set a stringBuilder to hold the JSON Response
		StringBuilder response = new StringBuilder(); 
		
		// Connect to the URL
		URL url = new URL(weatherURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String tempLine; 
		
		// Go through the lines and append them to the StringBuilder
		while((tempLine = rd.readLine()) != null)
			response.append(tempLine);
		rd.close(); // Close the buffer 
		
		return response.toString();
		
	}
	
	public Double [] getWeather(String city) throws IOException 
	{
		// Set the API url for zipcodes from openweathermap.org
		String weatherURL = "http://api.openweathermap.org/data/2.5/weather?q="
				+ city + ",us&APPID=c6b9504716ac59d175523cdfa422c501";
		
		String object;
		try {
		// Get the json object as a string by passing the url in the zip format
			object = getObject(weatherURL);
		}
		catch (Exception e) {
			temperatures[0] = -100000.0;
			return temperatures;
		}
		// Get a json object to narrow down the search
		JsonObject temp = parseJsonTemperature(object);
		temperatures[0] = temp.get("temp").getAsDouble() - 273;
		temperatures[1] = temp.get("temp_max").getAsDouble() - 273; 
		temperatures[2] = temp.get("temp_min").getAsDouble() - 273;
		temperatures[3] = temp.get("pressure").getAsDouble();
		
		// Get the current temperature of the city, convert to Celsius and return
		
		return temperatures;
	}
	
	// Function to parse and get the temperature readings of our json request
	public JsonObject parseJsonTemperature(String json) {
		
		// First, parse to JSONElement
		JsonElement jelement = new JsonParser().parse(json); 
		
		// Second, take the element and break it down to a json object, that way the search can be narrowed
		JsonObject MasterWeatherObject = jelement.getAsJsonObject(); 
		
		// Get the main object that holds the temperature readings
		JsonObject temperatureObject = MasterWeatherObject.getAsJsonObject("main"); 
		
		return temperatureObject;
		
	}
}
