//Program by Steven Alpert
//Assignment 1 - File to string
//For CIS 269 at Joliet Junior College
import java.util.Scanner;
import java.util.*;
import java.text.DecimalFormat;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
public class TestProgram {
	public static final TestProgram Instance = new TestProgram();

	DecimalFormat df;
	List<Account> accounts;
	boolean exit;
	Scanner scanner;

	public TestProgram(){
		df = new DecimalFormat("0.00");
		accounts = new ArrayList<Account>();
		exit = false;
		scanner = new Scanner(System.in);
	}
	
	public void Run(){
		mainMenu();
	}
	
	private void mainMenu(){
		System.out.println("Enter one of the following");
		System.out.println("1) Make a Deposit");
		System.out.println("2) Make a Withdraw");
		System.out.println("3) Display all accounts");
		System.out.println("4) Calculate Daily Interest");
		System.out.println("5) Exit");	
		System.out.println("");	
		
		while (!exit){
			int choice = getIntValue();
			
			switch (choice){
			case 1: makeDeposit(); break;
			case 2: makeWithdraw(); break;
			case 3: displayAllAccounts(); break;
			case 4: calculateDailyInterests(); break;
			case 5: exit(); break;
			}
		}	
		System.exit(0);
	}
	
	private void exit(){
		exit = true;
	}
	
	private void calculateDailyInterests(){
		for(Account account : accounts)
		{
			System.out.println(String.format("Account %d gets interest %s", account.accountNum, df.format(account.calculateDailyInterest())));
		}
	}
	
	private void displayAllAccounts(){
		System.out.println("***********************");
		for(Account account : accounts)
		{
			System.out.println(String.format("Account %d has balance %s", account.accountNum, df.format(account.balance)));
		}
		System.out.println("***********************");
	}
	
	private void makeWithdraw(){
		System.out.print("Which account to widthdraw? ");
		int value = getIntValue();
		Account account = accounts.get(value - 1);
		System.out.print("Enter the ammount to widthdraw: ");
		double ammount = getDoubleValue();
		account.withdraw(ammount);
	}
	
	private void makeDeposit(){
		System.out.print("Which account to deposit? ");
		int value = getIntValue();
		Account account = accounts.get(value - 1);
		System.out.print("Enter the ammount to deposit: ");
		double ammount = getDoubleValue();
		account.deposit(ammount);
	}
	

	public double getDoubleValue(){
		String user = scanner.next();
		try{
			double value = Double.parseDouble(user);
			return value;
		}
		//pie and cake are attempts at error checking.
		catch (NullPointerException pie){
			System.out.println("\nYou did not enter a value.");
			return 0;
		}
		catch (NumberFormatException cake){
			System.out.println("\nInvalid value");
			return 0;
		}
	}
	public int getIntValue(){
		String user = scanner.next();
		try{
			int value = Integer.parseInt(user);
			return value;
		}
		catch (NumberFormatException cake){
			System.out.println("\nInvalid value");
			return 0;
		}
	}
}
