package com.paypal.base.credential;

import org.testng.annotations.Test;

public class TokenAuthorizationTest {

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void illegalArgumentExceptionTest() {
		new TokenAuthorization(null, null);
	}
}
