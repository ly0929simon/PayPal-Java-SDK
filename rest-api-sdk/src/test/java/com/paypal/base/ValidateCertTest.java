package com.paypal.base;

import com.paypal.api.payments.Event;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

import static org.powermock.api.support.membermodification.MemberModifier.stub;

@PrepareForTest(SSLUtil.class)
@PowerMockIgnore({"javax.management.*", "javax.net.ssl.*", "javax.security.*"})
public class ValidateCertTest extends PowerMockTestCase {
	
	Map<String, String> headers, configs;
	APIContext apiContext;
	String requestBody;

	@ObjectFactory
	public IObjectFactory getObjectFactory() {
		return new org.powermock.modules.testng.PowerMockObjectFactory();
	}

	@BeforeMethod
	public void setUp() throws Exception {
		InputStream testClientCertStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("testClientCert.crt");
		stub(PowerMockito.method(SSLUtil.class, "downloadCertificateFromPath", String.class, Map.class)).toReturn(testClientCertStream);

		// Settings some default values before each methods
		headers = new HashMap<String, String>();
		configs = new HashMap<String, String>();
		apiContext = new APIContext();
		
		//configs.put(Constants.PAYPAL_TRUST_CERT_URL, "DigiCertSHA2ExtendedValidationServerCA.crt");
		configs.put(Constants.PAYPAL_WEBHOOK_ID, "96605875FF6516633");
		apiContext.setConfigurationMap(configs);
		
		headers.put(Constants.PAYPAL_HEADER_CERT_URL, "https://api.sandbox.paypal.com/v1/notifications/test");
		headers.put(Constants.PAYPAL_HEADER_TRANSMISSION_ID, "071f7d20-1584-11e7-abf0-6b62a8a99ac4");
		headers.put(Constants.PAYPAL_HEADER_TRANSMISSION_TIME, "2017-03-30T20:04:05Z");
		headers.put(Constants.PAYPAL_HEADER_AUTH_ALGO, "SHA256withRSA");
		headers.put(Constants.PAYPAL_HEADER_TRANSMISSION_SIG, "aBjgm5/xljRYu3G64Q0axISrP2xcy7WbW1u4UTCKnQvyprOZ1a1BBmqn2Jdr6ce8E76I3Ti/AL9y4VYSHyGSEaoE6dVQcCDebLbOXLH0fjTAbTc1/rmfdWmsk0DPltW84Y8W8jOHe3CLWDOwg5zmozyt+AceG2x5eOiw7mLSycaEoj/5RG+dOIWXnmWcLEArbG3VFshy0wuhZrKuGa9C/bD4Ku+Y9gK7Bv5I5IuGRVnTGpcFnVG0KxOyJxccLGyBVCIUMY5IZS7LkmJszQ3HZZFDTNihvgJHECSLwLhzUaysIye5G6CbbLdtAmeeb6wiDciEAvr2dFf+SplOR4Lrng==");
		
		requestBody = "{\"id\":\"WH-2MW4820926242972J-6SG447389E205703U\",\"event_version\":\"1.0\",\"create_time\":\"2017-03-30T20:04:05.613Z\",\"resource_type\":\"plan\",\"event_type\":\"BILLING.PLAN.CREATED\",\"summary\":\"A billing plan was created\",\"resource\":{\"merchant_preferences\":{\"setup_fee\":{\"currency\":\"USD\",\"value\":\"1\"},\"return_url\":\"https://www.mta.org/wp-content/plugins/AMS/api/Paypal/paypal/rest-api-sdk-php/sample/billing/ExecuteAgreement.php?success=true\",\"cancel_url\":\"https://www.mta.org/wp-content/plugins/AMS/api/Paypal/paypal/rest-api-sdk-php/sample/billing/ExecuteAgreement.php?success=false\",\"auto_bill_amount\":\"YES\",\"initial_fail_amount_action\":\"CONTINUE\",\"max_fail_attempts\":\"0\"},\"update_time\":\"2017-03-30T20:04:05.587Z\",\"create_time\":\"2017-03-30T20:04:05.587Z\",\"name\":\"T-Shirt of the Month Club Plan\",\"description\":\"Template creation.\",\"links\":[{\"href\":\"api.sandbox.paypal.com/v1/payments/billing-plans/P-2U911356NH683973BEDIWKUY\",\"rel\":\"self\",\"method\":\"GET\"}],\"payment_definitions\":[{\"name\":\"Regular Payments\",\"type\":\"REGULAR\",\"frequency\":\"Month\",\"frequency_interval\":\"2\",\"amount\":{\"currency\":\"USD\",\"value\":\"100\"},\"cycles\":\"12\",\"charge_models\":[{\"type\":\"SHIPPING\",\"amount\":{\"currency\":\"USD\",\"value\":\"10\"},\"id\":\"CHM-6H655806YS685182XEDIWKUY\"}],\"id\":\"PD-5N450756VM995270VEDIWKUY\"}],\"id\":\"P-2U911356NH683973BEDIWKUY\",\"state\":\"CREATED\",\"type\":\"FIXED\"},\"links\":[{\"href\":\"https://api.sandbox.paypal.com/v1/notifications/webhooks-events/WH-2MW4820926242972J-6SG447389E205703U\",\"rel\":\"self\",\"method\":\"GET\"},{\"href\":\"https://api.sandbox.paypal.com/v1/notifications/webhooks-events/WH-2MW4820926242972J-6SG447389E205703U/resend\",\"rel\":\"resend\",\"method\":\"POST\"}]}";
	}

	@Test(groups = "unit")
	public void testValidEndpoint() throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		try {
			boolean result = Event.validateReceivedEvent(apiContext, headers, requestBody);
			Assert.assertTrue(result);
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
	}

	@Test(groups = "unit", expectedExceptions = PayPalRESTException.class, expectedExceptionsMessageRegExp = "webhook.id cannot be null" )
	public void testMissingWebhookId() throws PayPalRESTException, InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		//configs.remove(Constants.PAYPAL_WEBHOOK_ID);
		//apiContext.setConfigurationMap(configs);
		apiContext.getConfigurationMap().remove(Constants.PAYPAL_WEBHOOK_ID);
		if (PayPalResource.getConfigurations() != null && PayPalResource.getConfigurations().containsKey(Constants.PAYPAL_WEBHOOK_ID)) {
			PayPalResource.getConfigurations().remove(Constants.PAYPAL_WEBHOOK_ID);
		}
		Event.validateReceivedEvent(apiContext, headers, requestBody);
	}

	@Test(groups = "unit")
	public void testInvalidWebhookId() throws PayPalRESTException, InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		configs.put(Constants.PAYPAL_WEBHOOK_ID, "NotToBeFound");
		apiContext.setConfigurationMap(configs);
		boolean result = Event.validateReceivedEvent(apiContext, headers, requestBody);
		Assert.assertFalse(result);
	}

	@Test(groups = "unit")
	public void testDefaultCert() throws Exception {
		boolean result = Event.validateReceivedEvent(apiContext, headers, requestBody);
		Assert.assertTrue(result);
	}

	@Test(groups = "unit", expectedExceptions= PayPalRESTException.class, expectedExceptionsMessageRegExp="Certificate Not Found")
	public void testInvalidTrustCertLocation() throws PayPalRESTException, InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		configs.put(Constants.PAYPAL_TRUST_CERT_URL, "InvalidCertLocation.crt");
		apiContext.setConfigurationMap(configs);
		Event.validateReceivedEvent(apiContext, headers, requestBody);
	}
	
	@Test(groups = "unit")
	public void testInvalidAuthType() throws PayPalRESTException, InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		configs.put(Constants.PAYPAL_WEBHOOK_CERTIFICATE_AUTHTYPE, "Invalid");
		apiContext.setConfigurationMap(configs);
		Event.validateReceivedEvent(apiContext, headers, requestBody);
	}

	@Test(groups = "unit")
	public void testInvalidRequestBody() throws PayPalRESTException, InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		requestBody = "{ something invalid }";
		Event.validateReceivedEvent(apiContext, headers, requestBody);
	}

	@Test(groups = "unit", expectedExceptions= NoSuchAlgorithmException.class, expectedExceptionsMessageRegExp="NotToBeFound Signature not available")
	public void testInvalidAuthAlgo() throws PayPalRESTException, InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		headers.put(Constants.PAYPAL_HEADER_AUTH_ALGO, "NotToBeFound");
		Event.validateReceivedEvent(apiContext, headers, requestBody);
	}

	@Test(groups = "unit", expectedExceptions = PayPalRESTException.class, expectedExceptionsMessageRegExp = "Headers cannot be null")
	public void testEmptyHeaders() throws PayPalRESTException, InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		Event.validateReceivedEvent(apiContext, null, requestBody);
	}
	
	@Test(groups = "unit")
	public void testEmptyRequestBody() throws PayPalRESTException, InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		Assert.assertFalse(Event.validateReceivedEvent(apiContext, headers, null));
	}
	
}
