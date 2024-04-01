package com.service;

import java.sql.SQLException;
import java.util.List;
import com.dao.*;
import com.enums.AccountType;
import com.model.Account;
import com.model.CurrentAccount;
import com.model.Customer;
import com.model.SavingsAccount;
import com.model.Transaction;
import com.model.ZeroBalanceAccount;
import com.exception.*;


public class BankService {
	Customer customer=new Customer();
	ICustomerServiceProvider customerService=new CustomerServiceProviderImpl();
	IBankServiceProvider bankService=new BankServiceProviderImpl();
	

	public List<Customer> fetchAllCustomers() throws SQLException {
		
		return bankService.fetchAllCustomers(); 
	}

	public boolean customerValidation(List<Customer> list, int customerId) {
		
		for(Customer c:list)
		{
			if(c.getId()==customerId)
				return true;
		}
		return false;
	}

	public void createCustomer(String fname, String lname, String email, String phoneNumber,
			String address) throws SQLException {
		
		bankService.createCustomer(fname,lname,email,phoneNumber,address);
		
	}

	public void createAccount(int accId, AccountType accountType, Double accountBalance, int customerId) throws SQLException {
			
		bankService.createAccount(accId,accountType,accountBalance,customerId);
	}

	public void fetchCustomerById(List<Customer> list, int customerId) {
		
		for(Customer c:list)
		{
			if(c.getId()==customerId)
			{
				System.out.println("Welcome "+c.getFirstName()+" "+c.getLastName()+"!!!");
				System.out.println();
				break;
			}
		}
		
	}

	public void depositAmount(List<Account> list,int accountId, double amount) throws SQLException, InvalidAccountException {
		
		boolean flag=false;
		for(Account a:list)
		{
			if(a.getId()==accountId)
			{
				flag=true;
				customerService.depositAmount(accountId,amount+a.getAccountBalance());
				break;
			}
		}
		if(flag==false)
			throw new InvalidAccountException("Account does not exist :(");
	}

	public List<Account> fetchAllAccounts() throws SQLException {

		return bankService.fetchAllAccounts();
	}

	public void withdrawAmount(List<Account> list, int accountId, double amount) throws SQLException, OverDraftLimitExcededException, InsufficientFundException, InvalidAccountException {
		boolean flag=false;
		for(Account a:list)
		{
			if(a.getId()==accountId)
			{
				switch(a.getAccountType())
				{
				case savings:
					//a=new SavingsAccount();
					if(amount<=a.getAccountBalance()-500)
					{
						customerService.withdrawAmount(accountId,a.getAccountBalance()-amount);
						flag=true;
					}
					else
						throw new InsufficientFundException("Insufficient Fund :|");
					break;
				case current:
					//a=new CurrentAccount();
					if(amount<=a.getAccountBalance()+CurrentAccount.overDraftLimit)
					{
						customerService.withdrawAmount(accountId,a.getAccountBalance()-amount);
						flag=true;
					}
					else
						throw new OverDraftLimitExcededException("OverDraft Limit Exceeded :|");
					break;
				case zero_balance:
					//a=new ZeroBalanceAccount();
					if(amount<=a.getAccountBalance())
					{
						customerService.withdrawAmount(accountId,a.getAccountBalance()-amount);
						flag=true;
					}
					else
						throw new InsufficientFundException("Insufficient Fund :|");	
					break;
				
				}
				break;	
			}
		}
		if(!flag)
			throw new InvalidAccountException("Account does not exist :(");
	}

	public Account fetchAccountBalanceById(List<Account> list, int accountId) throws InvalidAccountException {
		for(Account a:list)
		{
			if(a.getId()==accountId)
			{
				return a;
			}
		}
		throw new InvalidAccountException("Account does not exist :)");
	}

	public boolean accountsValidation(List<Account> listt, int fromAccId, int toAccId) throws InvalidAccountException {
		
		boolean flagfrom=false;
		boolean flagto=false;
		for(Account a:listt)
		{
			if(a.getId()==fromAccId)
				flagfrom=true;
			if(a.getId()==toAccId)
				flagto=true;
		}
		
		if(flagfrom && flagto)
		{
			return flagfrom && flagto;
		}
		else
		{
			throw new InvalidAccountException("Account does not exist :(");
		}
	}

	public void insertTansaction(String string, double amount, int fromAccId) throws SQLException {
		
		bankService.insertTransaction(string,amount,fromAccId);
		
	}

	public List<Transaction> fetchAllTransactions(int accId, String fromDate, String toDate) throws SQLException {
		return customerService.fetchAllTransactions(accId,fromDate,toDate);
	}

	public double validateInterest(List<Account> list1, int accId) throws InvalidAccountException {
		boolean flagid=false;
		boolean flagtype=false;
		double balance = 0;
		for(Account a:list1)
		{
			if(a.getId()==accId)
			{
				flagid=true;
				if(a.getAccountType().toString().equals("savings"))
					flagtype=true;
					balance=a.getAccountBalance();
			}
		}
		if(flagtype==true && flagid==true)
			return balance;
		else if (flagtype==false && flagid==true)
			throw new InvalidAccountException("Not a Savings Account :<");
		else
			throw new InvalidAccountException("Account does not exist :(");
	}

	public void calculateInterest(int accId, double balance) throws SQLException {

		bankService.calculateInterest(accId,balance+(balance*0.1));
	}

	public void accountValidate(List<Account> list, int accId) throws InvalidAccountException {
		
		for(Account a:list)
		{
			if(a.getId()==accId)
				throw new InvalidAccountException("Account Already Exist :{");
		}
		
	}

	public boolean accounttransactionValidate(List<Account> list2, int accId1) throws InvalidAccountException {
		for(Account a:list2)
		{
			if(a.getId()==accId1)
				return true;
		}
		throw new InvalidAccountException("Account does not Exist :{");
	}
	
	

}
