package com.model;

import com.enums.AccountType;

public class Account {
	private int id;
	private AccountType accountType;
	protected Double accountBalance;
	private int customerId;
	
	
	public Account() {
		this.accountBalance = 0.0;
	}

	public Account(int id,AccountType accountType, Double accountBalance, int customerId) {
		this.id = id;
		this.accountType = accountType;
		this.accountBalance = accountBalance;
		this.customerId = customerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance=accountBalance;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String toString() {
		return "Account [id=" + id + ", accountType=" + accountType + ", accountBalance=" + accountBalance
				+ ", customerId=" + customerId + "]";
	}

	
	
	
}