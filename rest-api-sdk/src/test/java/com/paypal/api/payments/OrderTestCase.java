package com.paypal.api.payments;

import com.paypal.base.util.TestConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.paypal.base.rest.PayPalRESTException;

/**
 * NOTE: Tests that use this class must be ignored when run in an automated environment because executing an order will require approval via the executed payment's approval_url.
 */
@Test(enabled = false)
public class OrderTestCase {
	
	private static Order order = null;
	
	public static final String ID = "O-2HT09787H36911800";

	@Test(groups = "integration", enabled = false)
	public void testGetOrder() throws PayPalRESTException {
		order = Order.get(TestConstants.SANDBOX_CONTEXT, ID);
	}
	
	@Test(groups = "integration", dependsOnMethods = { "testGetOrder" }, enabled = false)
	public void testAuthorize() throws PayPalRESTException {
		Authorization authorization = order.authorize(TestConstants.SANDBOX_CONTEXT);
		Assert.assertEquals(authorization.getState(), "Pending");
	}
	
	@Test(groups = "integration", dependsOnMethods = { "testAuthorize" }, enabled = false)
	public void testCapture() throws PayPalRESTException {
		Capture capture = order.capture(TestConstants.SANDBOX_CONTEXT, CaptureTestCase.createCapture());
		Assert.assertEquals(capture.getState(), "Pending");
	}
	
	@Test(groups = "integration", dependsOnMethods = { "testCapture" }, enabled = false)
	public void testDoVoid() throws PayPalRESTException {
		order = order.doVoid(TestConstants.SANDBOX_CONTEXT);
		Assert.assertEquals(order.getState(), "voided");
	}
}
