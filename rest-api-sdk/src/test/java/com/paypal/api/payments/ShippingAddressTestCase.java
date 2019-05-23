package com.paypal.api.payments;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ShippingAddressTestCase {

	public static final String RECIPIENTSNAME = "TestUser";

	public static ShippingAddress createShippingAddress() {
		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setRecipientName(RECIPIENTSNAME);
		return shippingAddress;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		ShippingAddress shippingAddress = createShippingAddress();
		Assert.assertEquals(shippingAddress.getRecipientName(), RECIPIENTSNAME);
	}

	@Test(groups = "unit")
	public void testTOJSON() {
		ShippingAddress address = createShippingAddress();
		Assert.assertEquals(address.toJSON().length() == 0, false);
	}

	@Test(groups = "unit")
	public void testTOString() {
		ShippingAddress address = createShippingAddress();
		Assert.assertEquals(address.toString().length() == 0, false);
	}

}
