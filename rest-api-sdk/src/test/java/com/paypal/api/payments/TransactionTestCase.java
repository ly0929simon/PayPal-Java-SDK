package com.paypal.api.payments;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TransactionTestCase {

	public static final Amount AMOUNT = AmountTestCase.createAmount("100.00");

	public static final Payee PAYEE = PayeeTestCase.createPayee();

	public static final String DESCRIPTION = "sample description";

    public static final String CUSTOM = "custom field value 1";

    public static final String INVOICE = "invoice 1";

    public static final String SOFT_DESCRIPTOR = "soft descriptor 1";

	public static Transaction createTransaction() {
		ItemList itemList = ItemListTestCase.createItemList();
		List<RelatedResources> relResources = new ArrayList<RelatedResources>();
		relResources.add(RelatedResourcesTestCase.createRelatedResources());
		Transaction transaction = new Transaction();
		transaction.setAmount(AMOUNT);
		transaction.setPayee(PAYEE);
		transaction.setDescription(DESCRIPTION);
		transaction.setItemList(itemList);
		transaction.setRelatedResources(relResources);
        transaction.setCustom( CUSTOM );
        transaction.setInvoiceNumber( INVOICE );
        transaction.setSoftDescriptor( SOFT_DESCRIPTOR );
		return transaction;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		Transaction transaction = createTransaction();
		Assert.assertEquals(transaction.getAmount().getTotal(), "100.00");
		Assert.assertEquals(transaction.getDescription(), DESCRIPTION);
		Assert.assertEquals(transaction.getPayee().getMerchantId(),
				PayeeTestCase.MERCHANTID);
		Assert.assertEquals(transaction.getItemList().getItems().get(0)
				.getName(), ItemTestCase.NAME);
		Assert.assertEquals(transaction.getRelatedResources().get(0)
				.getAuthorization().getId(), AuthorizationTestCase.ID);
		Assert.assertEquals(transaction.getAmount().getCurrency(),
				AmountTestCase.CURRENCY);
		Assert.assertEquals(transaction.getCustom(), CUSTOM);
		Assert.assertEquals(transaction.getInvoiceNumber(), INVOICE);
		Assert.assertEquals(transaction.getSoftDescriptor(), SOFT_DESCRIPTOR);
	}

	@Test(groups = "unit")
	public void testTOJSON() {
		Transaction transaction = createTransaction();
		Assert.assertEquals(transaction.toJSON().length() == 0, false);
	}

	@Test(groups = "unit")
	public void testTOString() {
		Transaction transaction = createTransaction();
		Assert.assertEquals(transaction.toString().length() == 0, false);
	}

}
