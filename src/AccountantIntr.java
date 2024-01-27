
public interface AccountantIntr 
{
	public Accountant Login(String username,String password) throws AccountantException;
	public int addCustomer(String customerName,String customerMail, String customerPassword, 
			String customerMobile,String customerAddress) throws CustomerException;
	public String addAccount(int customerBalance , int cid) throws CustomerException;
	public String updateCustomer(int customerAccountNumber , String customerAddress) throws CustomerException;
	public String deleteAccount(int customerAccountNumber) throws CustomerException;
	public Customer viewCustomer(String customerAccountNumber) throws CustomerException;
	public Customer viewAllCustomer() throws CustomerException;
}
