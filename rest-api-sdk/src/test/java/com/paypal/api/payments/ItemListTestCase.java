package com.paypal.api.payments;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ItemListTestCase {

	public static ItemList createItemList() {
		List<Item> items = new ArrayList<Item>();
		items.add(ItemTestCase.createItem());
		items.add(ItemTestCase.createItem());
		ItemList itemList = new ItemList();
		itemList.setItems(items);
		itemList.setShippingAddress(ShippingAddressTestCase
				.createShippingAddress());
		return itemList;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		ItemList itemList = createItemList();
		Assert.assertEquals(itemList.getShippingAddress().getRecipientName(),
				ShippingAddressTestCase.RECIPIENTSNAME);
		Assert.assertEquals(itemList.getItems().size(), 2);
	}

	@Test(groups = "unit")
	public void testTOJSON() {
		ItemList itemList = createItemList();
		Assert.assertEquals(itemList.toJSON().length() == 0, false);
	}

	@Test(groups = "unit")
	public void testTOString() {
		ItemList itemList = createItemList();
		Assert.assertEquals(itemList.toString().length() == 0, false);
	}
}
