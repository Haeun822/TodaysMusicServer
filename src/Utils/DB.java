package Utils;

import java.sql.*;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DB {
	static Connection conn;
	static Statement st;
	static ResultSet rs;
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String uri = "jdbc:mysql://52.11.70.122:3306/tdm";
			String id = "root";
			String pw = "root";
			
			conn = DriverManager.getConnection(uri, id, pw);
			st = conn.createStatement();
			
		} catch (ClassNotFoundException e) { }
		  catch (SQLException e) { }
	}
	
	public static String userCheck(String ID, String PW){
		try {
			rs = st.executeQuery("SELECT * FROM User WHERE ID = '" + ID + "';");
			while(rs.next()){
				if(rs.getString("PW").equals(PW))
					return "Match";
				else return "Wrong PW";
			}
			return "Can't Find";
		} catch (SQLException e) { }
		
		return "Failed";
	}
	
	public static String registerUser(String ID, String PW){
		try {
			st.executeUpdate("INSERT INTO User VALUES('" + ID + "', '" + PW + "');");
			return "Success";
		} catch (SQLException e) { }
		
		return "Failed";
	}
	
	public static void registerMusic(String ID, String Title, String Artist, String URL){
		try {
			st.executeUpdate("INSERT INTO Music VALUES('" + ID + "', '" + Title + "', '" + Artist + "', '" + URL + "')"
							+ "ON DUPLICATE KEY UPDATE ID = '" + ID + "', Title = '" + Title + "', Artist = '" + Artist + "', URL = '" + URL + "';");
		} catch (SQLException e) { }
	}
	
	public static void registerMusicList(String userID, String musicID, int star, String time, String feel, int isShared){
		try{
			rs = st.executeQuery("SELECT * FROM List WHERE UserID = '" + userID + "' AND MusicID = '" + musicID + "';");
			if(rs.next()){
				st.executeUpdate("UPDATE List SET Star=" + star + ", Time='" + time + "', Feel='" + feel + "', SharedTime = CURRENT_TIMESTAMP, IsShared = " + isShared
								+ " WHERE UserID = '" + userID + "' AND MusicID = '" + musicID + "';");
			}
			else{
				st.executeUpdate("INSERT INTO List VALUES('" + userID + "', '" + musicID + "', " + star + ", '" + time + "', '" + feel + "', CURRENT_TIMESTAMP, '" + isShared + "');");
			}
		} catch (SQLException e) { }
	}

	public static ArrayList<JSONObject> getMusicList(String ID){
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		
		try{
			rs = st.executeQuery("SELECT * FROM List WHERE UserID = '" + ID + "';");
			while(rs.next()){
				JSONObject json = new JSONObject();
				json.put("MusicID", rs.getString("MusicID"));
				json.put("Star", rs.getInt("Star"));
				json.put("Time", rs.getString("Time"));
				json.put("Feel", rs.getString("Feel"));
				json.put("SharedTime", rs.getTimestamp("SharedTime").toString());
				
				list.add(json);
			}
		} catch (SQLException e) { }
		
		return list;
	}
	
	public static ArrayList<JSONObject> getTimeLine(String ID, ArrayList<String> Followed){
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		
		try{
			String query = "SELECT * FROM List WHERE ";
			query += "UserID = '" + ID + "' ";
			for(int i=0; i<Followed.size(); i++)
				query += " OR UserID = '" + Followed.get(i) + "' ";
			query += ";";
			
			rs = st.executeQuery(query);
			while(rs.next()){
				JSONObject json = new JSONObject();
				json.put("UserID", rs.getString("UserID"));
				json.put("MusicID", rs.getString("MusicID"));
				json.put("Star", rs.getInt("Star"));
				json.put("Time", rs.getString("Time"));
				json.put("Feel", rs.getString("Feel"));
				json.put("SharedTime", rs.getTimestamp("SharedTime").toString());
				
				list.add(json);
			}
			
		} catch (SQLException e) { }
		
		return list;
	}
	
	public static void registerFollow(String userID, String followedID){
		try {
			st.executeUpdate("INSERT IGNORE INTO Follow VALUES('" + userID + "', '" + followedID + "');");
		} catch (SQLException e) { }
	}
	
	public static ArrayList<String> getFollowedUsers(String userID){
		ArrayList<String> list = new ArrayList<String>();
		try {
			rs = st.executeQuery("SELECT Followed FROM Follow WHERE Follower = '" + userID + "';");
			
			while(rs.next())
				list.add(rs.getString("Followed"));
		} catch (SQLException e) { }
		
		return list;
	}
}
