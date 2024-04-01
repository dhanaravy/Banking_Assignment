package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.model.Transaction;

public interface ICustomerServiceProvider {

	void depositAmount(int accountId, double amount) throws SQLException;

	void withdrawAmount(int accountId, double d) throws SQLException;

	List<Transaction> fetchAllTransactions(int accId, String fromDate, String toDate) throws SQLException;
	
}
