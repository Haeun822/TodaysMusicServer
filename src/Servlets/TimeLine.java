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

@WebServlet("/TimeLine")
public class TimeLine extends HttpServlet {
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
			Long start = (long) json.get("start");
			Long count = (long) json.get("count");
			
			json = Manager.TimeLine((String)json.get("ID"), start.intValue(), count.intValue());
			response.getWriter().print(json);
		}
		else{
			JSONObject json = new JSONObject();
			json.put("Check", "Failed");
			response.getWriter().print(json);
		}
	}
}
