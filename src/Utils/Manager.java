package Utils;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Manager {
	
	public static void RegisterMusicList(String ID, JSONObject musicData){
		String musicID = (String)musicData.get("ID");
		String title = (String)musicData.get("Title");
		String artist = (String)musicData.get("Artist");
		String time = (String)musicData.get("Time");
		String feel = (String)musicData.get("Feel");
		int star = (Integer)musicData.get("Star");
		int isShared = (Integer)musicData.get("IsShared");
		
		if(musicID == null){
			musicData = MusicServer.searchMusic(artist, title, 0, 1);
			musicData = (JSONObject)((JSONArray)musicData.get("tracks")).get(0);
			musicID = (String)musicData.get("track_id");
			title = (String)musicData.get("title");
			artist = (String)musicData.get("artist");
			String url = (String)musicData.get("url");
			
			DB.registerMusic(musicID, title, artist, url);
		}
		
		DB.registerMusicList(ID, musicID, star, time, feel, isShared);
	}
	
	public static JSONObject Recommend(String ID, String time, String feel){
		ArrayList<JSONObject> list = DB.getMusicList(ID);
		
		JSONObject result = MusicServer.recommendMusic((String)list.get(0).get("MusicID"), 10);
		
		return result;
	}
	
	public static JSONObject TimeLine(String ID, int start, int count){
		ArrayList<String> followed = DB.getFollowedUsers(ID);
		ArrayList<JSONObject> list = DB.getTimeLine(ID, followed);
		
		JSONObject result = new JSONObject();
		JSONArray resultArray = new JSONArray();
		for(int i=start; i<count; i++){
			if(i < list.size()){
				resultArray.add(list.get(i));
			}
		}
		
		result.put("List", resultArray);		
		return result;
	}
}
