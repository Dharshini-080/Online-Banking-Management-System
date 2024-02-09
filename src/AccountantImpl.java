
import java.sql.Connection;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
public class AccountantImpl implements AccountantIntr
{
	Connection con;
	@Override
	public Accountant Login(String username, String password) throws AccountantException {
	    // TODO Auto-generated method stub
	    Accountant acc = null;
	    try (Connection con = DBConnect.createDBConnect();
	         PreparedStatement pstm = con.prepareStatement("SELECT * FROM accountant WHERE username = ? AND password = ?")) {

	        pstm.setString(1, username);
	        pstm.setString(2, password);
	        try (ResultSet result = pstm.executeQuery()) {
	            if (result.next()) {
	                String n = result.getString("username");
	                String e = result.getString("email");
	                String p = result.getString("password");
	                acc = new Accountant(n, e, p);
	            }
	        }
	    } catch (SQLException e) {
	        // Print the stack trace for debugging purposes
	        e.printStackTrace();
	        throw new AccountantException("Invalid username and password");
	    }
	    return acc;
	}
	@Override
	public int addCustomer(String customerName, String customerMail, String customerPassword, String customerMobile,
			String customerAddress) throws CustomerException 
	{
		// TODO Auto-generated method stub
		int cid=-1;
		try(Connection con = DBConnect.createDBConnect();
				PreparedStatement ps=con.prepareStatement("insert into customerinformation (customerName, customerMail,customerPassword,customerMobile,customerAddress) values(?,?,?,?,?)"))
		{
			ps.setString(1,customerName);
			ps.setString(2,customerMail);
			ps.setString(3,customerPassword);
			ps.setString(4,customerMobile);
			ps.setString(5,customerAddress);
			
			int x=ps.executeUpdate();
			if(x>0)
			{
				PreparedStatement ps2=con.prepareStatement("select cid from customerinformation where customerMail=? and customerMobile=?");
				ps2.setString(1, customerMail);
				ps2.setString(2, customerMobile);
				
				ResultSet rs=ps2.executeQuery();
				if(rs.next())
				{
					cid = rs.getInt("cid");
				}
				else
				{
					System.out.println("Inserted data is incorrect please Try again");
				}
			}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Sql query related error");
		}
		return cid;
	}
	@Override
	public String addAccount(int customerBalance, int cid) throws CustomerException {
		// TODO Auto-generated method stub
		String message=null;
		try(Connection con = DBConnect.createDBConnect();
			PreparedStatement ps=con.prepareStatement("insert into account(customerBalance,cid) values(?,?)"))
		{
			ps.setInt(1, customerBalance);
			ps.setInt(2, cid);
			int x=ps.executeUpdate();
			if(x>0)
			{
				System.out.println("Account added successfully");
			}
			else
			{
				System.out.println("Not added successfully");
			}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Sql related exception");
		}
		return message;
	}
	@Override
	public String updateCustomer(int customerAccountNumber, String customerAddress, String customerMail,
            String customerPassword, String customerMobile) throws CustomerException 
	{
		// TODO Auto-generated method stub
		String message=null;
		try(Connection con = DBConnect.createDBConnect();
				PreparedStatement ps=con.prepareStatement("UPDATE customerinformation i INNER JOIN account a ON i.cid = a.cid AND a.customerAccountNumber = ? SET i.customerAddress = IFNULL(?, i.customerAddress), i.customerMail = IFNULL(?, i.customerMail), i.customerPassword = IFNULL(?, i.customerPassword), i.customerMobile = IFNULL(?, i.customerMobile)"))
		{
			ps.setInt(1, customerAccountNumber);
			ps.setString(2, customerAddress);
			ps.setString(3,customerMail);
			ps.setString(4, customerPassword);
			ps.setString(5, customerMobile);
			int x=ps.executeUpdate();
			if(x>0)
			{
				message="Customer information updated successfully";
			}
			else
			{
				message="Customer information updated failed.";
			}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			message="Error occured while updating customer information";
		}
		return message;
	}
	@Override
	public String deleteAccount(int customerAccountNumber) throws CustomerException 
	{
		// TODO Auto-generated method stub
		String message = null;
		try(Connection con = DBConnect.createDBConnect();
			PreparedStatement ps=con.prepareStatement("delete i from customerinformation i inner join account a on i.cid=a.cid where a.customerAccountNumber =? ")) 
		{
			ps.setInt(1, customerAccountNumber);
			int x=ps.executeUpdate();
			if(x>0)
			{
				System.out.println("Account deleted successfully...");
			}
			else
			{
				System.out.println("Deletion failed....account not found");
			}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			message=e.getMessage();
		}
		
		return message;
	}
	@Override
	public Customer viewCustomer(String customerAccountNumber) throws CustomerException 
	{
		// TODO Auto-generated method stub
		Customer cu=null;
		try(Connection con = DBConnect.createDBConnect();
			PreparedStatement ps=con.prepareStatement("select*from customerinformation i inner join account a on a.cid=i.cid where customerAccountNumber=?")) 
		{
			ps.setString(1, customerAccountNumber);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				int a=rs.getInt("customerAccountNumber");
				String n=rs.getString("customerName");
				int b=rs.getInt("customerBalance");
				String e=rs.getString("customerPassword");
				String p=rs.getString("customerMail");
				String m=rs.getString("customerMobile");
				String ad=rs.getString("customerAddress");
				
				cu=new Customer(a,n,b,e,p,m,ad);
			}
			else
			{
				throw new CustomerException("Invalid Account Number...");
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return cu;
	}
	@Override
	public Customer viewAllCustomer() throws CustomerException
	{
		// TODO Auto-generated method stub
		Customer cu=null;
		try(Connection con = DBConnect.createDBConnect();
			PreparedStatement ps=con.prepareStatement("select*from customerinformation i inner join account a on a.cid=i.cid")) 
		{
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				int a=rs.getInt("customerAccountNumber");
				String n=rs.getString("customerName");
				int b=rs.getInt("customerBalance");
				String e=rs.getString("customerPassword");
				String p=rs.getString("customerMail");
				String m=rs.getString("customerMobile");
				String ad=rs.getString("customerAddress");
				System.out.println("Account Number : "+a);
				System.out.println("Customer Name : "+n);
				System.out.println("Customer Balance : "+b);
				System.out.println("Customer Password : "+e);
				System.out.println("Customer Mail : "+p);
				System.out.println("Customer Mobile : "+m);
				System.out.println("Customer Address : "+ad);
				System.out.println("------------------------");
				cu=new Customer(a,n,b,e,p,m,ad);
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CustomerException("Invalid Accoun Number");
		}
		return cu;
	}
	
}
