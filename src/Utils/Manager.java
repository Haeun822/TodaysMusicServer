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
		ArrayList<JSONObject> resultList = new ArrayList<JSONObject>();

		int itemNum = 10;
		for (int i = 0; i < list.size(); i++) {
			JSONObject temp = MusicServer.recommendMusic((String) list.get(i)
					.get("MusicID"), itemNum);
			JSONArray tempArray = (JSONArray) temp.get("tracks");
			int star = (int) list.get(i).get("Star");

			for (int j = 0; j < tempArray.size(); j++) {
				JSONObject json = (JSONObject) tempArray.get(j);
				if (resultList.size() == 0) {
					resultList.add(json);
					resultList.get(0).put("tscore",	star * 10 + (double) json.get("score"));
				} else if (resultList.contains(json))
					break;
				else {
					for (int k = 0; k < resultList.size(); k++) {

						if ((double) resultList.get(k).get("tscore") < (star * 10 + (double) json
								.get("score"))) {
							resultList.add(k, json);
							resultList.get(k).put("tscore",	star * 10 + (double) json.get("score"));
							break;
						}
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
}
