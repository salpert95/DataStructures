//Program by Steven Alpert
//Assignment 2 - File to Linked Lists
//For CIS 269 at Joliet Junior College
package com.school.Bank2;

public class AccountHelper {
	public static double calculateDailyInterest(double value){
		double interest = 0;
		// note to self for future reference: 3% interest can be calculated simply by multiplying by 1.03
		double premath = 1.03;
		interest = value * premath;
		return interest;
	}
}
