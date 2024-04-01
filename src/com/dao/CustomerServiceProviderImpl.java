package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.Util.DBUtil;
import com.enums.TransactionType;
import com.model.Customer;
import com.model.Transaction;

public class CustomerServiceProviderImpl implements ICustomerServiceProvider {

	public void depositAmount(int accountId, double amount) throws SQLException {
		
		Connection conn=DBUtil.getDBconn();
		
		String sql="update account set balance=? where id=?";
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		pstmt.setDouble(1, amount);
		pstmt.setInt(2, accountId);
		
		pstmt.executeUpdate();
		
		DBUtil.DBclose();
		
	}

	public void withdrawAmount(int accountId, double amount) throws SQLException {
		
		Connection conn=DBUtil.getDBconn();
		
		String sql="update account set balance=? where id=?";
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		pstmt.setDouble(1, amount);
		pstmt.setInt(2, accountId);
		
		pstmt.executeUpdate();
		
		DBUtil.DBclose();
	}

	public List<Transaction> fetchAllTransactions(int accountId, String fromDate, String toDate) throws SQLException {
		
		List<Transaction> list=new ArrayList<>();
		Connection conn=DBUtil.getDBconn();
		
		String sql="select * from transaction where account_id=? and transaction_date between ? and ? ";
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		pstmt.setInt(1,accountId);
		pstmt.setDate(2, Date.valueOf(fromDate));
		pstmt.setDate(3, Date.valueOf(toDate));
		
		ResultSet rst=pstmt.executeQuery();
		
		while(rst.next())
		{
			int id=rst.getInt("id");
			String transactionTpyeStr=rst.getString("transaction_type");
			double amount=rst.getDouble("amount");
			String transactionDate=String.valueOf(rst.getDate("transaction_date"));
			int accId=rst.getInt("account_id");
			
			TransactionType transactionType=TransactionType.valueOf(transactionTpyeStr);
			Transaction t=new Transaction(id,transactionType,amount,LocalDate.parse(transactionDate),accId);
			list.add(t);
		}
		
		DBUtil.DBclose();
		
		return list;
	}

	

}
