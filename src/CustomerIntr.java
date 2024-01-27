
public interface CustomerIntr 
{
	public Customer LoginCustomer(String customerName,String customerPassword,int customerAccountNumber) throws CustomerException;
	public int viewBalance(int customerAccountNumber) throws CustomerException;
	public int Deposit(int customerAccountNumber,int amount) throws CustomerException;
	public int Withdraw(int customerAccountNumber,int amount) throws CustomerException;
	public int Tranfer(int customerAccountNumber,int amount,int customerAccountNumber2) throws CustomerException;
}
