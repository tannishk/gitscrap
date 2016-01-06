package gitscrap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.jdbc.Statement;
class follower
{
	int id;
	String name;
	String followers_name;
	int followers_id;
}
class jdbcexec2
{
	Connection conn;
	void insert(follower u) throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost/linkedin";
		String user = "root";
		String password = "root";
		conn = DriverManager.getConnection(url, user,password);
		PreparedStatement pstmt = conn.prepareStatement("insert into followers values(?,?,?,?);");
		pstmt.setInt(1, u.id);
		pstmt.setString(2, u.name);
		pstmt.setString(3, u.followers_name);
		pstmt.setInt(4, u.followers_id);
		pstmt.close();
		conn.close();
	}
}

public class followers {
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		Connection conn1;

		Class.forName("com.mysql.jdbc.Driver");
		String url1 = "jdbc:mysql://localhost/linkedin";
		String user = "root";
		String password = "root";
		conn1 = DriverManager.getConnection(url1, user,password);
		Statement pstmt = (Statement) conn1.createStatement();
		try
		{
			ResultSet rs = pstmt.executeQuery("select login,id from github");
			while(rs.next())
			{
				HttpURLConnection conn;
				String owner =rs.getString("login");
				int id = rs.getInt("id");
				URL url = new URL("https://api.github.com/users/"+owner+"/followers");
		
				conn = (HttpURLConnection) url.openConnection();
				conn.connect();
				BufferedReader readit = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String b="";
				String line;
				while((line=readit.readLine())!=null)
				{
					b = b+line;
				}
				System.out.println(b);
				follower f = new follower();
				JSONArray ja = new JSONArray(b);
				jdbcexec2 j = new jdbcexec2();
				for(int i=0;i<ja.length();i++)
				{
					JSONObject obj = ja.getJSONObject(i);
					f.name = owner;
					f.id= id;
					f.followers_id = obj.getInt("id");
					f.followers_name = String.valueOf(obj.get("login"));
					System.out.println(f.name+" followed by "+f.followers_name);
					j.insert(f);
				}
			}
		}
			catch(Exception e)
			{
				System.out.println(e);
			}
		
		}
	}
