package com.paypal.api.payments;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PayerInfoTestCase {

	public static final String EMAIL = "somename@somedomain.com";

	public static final String FIRSTNAME = "Joe";

	public static final String LASTNAME = "Shopper";

	public static final String PAYERID = "12345";

	public static final String PHONE = "716-298-1822";

	public static final ShippingAddress SHIPPINGADDRESS = AddressTestCase
			.createShippingAddress();

	public static PayerInfo createPayerInfo() {
		PayerInfo payerInfo = new PayerInfo();
		payerInfo.setFirstName(FIRSTNAME);
		payerInfo.setLastName(LASTNAME);
		payerInfo.setEmail(EMAIL);
		payerInfo.setPayerId(PAYERID);
		payerInfo.setPhone(PHONE);
		payerInfo.setShippingAddress(SHIPPINGADDRESS);
		return payerInfo;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		PayerInfo payerInfo = createPayerInfo();
		Assert.assertEquals(payerInfo.getFirstName(), FIRSTNAME);
		Assert.assertEquals(payerInfo.getLastName(), LASTNAME);
		Assert.assertEquals(payerInfo.getEmail(), EMAIL);
		Assert.assertEquals(payerInfo.getPayerId(), PAYERID);
		Assert.assertEquals(payerInfo.getPhone(), PHONE);
		Assert.assertEquals(payerInfo.getShippingAddress().getCity(),
				AddressTestCase.CITY);
	}

	@Test(groups = "unit")
	public void testTOJSON() {
		PayerInfo payerInfo = createPayerInfo();
		Assert.assertEquals(payerInfo.toJSON().length() == 0, false);
	}

	@Test(groups = "unit")
	public void testTOString() {
		PayerInfo payerInfo = createPayerInfo();
		Assert.assertEquals(payerInfo.toString().length() == 0, false);
	}
}
