//Program by Steven Alpert
//Assignment 2 - File to Linked Lists
//For CIS 269 at Joliet Junior College
package com.school.Bank2;

import java.io.*;

public class BankFileAccess {
	private CustomLinkedListAdvanced l;
	private String filename;
	public int count;
	
//file access and loading into linked list format	
	public BankFileAccess(String filename){
		count = 0;
		this.filename = filename;
		this.l = new CustomLinkedListAdvanced();
		loadBankFile();
	}
	public CustomLinkedListAdvanced getList(){
		return l;
	}
	
//save file	
	public void saveBankFile(CustomLinkedListAdvanced list){
		try {
			RandomAccessFile file = new RandomAccessFile(filename, "rw");
			for (int i = 1; i <= list.count(); i += 2){
				file.writeUTF(list.get(i).toString());
				file.writeDouble(Double.valueOf(list.get(i + 1).toString()));
			}
			file.close();
			return;
		}  catch (IOException e) {
			System.out.println("Unable to save file. Application will now exit.");
			System.exit(1);
		}
	}
	
//load file	
	private void loadBankFile(){
		try {
			RandomAccessFile file = new RandomAccessFile(filename, "rw");
			while (file.getFilePointer() != file.length()){
				l.add(file.readUTF());
				l.add(file.readDouble());
				count++;
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
}
