package Utils;

import java.sql.*;

public class DB {
	static Connection conn;
	static Statement st;
	
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
	
	public static void registerUser(String ID, String PW){
		
	}
}
