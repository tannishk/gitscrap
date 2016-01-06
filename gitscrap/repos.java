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
class rep
{
	int id;
    String name;
    String owner;
    
    String description;
   // boolean fork;
    int owner_id;
    String created_at;
    String updated_at;
    String pushed_at;
    String homepage; 
    int size;
    int stargazers_count;
    int watchers_count;
    String language;
    
    int forks_count;
    
    int open_issues_count;
    int forks;
    int open_issues;
    int watchers;
}
class jdbcexec1
{
	Connection conn;
	void insert(rep r) throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost/linkedin";
		String user = "root";
		String password = "root";
		conn = DriverManager.getConnection(url, user,password);
		PreparedStatement pstmt = conn.prepareStatement("insert into repos values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		pstmt.setInt(1,r.id);
	    pstmt.setString(2,r.name);
	    pstmt.setString(3, r.owner);
	    
	    pstmt.setString(4,r.description);
	   // boolean fork;
	    
	    pstmt.setString(5,r.created_at);
	    pstmt.setString (6,r.updated_at);
	    pstmt.setString (7,r.pushed_at);
	    pstmt.setString (8,r.homepage); 
	    pstmt.setInt (9,r.size);
	    pstmt.setInt (10,r.stargazers_count);
	    pstmt.setInt (11,r.watchers_count);
	    pstmt.setString (12,r.language);
	    
	    pstmt.setInt (13,r.forks_count);
	    
	    pstmt.setInt (14,r.open_issues_count);
	    pstmt.setInt (15,r.forks);
	    pstmt.setInt (16,r.open_issues);
	    pstmt.setInt (17,r.watchers);
	    pstmt.setInt(18, r.owner_id);
	    Boolean bool = pstmt.execute();
		System.out.println(bool);
		conn.close();
	}
}

public class repos {

	static Connection conn;
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		Class.forName("com.mysql.jdbc.Driver");
		String url1 = "jdbc:mysql://localhost/linkedin";
		String user = "root";
		String password = "root";
		conn = DriverManager.getConnection(url1, user,password);
		Statement pstmt = (Statement) conn.createStatement();
		try
		{
			ResultSet rs = pstmt.executeQuery("select login,id from github where id > 2295"); // Alter the id to continue forward
			while(rs.next())
			{
				
			rep r = new rep();
			HttpURLConnection conn;
			String owner = rs.getString("login");
			int id = rs.getInt("id");
			URL url = new URL("https://api.github.com/users/"+owner+"/repos");
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
			jdbcexec1 j = new jdbcexec1();
			JSONArray parentobj = new JSONArray(b);
			for(int i=0;i<parentobj.length();i++)
			{
				JSONObject obj = parentobj.getJSONObject(i);
				r.created_at = obj.getString("created_at");
				r.description = String.valueOf(obj.get("description"));
				r.homepage = String.valueOf(obj.get("homepage"));
				r.name = obj.getString("name");
				r.language = String.valueOf(obj.get("language"));;
				r.updated_at = String.valueOf(obj.get("updated_at"));
				r.pushed_at = String.valueOf(obj.get("pushed_at"));
				r.forks = obj.getInt("forks");
				r.forks_count = obj.getInt("forks_count");
				r.id=obj.getInt("id");
				r.open_issues=obj.getInt("open_issues");
				r.open_issues_count = obj.getInt("open_issues_count");
				r.size = obj.getInt("size");
				r.stargazers_count = obj.getInt("stargazers_count");
				r.watchers = obj.getInt("watchers");
				r.watchers_count = obj.getInt("watchers_count");
				r.owner=owner;
				r.owner_id = id;
				System.out.println(r.name);
				j.insert(r);
			}
			Thread.currentThread().sleep(60000);
		}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			conn.close();
		}
	}

}
