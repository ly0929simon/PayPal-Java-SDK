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
import java.util.*;

public class CreditCardTestCase {

	private static final Logger logger = Logger
			.getLogger(CreditCardTestCase.class);

	public CreditCard creditCard;

	public static String createdCreditCardId = null;

	public static final String TYPE = "visa";

	public static final String NUMBER = "4329685037393232";

	public static final String FIRSTNAME = "Joe";

	public static final String LASTNAME = "Shopper";

	public static final int EXPMONTH = 11;

	public static final int EXPYEAR = 2018;

	public static final String CVV2 = "874";

	public static final String ID = "12345";

	public static final String EXTERNAL_CUSTOMER_ID = "12345";

	public static final String STATE = "Approved";

	public static final String VALIDUNTIL = "2020";

	public static final Address BILLING_ADDRESS = AddressTestCase.createAddress();

	public static CreditCard createCreditCard() {
		CreditCard creditCard = new CreditCard();

		creditCard.setBillingAddress(BILLING_ADDRESS).setExpireMonth(EXPMONTH)
				.setExpireYear(EXPYEAR).setFirstName(FIRSTNAME)
				.setLastName(LASTNAME).setNumber(NUMBER).setType(TYPE)
				.setCvv2(CVV2).setBillingAddress(BILLING_ADDRESS).setId(ID)
				.setState(STATE).setValidUntil(VALIDUNTIL);
		return creditCard;
	}

	public static CreditCard createDummyCreditCard() {
		CreditCard creditCard = new CreditCard();
		creditCard.setBillingAddress(BILLING_ADDRESS);
		creditCard.setExpireMonth(EXPMONTH);
		creditCard.setExpireYear(EXPYEAR);
		creditCard.setFirstName(FIRSTNAME);
		creditCard.setLastName(LASTNAME);
		creditCard.setNumber(NUMBER);
		creditCard.setType(TYPE);
		creditCard.setCvv2(CVV2);
		creditCard.setBillingAddress(BILLING_ADDRESS);
		creditCard.setId(ID);
		creditCard.setExternalCustomerId(EXTERNAL_CUSTOMER_ID);
		creditCard.setState(STATE);
		creditCard.setValidUntil(VALIDUNTIL);
		List<Links> links = new ArrayList<Links>();
		links.add(LinksTestCase.createLinks());
		creditCard.setLinks(links);
		return creditCard;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		CreditCard creditCard = createDummyCreditCard();
		Assert.assertEquals(creditCard.getCvv2String(), CVV2);
		Assert.assertEquals(creditCard.getExpireMonth(), EXPMONTH);
		Assert.assertEquals(creditCard.getExpireYear(), EXPYEAR);
		Assert.assertEquals(creditCard.getFirstName(), FIRSTNAME);
		Assert.assertEquals(creditCard.getLastName(), LASTNAME);
		Assert.assertEquals(creditCard.getNumber(), NUMBER);
		Assert.assertEquals(creditCard.getType(), TYPE);
		Assert.assertEquals(creditCard.getBillingAddress().getCity(),
				AddressTestCase.CITY);
		Assert.assertEquals(creditCard.getId(), ID);
		Assert.assertEquals(creditCard.getExternalCustomerId(), EXTERNAL_CUSTOMER_ID);
		Assert.assertEquals(creditCard.getState(), STATE);
		Assert.assertEquals(creditCard.getValidUntil(), VALIDUNTIL);
		Assert.assertEquals(creditCard.getLinks().size(), 1);
	}
	

	@Test(groups = "unit")
	public void testCvv2() {
		logger.info("**** Test CreditCard CVV2 ****");
		CreditCard creditCard = createDummyCreditCard();
		
		// empty CVV2
		creditCard.setCvv2("");
		Assert.assertEquals("", creditCard.getCvv2String());
		
		// valid CVV2
		creditCard.setCvv2(123);
		Assert.assertEquals("123", creditCard.getCvv2String());

		// CVV2 starting with 0
		creditCard.setCvv2("012");
		Assert.assertEquals("012", creditCard.getCvv2String());
	}

	@Test(groups = "integration")
	public void createCreditCardTest() throws PayPalRESTException {
		CreditCard creditCard = new CreditCard();
		creditCard.setExpireMonth(EXPMONTH);
		creditCard.setExpireYear(EXPYEAR);
		creditCard.setNumber(NUMBER);
		creditCard.setType(TYPE);
		this.creditCard = creditCard.create(TestConstants.SANDBOX_CONTEXT);
		Assert.assertEquals(true,
				"ok".equalsIgnoreCase(this.creditCard.getState()));
		logger.info("Created Credit Card status = "
				+ this.creditCard.getState());
		createdCreditCardId = this.creditCard.getId();
	}

	@Test(groups = "integration", dependsOnMethods = { "createCreditCardTest" })
	public void testGetCreditCard() throws PayPalRESTException {
		CreditCard retrievedCreditCard = CreditCard.get(TestConstants.SANDBOX_CONTEXT, createdCreditCardId);
		Assert.assertEquals(
				true,
				this.creditCard.getId().equalsIgnoreCase(
						retrievedCreditCard.getId()));
	}

	@Test(groups = "integration", dependsOnMethods = { "testGetCreditCard" })
	public void testUpdateCreditCard() throws PayPalRESTException {
		// set up patch request
		Patch patch = new Patch();
		patch.setOp("replace");
		patch.setPath("/expire_year");
		patch.setValue(new Integer(2020));
		List<Patch> patchRequest = new ArrayList<Patch>();
		patchRequest.add(patch);
		
		// send patch request
		CreditCard creditCard = new CreditCard();
		creditCard.setId(createdCreditCardId);
		CreditCard retrievedCreditCard = creditCard.update(TestConstants.SANDBOX_CONTEXT, patchRequest);
		Assert.assertEquals(2020, retrievedCreditCard.getExpireYear());

	}
	
	
	@Test(groups = "integration", dependsOnMethods = { "testUpdateCreditCard" })
	public void deleteCreditCard() throws PayPalRESTException {
		CreditCard retrievedCreditCard = CreditCard.get(TestConstants.SANDBOX_CONTEXT, createdCreditCardId);
		retrievedCreditCard.delete(TestConstants.SANDBOX_CONTEXT);
		try {
			CreditCard.get(TestConstants.SANDBOX_CONTEXT, createdCreditCardId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Pass empty HashMap
	@Test(groups = "integration", dependsOnMethods = { "createCreditCardTest" })
	public void testListCreditCardContextOnly() throws PayPalRESTException {
		CreditCardHistory creditCards = CreditCard.list(TestConstants.SANDBOX_CONTEXT);
		Assert.assertTrue(creditCards.getTotalItems() > 0);

	}

	// Pass empty HashMap
	@Test(groups = "integration", dependsOnMethods = { "createCreditCardTest" })
	public void testListCreditCard() throws PayPalRESTException {
		Map<String, String> containerMap = new HashMap<String, String>();
		CreditCardHistory creditCards = CreditCard.list(TestConstants.SANDBOX_CONTEXT, containerMap);
		Assert.assertTrue(creditCards.getTotalItems() > 0);

	}

	// Pass HashMap with count
	@Test(groups = "integration", dependsOnMethods = { "createCreditCardTest" })
	public void testListCreditCardwithCount() throws PayPalRESTException {
		Map<String, String> containerMap = new HashMap<String, String>();
		containerMap.put("count", "10");
		CreditCardHistory creditCards = CreditCard.list(TestConstants.SANDBOX_CONTEXT, containerMap);
		Assert.assertTrue(creditCards.getTotalItems() > 0);
	}

	@Test(groups = "integration", dependsOnMethods = { "testGetCreditCard" })
	public void getCreditCardForNull() {
		try {
			CreditCard.get(TestConstants.SANDBOX_CONTEXT, null);
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(e != null,
					"Illegal Argument Exception not thrown for null arguments");
		} catch (PayPalRESTException e) {
			Assert.fail();
		}
	}

	@Test
	public void testCreditCardUnknownFileConfiguration() {
		try {
			CreditCard.initConfig(new File("unknown.properties"));
		} catch (PayPalRESTException e) {
			Assert.assertEquals(e.getCause().getClass().getSimpleName(),
					"FileNotFoundException");
		}
	}

	@Test
	public void testCreditCardInputStreamConfiguration() {
		try {
			File testFile = new File(".",
					"src/test/resources/sdk_config.properties");
			FileInputStream fis = new FileInputStream(testFile);
			CreditCard.initConfig(fis);
		} catch (PayPalRESTException e) {
			Assert.fail("[sdk_config.properties] stream loading failed");
		} catch (FileNotFoundException e) {
			Assert.fail("[sdk_config.properties] file is not available");
		}
	}

	@Test
	public void testCreditCardPropertiesConfiguration() {
		try {
			File testFile = new File(".",
					"src/test/resources/sdk_config.properties");
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream(testFile);
			props.load(fis);
			CreditCard.initConfig(props);
		} catch (FileNotFoundException e) {
			Assert.fail("[sdk_config.properties] file is not available");
		} catch (IOException e) {
			Assert.fail("[sdk_config.properties] file is not loaded into properties");
		}
	}

	@Test
	public void testTOJSON() {
		CreditCard creditCard = createCreditCard();
		Assert.assertEquals(creditCard.toJSON().length() == 0, false);
	}

	@Test
	public void testTOString() {
		CreditCard creditCard = createCreditCard();
		Assert.assertEquals(creditCard.toString().length() == 0, false);
	}

}
