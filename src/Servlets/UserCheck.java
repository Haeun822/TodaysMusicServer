package Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import Utils.DB;

@WebServlet("/UserCheck")
public class UserCheck extends HttpServlet {

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
			
			String checkValue = DB.userCheck(json.get("ID").toString(), json.get("PW").toString());
		
			json.clear();
			json.put("Check", checkValue);
			response.getWriter().print(json);
		}
		else{
			JSONObject json = new JSONObject();
			json.put("Check", "Failed");
			response.getWriter().print(json);
		}
		
	}

}
