package com.paypal.api.payments;

import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.util.TestConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.util.ArrayList;
import java.util.List;

@Test
public class CaptureTestCase {

	private static final Logger logger = Logger
			.getLogger(CaptureTestCase.class);

	public static final String AUTHID = "12345";

	public static final String ID = "12345";

	public static final String PARENTPAYMENT = "12345";

	public static final String STATE = "Approved";

	public static final Amount AMOUNT = AmountTestCase.createAmount("120.00");

	public static final String CREATEDTIME = "2013-01-17T18:12:02.347Z";

	private Capture retrievedCapture = null;

	public static Capture createCapture() {
		List<Links> links = new ArrayList<Links>();
		links.add(LinksTestCase.createLinks());
		Capture capture = new Capture();
		capture.setId(ID);
		capture.setParentPayment(PARENTPAYMENT);
		capture.setState(STATE);
		capture.setAmount(AMOUNT);
		capture.setCreateTime(CREATEDTIME);
		capture.setLinks(links);
		return capture;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		Capture capture = createCapture();
		Assert.assertEquals(capture.getId(), ID);
		Assert.assertEquals(capture.getParentPayment(), PARENTPAYMENT);
		Assert.assertEquals(capture.getState(), STATE);
		Assert.assertEquals(capture.getAmount().getCurrency(),
				AMOUNT.getCurrency());
		Assert.assertEquals(capture.getCreateTime(), CREATEDTIME);
		Assert.assertEquals(capture.getLinks().size(), 1);
	}

	@Test(groups = "integration")
	public void testGetCapture() throws PayPalRESTException {
		Payment payment = getPaymentAgainstAuthorization("9.00");
		Payment authPayment = payment.create(TestConstants.SANDBOX_CONTEXT);
		String authorizationId = authPayment.getTransactions().get(0)
				.getRelatedResources().get(0).getAuthorization().getId();
		Authorization authorization = Authorization.get(
				TestConstants.SANDBOX_CONTEXT, authorizationId);
		Capture capture = new Capture();
		Amount amount = new Amount();
		amount.setCurrency("USD").setTotal("1");
		capture.setAmount(amount).setIsFinalCapture(true);
		Capture responsecapture = authorization.capture(TestConstants.SANDBOX_CONTEXT, capture);
		retrievedCapture = Capture.get(TestConstants.SANDBOX_CONTEXT,	responsecapture.getId());
	}

	@Test(groups = "integration", dependsOnMethods = { "testGetCapture" })
	public void testRefundCapture() throws PayPalRESTException {
		/* testing */
		Refund refund = new Refund();
		Amount amount = new Amount();
		amount.setCurrency("USD").setTotal("1");
		refund.setAmount(amount);
		Refund responseRefund = retrievedCapture.refund(TestConstants.SANDBOX_CONTEXT, refund);
		Assert.assertEquals("completed", responseRefund.getState());
	}

	@Test(groups = "integration", expectedExceptions = { IllegalArgumentException.class })
	public void testGetCaptureNullCaptureId() throws PayPalRESTException {
		Capture.get(TestConstants.SANDBOX_CONTEXT, null);
	}

	@Test(groups = "integration", expectedExceptions = { IllegalArgumentException.class })
	public void testCaptureNullRefund() throws PayPalRESTException {
		Payment payment = getPaymentAgainstAuthorization("2.50");
		Payment authPayment = payment.create(TestConstants.SANDBOX_CONTEXT);
		String authorizationId = authPayment.getTransactions().get(0)
				.getRelatedResources().get(0).getAuthorization().getId();
		Authorization authorization = Authorization.get(TestConstants.SANDBOX_CONTEXT, authorizationId);
		Capture capture = new Capture();
		Amount amount = new Amount();
		amount.setCurrency("USD").setTotal("1");
		capture.setAmount(amount).setIsFinalCapture(true);
		Capture responsecapture = authorization.capture(TestConstants.SANDBOX_CONTEXT, capture);
		logger.info("Generated Capture Id = " + responsecapture.getId());
		Capture rCapture = Capture.get(TestConstants.SANDBOX_CONTEXT,
				responsecapture.getId());

		rCapture.refund(TestConstants.SANDBOX_CONTEXT, new Refund());
	}

	@Test
	public void testTOJSON() {
		Capture capture = createCapture();
		Assert.assertEquals(capture.toJSON().length() == 0, false);
	}

	@Test
	public void testTOString() {
		Capture capture = createCapture();
		Assert.assertEquals(capture.toString().length() == 0, false);
	}

	private Payment getPaymentAgainstAuthorization(String totalAmount) {
		Address billingAddress = AddressTestCase.createAddress();

		CreditCard creditCard = new CreditCard();
		creditCard.setBillingAddress(billingAddress);
		creditCard.setCvv2(617);
		creditCard.setExpireMonth(01);
		creditCard.setExpireYear(2020);
		creditCard.setFirstName("Joe");
		creditCard.setLastName("Shopper");
		creditCard.setNumber("4422009910903049");
		creditCard.setType("visa");

		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal(totalAmount);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction
				.setDescription("This is the payment transaction description.");
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setCreditCard(creditCard);
		List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
		fundingInstrumentList.add(fundingInstrument);

		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstrumentList);
		payer.setPaymentMethod("credit_card");

		Payment payment = new Payment();
		payment.setIntent("authorize");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		return payment;
	}

}
