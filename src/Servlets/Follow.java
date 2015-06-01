package Servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import Utils.DB;
import Utils.Manager;

@WebServlet("/Follow")
public class Follow extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String JSON = request.getParameter("JSON");
		
		if(JSON != null){
			JSONObject json = (JSONObject) JSONValue.parse(JSON);
			
			String type = (String)json.get("Type");
			
			if(type.equals("Get")){
				ArrayList<String> list = DB.getFollowedUsers((String)json.get("Follower"));
				json.clear();
				for(int i=0; i<list.size(); i++)
					json.put(i, list.get(i));
				response.getWriter().print(json);
			}
			else if(type.equals("Register")){
				DB.registerFollow((String)json.get("Follower"), (String)json.get("Followed"));
			}
		}
		else{
			JSONObject json = new JSONObject();
			json.put("Check", "Failed");
			response.getWriter().print(json);
		}
	}

}
