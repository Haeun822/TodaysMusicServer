package Utils;

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
			musicID = (String)musicData.get("track_id");
			title = (String)musicData.get("title");
			artist = (String)musicData.get("artist");
			String url = (String)musicData.get("url");
			
			DB.registerMusic(musicID, title, artist, url);
		}
		
		DB.registerMusicList(ID, musicID, star, time, feel, isShared);
	}
	
	public static JSONObject Recommend(String ID, String time, String feel){
		JSONObject result = new JSONObject();
		
		return result;
	}
	
	public static JSONObject TimeLine(String ID, String FriendID, int start, int count){
		JSONObject result = new JSONObject();
		
		return result;
	}
}
