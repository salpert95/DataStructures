//Program by Steven Alpert
//Assignment 1 - File to string
//For CIS 269 at Joliet Junior College
public abstract class Account {
	public Account(){
		balance = 0;
	}
	protected String accountNum;
	protected double balance;
	public double calculateDailyInterest() {
		double interest = 0;
		if (balance > 500){
			double premath = .03 / 365;
			interest = (balance - 500) * premath;
			balance += interest;
		}
		return interest;
	}
	void deposit(double amount){
		balance += amount;
	}
	void withdraw(double amount){
		balance -= amount;
	}
}
