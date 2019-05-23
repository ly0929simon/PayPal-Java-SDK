package com.paypal.api.payments;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RelatedResourcesTestCase {

	public static final Sale SALE = SaleTestCase.createSale();

	public static final Authorization AUTHORIZATION = AuthorizationTestCase
			.createAuthorization();

	public static final Refund REFUND = RefundTestCase.createRefund();

	public static final Capture CAPTURE = CaptureTestCase.createCapture();

	public static RelatedResources createRelatedResources() {
		RelatedResources subTransaction = new RelatedResources();
		subTransaction.setAuthorization(AUTHORIZATION);
		subTransaction.setCapture(CAPTURE);
		subTransaction.setRefund(REFUND);
		subTransaction.setSale(SALE);
		return subTransaction;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		RelatedResources subTransaction = createRelatedResources();
		Assert.assertEquals(subTransaction.getAuthorization().getId(),
				AuthorizationTestCase.ID);
		Assert.assertEquals(subTransaction.getSale().getId(), SaleTestCase.ID);
		Assert.assertEquals(subTransaction.getRefund().getId(),
				RefundTestCase.ID);
		Assert.assertEquals(subTransaction.getCapture().getId(),
				CaptureTestCase.ID);
	}

	@Test(groups = "unit")
	public void testTOJSON() {
		RelatedResources subTransaction = createRelatedResources();
		Assert.assertEquals(subTransaction.toJSON().length() == 0, false);
	}

	@Test(groups = "unit")
	public void testTOString() {
		RelatedResources subTransaction = createRelatedResources();
		Assert.assertEquals(subTransaction.toString().length() == 0, false);
	}

}
