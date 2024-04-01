package com.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.enums.AccountType;
import com.exception.InsufficientFundException;
import com.exception.InvalidAccountException;
import com.exception.OverDraftLimitExcededException;
import com.model.Account;
import com.model.CurrentAccount;
import com.model.Customer;
import com.model.SavingsAccount;
import com.model.Transaction;
import com.model.ZeroBalanceAccount;
import com.service.BankService;

public class BankApp {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		BankService bankService = new BankService();
		while (true) {
			System.out.println("**********BANK OPERATION**********");
			System.out.println("press 1. Create Account");
			System.out.println("press 2. Deposit");
			System.out.println("press 3. Withdraw");
			System.out.println("press 4. Get Balance");
			System.out.println("press 5. Transfer");
			System.out.println("press 6. Get Account Details");
			System.out.println("press 7. Calculate Interest");
			System.out.println("press 8. Get Transaction Details");
			System.out.println("press 0. for exit");
			System.out.println("***********************************");
			int input = sc.nextInt();
			if (input == 0) {
				System.out.println("Exiting...Thank you!!!");
				break;
			}
			switch (input) {
			case 1:
				try {
					// Step 1: Fetch all Customers
					List<Customer> list = bankService.fetchAllCustomers();
					List<Account> listt = bankService.fetchAllAccounts();
					// Step 2:Validate customer
					System.out.println("Enter Customer Id:");
					int CustomerId = sc.nextInt();
					boolean customerValidation = bankService.customerValidation(list, CustomerId);
					if (customerValidation == false) {
						System.out.println("User does not exist!!");
						System.out.println("Do you want to create an account?[y/n]");
						String ch = sc.next();
						switch (ch.toLowerCase()) {
						case "y":
							System.out.println("Enter First Name:");
							String fname = sc.next();
							System.out.println("Enter Last Name:");
							String lname = sc.next();
							System.out.println("Enter Email:");
							String email = sc.next();
							System.out.println("Enter Phone Number:");
							String phoneNumber = sc.next();
							System.out.println("Enter Address:");
							String address = sc.next();
							bankService.createCustomer(fname, lname, email, phoneNumber, address);
							customerValidation = true;
							break;
						case "n":
							System.out.println("Exiting....");
							break;
						default:
							System.out.println("Invalid input");
						}
					}
					if (customerValidation) {
						bankService.fetchCustomerById(list, CustomerId);
						System.out.println("Enter Account No:");
						int accId = sc.nextInt();
						bankService.accountValidate(listt, accId);
						System.out.println("Enter Account Type:");
						String accountTypeStr = sc.next();
						AccountType accountType = AccountType.valueOf(accountTypeStr.toLowerCase());
						Account account = null;
						switch (accountType) {
						case savings:
							account = new SavingsAccount(accId, accountType, CustomerId);
							break;
						case current:
							account = new CurrentAccount(accId, accountType, CustomerId);
							break;
						case zero_balance:
							account = new ZeroBalanceAccount(accId, accountType, CustomerId);
							break;
						}
						System.out.println(account.getAccountBalance());
						bankService.createAccount(accId, accountType, account.getAccountBalance(), CustomerId);
						System.out.println("Account Created Successfully!!!");
					}
				} catch (SQLException | InvalidAccountException e) {
					System.out.println(e.getMessage());
					break;
				}
				break;
			case 2:
				try {
					// Step 1: List all Accounts
					List<Account> list = bankService.fetchAllAccounts();
					// Step 2: Deposit process
					System.out.println("Enter Account Id:");
					int accountId = sc.nextInt();
					System.out.println("Enter Amount to be deposited");
					double amount = sc.nextDouble();
					bankService.depositAmount(list, accountId, amount);
					bankService.insertTansaction("deposit", amount, accountId);
					System.out.println("Amount Deposited!!");
				} catch (SQLException | InvalidAccountException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				try {
					List<Account> list = bankService.fetchAllAccounts();
					System.out.println("Enter Account Id:");
					int accountId = sc.nextInt();
					System.out.println("Enter Amount to be withdraw:");
					double amount = sc.nextDouble();
					bankService.withdrawAmount(list, accountId, amount);
					bankService.insertTansaction("withdrawal", amount, accountId);
					System.out.println("Withdrawal Successfull :)");
				} catch (SQLException | OverDraftLimitExcededException | InsufficientFundException
						| InvalidAccountException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				List<Account> list;
				try {
					list = bankService.fetchAllAccounts();
					System.out.println("Enter Account Id:");
					int accountId = sc.nextInt();
					Account a = bankService.fetchAccountBalanceById(list, accountId);
					System.out.println("Account Id\tAccount Balance");
					System.out.println(a.getId() + "\t\t" + a.getAccountBalance());
				} catch (SQLException | InvalidAccountException e) {
					System.out.println(e.getMessage());
				}

				break;
			case 5:
				try {
					System.out.println("Enter your Account Number:");
					int fromAccId = sc.nextInt();
					System.out.println("Enter the Account Number to be deposited:");
					int toAccId = sc.nextInt();

					// validate account numbers;
					List<Account> listt = bankService.fetchAllAccounts();
					bankService.accountsValidation(listt, fromAccId, toAccId);

					// amount to be deposited
					System.out.println("Enter the amount to be transfered");
					double amount = sc.nextDouble();

					// withdraw from fromAccount
					bankService.withdrawAmount(listt, fromAccId, amount);

					// deposit to toAccount
					bankService.depositAmount(listt, toAccId, amount);

					// Inserting the record into transaction table
					bankService.insertTansaction("transfer", amount, fromAccId);

					System.out.println("Amount Transferred Successfully :}");
				} catch (SQLException | InvalidAccountException | OverDraftLimitExcededException
						| InsufficientFundException e) {
					System.out.println(e.getMessage());
				}

				break;
			case 6:
				try {
					List<Account> list1 = bankService.fetchAllAccounts();
					System.out.println("Account Id\t  Acount Type\t     Account Balance\tCustomer Id");
					for (Account a1 : list1) {
						System.out.println(String.format("%-20d%-20s%-20f%d", a1.getId(), a1.getAccountType(),
								a1.getAccountBalance(), a1.getCustomerId()));
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 7:
				List<Account> list1;
				try {
					list1 = bankService.fetchAllAccounts();
					System.out.println("Enter Account Id:");
					int accId = sc.nextInt();
					double balance = bankService.validateInterest(list1, accId);
					bankService.calculateInterest(accId, balance);
					System.out.println("Interest Added to Balance :>");
				} catch (SQLException | InvalidAccountException e) {
					System.out.println(e.getMessage());
					break;
				}
				break;
			case 8:
				try {
					List<Account> list2 = bankService.fetchAllAccounts();
					System.out.println("Enter Account Number");
					int accId1 = sc.nextInt();
					if (bankService.accounttransactionValidate(list2, accId1)) {
						System.out.println("Enter From Date[YYYY-MM-DD]");
						String fromDate = sc.next();
						System.out.println("Enter To Date[YYYY-MM-DD]");
						String toDate = sc.next();
						List<Transaction> listt = bankService.fetchAllTransactions(accId1, fromDate, toDate);
						System.out.println(String.format("%-20s%-20s%-20s%-20s%s", "Transaction", "TransactionType",
								"Amount", "Transaction Date", "Account Number"));
						for (Transaction t : listt) {
							System.out.println(String.format("%-20d%-20s%-20f%-20s%d", t.getAccountId(),
									t.getTransaction_type(), t.getAmount(), t.getDateTime(), t.getAccountId()));
							System.out.println();
						}
						System.out.println();
					}
				} catch (SQLException | InvalidAccountException e) {
					System.out.println(e.getMessage());
					break;
				}
			}

		}
		sc.close();
	}
}
