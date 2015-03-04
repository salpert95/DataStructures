//Program by Steven Alpert
//Assignment 2 - File to Linked Lists
//For CIS 269 at Joliet Junior College
package com.school.Bank2;

import java.text.DecimalFormat;
import java.util.*;

public class Bank2 {
	public static final Bank2 INSTANCE = new Bank2();
	DecimalFormat df;
	boolean exit;
	Scanner scanner;
	CustomLinkedListAdvanced list;
	BankFileAccess bfa;
	private Bank2(){
		df = new DecimalFormat("'$'0.00");
		exit = false;
		scanner = new Scanner(System.in);	
	}
	public void run(String path) {
		bfa = new BankFileAccess(path);
		list = bfa.getList();
		mainMenu();
	}
//menu and interface	
	private void mainMenu(){
		System.out.println("Enter one of the following");
		System.out.println("1) Deposit");
		System.out.println("2) Withdraw");
		System.out.println("3) Display all accounts");
		System.out.println("4) Calculate Daily Interest");
		System.out.println("5) Create a New Account");
		System.out.println("6) Delete an Account");
		System.out.println("7) Exit");	
		System.out.println("");	
		
		while (!exit){
			int choice = scanner.nextInt();
			switch (choice){
			case 1: doTransaction(true); break;
			case 2: doTransaction(false); break;
			case 3: displayAllAccounts(); break;
			case 4: calculateDailyInterests(); break;
			case 5: editAccounts(false); break;
			case 6: editAccounts(true); break;
			case 7: exit(); break;
			}
		}	
		
		System.exit(0);
	}
//account creation and deletion
	private void editAccounts(boolean delete) {
		Scanner localScanner = new Scanner(System.in);
		String accountName;
		System.out.print(delete ? "Enter the account number to delete: " : "Enter the account number for the new checking account: ");
        accountName = localScanner.nextLine();
        
        int index = 0;
		boolean accountFound = false;
		for(int i=1; i <= list.count(); i++){
			if(list.get(i).equals(accountName)){
				accountFound = true;
				index = i;
				break;
			}
		}
		
		if (delete){
			if (!accountFound){
				System.out.println("Account does not exist!");
				editAccounts(delete);
				return;
			}
			
			list.remove(index);
			list.remove(index);
			return;
		}
		
		if (accountFound){
			System.out.println("Account already exists!");
			editAccounts(delete);
			return;
		}
		
		double accountBalance;
		System.out.print("Enter the initial balance: ");
		accountBalance = getDoubleValue();
		
		list.add(accountName);
		list.add(accountBalance);
	}

	private void exit() {
		bfa.saveBankFile(list);
		exit = true;
	}
//calculate daily intererst
	private void calculateDailyInterests() {
		if (list.count() <= 0){
			System.out.println("No accounts to display!");
			return;
		}
		for (int i = 1; i <= list.count(); i += 2){
			double interest = AccountHelper.calculateDailyInterest(Double.valueOf(list.get(i + 1).toString()));
			list.set(interest, i + 1);
			System.out.println(String.format("Account %s has balance %s", list.get(i), df.format(Double.valueOf(list.get(i + 1).toString()))));
		}
	}
	
//display all accounts
	private void displayAllAccounts() {
		System.out.println("***********************");
		if (list.count() <= 0){
			System.out.println("No accounts to display!");
			System.out.println("***********************");
			return;
		}
		for (int i = 1; i <= list.count(); i += 2){
			System.out.println(String.format("Account %s has balance %s", list.get(i), df.format(Double.valueOf(list.get(i + 1).toString()))));
		}
		System.out.println("***********************");
	}
	
// transactions deposit when true, withdrawl when false.
	private void doTransaction(boolean deposit) {
		if (list.count() == 0){
			System.out.println("No accounts to display!");
			return;
		}
		Scanner localScanner = new Scanner(System.in);
		String accountName;
		System.out.print("Account number: ");
        accountName = localScanner.nextLine();
        int index = 0;
		boolean accountFound = false;
		for(int i=1; i <= list.count(); i++){
			if(list.get(i).equals(accountName)){
				accountFound = true;
				index = i;
				break;
			}
		}
	
		//deposit/withdraw error catching	
		if (!accountFound){
			System.out.println("Account not found!");
			doTransaction(deposit);
			return;
		}
		System.out.print("Amount of " + (deposit ? "deposit" : "withdrawal") + ": ");
		double base = Double.valueOf(list.get(index + 1).toString());
		double input = getDoubleValue();
		if (deposit){
			list.set(input + base, index + 1);
			return;
		}
		list.set(base - input, index + 1);
	}

	//Cake and Pie error catching 	
	public double getDoubleValue(){
		String dude = scanner.next();
		try{
			double value = Double.parseDouble(dude);
			return value;
		}
		catch (NullPointerException pie){
			System.out.println("\nYou did not enter a value. (using 0)");
			return 0;
		}
		catch (NumberFormatException cake){
			System.out.println("\nInvalid value (using 0)");
			return 0;
		}
	}
}
