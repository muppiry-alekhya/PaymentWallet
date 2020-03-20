package com.cg.mypaymentapp.pl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class Client 
{
	HashMap<String,Customer>map=new HashMap<String,Customer>();
WalletService pk=new WalletServiceImpl(map);
void menu()
{ 
	
		System.out.println("Please enter ur choice");
		System.out.println("1.Create an account");
		System.out.println("2.show Balance");
		System.out.println("3.fund transfer");
			
		Scanner sc=new Scanner(System.in);
		int option=sc.nextInt();
		switch(option)
		{
		
		case 1:
	       System.out.println("please enter ur name");
		String name=sc.next();
		System.out.println("please enter ur mobile no");
		String mob=sc.next();
		System.out.println("please enter amount ");
		BigDecimal bigdecimal=sc.nextBigDecimal();
		try {
        Customer cust=pk.createAccount(name, mob, bigdecimal);
System.out.println(cust);
		}
		catch(Exception e)
		{
	System.out.println(e.getMessage());
		}
		
break;
		case 2:System.out.println("please enter your mobile no");
		   String mob1=sc.next();
		   try {
		    Customer cust1=pk.showBalance(mob1);
			 System.out.println("ur balance in account is:"+cust1.getWallet().getBalance());
		   }
		   catch(Exception e)
		   {   
			  System.out.println(e.getMessage()); 
		   }

		  break;
	default:System.out.println("wrong choice");
	case 3:
	System.out.println("please enter your source mobile no");
	String smob=sc.next();
	System.out.println("please enter your target mobile no");
	String tmob=sc.next();
	System.out.println("please enter amount to be transferred");
	BigDecimal amount=sc.nextBigDecimal();
	try {
	Customer cust=pk.fundTransfer(smob, tmob, amount);
	System.out.println("Balance has been successfully transfered ur balance after transaction is"+cust.getWallet().getBalance());
	}
	catch(Exception e)
	{System.out.println(e.getMessage());}
		break;
		}
	
	
	
}
	
public static void main(String[] args) 
{
Client ob=new Client();
while(true)
{
	
	ob.menu();
}
	
	
}	
	
}
