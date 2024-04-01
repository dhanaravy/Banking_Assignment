package com.test;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.exception.InvalidAccountException;
import com.model.Account;
import com.model.Customer;
import com.service.BankService;


public class BankServiceTest {
	
	BankService bankService=new BankService();
	
	@Test
	public void fetchAllCustomersTest() {
		try {
            List<Customer> allCustomers = bankService.fetchAllCustomers();
            Assert.assertTrue(allCustomers.size() > 0);
        } catch (SQLException e) {
            Assert.fail();
        }
	}
	
	@Test
	public void customerValidationTest() {
		try {
			
			// Usecase 1
			List<Customer> customer=bankService.fetchAllCustomers();
			Customer customerr=customer.get(0);
			Assert.assertEquals(true, bankService.customerValidation(customer,customerr.getId()));
			
			// Usecase 2
			Customer customer1=new Customer( 32,"dhana","lakshmi","dhana@gmail.com","9360805403","ranipet");
			Assert.assertEquals(false, bankService.customerValidation(customer,customer1.getId()));
		} catch (SQLException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void fetchAllAccountsTest() {
		try {
            List<Account> allAccounts = bankService.fetchAllAccounts();
            Assert.assertTrue(allAccounts.size() > 0);
        } catch (SQLException e) {
            Assert.fail();
        }
	}
	
	@Test
	public void fetchAccountBalanceByIdTest() {
		List<Account> allAccounts;
		try {
			allAccounts = bankService.fetchAllAccounts();
			int accountId=13;
			Account a=bankService.fetchAccountBalanceById(allAccounts,accountId);
			Assert.assertEquals(136000.0,a.getAccountBalance(),0.001);
		} catch (SQLException e) {
			Assert.fail();
		} catch (InvalidAccountException e) {
			Assert.assertEquals("Account does not exist :)", e.getMessage());
		}
		
	}
	
}
