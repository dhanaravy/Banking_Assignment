package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.enums.AccountType;
import com.model.Account;
import com.model.Customer;

public interface IBankServiceProvider {
	List<Customer> fetchAllCustomers() throws SQLException;

	void createCustomer(String fname, String lname, String email, String phoneNumber, String address) throws SQLException;

	void createAccount(int accId, AccountType accountType, Double accountBalance, int customerId) throws SQLException;

	List<Account> fetchAllAccounts() throws SQLException;

	void insertTransaction(String string, double amount, int fromAccId) throws SQLException;

	void calculateInterest(int accId, double d) throws SQLException;
}
