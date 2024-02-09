
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnect
{
	
	static Connection con;
	public static Connection createDBConnect() throws SQLException
	{
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/banking_system";
			String username="root";
			String password="Dharshini@08";
			con = DriverManager.getConnection(url,username,password);
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error occur in Database");
		}
		return con;
	}
}
