
import java.sql.Timestamp;
public class Transaction 
{
	private int transactionId;
    private int customerAccountNumber;
    private String transactionType;
    private double amount;
    private Timestamp transactionDate;
    
    
    public Transaction(int transactionId, int customerAccountNumber, String transactionType, double amount, Timestamp transactionDate) {
        this.transactionId = transactionId;
        this.customerAccountNumber = customerAccountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        
        
    }


	public int getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}


	public int getCustomerAccountNumber() {
		return customerAccountNumber;
	}


	public void setCustomerAccountNumber(int customerAccountNumber) {
		this.customerAccountNumber = customerAccountNumber;
	}


	public String getTransactionType() {
		return transactionType;
	}


	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public Timestamp getTransactionDate() {
		return transactionDate;
	}


	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}


	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", customerAccountNumber=" + customerAccountNumber
				+ ", transactionType=" + transactionType + ", amount=" + amount + ", transactionDate=" + transactionDate
				+ "]";
	}
	
	
}
