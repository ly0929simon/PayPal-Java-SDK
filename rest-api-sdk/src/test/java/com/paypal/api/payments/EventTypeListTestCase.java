package com.paypal.api.payments;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

public class EventTypeListTestCase {
	
	private static final Logger logger = Logger.getLogger(EventTypeListTestCase.class);

	public static List<EventType> createAuthEventTypeList() {
		List<EventType> eventTypes = new ArrayList<EventType>();
		
		EventType eventType1 = new EventType();
		eventType1.setName(WebhooksInputData.availableEvents[1][0]);
		eventType1.setDescription(WebhooksInputData.availableEvents[1][1]);
		
		EventType eventType2 = new EventType();
		eventType2.setName(WebhooksInputData.availableEvents[2][0]);
		eventType2.setDescription(WebhooksInputData.availableEvents[2][1]);
		
		eventTypes.add(eventType1);
		eventTypes.add(eventType2);
		
		return eventTypes;
	}
	
	public static List<EventType> createAllEventTypeList() {
		List<EventType> eventTypes = new ArrayList<EventType>();
		for(String[] availableEvent : WebhooksInputData.availableEvents) {
			EventType eventType = new EventType();
			eventType.setName(availableEvent[0]);
			eventType.setDescription(availableEvent[1]);
			eventTypes.add(eventType);
		}
		return eventTypes;
	}
	
	@Test(groups = "unit")
	public void testCreateAllEventTypeListConstruction() {
		List<EventType> eventTypeList = createAllEventTypeList();
		for(int i=0; i < eventTypeList.size(); i++) {
			Assert.assertEquals(eventTypeList.get(i).getName(), WebhooksInputData.availableEvents[i][0]);
		}
	}

	@Test(groups = "unit")
	public void testCreateAuthEventTypeListConstruction() {
		List<EventType> eventTypeList = createAuthEventTypeList();
		Assert.assertEquals(eventTypeList.get(0).getName(), WebhooksInputData.availableEvents[1][0]);
		Assert.assertEquals(eventTypeList.get(1).getName(), WebhooksInputData.availableEvents[2][0]);
	}

	@Test(groups = "unit")
	public void testTOJSON() {
		EventTypeList eventTypeList = new EventTypeList();
		eventTypeList.setEventTypes(createAllEventTypeList());
		Assert.assertEquals(eventTypeList.toJSON().length() == 0, false);
		logger.info("EventTypeListJSON = " + eventTypeList.toJSON());
	}

	@Test(groups = "unit")
	public void testTOString() {
		EventTypeList eventTypeList = new EventTypeList();
		eventTypeList.setEventTypes(createAllEventTypeList());
		Assert.assertEquals(eventTypeList.toString().length() == 0, false);
	}
	
}
