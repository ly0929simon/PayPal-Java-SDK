package com.paypal.api.payments;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DetailsTestCase {

	public static final String FEE = "100.00";

	public static final String SHIPPING = "12.50";

	public static final String SUBTOTAL = "200.00";

	public static final String TAX = "20.00";

	public static Details createDetails() {
		Details amountDetails = new Details();
		amountDetails.setFee(FEE);
		amountDetails.setShipping(SHIPPING);
		amountDetails.setSubtotal(SUBTOTAL);
		amountDetails.setTax(TAX);
		return amountDetails;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		Details amountDetails = createDetails();
		Assert.assertEquals(amountDetails.getFee(), FEE);
		Assert.assertEquals(amountDetails.getShipping(), SHIPPING);
		Assert.assertEquals(amountDetails.getSubtotal(), SUBTOTAL);
		Assert.assertEquals(amountDetails.getTax(), TAX);
	}
	
	@Test(groups = "unit")
	public void testTOJSON() {
		Details amountDetails = createDetails();
		Assert.assertEquals(amountDetails.toJSON().length() == 0, false);
	}
	
	@Test(groups = "unit")
	public void testTOString() {
		Details amountDetails = createDetails();
		Assert.assertEquals(amountDetails.toString().length() == 0, false);
	}

}
