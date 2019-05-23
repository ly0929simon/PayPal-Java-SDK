package com.paypal.api.payments;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.paypal.base.rest.JSONFormatter;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.util.TestConstants;

public class WebProfileTestCase {

	private String id = null;

	public static WebProfile loadWebProfile() {
	    try {
		    BufferedReader br = new BufferedReader(new FileReader("src/test/resources/webexperience_create.json"));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.getProperty("line.separator"));
	            line = br.readLine();
	        }
	        br.close();
	        return JSONFormatter.fromJSON(sb.toString(), WebProfile.class);
	        
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}
	
	@Test(groups = "integration")
	public void testCreateWebProfile() throws PayPalRESTException {
		Random random = new Random();
		long randomNumber = random.nextLong();
		WebProfile webProfile = loadWebProfile();
		webProfile.setName(webProfile.getName() + String.valueOf(randomNumber));
		CreateProfileResponse response = webProfile.create(TestConstants.SANDBOX_CONTEXT);
		this.id = response.getId();
		Assert.assertNotNull(response.getId());
	}
	
	@Test(groups = "integration", dependsOnMethods = { "testCreateWebProfile" })
	public void testRetrieveWebProfile() throws PayPalRESTException {
		WebProfile webProfile = WebProfile.get(TestConstants.SANDBOX_CONTEXT, this.id);
		Assert.assertEquals(this.id, webProfile.getId());
	}
	
	@Test(groups = "integration", dependsOnMethods = { "testCreateWebProfile" })
	public void testListWebProfiles() throws PayPalRESTException {
		List<WebProfile> webProfileList = WebProfile.getList(TestConstants.SANDBOX_CONTEXT);
		Assert.assertTrue(webProfileList.size() > 0);
	}
	
	@Test(groups = "integration", dependsOnMethods = { "testCreateWebProfile" })
	public void testUpdateWebProfile() throws PayPalRESTException {
		Random random = new Random();
		long randomNumber = random.nextLong();
		String newName = "YeowZa! T-Shirt Shop" + String.valueOf(randomNumber);
		WebProfile webProfile = loadWebProfile();
		webProfile.setId(this.id);
		webProfile.setName(newName);
		webProfile.update(TestConstants.SANDBOX_CONTEXT);
		webProfile = WebProfile.get(TestConstants.SANDBOX_CONTEXT, this.id);
		Assert.assertEquals(webProfile.getName(), newName);
	}
}
