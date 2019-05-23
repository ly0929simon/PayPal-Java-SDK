package com.paypal.api.payments;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PayerTestCase {

	public static final String PAYMENTMETHOD = "credit_card";

	public static Payer createPayer() {
		List<FundingInstrument> fundingInstruments = new ArrayList<FundingInstrument>();
		fundingInstruments.add(FundingInstrumentTestCase
				.createFundingInstrument());
		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstruments);
		payer.setPayerInfo(PayerInfoTestCase.createPayerInfo());
		payer.setPaymentMethod(PAYMENTMETHOD);
		return payer;
	}

	@Test(groups = "unit")
	public void testConstruction() {
		Payer payer = createPayer();

		Assert.assertEquals(payer.getPaymentMethod(), PAYMENTMETHOD);
		Assert.assertEquals(payer.getFundingInstruments().get(0)
				.getCreditCardToken().getCreditCardId(),
				CreditCardTokenTestCase.CREDITCARDID);
		Assert.assertEquals(payer.getPayerInfo().getFirstName(),
				PayerInfoTestCase.FIRSTNAME);
	}

	@Test(groups = "unit")
	public void testTOJSON() {
		Payer payer = createPayer();
		Assert.assertEquals(payer.toJSON().length() == 0, false);
	}

	@Test(groups = "unit")
	public void testTOString() {
		Payer payer = createPayer();
		Assert.assertEquals(payer.toString().length() == 0, false);
	}
}
