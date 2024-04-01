package com.model;

import com.enums.AccountType;

public class CurrentAccount extends Account{
	
	public final static double overDraftLimit=100000.0;
	public CurrentAccount(int accId, AccountType accountType, int customerId) {
		super(accId,accountType,10000.0,customerId);
	}
	
	public CurrentAccount() {
		super();
	}

}
