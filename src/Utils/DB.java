package Utils;

import java.sql.*;

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
}
