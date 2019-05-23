package com.paypal.api.payments;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.paypal.base.util.TestConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.paypal.base.rest.PayPalRESTException;

public class WebhookTestCase {
	
	private static final Logger logger = Logger.getLogger(WebhookTestCase.class);

	public static final String WEBHOOK_ID = "WH-ID-SDK-1S115631EN580315E-9KH94552VF7913711";
	public static String webhookId =  "";
	public static String webhookUrl =  "";

	public static Webhook createWebhook() {
 		Webhook webhook = new Webhook();
		webhook.setId(WEBHOOK_ID);
		webhook.setUrl(WebhooksInputData.WEBHOOK_URL);
		webhook.setLinks(createWebhookHATEOASLinks());
		webhook.setEventTypes(EventTypeListTestCase.createAuthEventTypeList());
		return webhook;
	}
	
	public static Webhook createWebhookWithAllEvents() {
		Webhook webhook = new Webhook();
		webhook.setId(WEBHOOK_ID);
		webhook.setUrl(WebhooksInputData.WEBHOOK_URL);
		webhook.setLinks(createWebhookHATEOASLinks());
		webhook.setEventTypes(EventTypeListTestCase.createAllEventTypeList());
		return webhook;
	}
	
	public static List<Links> createWebhookHATEOASLinks() {
		List<Links> linksList = new ArrayList<Links>();
		
		Links link1 = new Links();
		link1.setHref(WebhooksInputData.WEBHOOK_HATEOAS_URL + WEBHOOK_ID);
		link1.setRel("self");
		link1.setMethod("GET");
		
		Links link2 = new Links();
		link1.setHref(WebhooksInputData.WEBHOOK_HATEOAS_URL + WEBHOOK_ID);
		link2.setRel("update");
		link2.setMethod("PATCH");
		
		Links link3 = new Links();
		link1.setHref(WebhooksInputData.WEBHOOK_HATEOAS_URL + WEBHOOK_ID);
		link3.setRel("delete");
		link3.setMethod("DELETE");
		
		linksList.add(link1);
		linksList.add(link2);
		linksList.add(link3);
		
		return linksList;
	}

	@Test(groups = "unit")
	public void testWebhookConstruction() {
		Webhook webhook = createWebhook();
		Assert.assertEquals(webhook.getId(), WEBHOOK_ID);
		Assert.assertEquals(webhook.getUrl(), WebhooksInputData.WEBHOOK_URL);
		Assert.assertEquals(webhook.getLinks().size(), 3);
		Assert.assertEquals(webhook.getEventTypes().size(), 2);
		Assert.assertEquals(webhook.getEventTypes().get(0).getName(), WebhooksInputData.availableEvents[1][0]);
		Assert.assertEquals(webhook.getEventTypes().get(1).getName(), WebhooksInputData.availableEvents[2][0]);
		Assert.assertNotEquals(webhook.getEventTypes().size(), WebhooksInputData.availableEvents.length);
	}

	@Test(groups = "unit")
	public void testWebhookWithAllEventsConstruction() {
		Webhook webhook = createWebhookWithAllEvents();
		Assert.assertEquals(webhook.getId(), WEBHOOK_ID);
		Assert.assertEquals(webhook.getUrl(), WebhooksInputData.WEBHOOK_URL);
		Assert.assertEquals(webhook.getLinks().size(), 3);
		Assert.assertEquals(webhook.getEventTypes().size(), WebhooksInputData.availableEvents.length);
		for(int i=0; i < webhook.getEventTypes().size(); i++) {
			Assert.assertEquals(webhook.getEventTypes().get(i).getName(), WebhooksInputData.availableEvents[i][0]);
		}
	}

	@Test(groups = "unit")
	public void testTOJSON() {
		Webhook webhook =  createWebhook();
		Assert.assertEquals(webhook.toJSON().length() == 0, false);
		logger.info("EventTypeJSON = " + webhook.toJSON());
	}

	@Test(groups = "unit")
	public void testTOString() {
		Webhook webhook =  createWebhook();
		Assert.assertEquals(webhook.toString().length() == 0, false);
	}
	
	@Test(groups = "integration")
	public void testCreateWebhook() throws PayPalRESTException {
		Webhook webhookRequest = new Webhook();
		String uuid = UUID.randomUUID().toString();
		webhookRequest.setUrl(WebhooksInputData.WEBHOOK_URL + uuid);
		webhookRequest.setEventTypes(EventTypeListTestCase.createAuthEventTypeList());
		logger.info("Request = " + webhookRequest.toJSON());
		Webhook webhookResponse = webhookRequest.create(TestConstants.SANDBOX_CONTEXT, webhookRequest);
		logger.info("Response = " + webhookResponse.toJSON());
		webhookId =  webhookResponse.getId();
		webhookUrl = webhookResponse.getUrl();
		
		Assert.assertNotNull(webhookResponse.getId());
		Assert.assertEquals(webhookResponse.getUrl(), WebhooksInputData.WEBHOOK_URL + uuid);
		Assert.assertEquals(webhookResponse.getLinks().size(), 3);
		Assert.assertEquals(webhookResponse.getEventTypes().size(), 2);
		Assert.assertEquals(webhookResponse.getEventTypes().get(0).getName(), WebhooksInputData.availableEvents[1][0]);
		Assert.assertEquals(webhookResponse.getEventTypes().get(1).getName(), WebhooksInputData.availableEvents[2][0]);
		Assert.assertNotEquals(webhookResponse.getEventTypes().size(), WebhooksInputData.availableEvents.length);
	}
	
	@Test(groups = "integration", dependsOnMethods = { "testCreateWebhook" })
	public void testGetWebhook() throws PayPalRESTException {
		Webhook webhookRequest = new Webhook();
		Webhook webhookGetResponse = webhookRequest.get(TestConstants.SANDBOX_CONTEXT, webhookId);
		logger.info("Response = " + webhookGetResponse.toJSON());
		
		Assert.assertNotNull(webhookGetResponse.getId());
		Assert.assertNotNull(webhookGetResponse.getUrl());
		Assert.assertEquals(webhookGetResponse.getLinks().size(), 3);
		Assert.assertEquals(webhookGetResponse.getEventTypes().size(), 2);
		Assert.assertEquals(webhookGetResponse.getEventTypes().get(0).getName(), WebhooksInputData.availableEvents[2][0]);
		Assert.assertEquals(webhookGetResponse.getEventTypes().get(1).getName(), WebhooksInputData.availableEvents[1][0]);
		Assert.assertNotEquals(webhookGetResponse.getEventTypes().size(), WebhooksInputData.availableEvents.length);
	}
	
	@Test(groups = "integration", dependsOnMethods = { "testGetWebhook" })
	public void testUpdateWebhook() throws PayPalRESTException {
		String webhookUpdatedUrl = webhookUrl + "/new_url";
		
		JsonObject patchUrl = new JsonObject();
		patchUrl.addProperty("op", "replace");
		patchUrl.addProperty("path", "/url");
		patchUrl.addProperty("value", webhookUpdatedUrl);
		
		JsonObject eventType = new JsonObject();
		eventType.addProperty("name", WebhooksInputData.availableEvents[4][0]);
		JsonArray eventTypeArray = new JsonArray();
		eventTypeArray.add(eventType);
		
		JsonObject patchEventType = new JsonObject();
		patchEventType.addProperty("op", "replace");
		patchEventType.addProperty("path", "/event_types");
		patchEventType.add("value", eventTypeArray);
		
        JsonArray patchArray = new JsonArray();
        patchArray.add(patchUrl);
        patchArray.add(patchEventType);

        String patchRequest = patchArray.toString(); 
		logger.info("Request" + patchRequest);
		
		Webhook webhookRequest = new Webhook();
		Webhook webhookGetResponse = webhookRequest.update(TestConstants.SANDBOX_CONTEXT, webhookId, patchRequest);
		logger.info("Response = " + webhookGetResponse.toJSON());
		
		Assert.assertNotNull(webhookGetResponse);
		Assert.assertEquals(webhookGetResponse.getUrl(), webhookUpdatedUrl);
		Assert.assertEquals(webhookGetResponse.getLinks().size(), 3);
		Assert.assertEquals(webhookGetResponse.getEventTypes().size(), 1);
		Assert.assertEquals(webhookGetResponse.getEventTypes().get(0).getName(), WebhooksInputData.availableEvents[4][0]);
	}
	
	@Test(groups = "integration", dependsOnMethods= { "testUpdateWebhook" }, expectedExceptions = {PayPalRESTException.class} )
	public void testDeleteWebhook() throws PayPalRESTException {
		Webhook webhookRequest = new Webhook();
		webhookRequest.delete(TestConstants.SANDBOX_CONTEXT, webhookId);
		webhookRequest.get(TestConstants.SANDBOX_CONTEXT, webhookId); //This should throw PayPalRESTException since Resource does not exist
	}
}
