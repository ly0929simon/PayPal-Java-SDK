package com.paypal.api.payments;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RedirectUrlsTestCase {
	
	public static final String CANCELURL = "http://somedomain.com";

	public static final String RETURNURL = "http://somedomain.com";
	
	public static RedirectUrls createRedirectUrls() {
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(CANCELURL);
		redirectUrls.setReturnUrl(RETURNURL);
		return  redirectUrls;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		RedirectUrls redirectUrls = createRedirectUrls();
		Assert.assertEquals(redirectUrls.getCancelUrl(), CANCELURL);
		Assert.assertEquals(redirectUrls.getReturnUrl(), RETURNURL);
	}

	@Test(groups = "unit")
	public void testTOJSON() {
		RedirectUrls redirectUrls = createRedirectUrls();
		Assert.assertEquals(redirectUrls.toJSON().length() == 0, false);
	}

	@Test(groups = "unit")
	public void testTOString() {
		RedirectUrls redirectUrls = createRedirectUrls();
		Assert.assertEquals(redirectUrls.toString().length() == 0, false);
	}

}
