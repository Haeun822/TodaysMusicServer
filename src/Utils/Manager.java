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
		Long star_long = (long)musicData.get("Star");
		int star = star_long.intValue();
		boolean isShared = (boolean)musicData.get("IsShared");
		
		if(musicID == null){
			JSONObject tempData = MusicServer.searchMusic(artist, title, 0, 1);
			JSONArray array = (JSONArray)tempData.get("tracks");
			if(array.size() != 0){
				tempData = (JSONObject)array.get(0);
				musicID = (String)tempData.get("track_id");
				title = (String)tempData.get("title");
				artist = (String)tempData.get("artist");
				String url = (String)tempData.get("url");
			
				DB.registerMusic(musicID, title, artist, url);
			}
		}
		
		if(musicID != null)
			DB.registerMusicList(ID, musicID, star, time, feel, (isShared == true)?1:0);
	}
	
	public static JSONObject Recommend(String ID, String time, String feel){
		ArrayList<JSONObject> list = DB.getMusicList(ID);
		ArrayList<JSONObject> resultList = new ArrayList<JSONObject>();

		int itemNum = 10;
		for (int i = 0; i < list.size(); i++) {
			JSONObject temp = MusicServer.recommendMusic((String) list.get(i).get("MusicID"), itemNum);
			JSONArray tempArray = (JSONArray) temp.get("tracks");
			int star = (int) list.get(i).get("Star");

			for (int j = 0; j < tempArray.size(); j++) {
				JSONObject json = (JSONObject) tempArray.get(j);
				if (resultList.size() == 0) {
					json.put("tscore",	star * 5 + (double) json.get("score"));
					resultList.add(json);
				} 
				else if (resultList.contains(json))
					break;
				else {
					boolean check = false;
					for (int k = 0; k < resultList.size(); k++) {
						if ((double) resultList.get(k).get("tscore") < (star * 5 + (double) json.get("score"))) {
							json.put("tscore",	star * 5 + (double) json.get("score"));
							resultList.add(k, json);
							check = true;
							break;
						}
					}
					if(check == false){
						json.put("tscore",	star * 5 + (double) json.get("score"));
						resultList.add(json);
					}
				}

				if (resultList.size() > itemNum) {
					resultList.remove(itemNum);
				}
			}
		}

		JSONObject result = new JSONObject();
		result.put("List", resultList);

		return result;
	}
	
	public static JSONObject getMusicList(String ID){
		ArrayList<JSONObject> list = DB.getMusicList(ID);
		JSONObject result = new JSONObject();
		JSONArray resultList = new JSONArray();
		for(int i=0; i<list.size(); i++)
			resultList.add(list.get(i));
		result.put("List", resultList);
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
	
	public static JSONObject searchUser(String ID){
		ArrayList<String> list = DB.searchUsers(ID);
		JSONObject result = new JSONObject();
		JSONArray resultList = new JSONArray();
		for(int i=0; i<list.size(); i++)
			resultList.add(list.get(i));
		result.put("List", resultList);
		return result;
	}
}
