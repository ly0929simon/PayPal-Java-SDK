package com.paypal.base;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.paypal.api.openidconnect.CreateFromAuthorizationCodeParameters;
import com.paypal.api.openidconnect.CreateFromRefreshTokenParameters;
import com.paypal.api.openidconnect.Session;
import com.paypal.api.openidconnect.Tokeninfo;
import com.paypal.api.openidconnect.Userinfo;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

public class OpenIdTest {

	private static final Logger logger = Logger.getLogger(OpenIdTest.class);

	private Tokeninfo info;

	Map<String, String> configurationMap = new HashMap<String, String>();

	public OpenIdTest() {
		// configurationMap.put(Constants.CLIENT_ID, "");
		// configurationMap.put(Constants.CLIENT_SECRET, "");
		configurationMap.put("mode", "sandbox");
	}
	
	private static String generateAccessToken() throws PayPalRESTException {
		Map<String, String> configurationMap = new HashMap<String, String>();
		configurationMap.put("service.EndPoint",
				"https://api.sandbox.paypal.com");
		OAuthTokenCredential merchantTokenCredential = new OAuthTokenCredential(
				"AQkquBDf1zctJOWGKWUEtKXm6qVhueUEMvXO_-MCI4DQQ4-LWvkDLIN2fGsd", "EL1tVxAjhT7cJimnz5-Nsx9k2reTKSVfErNQF-CmrwJgxRtylkGTKlU4RvrX", configurationMap);
		return merchantTokenCredential.getAccessToken();
	}

	@Test(groups = "integration", enabled = false)
	public void testCreateFromAuthorizationCodeDynamic()
			throws PayPalRESTException, UnsupportedEncodingException {
		CreateFromAuthorizationCodeParameters param = new CreateFromAuthorizationCodeParameters();
		param.setClientID("AQkquBDf1zctJOWGKWUEtKXm6qVhueUEMvXO_-MCI4DQQ4-LWvkDLIN2fGsd");
		param.setClientSecret("EL1tVxAjhT7cJimnz5-Nsx9k2reTKSVfErNQF-CmrwJgxRtylkGTKlU4RvrX");
		param.setCode("uda60BSsvnjRigULcO5J9vb-t85kqR9eGMdVpQ1oyQqOS7vjv8Y2WcYOvNq1WAxSPuxTzS7zlMB03h_KQUVWcrz2cKu8cNfc5tqSB_-duFi6Iuwq3Iie0ouOxmlBstHHzx200VIAMxgqU9bWOycRNxc78iqNhfkA_xGkPglTGal3kqJN");
		APIContext apiContext = new APIContext();
		apiContext.setConfigurationMap(configurationMap);
		info = Tokeninfo.createFromAuthorizationCode(apiContext, param);
		logger.info("Generated Access Token : " + info.getAccessToken());
		logger.info("Generated Refresh Token: " + info.getRefreshToken());
	}

	@Test(dependsOnMethods = { "testCreateFromAuthorizationCodeDynamic" }, enabled = false)
	public void testCreateFromRefreshTokenDynamic() throws PayPalRESTException {
		CreateFromRefreshTokenParameters param = new CreateFromRefreshTokenParameters();
		APIContext apiContext = new APIContext();
		apiContext.setConfigurationMap(configurationMap);
		param.setClientID("AQkquBDf1zctJOWGKWUEtKXm6qVhueUEMvXO_-MCI4DQQ4-LWvkDLIN2fGsd");
		param.setClientSecret("EL1tVxAjhT7cJimnz5-Nsx9k2reTKSVfErNQF-CmrwJgxRtylkGTKlU4RvrX");
		info = info.createFromRefreshToken(apiContext, param);
		logger.info("Regenerated Access Token: " + info.getAccessToken());
		logger.info("Refresh Token: " + info.getRefreshToken());
	}

	@Test(groups = "integration")
	public void testUserinfoDynamic() throws PayPalRESTException {
		APIContext apiContext = new APIContext(generateAccessToken());
		apiContext.setConfigurationMap(configurationMap);
		Userinfo userInfo = Userinfo.getUserinfo(apiContext);
		Assert.assertNotNull(userInfo.getUserId());
		logger.info("User ID: " + userInfo.getUserId());
		logger.info("User Info Email: " + userInfo.getEmail());
		logger.info("User Info Account Type: " + userInfo.getAccountType());
		logger.info("User Info Name: " + userInfo.getGivenName());
	}

	@Test(expectedExceptions = PayPalRESTException.class)
	public void testCreateFromAuthorizationCodeDynamicError()
			throws PayPalRESTException, UnsupportedEncodingException {
		CreateFromAuthorizationCodeParameters param = new CreateFromAuthorizationCodeParameters();
		Tokeninfo.createFromAuthorizationCode(null, param);
	}

	@Test()
	public void testRedirectURL() {
		Map<String, String> m = new HashMap<String, String>();
		m.put("openid.RedirectUri",
				"https://www.paypal.com");
		m.put("clientId", "ANdfsalkoiarT");
		List<String> l = new ArrayList<String>();
		l.add("openid");
		l.add("profile");
		APIContext apiContext = new APIContext();
		apiContext.setConfigurationMap(m);
		String redirectURL = Session.getRedirectURL("http://google.com", l,
				apiContext);
		logger.info("Redirect URL: " + redirectURL);
		Assert.assertEquals(
				redirectURL,
				"https://www.paypal.com/signin/authorize?client_id=ANdfsalkoiarT&response_type=code&scope=openid+profile+&redirect_uri=http%3A%2F%2Fgoogle.com");
	}

	@Test()
	public void testRedirectURLClientCredentials() {
		Map<String, String> m = new HashMap<String, String>();
		m.put("openid.RedirectUri",
				"https://www.paypal.com");
		ClientCredentials clientCredentials = new ClientCredentials();
		clientCredentials.setClientID("ANdfsalkoiarT");
		List<String> l = new ArrayList<String>();
		l.add("openid");
		l.add("profile");
		APIContext apiContext = new APIContext();
		apiContext.setConfigurationMap(m);
		String redirectURL = Session.getRedirectURL("http://google.com", l,
				apiContext, clientCredentials);
		logger.info("Redirect URL: " + redirectURL);
		Assert.assertEquals(
				redirectURL,
				"https://www.paypal.com/signin/authorize?client_id=ANdfsalkoiarT&response_type=code&scope=openid+profile+&redirect_uri=http%3A%2F%2Fgoogle.com");
	}

	@Test()
	public void testRedirectURLClientCredentialsSandbox() {
		Map<String, String> m = new HashMap<String, String>();
		m.put("mode", "sandbox");
		ClientCredentials clientCredentials = new ClientCredentials();
		clientCredentials.setClientID("ANdfsalkoiarT");
		List<String> l = new ArrayList<String>();
		l.add("openid");
		l.add("profile");
		APIContext apiContext = new APIContext();
		apiContext.setConfigurationMap(m);
		String redirectURL = Session.getRedirectURL("http://google.com", l,
				apiContext, clientCredentials);
		logger.info("Redirect URL: " + redirectURL);
		Assert.assertEquals(
				redirectURL,
				"https://www.sandbox.paypal.com/signin/authorize?client_id=ANdfsalkoiarT&response_type=code&scope=openid+profile+&redirect_uri=http%3A%2F%2Fgoogle.com");
	}

	@Test()
	public void testRedirectURLClientCredentialsLive() {
		Map<String, String> m = new HashMap<String, String>();
		m.put("mode", "live");
		ClientCredentials clientCredentials = new ClientCredentials();
		clientCredentials.setClientID("ANdfsalkoiarT");
		List<String> l = new ArrayList<String>();
		l.add("openid");
		l.add("profile");
		APIContext apiContext = new APIContext();
		apiContext.setConfigurationMap(m);
		String redirectURL = Session.getRedirectURL("http://google.com", l,
				apiContext, clientCredentials);
		logger.info("Redirect URL: " + redirectURL);
		Assert.assertEquals(
				redirectURL,
				"https://www.paypal.com/signin/authorize?client_id=ANdfsalkoiarT&response_type=code&scope=openid+profile+&redirect_uri=http%3A%2F%2Fgoogle.com");
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testRedirectURLError() {
		Map<String, String> m = new HashMap<String, String>();
		ClientCredentials clientCredentials = new ClientCredentials();
		clientCredentials.setClientID("ANdfsalkoiarT");
		List<String> l = new ArrayList<String>();
		l.add("openid");
		l.add("profile");
		APIContext apiContext = new APIContext();
		apiContext.setConfigurationMap(m);
		Session.getRedirectURL("http://google.com", l, apiContext,
				clientCredentials);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testLogoutURLError() {
		Map<String, String> m = new HashMap<String, String>();
		APIContext apiContext = new APIContext();
		apiContext.setConfigurationMap(m);
		String logoutURL = Session.getLogoutUrl("http://google.com", "tokenId",
				apiContext);
		logger.info("Redirect URL: " + logoutURL);
		Assert.assertEquals(
				logoutURL,
				"https://www.paypal.com/webapps/auth/protocol/openidconnect/v1/endsession?id_token=tokenId&redirect_uri=http%3A%2F%2Fgoogle.com&logout=true");
	}

	@Test()
	public void testLogoutURL() {
		Map<String, String> m = new HashMap<String, String>();
		m.put("openid.RedirectUri",
				"https://www.paypal.com");
		APIContext apiContext = new APIContext();
		apiContext.setConfigurationMap(m);
		String logoutURL = Session.getLogoutUrl("http://google.com", "tokenId",
				apiContext);
		logger.info("Logout URL: " + logoutURL);
		Assert.assertEquals(
				logoutURL,
				"https://www.paypal.com/webapps/auth/protocol/openidconnect/v1/endsession?id_token=tokenId&redirect_uri=http%3A%2F%2Fgoogle.com&logout=true");
	}

	@Test()
	public void testLogoutURLSandbox() {
		Map<String, String> m = new HashMap<String, String>();
		m.put("mode", "sandbox");
		APIContext apiContext = new APIContext();
		apiContext.setConfigurationMap(m);
		String logoutURL = Session.getLogoutUrl("http://google.com", "tokenId",
				apiContext);
		logger.info("Logout URL: " + logoutURL);
		Assert.assertEquals(
				logoutURL,
				"https://www.sandbox.paypal.com/webapps/auth/protocol/openidconnect/v1/endsession?id_token=tokenId&redirect_uri=http%3A%2F%2Fgoogle.com&logout=true");
	}

	@Test()
	public void testLogoutURLLive() {
		Map<String, String> m = new HashMap<String, String>();
		m.put("mode", "live");
		APIContext apiContext = new APIContext();
		apiContext.setConfigurationMap(m);
		String logoutURL = Session.getLogoutUrl("http://google.com", "tokenId",
				apiContext);
		logger.info("Logout URL: " + logoutURL);
		Assert.assertEquals(
				logoutURL,
				"https://www.paypal.com/webapps/auth/protocol/openidconnect/v1/endsession?id_token=tokenId&redirect_uri=http%3A%2F%2Fgoogle.com&logout=true");
	}

}
