package com.paypal.api.payments;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymentHistoryTestCase {

	public static final Integer COUNT = 1;

	public static final String NEXTID = "1";

	public static PaymentHistory createPaymentHistory() {
		List<Payment> payments = new ArrayList<Payment>();
		payments.add(PaymentTestCase.createPayment());
		PaymentHistory paymentHistory = new PaymentHistory();
		paymentHistory.setCount(COUNT);
		paymentHistory.setPayments(payments);
		paymentHistory.setNextId(NEXTID);
		return paymentHistory;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		PaymentHistory paymentHistory = PaymentHistoryTestCase
				.createPaymentHistory();
		Assert.assertEquals(paymentHistory.getCount(), COUNT.intValue());
		Assert.assertEquals(paymentHistory.getNextId(), NEXTID);
		Assert.assertEquals(paymentHistory.getPayments().size(), 1);
	}

	@Test(groups = "unit")
	public void testTOJSON() {
		PaymentHistory paymentHistory = PaymentHistoryTestCase
				.createPaymentHistory();
		Assert.assertEquals(paymentHistory.toJSON().length() == 0, false);
	}

	@Test(groups = "unit")
	public void testTOString() {
		PaymentHistory paymentHistory = PaymentHistoryTestCase
				.createPaymentHistory();
		Assert.assertEquals(paymentHistory.toString().length() == 0, false);
	}

}
