package com.paypal.api.payments;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.paypal.base.rest.JSONFormatter;

public class ErrorTestCase {

	public static final String NAME = "VALIDATION_ERROR";
	
	public static final String MESSAGE = "Invalid request - see details";
	
	public static final String INFORMATION_LINK = "https://developer.paypal.com/docs/api/#VALIDATION_ERROR";
	
	public static final String DEBUG_ID = "5c16c7e108c14";
	
	public static final String DETAILS_FIELD = "number";
	
	public static final String DETAILS_ISSUE = "Value is invalid";
	

	public static Error loadError() {
	    try {
		    BufferedReader br = new BufferedReader(new FileReader("src/test/resources/error.json"));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            sb.append(System.getProperty("line.separator"));
	            line = br.readLine();
	        }
	        br.close();
	        return JSONFormatter.fromJSON(sb.toString(), Error.class);
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}

	@Test(groups = "unit")
	public void fromJSON() {
		Error error = loadError();
		Assert.assertNotNull(error);
		Assert.assertEquals(error.getName(), NAME);
		Assert.assertEquals(error.getMessage(), MESSAGE);
		Assert.assertEquals(error.getInformationLink(), INFORMATION_LINK);
		Assert.assertEquals(error.getDebugId(), DEBUG_ID);
		Assert.assertNotNull(error.getDetails());
		Assert.assertEquals(error.getDetails().size(), 1);
		Assert.assertEquals(error.getDetails().get(0).getField(), DETAILS_FIELD);
		Assert.assertEquals(error.getDetails().get(0).getIssue(), DETAILS_ISSUE);
	}

}
