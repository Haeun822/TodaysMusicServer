package Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import Utils.Manager;

@WebServlet("/Recommend")
public class Recommend extends HttpServlet {
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
			
			json = Manager.Recommend((String)json.get("ID"), (String)json.get("Time"), (String)json.get("Feel"));
			response.getWriter().print(json);
		}
		else{
			JSONObject json = new JSONObject();
			json.put("Check", "Failed");
			response.getWriter().print(json);
		}
	}
}
