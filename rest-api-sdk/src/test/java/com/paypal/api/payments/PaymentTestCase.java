package com.paypal.api.payments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.util.TestConstants;

public class PaymentTestCase {

	private static final Logger logger = Logger
			.getLogger(PaymentTestCase.class);

	private String createdPaymentID = null;

	public static final String CREATEDTIME = "2013-01-17T18:12:02.347Z";

	public static final String CANCELURL = "http://somedomain.com";

	public static final String RETURNURL = "http://somedomain.com";

	public static final String INTENT = "sale";

	public static final String EXPERIENCEPROFILEID = "XP-ABCD-1234-EFGH-5678";

	public static final String ID = "12345";

	public static Payment payment;

	public static Payment createCallPayment() {
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


        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<Item>();
        items.add(ItemTestCase.createItem());
        itemList.setItems(items);

        Details amountDetails = new Details();
        amountDetails.setTax("8.40");
        amountDetails.setSubtotal("44.10");
        amountDetails.setShipping("4.99");

        Amount amount = new Amount();
        amount.setDetails(amountDetails);
        amount.setCurrency("USD");
        amount.setTotal("57.49");

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
        transaction.setItemList(itemList);
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
		payment.setIntent("sale");
        // payment.setExperienceProfileId(EXPERIENCEPROFILEID);
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		return payment;
	}

	public static Payment createPayment() {
		Address billingAddress = AddressTestCase.createAddress();

		CreditCard creditCard = new CreditCard();
		creditCard.setBillingAddress(billingAddress);
		creditCard.setCvv2(874);
		creditCard.setExpireMonth(11);
		creditCard.setExpireYear(2018);
		creditCard.setFirstName("Joe");
		creditCard.setLastName("Shopper");
		creditCard.setNumber("4111111111111111");
		creditCard.setType("visa");

		Details details = new Details();
		details.setShipping("10");
		details.setSubtotal("75");
		details.setTax("15");

		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal("100");
		amount.setDetails(details);

		Payee payee = new Payee();
		payee.setMerchantId("NMXBYHSEL4FEY");

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<Item>();
        items.add(ItemTestCase.createItem());
        itemList.setItems(items);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
        transaction.setItemList(itemList);
		transaction.setPayee(payee);
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

		List<Links> links = new ArrayList<Links>();
		links.add(LinksTestCase.createLinks());

		RedirectUrls redirectUrls = RedirectUrlsTestCase.createRedirectUrls();

		Payment payment = new Payment();
		payment.setIntent("sale");
        payment.setExperienceProfileId(EXPERIENCEPROFILEID);
		payment.setId(ID);
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		payment.setCreateTime(CREATEDTIME);
		payment.setLinks(links);
		payment.setRedirectUrls(redirectUrls);
		return payment;
	}

	public static Payment createPaymentForExecution() {
		Details details = new Details();
		details.setShipping("10");
		details.setSubtotal("75");
		details.setTax("15");

		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal("100");
		amount.setDetails(details);

		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("http://www.hawaii.com");
		redirectUrls.setReturnUrl("http://www.hawaii.com");

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<Item>();
        items.add(ItemTestCase.createItem());
        itemList.setItems(items);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
        transaction.setItemList(itemList);
		transaction
				.setDescription("This is the payment transaction description.");
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setRedirectUrls(redirectUrls);
		payment.setTransactions(transactions);
		return payment;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		Payment payment = createPayment();
		Assert.assertEquals(payment.getPayer().getPaymentMethod(),
				"credit_card");
		Assert.assertEquals(payment.getTransactions().get(0).getAmount()
				.getTotal(), "100");
		Assert.assertEquals(payment.getIntent(), INTENT);
		Assert.assertEquals(payment.getExperienceProfileId(), EXPERIENCEPROFILEID);
		Assert.assertEquals(payment.getRedirectUrls().getCancelUrl(),
				RedirectUrlsTestCase.CANCELURL);
		Assert.assertEquals(payment.getRedirectUrls().getReturnUrl(),
				RedirectUrlsTestCase.RETURNURL);
		Assert.assertEquals(payment.getId(), ID);
		Assert.assertEquals(payment.getCreateTime(), CREATEDTIME);
		Assert.assertEquals(payment.getLinks().size(), 1);
	}

	@Test(groups = "integration")
	public void testCreatePaymentAPI() throws PayPalRESTException {
		Payment payment = createCallPayment();
		Payment createdPayment = payment.create(TestConstants.SANDBOX_CONTEXT);
		createdPaymentID = createdPayment.getId();
		String json = Payment.getLastResponse();
		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(json);
		JsonObject obj = jsonElement.getAsJsonObject();

		// State of a created payment object is approved
		Assert.assertEquals(obj.get("state").getAsString()
				.equalsIgnoreCase("approved"),true);
		obj.get("transactions").getAsJsonArray().get(0)
				.getAsJsonObject().get("related_resources").getAsJsonArray()
				.get(0).getAsJsonObject().get("sale").getAsJsonObject()
				.get("id").getAsString();
	}

	@Test(groups = "integration", dependsOnMethods = { "testCreatePaymentAPI" })
	public void testGetPaymentAPI() throws PayPalRESTException {
		payment = Payment.get(TestConstants.SANDBOX_CONTEXT, createdPaymentID);
	}

	@Test(groups = "integration", enabled = false, dependsOnMethods = { "testGetPaymentAPI" })
	public void testExecutePayment() throws PayPalRESTException, IOException {
		Payment exPayment = createPaymentForExecution();
		exPayment = exPayment.create(TestConstants.SANDBOX_CONTEXT);
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);
		String payerId = in.readLine();
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);
		exPayment = exPayment
				.execute(TestConstants.SANDBOX_CONTEXT, paymentExecution);
	}

	@Test(groups = "integration", dependsOnMethods = { "testGetPaymentAPI" })
	public void testGetPaymentHistoryAPI() throws PayPalRESTException {
		Map<String, String> containerMap = new HashMap<String, String>();
		containerMap.put("count", "10");
		Payment.list(TestConstants.SANDBOX_CONTEXT, containerMap);
	}

	@Test(groups = "integration", dependsOnMethods = { "testGetPaymentHistoryAPI" })
	public void testFailCreatePaymentAPI() {
		Payment payment = new Payment();
		try {
			payment.create(TestConstants.SANDBOX_CONTEXT);
		} catch (PayPalRESTException e) {
			Assert.assertEquals(e.getCause().getClass().getSimpleName(),
					"HttpErrorException");
		}
	}

	@Test(groups = "integration", dependsOnMethods = { "testFailCreatePaymentAPI" })
	public void testFailGetPaymentAPI() {
		try {
			Payment.get(TestConstants.SANDBOX_CONTEXT, (String) null);
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e != null,
					"Illegal Argument Exception not thrown for null arguments");
		} catch (PayPalRESTException e) {
			logger.error("response code: " + e.getResponsecode());
			logger.error("message: " + e.getMessage());
			Assert.fail();
		}
	}

	@Test(groups = "unit")
	public void testTOJSON() {
		try {
			Payment payment = createPayment();
			Assert.assertEquals(payment.toJSON().length() == 0, false);
		} catch (IllegalStateException e) {
		}
	}

	@Test(groups = "unit")
	public void testTOString() {
		try {
			Payment payment = createPayment();
			Assert.assertEquals(payment.toString().length() == 0, false);
		} catch (IllegalStateException e) {
		}
	}

}
