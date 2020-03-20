
package com.cg.mypaymentapp.service;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.Exception.InsufficientBalanceException;
import com.cg.mypaymentapp.Exception.InvalidInputException;
import com.cg.mypaymentapp.Exception.MobileNumberNotRegistered;
import com.cg.mypaymentapp.Exception.ZeroBalanceException;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;


public class WalletServiceImpl implements WalletService{

private WalletRepo repo;

	public WalletServiceImpl(Map<String, Customer> data){
		repo= new WalletRepoImpl(data);
	}
	public WalletServiceImpl(WalletRepo repo) {
		super();
		this.repo = repo;
	}

	public WalletServiceImpl() 

{		

	super();	
}

	public Customer createAccount(String name, String mobileNo, BigDecimal amount)
	{
		
		if(amount.compareTo(new BigDecimal("0.00")) <0)
		{
			
			throw new InvalidInputException("amount caanot be negative");
			
		}
		Pattern p=Pattern.compile("[0-9]{10}");
		Matcher m=p.matcher(mobileNo);
		if(m.find()&& m.group().equals(mobileNo))
		{		
		         Customer customer1=null;
			     customer1=repo.findOne(mobileNo);
		         if(customer1==null)
		              {
		              Wallet ob=new Wallet(amount);
	                  Customer customer=new Customer(name,mobileNo,ob);
			            customer1=customer;
				        repo.save(customer);
		                  return customer1;
		              }
		                else
		                    {
			
			
			                    throw new MobileNumberNotRegistered("your mobile number already registered");
		                         }
		}
		else
		{
			throw new InvalidInputException("please correctly enter 10 digit mobile number");
		}
		}

	public Customer showBalance(String mobileNo) 
	{
		Pattern p=Pattern.compile("[0-9]{10}");
		Matcher m=p.matcher(mobileNo);
		if(m.find()&& m.group().equals(mobileNo))
		{	
		               Customer customer=null;
			         customer = repo.findOne(mobileNo);
		                   if(customer!=null)
			                 return customer;
		                    else
		                	throw new MobileNumberNotRegistered("your mobile number not registered");
		}
		else
		{
			throw new InvalidInputException("please correctly enter 10 digit mobile number");
		}
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) 
	{
		
		if(amount.compareTo(new BigDecimal("0.00")) ==0)
		{
			
			throw new ZeroBalanceException("You cannot transfer Zero balance");
			
		} 
		if(amount.compareTo(new BigDecimal("0.00")) <0)
		{
			
			throw new InvalidInputException("amount caanot be negative");
			
		} 
		Pattern pattern=Pattern.compile("[0-9]{10}");
		Matcher m=pattern.matcher(sourceMobileNo);
		Matcher m1=pattern.matcher(targetMobileNo);
		if(m.find()&& m.group().equals(sourceMobileNo) &&  m1.find()&& m1.group().equals(targetMobileNo))
		{
		   Customer cust1=null;
		    Customer cust2=null;
			cust1=repo.findOne(sourceMobileNo);
			 cust2=repo.findOne(targetMobileNo);
	                 if(cust1==null && cust2==null)
	             	throw new MobileNumberNotRegistered("Source mobile Number and target mobile number is not registered");
	              else if(cust2==null)
	    	   throw new MobileNumberNotRegistered("Target mobile Number is not registered");
	              else if(cust1==null)
	    	     throw new MobileNumberNotRegistered("Source mobile Number is not registered");
	    
	              else
	                {
	    	          if(sourceMobileNo.equals(targetMobileNo))
			           {
			throw new InvalidInputException("Source mobile number and target mobile number cannot be same");
			             }
		    BigDecimal p=cust1.getWallet().getBalance();
		   p=p.subtract(amount);
		if(p.compareTo(new BigDecimal("0.00")) <0)
		{
			throw new InsufficientBalanceException("Insufficient balance in source registered mobile number");
		}
		Wallet ob=new Wallet(p);
		cust1.setWallet(ob);
		BigDecimal q=cust2.getWallet().getBalance();
		q=q.add(amount);
				Wallet ob1=new Wallet(q);
		cust2.setWallet(ob1);
          
			repo.save(cust1);
			repo.save(cust2);
	
         return cust1;
	    }
    }
	else
	{
		throw new InvalidInputException("please correctly enter 10 digit mobile number");
	}
	}
}

	/*public Customer depositAmount(String mobileNo, BigDecimal amount)
	{
		if(amount.compareTo(new BigDecimal("0.00")) ==0)
		{
			
			throw new ZeroBalanceException("You cannot deposit Zero balance in your account");
			
		} 
		if(amount.compareTo(new BigDecimal("0.00")) <0)
		{
			
			throw new InvalidInputException(" deposited amount caanot be negative or zero");
			
		} 
		Pattern pattern=Pattern.compile("[0-9]{10}");
		Matcher m=pattern.matcher(mobileNo);
		if(m.find()&& m.group().equals(mobileNo))
		{
		Customer cust1=null;
		
			cust1=repo.findOne(mobileNo);
		
		 if(cust1==null)
		    
		    	throw new MobileNumberNotRegistered(" mobile Number is not registered");
		 else
		 {
		BigDecimal p1=cust1.getWallet().getBalance();
		p1=p1.add(amount);
		Wallet ob=new Wallet(p1);
		cust1.setWallet(ob);
		
		repo.save(cust1);
		return cust1;
		 }
		}
		else
		{
			
			throw new InvalidInputException("please correctly enter 10 digit mobile number");
		}	
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) 
	{	if(amount.compareTo(new BigDecimal("0.00")) ==0)
			throw new ZeroBalanceException("You cannot withdraw Zero balance in your account");
		if(amount.compareTo(new BigDecimal("0.00")) <=0)
			throw new InvalidInputException("withdrawl amount caanot be negative ror zero");
		Pattern pattern=Pattern.compile("[0-9]{10}");
		Matcher m=pattern.matcher(mobileNo);
		if(m.find()&& m.group().equals(mobileNo))
		{
		     Customer cust1=null;
			cust1=repo.findOne(mobileNo);
		      if(cust1==null)
		    	throw new MobileNumberNotRegistered("mobile Number is not registered");
		     else
		    {
		      BigDecimal p1=cust1.getWallet().getBalance();
		        p1=p1.subtract(amount);
		      if(p1.compareTo(new BigDecimal("0.00")) <0)
			  throw new InsufficientBalanceException("Insufficient balance in source registered mobile number");
		     Wallet ob=new Wallet(p1);
		      cust1.setWallet(ob);
		     repo.save(cust1);
		return cust1;
		 }
		}
		else
		throw new InvalidInputException("please correctly enter 10 digit mobile number");
}	
}*/

