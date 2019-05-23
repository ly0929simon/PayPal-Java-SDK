package com.paypal.api.payments;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AddressTestCase {

	public static final String CITY = "Niagara Falls";

	public static final String COUNTRYCODE = "US";

	public static final String LINE1 = "3909 Witmer Road";

	public static final String LINE2 = "Niagara Falls";

	public static final String POSTALCODE = "14305";

	public static final String PHONE = "716-298-1822";

	public static final String STATE = "NY";
	
	public static Address createAddress() {
		Address billingAddress = new Address();
		billingAddress.setCity(CITY);
		billingAddress.setCountryCode(COUNTRYCODE);
		billingAddress.setLine1(LINE1);
		billingAddress.setLine2(LINE2);
		billingAddress.setPostalCode(POSTALCODE);
		billingAddress.setPhone(PHONE);
		billingAddress.setState(STATE);
		return billingAddress;
	}
	
	public static ShippingAddress createShippingAddress() {
		ShippingAddress address = new ShippingAddress();
		address.setCity(CITY);
		address.setCountryCode(COUNTRYCODE);
		address.setLine1(LINE1);
		address.setLine2(LINE2);
		address.setPostalCode(POSTALCODE);
		address.setPhone(PHONE);
		address.setState(STATE);
		return address;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		Address address = createAddress();
		Assert.assertEquals(address.getCity(), CITY);
		Assert.assertEquals(address.getCountryCode(), COUNTRYCODE);
		Assert.assertEquals(address.getLine1(), LINE1);
		Assert.assertEquals(address.getLine2(), LINE2);
		Assert.assertEquals(address.getPostalCode(), POSTALCODE);
		Assert.assertEquals(address.getPhone(), PHONE);
		Assert.assertEquals(address.getState(), STATE);
	}
	
	@Test(groups = "unit")
	public void testTOJSON() {
		Address address = createAddress();
		Assert.assertEquals(address.toJSON().length() == 0, false);
	}
	
	@Test(groups = "unit")
	public void testTOString() {
		Address address = createAddress();
		Assert.assertEquals(address.toString().length() == 0, false);
	}
}
