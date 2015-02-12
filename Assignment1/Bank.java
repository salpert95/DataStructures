//Program by Steven Alpert
//Assignment 1 - File to string
//For CIS 269 at Joliet Junior College
package com.school.bank;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.*;

public class Bank{
	public static final Bank instance = new Bank();
	
	DecimalFormat df;
	boolean exit;
	Scanner scanner;
	String accountname;
	double accountbalance;
	
	String[] accountArray;
	double[] balanceArray;
	
	String[] _accountArray = {"C123", "S456", "S789"};
	double[] _balanceArray = {100, 200, 300};
	
	private Bank(){
		df = new DecimalFormat("'$'0.00");
		exit = false;
		scanner = new Scanner(System.in);
	}
	
	public void Run(String path){
		loadFile(path);
		mainMenu();
	}
	
//file opening and parsing
	
	private void loadFile(String path){
		try {
			RandomAccessFile file = new RandomAccessFile(path, "rw");
			
			int i = 0;
			
			while (file.getFilePointer() != file.length()){
				String temp1 = file.readUTF();
				double temp2 = file.readDouble();
				i++;
			}
			
			accountArray = new String[i];
			balanceArray = new double[i];
			
			
			file.seek(0);
			
			for (int j = 0; j < i; j++){
				accountArray[j] = file.readUTF();
				balanceArray[j] = file.readDouble();
			}
			
			file.close();
			return;
			
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find file for processing. Application will now exit.");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Unable to load file for processing. Application will now exit.");
			System.exit(1);
		}
	}
// menu and interface
	private void mainMenu(){
		System.out.println("Enter one of the following");
		System.out.println("1) Make a Deposit");
		System.out.println("2) Make a Withdraw");
		System.out.println("3) Display all accounts");
		System.out.println("4) Calculate Daily Interest");
		System.out.println("5) Exit");	
		System.out.println("");	
		while (!exit){
			int choice = scanner.nextInt();
			switch (choice){
			case 1: doTransaction(true); break;
			case 2: doTransaction(false); break;
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
	
//transactions deposit with true, withdraw with false	
	
	private void doTransaction(boolean deposit){
		if (accountArray.length == 0){
			System.out.println("No accounts to display!");
			return;
		}
		
		Scanner localScanner = new Scanner(System.in);
		String accountName;
		System.out.print("Accont number: ");
        accountName = localScanner.nextLine();
		
		int index = 0;
		boolean accountFound = false;
		for(int i=0; i < (accountArray.length); i++){
			if(accountArray[i].equals(accountName)){
				accountFound = true;
				index = i;
				break;
			}
		}
		
		if (!accountFound){
			System.out.println("Account not found!");
			doTransaction(deposit);
			localScanner.close();
			return;
		}
		
		localScanner = new Scanner(System.in);
		System.out.print("Amount of " + (deposit ? "deposit" : "withdrawal") + ": ");
		if (deposit){
			balanceArray[index] += localScanner.nextDouble();
			return;
		}
		balanceArray[index] -= localScanner.nextDouble();
	}
	
	
// Display all accounts balance	
	
	private void displayAllAccounts(){
		System.out.println("***********************");
		if (accountArray.length < 1){
			System.out.println("No accounts to display!");
			return;
		}
		for(int i=0; i < (accountArray.length); i++){
			System.out.println(String.format("Account %s has balance %s", accountArray[i], df.format(balanceArray[i])));
		}
		System.out.println("***********************");
	}
	
	
//calculate daily interest	
	
	private void calculateDailyInterests(){
		if (accountArray.length < 1){
			System.out.println("No accounts to display!");
			return;
		}
		for(int i=0; i < (accountArray.length); i++){
			double interest = AccountHelper.calculateDailyInterest(balanceArray[i]);
			balanceArray[i] = interest;
			System.out.println(String.format("Account %s has balance %s", accountArray[i], df.format(balanceArray[i])));
		}
	}
}
