package com.paypal.api.payments;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

public class EventListTestCase {
	
	public static final String EVENTTYPE_PAYMENT_AUTH = "PAYMENT.AUTHORIZATION.CREATED";
	public static final String RESOURCETYPE_AUTHORIZATION = "authorization";
	public static final String EVENT_ID = "WH-SDK-1S115631EN580315E-9KH94552VF7913711";
	public static final String EVENT_SUMMARY = "A successful payment authorization was created";
	
	private static final Logger logger = Logger.getLogger(EventListTestCase.class);
	
	public static EventList createEventList() {
		EventList eventList = new EventList();
		List<Event> events = new ArrayList<Event>();
		Event authEvent = EventTestCase.createEvent();
		Event saleEvent = EventTestCase.createSaleEvent();
		events.add(authEvent);
		events.add(saleEvent);
		eventList.setEvents(events);
		eventList.setCount(events.size());
		eventList.setLinks(WebhooksInputData.createLinksList());
		return eventList;
	}
	
	@Test(groups = "unit")
	public void testEventListConstruction() {
		EventList eventListt = createEventList();
		Assert.assertNotEquals(eventListt.getEvents(), null);
		Assert.assertEquals(eventListt.getCount(), 2);
		Assert.assertNotEquals(eventListt.getLinks(), null);
	}
	
	@Test(groups = "unit")
	public void testTOJSON() {
		EventList eventList = createEventList();
		Assert.assertEquals(eventList.toJSON().length() == 0, false);
		logger.info("EventListJSON = " + eventList.toJSON());
	}
	
	@Test(groups = "unit")
	public void testTOString() {
		EventList eventList = createEventList();
		Assert.assertEquals(eventList.toString().length() == 0, false);
	}
}
