
import java.sql.SQLException;
import java.util.*;
public class App 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		boolean f=true;
		while(f)
		{
			System.out.println("*** Welcome to Online Banking System ***");
			System.out.println("-----------------------------------------");
			System.out.println("1. ADMIN LOGIN PORTAL");
			System.out.println("2. CUSTOMER LOGIN PORTAL");
			System.out.println("Enter your choice");
			int ch=sc.nextInt();
			switch(ch)
			{
				case 1:
					System.out.println("Admin login credentials");
					System.out.println("Enter username: ");
					String username=sc.next();
					System.out.println("Enter password: ");
					String password=sc.next();
					AccountantIntr ad=new AccountantImpl();

				try 
				{
					Accountant a = ad.Login(username, password);
					if(a==null)
					{
						System.out.println("wrong credentials");
						System.out.println("------------------");
						break;
					}
					System.out.println("login successfull");
					System.out.println("Welcome to: "+a.getUsername()+ " as Admin of Online Banking System ");
					System.out.println("-------------------------------------------------------------------");
					boolean y=true;
					while(y)
					{
						System.out.println("1. Add new customer");
						System.out.println("2. Update customer address");
						System.out.println("3. Remove/Delete the Account using Account Number");
						System.out.println("4. View particular Account Details using Account Number");
						System.out.println("5. View all customer account details");
						System.out.println("6. Logout");
						int x=sc.nextInt();
						if(x==1)
						{
							System.out.println("---New Account---");
							System.out.println("Enter customer name :");
							String a1=sc.next();
							
							System.out.println("Enter account opening balance :");
							int a2=sc.nextInt();
							System.out.println("Enter customer mail :");
							String a3=sc.next();
							System.out.println("Enter customer password :");
							String a4=sc.next();
							System.out.println("Enter customer mobile :");
							String a5=sc.next();
							System.out.println("Enter customer address :");
							String a6=sc.next();
							int s1=-1;
							try 
							{
								s1=ad.addCustomer(a1, a3, a4, a5, a6);
								try {
									ad.addAccount(a2, s1);
								}
								catch(CustomerException e) {
									e.printStackTrace();
								}
							}
							catch(CustomerException e)
							{
								System.out.println(e.getMessage());
							}
							System.out.println("Customer Added Successfully");
							System.out.println("-----------");
						}
						if(x==2)
						{
							System.out.println("---Update customer address---");
							System.out.println("Enter customer Account Number...");
							int u=sc.nextInt();
							System.out.println("Enter new address");
							String u2=sc.next();
							
							try {
								String mes=ad.updateCustomer(u, u2);
							}
							catch(CustomerException e)
							{
								e.printStackTrace();
							}
						}
						if(x==3)
						{
							System.out.println("--Remove Account--");
							System.out.println("Enter Account Number");
							int ac=sc.nextInt();
							String s=null;
							try {
								s= ad.deleteAccount(ac);
							}
							catch(CustomerException e)
							{
								e.printStackTrace();
							}
							if(s!=null)
							{
								System.out.println(s);
							}
						}
						if(x==4)
						{
							System.out.println("--Customer Details--");
							System.out.println("Enter customer account number");
							String ac=sc.next();
							try {
								Customer cus=ad.viewCustomer(ac);
								if(cus!=null)
								{
									System.out.println("Account Number : "+cus.getCustomerAccountNumber());
									System.out.println("Name : "+cus.getCustomerName());
									System.out.println("Balance : "+cus.getCustomerBalance());
									System.out.println("Email : "+cus.getCustomerMail());
									System.out.println("Password : "+cus.getCustomerPassword());
									System.out.println("Mobile number : "+cus.getCustomerMobile());
									System.out.println("Address : "+cus.getCustomerAddress());
									System.out.println("--------------------------------------");
									
									
								}
								else
								{
									System.out.println("Account Does Not Exist");
								}
							}
							catch(CustomerException e)
							{
								e.printStackTrace();
							}
						}
						if(x==5)
						{
							System.out.println("--View all customer details--");
							try {
								Customer cus=ad.viewAllCustomer();
							}
							catch(CustomerException e) 
							{
								e.printStackTrace();
							}
						}
						if(x==6)
						{
							System.out.println("Account Logout successfully!!!!");
							y=false;
							
						}
					}
					break;
				} 
				catch (AccountantException e) 
				{
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
				
				break;
				//customer login
				case 2:
					System.out.println("User login credentials");
					System.out.println("Enter username: ");
					String user=sc.next();
					System.out.println("Enter password: ");
					String pas=sc.next();
					System.out.println("Enter Account Number: ");
					int acc=sc.nextInt();
					
					CustomerIntr cd=new CustomerImpl();
					try 
					{
						
						Customer cu=cd.LoginCustomer(user,pas,acc);
						System.out.println("Login successfull!!!!");
						System.out.println("Welcome:  "+cu.getCustomerName());
						boolean m=true;
						while(m)
						{
							System.out.println("1. View Balance");
							System.out.println("2. Deposit Money");
							System.out.println("3. Withdraw money");
							System.out.println("4. Transfer money");
							System.out.println("5. LOGOUT");
							int x=sc.nextInt();
							if(x==1)
							{
								System.out.println("--Balance Details--");
								System.out.println("Current Account Balance");
								System.out.println(cd.viewBalance(acc));
								System.out.println("-------------------");
							}
							if(x==2)
							{
								System.out.println("--Deposit money--");
								System.out.println("Enter Amount to Deposit");
								int am=sc.nextInt();
								cd.Deposit(acc, am);
								System.out.println("Your Balance after deposit");
								System.out.println(cd.viewBalance(acc));
								System.out.println("-------------------");
							}
							if(x==3)
							{
								System.out.println("--Withdraw money--");
								System.out.println("Enter amount to withdraw");
								int am=sc.nextInt();
								try {
									cd.Withdraw(acc, am);
									System.out.println("Your Balance after withdraw");
									System.out.println(cd.viewBalance(acc));
									System.out.println("---------------------");
								}
								catch(CustomerException e)
								{
									System.out.println(e.getMessage());
								}
							}
							if(x==4)
							{
								System.out.println("--Amount transfer--");
								System.out.println("Enter amount to transfer");
								int t=sc.nextInt();
								System.out.println("Enter account number to transfer money");
								int ac=sc.nextInt();
								
								try {
									cd.Tranfer(acc, t, ac);
									System.out.println("Amount transferred successfully!!!!");
									System.out.println("Your Balance after Tranfer");
									System.out.println(cd.viewBalance(acc));
									System.out.println("-----------------------------------");
								}
								catch(Exception e)
								{
									System.out.println(e.getMessage());
								}
							}
							if(x==5)
							{
								System.out.println("Customer Logout successfully!!!!");
								System.out.println("Thank you for using our banking services...!!!!!");
								m=false;
							}
						}
						break;
					}
					catch (CustomerException e) 
					{
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}	
			}
		}
	}

}

