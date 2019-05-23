package com.paypal.api.payments;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CreditCardTokenTestCase {
	public static final String CREDITCARDID = "CARD-8PV12506MG6587946KEBHH4A";
	public static final String PAYERID = "12345";

	public static CreditCardToken createCreditCardToken() {
		CreditCardToken creditCardToken = new CreditCardToken();
		creditCardToken.setCreditCardId(CREDITCARDID);
		creditCardToken.setPayerId(PAYERID);
		return creditCardToken;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		CreditCardToken creditCardToken = createCreditCardToken();
		Assert.assertEquals(creditCardToken.getCreditCardId(), "CARD-8PV12506MG6587946KEBHH4A");
		Assert.assertEquals(creditCardToken.getPayerId(), "12345");
	}

	@Test(groups = "unit")
	public void testTOJSON() {
		CreditCardToken creditCardToken = createCreditCardToken();
		Assert.assertEquals(creditCardToken.toJSON().length() == 0, false);
	}

	@Test(groups = "unit")
	public void testTOString() {
		CreditCardToken creditCardToken = createCreditCardToken();
		Assert.assertEquals(creditCardToken.toString().length() == 0, false);
	}

}
