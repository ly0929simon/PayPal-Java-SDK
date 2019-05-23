package com.paypal.api.payments;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ItemTestCase {

	public static final String NAME = "Sample Item";

	public static final String CURRENCY = "USD";

	public static final String PRICE = "8.82";

	public static final String TAX = "1.68";

	public static final String QUANTITY = "5";

	public static final String SKU = "123";

	public static Item createItem() {
		Item item = new Item();
		item.setName(NAME);
		item.setCurrency(CURRENCY);
		item.setPrice(PRICE);
        item.setTax(TAX);
		item.setQuantity(QUANTITY);
		item.setSku(SKU);
		return item;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		Item item = createItem();
		Assert.assertEquals(item.getName(), NAME);
		Assert.assertEquals(item.getCurrency(), CURRENCY);
		Assert.assertEquals(item.getPrice(), PRICE);
		Assert.assertEquals(item.getTax(), TAX);
		Assert.assertEquals(item.getQuantity(), QUANTITY);
		Assert.assertEquals(item.getSku(), SKU);
	}

	@Test(groups = "unit")
	public void testTOJSON() {
		Item item = createItem();
		Assert.assertEquals(item.toJSON().length() == 0, false);
	}

	@Test(groups = "unit")
	public void testTOString() {
		Item item = createItem();
		Assert.assertEquals(item.toString().length() == 0, false);
	}

}
