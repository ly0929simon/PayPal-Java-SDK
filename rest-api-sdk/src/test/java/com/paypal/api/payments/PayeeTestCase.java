package com.paypal.api.payments;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PayeeTestCase {

	public static final String MERCHANTID = "12345";

	public static final String EMAIL = "somename@somedomain.com";

	public static final Phone PHONE = new Phone("1", "716-298-1822");

	public static Payee createPayee() {
		Payee payee = new Payee();
		payee.setMerchantId(MERCHANTID);
		payee.setEmail(EMAIL);
		payee.setPhone(PHONE);
		return payee;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		Payee payee = createPayee();
		Assert.assertEquals(payee.getMerchantId(), MERCHANTID);
		Assert.assertEquals(payee.getEmail(), EMAIL);
		Assert.assertEquals(payee.getPhone(), PHONE);
	}

	@Test(groups = "unit")
	public void testTOJSON() {
		Payee payee = createPayee();
		Assert.assertEquals(payee.toJSON().length()==0, false);
	}

	@Test(groups = "unit")
	public void testTOString() {
		Payee payee = createPayee();
		Assert.assertEquals(payee.toString().length()==0, false);
	}

}
