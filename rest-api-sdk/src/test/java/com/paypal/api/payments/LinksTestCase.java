package com.paypal.api.payments;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.paypal.base.rest.JSONFormatter;

public class LinksTestCase {

	public static final String HREF = "http://sample.com";

	public static final String METHOD = "POST";

	public static final String REL = "authorize";

	public static String getJson() {
		return "{\"href\":\"http://localhost/\",\"method\":\"POST\",\"rel\":\"self\"}";
	}
	
	public static Links getObject() {
		return JSONFormatter.fromJSON(getJson(), Links.class);
	}
	
	public static Links createLinks() {
		Links link = new Links();
		link.setHref(HREF);
		link.setMethod(METHOD);
		link.setRel(REL);
		return link;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		Links link = LinksTestCase.createLinks();
		Assert.assertEquals(link.getHref(), HREF);
		Assert.assertEquals(link.getRel(), REL);
		Assert.assertEquals(link.getMethod(), METHOD);
	}

	@Test(groups = "unit")
	public void testTOJSON() {
		Links link = LinksTestCase.createLinks();
		Assert.assertEquals(link.toJSON().length() == 0, false);
	}

	@Test(groups = "unit")
	public void testTOString() {
		Links link = LinksTestCase.createLinks();
		Assert.assertEquals(link.toString().length() == 0, false);
	}

}
