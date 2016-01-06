package gitscrap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;
class user
{
	String login;
	int id;
	String type;
	String name;
	String company;
	String location;
	String blog;
	String email;
	String bio;
	int public_repos;
	int public_gists;
	int followers;
	int following;
}
class jdbcexec
{
	Connection conn;
	void insert(user u) throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost/linkedin";
		String user = "root";
		String password = "root";
		conn = DriverManager.getConnection(url, user,password);
		PreparedStatement pstmt = conn.prepareStatement("insert into github values(?,?,?,?,?,?,?,?,?,?,?,?,?);");
		pstmt.setString(1, u.login);
		pstmt.setInt(2, u.id);
		pstmt.setString(3, u.type);
		pstmt.setString(4, u.name);
		pstmt.setString(5, u.company);
		pstmt.setString(6, u.blog);
		pstmt.setString(7,u.location);
		pstmt.setString(8,u.email);
		pstmt.setString(9,u.bio);
		pstmt.setInt(10, u.public_repos);
		pstmt.setInt(11, u.public_gists);
		pstmt.setInt(12, u.followers);
		pstmt.setInt(13, u.following);
		boolean bool = pstmt.execute();
		System.out.println(bool);
		
	}
}

public class qwerty {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=4200;i<=100000;i++)
		{
		URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line;
	    jdbcexec obj = new jdbcexec();
	    try {
	        url = new URL("https://api.github.com/user/"+i);
	        is = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(is));
            String str="";
	        while ((line = br.readLine()) != null) {
	            str += line;
	        }
	       JSONObject json = new JSONObject(str);
	       user u = new user();
	       u.login = String.valueOf(json.get("login"));
	       u.id = Integer.valueOf(json.getInt("id"));
	       u.type = String.valueOf(json.get("type"));
	       u.name = String.valueOf(json.get("name"));
	       u.company = String.valueOf(json.get("company"));
	       u.location = String.valueOf(json.get("location"));
	       u.email = String.valueOf(json.get("email"));
	       u.blog = String.valueOf(json.get("blog"));
	       u.bio = String.valueOf(json.get("bio"));
	       u.public_repos =  Integer.valueOf(json.getInt("public_repos"));
	       u.public_gists =  Integer.valueOf(json.getInt("public_gists"));
	       u.followers = Integer.valueOf(json.getInt("followers"));
	       u.following = Integer.valueOf(json.getInt("following"));
	       System.out.println(u.name);
	      obj.insert(u);
	      
	      try {
	    	    Thread.sleep(60000);                
	      } catch(InterruptedException ex) {
	          Thread.currentThread().interrupt();
	      }
	    }
	    catch(Exception e)
	    {
	     System.out.println(e);	
	     i++;
	    }
	    finally {
	        try {
	            if (is != null) is.close();
	        } catch (IOException ioe) {
	            // nothing to see here
	        }
	    }
		}

	}

}
