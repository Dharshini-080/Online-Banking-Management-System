
import java.util.*;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;



public class CustomerImpl implements CustomerIntr{

	@Override
	public Customer LoginCustomer(String customerName, String customerPassword, int customerAccountNumber)
			throws CustomerException
	{
		// TODO Auto-generated method stub
		Customer customer=null;
		try(Connection con = DBConnect.createDBConnect();
			PreparedStatement ps=con.prepareStatement("select*from customerinformation i inner join account a on i.cid=a.cid where customerName=? and customerPassword=? and customerAccountNumber=?")) 
		{
			ps.setString(1, customerName);
			ps.setString(2, customerPassword);
			ps.setInt(3,customerAccountNumber);
			
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				int ac=rs.getInt("customerAccountNumber");
				String n=rs.getString("customerName");
				int b=rs.getInt("customerBalance");
				String e=rs.getString("customerMail");
				String p=rs.getString("customerPassword");
				String m=rs.getString("customerMobile");
				String ad=rs.getString("customerAddress");
				
				customer =new Customer(ac,n,b,e,p,m,ad);
			}
			else
			{
				throw new CustomerException("Invalid customer name and password !!!!! please try again!!!!");
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CustomerException(e.getMessage());
		}
		return customer;
	}

	@Override
	public int viewBalance(int customerAccountNumber) throws CustomerException
	{
		// TODO Auto-generated method stub
		int b=-1;
		try(Connection con = DBConnect.createDBConnect();
			PreparedStatement ps=con.prepareStatement("select customerBalance from account where customerAccountNumber=?"))
		{
			ps.setInt(1, customerAccountNumber);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				b=rs.getInt("customerBalance");
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CustomerException(e.getMessage());
		}
		return b;
	}

	@Override
	public int Deposit(int customerAccountNumber, int amount) throws CustomerException {
	    int b = -1;
	    try (Connection con = DBConnect.createDBConnect();
	         PreparedStatement ps = con.prepareStatement("update account set customerBalance = customerBalance + ? where customerAccountNumber = ?")) {
	        ps.setInt(1, amount);
	        ps.setInt(2, customerAccountNumber);
	        int rowsAffected = ps.executeUpdate();
	        if (rowsAffected > 0) {
	            recordTransaction(customerAccountNumber, "DEPOSIT", amount); // Record transaction
	        } else {
	            throw new CustomerException("Deposit failed");
	        }
	    } catch (SQLException e) {
	        throw new CustomerException("Error occurred during deposit: " + e.getMessage());
	    }
	    return b;
	}


	@Override
	public int Withdraw(int customerAccountNumber, int amount) throws CustomerException {
	    int vb = viewBalance(customerAccountNumber);
	    if (vb > amount) {
	        try (Connection con = DBConnect.createDBConnect();
	             PreparedStatement ps = con.prepareStatement("update account set customerBalance = customerBalance - ? where customerAccountNumber = ?")) {
	            ps.setInt(1, amount);
	            ps.setInt(2, customerAccountNumber);
	            int rowsAffected = ps.executeUpdate();
	            if (rowsAffected > 0) {
	                recordTransaction(customerAccountNumber, "WITHDRAW", amount); // Record transaction
	            } else {
	                throw new CustomerException("Withdrawal failed");
	            }
	        } catch (SQLException e) {
	            throw new CustomerException("Error occurred during withdrawal: " + e.getMessage());
	        }
	    } else {
	        throw new CustomerException("Insufficient Balance");
	    }
	    return vb - amount;
	}

	@Override
	public int Tranfer(int customerAccountNumber, int amount, int customerAccountNumber2) throws CustomerException {
	    int vb = viewBalance(customerAccountNumber);
	    if (vb > amount && checkAccount(customerAccountNumber2)) {
	        int wit = Withdraw(customerAccountNumber, amount);
	        int dep = Deposit(customerAccountNumber2, amount);
	        recordTransaction(customerAccountNumber, "WITHDRAW", amount); // Record outbound transfer
	        recordTransaction(customerAccountNumber2, "DEPOSIT", amount); // Record inbound transfer
	    } else {
	        throw new CustomerException("Insufficient balance");
	    }
	    return 0;
	}


	private boolean checkAccount(int customerAccountNumber) 
	{
		// TODO Auto-generated method stub
		try(Connection con = DBConnect.createDBConnect();
			PreparedStatement ps=con.prepareStatement("select *from account where customerAccountNumber=?")) 
		{
			ps.setInt(1, customerAccountNumber);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				return true;
			}
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	@Override
	public List<Transaction> getTransactionHistory(int customerAccountNumber) throws CustomerException {
	    List<Transaction> transactions = new ArrayList<>();
	 
	    try (Connection con = DBConnect.createDBConnect();
	         PreparedStatement ps = con.prepareStatement(
	                 "SELECT * FROM transaction WHERE customer_account_number = ? ORDER BY transaction_date DESC")) {
	        ps.setInt(1, customerAccountNumber);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            int transactionId = rs.getInt("transaction_id");
	            String transactionType = rs.getString("transaction_type");
	            double amount = rs.getDouble("amount");
	            Timestamp transactionDate = rs.getTimestamp("transaction_date");
	            
	            Transaction transaction = new Transaction(transactionId, customerAccountNumber, transactionType, amount, transactionDate);
	            transactions.add(transaction);
	        }
	        
	        	
	    } catch (SQLException e) {
	        throw new CustomerException("Error retrieving transaction history: " + e.getMessage());
	    }
	    return transactions;
	}
	private void recordTransaction(int customerAccountNumber, String transactionType, int amount) throws CustomerException {
	    try (Connection con = DBConnect.createDBConnect();
	         PreparedStatement ps = con.prepareStatement(
	                 "INSERT INTO transaction (customer_account_number, transaction_type, amount) VALUES (?, ?, ?)")) {
	        ps.setInt(1, customerAccountNumber);
	        ps.setString(2, transactionType);
	        ps.setInt(3, amount);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        throw new CustomerException("Error recording transaction: " + e.getMessage());
	    }
	}


	
	
}
