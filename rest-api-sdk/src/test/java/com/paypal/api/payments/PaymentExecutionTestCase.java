package com.paypal.api.payments;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymentExecutionTestCase {
	
	public static PaymentExecution createPaymentExecution(){
		List<Transactions> transactions = new ArrayList<Transactions>();
		transactions.add(TransactionsTestCase.createTransactions());
		PaymentExecution pae=new PaymentExecution();
		pae.setPayerId(PayerInfoTestCase.PAYERID);
		pae.setTransactions(transactions);
		return pae;
	}

	@Test(groups = "unit")
	public void testConstruction(){
		PaymentExecution pae = createPaymentExecution();
		Assert.assertEquals(pae.getPayerId(), PayerInfoTestCase.PAYERID);
		Assert.assertEquals(pae.getTransactions().get(0).getAmount().getTotal(),"100.00");
		Assert.assertEquals(pae.getTransactions().get(0).getAmount().getCurrency(),AmountTestCase.CURRENCY);
	}
	

	@Test(groups = "unit")
	public void testToJson(){
		PaymentExecution pae = createPaymentExecution();
		Assert.assertEquals(pae.toJSON().length() == 0, false);
	}

	@Test(groups = "unit")
	public void testToString(){
		PaymentExecution pae = createPaymentExecution();
		Assert.assertEquals(pae.toString().length() == 0, false);
	}
}
