package com.paypal.base.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.paypal.base.ClientCredentials;
import com.paypal.base.Constants;

public class PayPalResourceTestCase {

	@Test
	public void testUnknownFileConfiguration() {
		try {
			PayPalResource.initConfig(new File("unknown.properties"));
		} catch (PayPalRESTException e) {
			Assert.assertEquals(e.getCause().getClass().getSimpleName(),
					"FileNotFoundException");
		}
	}

	@Test
	public void testInputStreamConfiguration() {
		try {
			File testFile = new File(".",
					"src/test/resources/sdk_config.properties");
			FileInputStream fis = new FileInputStream(testFile);
			PayPalResource.initConfig(fis);
		} catch (PayPalRESTException e) {
			Assert.fail("[sdk_config.properties] stream loading failed");
		} catch (FileNotFoundException e) {
			Assert.fail("[sdk_config.properties] file is not available");
		}
	}
	
	@Test
	public void testPropertiesConfiguration() {
		try {
			File testFile = new File(".",
					"src/test/resources/sdk_config.properties");
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream(testFile);
			props.load(fis);
			PayPalResource.initConfig(props);
		} catch (FileNotFoundException e) {
			Assert.fail("[sdk_config.properties] file is not available");
		} catch (IOException e) {
			Assert.fail("[sdk_config.properties] file is not loaded into properties");
		}
	}

	@Test
	public void testClientCredentials() {
		try {
			// Init configuration from file
			File testFile = new File(getClass().getClassLoader().getResource("sdk_config.properties").getFile());
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream(testFile);
			props.load(fis);
			PayPalResource.initConfig(props);

			// Check if ClientCredentials is constructed correctly
			ClientCredentials clientCredentials = PayPalResource.getCredential();
			Assert.assertEquals(props.getProperty(Constants.CLIENT_ID), clientCredentials.getClientID());
			Assert.assertEquals(props.getProperty(Constants.CLIENT_SECRET), clientCredentials.getClientSecret());
		} catch (FileNotFoundException e) {
			Assert.fail("[sdk_config.properties] file is not available");
		} catch (IOException e) {
			Assert.fail("[sdk_config.properties] file is not loaded into properties");
		}
	}

}
