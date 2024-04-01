package com.model;

import com.enums.AccountType;

public class ZeroBalanceAccount extends Account{

	public ZeroBalanceAccount(int accId, AccountType accountType, int customerId) {
		super(accId,accountType,0.0,customerId);
	}

	public ZeroBalanceAccount() {
		super();
	}
	
}
