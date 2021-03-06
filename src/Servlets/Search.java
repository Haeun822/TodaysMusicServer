package Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import Utils.MusicServer;

@WebServlet("/Search")
public class Search extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String JSON = request.getParameter("JSON");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		if(JSON != null){
			JSONObject json = (JSONObject) JSONValue.parse(JSON);
			String artist = (String)json.get("Artist");
			String title = (String)json.get("Title");
			
			json = MusicServer.searchMusic(artist, title, 0, 20);
			json.put("Request", JSON);
			response.getWriter().print(json);
		}
		else{
			JSONObject json = new JSONObject();
			json.put("Check", "Failed");
			response.getWriter().print(json);
		}
	}

}
