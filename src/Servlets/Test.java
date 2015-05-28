package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import Utils.MusicServer;

@WebServlet("/Test")
public class Test extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String artist = "", title = "";
		
		request.setCharacterEncoding("UTF-8");
		
		if(request.getParameter("artist") != null)
			artist = request.getParameter("artist");
		if(request.getParameter("title") != null)
			title = request.getParameter("title");

		System.out.println(artist + " - " + title);
		
		if(!artist.isEmpty() || !title.isEmpty()){
			JSONObject json = MusicServer.searchMusic(artist, title, 0, 10);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print(json.toString());
		}
	}
}
