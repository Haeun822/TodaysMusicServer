package Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class MusicServer {
	static String serverURL = "http://52.68.113.200/soundnerd/";
	
	public static JSONObject similarMusic(String feature, int count){
		JSONObject json = new JSONObject();
		json.put("feature", feature);
		json.put("count", count);
		
		return connectServer("music/similar", json);
	}
	
	public static JSONObject recommendMusic(String track_id, int count){
		JSONObject json = new JSONObject();
		json.put("track_id", track_id);
		json.put("count", count);
		
		return connectServer("music/recommend", json);
	}
	
	public static JSONObject searchMusic(String artist, String title, int start, int count){
		JSONObject json = new JSONObject();
		if(artist != null && !artist.isEmpty())
			json.put("artist", artist);
		if(title != null && !title.isEmpty())
			json.put("title", title);
		json.put("start", start);
		json.put("count", count);
		
		json = connectServer("music/search", json);
		JSONArray array = (JSONArray)json.get("tracks");
		if(array != null){
			for(int i=0; i<array.size(); i++){
				JSONObject temp = (JSONObject)array.get(i);
				DB.registerMusic((String)temp.get("track_id"), (String)temp.get("title"), (String)temp.get("artist"), (String)temp.get("url"));
			}
		}
		return json;
	}
	
	static JSONObject connectServer(String subURL, JSONObject postData){
		try {
			URL url = new URL(serverURL + subURL);
			
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setDoOutput(true);
			conn.setDoInput(true);
	
			OutputStream output = conn.getOutputStream();
			output.write(("data=" + postData.toString()).getBytes("UTF-8"));
			output.flush();
			output.close();
			
			InputStreamReader input = new InputStreamReader(conn.getInputStream(), "UTF-8");
			BufferedReader reader = new BufferedReader(input);
	
			String returnString = reader.readLine();
			JSONObject returnData = (JSONObject) JSONValue.parse(returnString);
			
			return returnData;

		} catch (Exception e) {
			JSONObject error = new JSONObject();
			error.put("Error", "Error");
			
			return error;
		}		
	}
}
