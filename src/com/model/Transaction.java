package com.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.enums.TransactionType;

public class Transaction {
	private int id;
	private TransactionType transaction_type;
	private double amount;
	private LocalDate dateTime;
	private int accountId;
	
	public Transaction() {

	}

	public Transaction(TransactionType transaction_type, double amount, LocalDate dateTime, int accountId) {
		this.transaction_type = transaction_type;
		this.amount = amount;
		this.dateTime = dateTime;
		this.accountId = accountId;
	}

	public Transaction(int id, TransactionType transaction_type, double amount, LocalDate dateTime, int accountId) {
		this.id = id;
		this.transaction_type = transaction_type;
		this.amount = amount;
		this.dateTime = dateTime;
		this.accountId = accountId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TransactionType getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(TransactionType transaction_type) {
		this.transaction_type = transaction_type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDate dateTime) {
		this.dateTime = dateTime;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String toString() {
		return "Transaction [id=" + id + ", transaction_type=" + transaction_type + ", amount=" + amount + ", dateTime="
				+ dateTime + ", accountId=" + accountId + "]";
	}
	
}
