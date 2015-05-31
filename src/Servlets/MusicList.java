package Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import Utils.Manager;

@WebServlet("/MusicList")
public class MusicList extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String JSON = request.getParameter("JSON");
		
		if(JSON != null){
			JSONObject json = (JSONObject) JSONValue.parse(JSON);
			
			String userID = (String)json.get("ID");
			JSONArray musicArray = (JSONArray)json.get("Musics"); 
			
			for(int i=0; i<musicArray.size(); i++){
				JSONObject musicData = (JSONObject)musicArray.get(i);
				Manager.RegisterMusicList(userID, musicData);
			}
		}
	}
}
