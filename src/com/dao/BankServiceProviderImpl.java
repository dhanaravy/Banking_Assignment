package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.Util.DBUtil;
import com.enums.AccountType;
import com.model.Account;
import com.model.Customer;

public class BankServiceProviderImpl implements IBankServiceProvider {
public List<Customer> fetchAllCustomers() throws SQLException {
		
		List<Customer> list=new ArrayList<>();
		Connection conn=DBUtil.getDBconn();
		
		String sql="select * from customer";
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		ResultSet rst=pstmt.executeQuery();
		
		while(rst.next())
		{
			int id=rst.getInt("id");
			String firstName=rst.getString("first_name");
			String lastName=rst.getString("last_name");
			String email=rst.getString("email");
			String phoneNumber=rst.getString("phone_number");
			String Address=rst.getString("address");
			Customer c=new Customer(id,firstName,lastName,email,phoneNumber,Address);
			list.add(c);
		}
		
		DBUtil.DBclose();
		
		return list;		
		
	}

	public void createCustomer(String fname, String lname, String email, String phoneNumber,
			String address) throws SQLException {
		
		Connection conn=DBUtil.getDBconn();
		
		String sql="insert into customer(first_name,last_name,email,phone_number,address) values(?,?,?,?,?)";
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		pstmt.setString(1, fname);
		pstmt.setString(2, lname);
		pstmt.setString(3, email);
		pstmt.setString(4, phoneNumber);
		pstmt.setString(5, address);
		
		pstmt.executeUpdate();
		
		DBUtil.DBclose();
		
	}

	public void createAccount(int accId, AccountType accountType, Double accountBalance, int customerId) throws SQLException {

		Connection conn=DBUtil.getDBconn();
		
		String sql="insert into account values(?,?,?,?)";
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		pstmt.setInt(1, accId);
		pstmt.setString(2, accountType.toString());
		pstmt.setDouble(3, accountBalance);
		pstmt.setInt(4, customerId);
		
		pstmt.executeUpdate();
		
		DBUtil.DBclose();
	}

	public List<Account> fetchAllAccounts() throws SQLException {
		List<Account> list=new ArrayList<>();
		Connection conn=DBUtil.getDBconn();
		
		String sql="select * from account";
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		ResultSet rst=pstmt.executeQuery();
		
		while(rst.next())
		{
			int id=rst.getInt("id");
			String accountTypeStr=rst.getString("account_type");
			double accountBalance=rst.getDouble("balance");
			int customerId=rst.getInt("customer_id");
			AccountType accountType=AccountType.valueOf(accountTypeStr.toLowerCase());
			Account a=new Account(id,accountType,accountBalance,customerId);
			list.add(a);
		}
		
		DBUtil.DBclose();
		
		return list;
	}

	public void insertTransaction(String string, double amount, int fromAccId) throws SQLException {
		
		Connection conn=DBUtil.getDBconn();
		
		String sql="insert into transaction(transaction_type,amount,transaction_date,account_id) values(?,?,?,?)";
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		pstmt.setString(1, string);
		pstmt.setDouble(2, amount);
		pstmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
		pstmt.setInt(4, fromAccId);
		
		pstmt.executeUpdate();
		
		DBUtil.DBclose();
		
	}

	public void calculateInterest(int accId,double balance) throws SQLException {
		Connection conn=DBUtil.getDBconn();
		
		String sql="update account set balance=? where id=?";
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		pstmt.setDouble(1, balance);
		pstmt.setInt(2, accId);
		
		pstmt.executeUpdate();
		
		DBUtil.DBclose();
		
	}
}
