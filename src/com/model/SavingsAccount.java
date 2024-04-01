package com.model;

import com.enums.AccountType;

public class SavingsAccount extends Account{

	public SavingsAccount(int accId, AccountType accountType, int customerId) {
		super(accId,accountType,500.0,customerId);
	}

	public SavingsAccount() {
		super();
	}
	
}
