package com.paypal.api.payments;

import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.util.TestConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SaleTestCase {

	public static final Logger logger = Logger.getLogger(SaleTestCase.class);

	public static final String ID = "12345";

	public static final String PARENTPAYMENT = "12345";

	public static final String STATE = "Approved";

	public static final Amount AMOUNT = AmountTestCase.createAmount("100.00");

	public static final String CREATEDTIME = "2013-01-17T18:12:02.347Z";

	public String SALE_ID = null;

	public static Sale createSale() {
		List<Links> links = new ArrayList<Links>();
		links.add(LinksTestCase.createLinks());
		Sale sale = new Sale();
		sale.setAmount(AMOUNT);
		sale.setId(ID);
		sale.setParentPayment(PARENTPAYMENT);
		sale.setState(STATE);
		sale.setCreateTime(CREATEDTIME);
		sale.setLinks(links);
		return sale;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		Sale sale = createSale();
		Assert.assertEquals(sale.getId(), ID);
		Assert.assertEquals(sale.getAmount().getTotal(), "100.00");
		Assert.assertEquals(sale.getParentPayment(), PARENTPAYMENT);
		Assert.assertEquals(sale.getState(), STATE);
		Assert.assertEquals(sale.getCreateTime(), CREATEDTIME);
		Assert.assertEquals(sale.getLinks().size(), 1);
	}

	@Test(groups = "integration")
	public void testSaleRefundAPI() throws PayPalRESTException {
		Payment payment = PaymentTestCase.createCallPayment();
		Payment createdPayment = payment.create(TestConstants.SANDBOX_CONTEXT);

		List<Transaction> transactions = createdPayment.getTransactions();
		List<RelatedResources> subTransactions = transactions.get(0)
				.getRelatedResources();
		String id = subTransactions.get(0).getSale().getId();
		this.SALE_ID = id;
		Sale sale = new Sale();
		sale.setId(id);

		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal("3");

		Refund refund = new Refund();
		refund.setAmount(amount);
		refund.setSaleId(id);
		Refund returnRefund = sale.refund(TestConstants.SANDBOX_CONTEXT, refund);
		ObjectHolder.refundId = returnRefund.getId();
		Assert.assertEquals(true, "completed".equalsIgnoreCase(returnRefund.getState()));
	}

	@Test(groups = "integration", dependsOnMethods = { "testSaleRefundAPI" })
	public void testGetSale() {
		Sale sale = null;
		try {
			sale = Sale.get(TestConstants.SANDBOX_CONTEXT, this.SALE_ID);
			Assert.assertNotNull(sale);
			Assert.assertEquals(this.SALE_ID, sale.getId());
		} catch (PayPalRESTException ppx) {
			Assert.fail();
		}
	}

	@Test(groups = "integration", dependsOnMethods = { "testGetSale" })
	public void testSaleRefundAPIForNullRefund() {
		Sale sale = new Sale();
		Refund refund = null;
		try {
			sale.refund(TestConstants.SANDBOX_CONTEXT, refund);
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e != null, "IllegalArgument exception not thrown for null Refund");
		} catch (PayPalRESTException e) {
			Assert.fail();
		}
	}

	@Test(groups = "integration", dependsOnMethods = { "testSaleRefundAPIForNullRefund" })
	public void testSaleRefundAPIForNullID() {
		Sale sale = new Sale();
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal("10");
		Refund refund = new Refund();
		refund.setAmount(amount);
		refund.setSaleId("123");
		try {
			sale.refund(TestConstants.SANDBOX_CONTEXT, refund);
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e != null, "IllegalArgument exception not thrown for null Id");
		} catch (PayPalRESTException e) {
			Assert.fail();
		}
	}

	@Test(groups = "integration", dependsOnMethods = { "testSaleRefundAPIForNullID" })
	public void testGetSaleForNullId() {
		try {
			Sale.get(TestConstants.SANDBOX_CONTEXT, null);
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e != null, "IllegalArgument exception not thrown for null Id");
		} catch (PayPalRESTException e) {
			Assert.fail();
		}
	}

	@Test
	public void testSaleUnknownFileConfiguration() {
		try {
			Sale.initConfig(new File("unknown.properties"));
		} catch (PayPalRESTException e) {
			Assert.assertEquals(e.getCause().getClass().getSimpleName(),
					"FileNotFoundException");
		}
	}

	@Test
	public void testSaleInputStreamConfiguration() {
		try {
			File testFile = new File(".",
					"src/test/resources/sdk_config.properties");
			FileInputStream fis = new FileInputStream(testFile);
			Sale.initConfig(fis);
		} catch (PayPalRESTException e) {
			Assert.fail("[sdk_config.properties] stream loading failed");
		} catch (FileNotFoundException e) {
			Assert.fail("[sdk_config.properties] file is not available");
		}
	}

	@Test
	public void testSalePropertiesConfiguration() {
		try {
			File testFile = new File(".",
					"src/test/resources/sdk_config.properties");
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream(testFile);
			props.load(fis);
			Sale.initConfig(props);
		} catch (FileNotFoundException e) {
			Assert.fail("[sdk_config.properties] file is not available");
		} catch (IOException e) {
			Assert.fail("[sdk_config.properties] file is not loaded into properties");
		}
	}

	@Test(groups = "unit")
	public void testTOJSON() {
		Sale sale = createSale();
		Assert.assertEquals(sale.toJSON().length() == 0, false);
	}

	@Test(groups = "unit")
	public void testTOString() {
		Sale sale = createSale();
		Assert.assertEquals(sale.toString().length() == 0, false);
	}

}
