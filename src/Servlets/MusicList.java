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

import Utils.DB;
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
		response.setContentType("text/html; charset=UTF-8");
		
		if(JSON != null){
			JSONObject json = (JSONObject) JSONValue.parse(JSON);
			
			String type = (String)json.get("Type");
			String userID = (String)json.get("ID");
			
			if(type.equals("Register")){
				JSONArray musicArray = (JSONArray)json.get("Musics"); 
				
				for(int i=0; i<musicArray.size(); i++){
					JSONObject musicData = (JSONObject)musicArray.get(i);
					Manager.RegisterMusicList(userID, musicData);
				}
			}
			else if(type.equals("View")){
				json = Manager.getMusicList(userID);
				response.getWriter().print(json);
			}
			else if(type.equals("Delete")){
				String musicID = (String)json.get("MusicID");
				DB.deleteMusicList(userID, musicID);
			}
			
			response.getWriter().print("");
		}
	}
}
