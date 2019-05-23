package com.paypal.api.payments;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TransactionsTestCase {

	public static final Amount AMOUNT = AmountTestCase.createAmount("100.00");

	public static Transactions createTransactions() {
		Transactions transactions = new Transactions();
		transactions.setAmount(AMOUNT);
		return transactions;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		Transactions transactions = TransactionsTestCase.createTransactions();
		Assert.assertEquals(transactions.getAmount().getTotal(), "100.00");

	}

	@Test(groups = "unit")
	public void testTOJSON() {
		Transactions transactions = TransactionsTestCase.createTransactions();
		Assert.assertEquals(transactions.toJSON().length() == 0, false);
	}

	@Test(groups = "unit")
	public void testTOString() {
		Transactions transactions = TransactionsTestCase.createTransactions();
		Assert.assertEquals(transactions.toString().length() == 0, false);
	}

}
