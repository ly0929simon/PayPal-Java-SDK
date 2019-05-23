// #RefundCapture Sample
// This sample code demonstrate how you
// can do a Refund on a Capture
// resource
// API used: /v1/payments/capture/{capture_id}/refund
package com.paypal.api.payments.servlet;

import com.paypal.api.payments.*;
import com.paypal.api.payments.util.ResultPrinter;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.paypal.api.payments.util.SampleConstants.*;

public class RefundCaptureServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger
			.getLogger(GetAuthorizationServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	// ##RefundCapture
	// Sample showing to how to do a Refund on
	// a Capture
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			// ### Api Context
			// Pass in a `ApiContext` object to authenticate
			// the call and to send a unique request id
			// (that ensures idempotency). The SDK generates
			// a request id if you do not pass one explicitly.
			APIContext apiContext = new APIContext(clientID, clientSecret, mode);

			/// ###Capture
			// Create a Capture object
			// by doing a capture on
			// Authorization object
			// and retrieve the Id
			String captureId = "<your capture id here>";

			// Retrieve the Capture object by
			// doing a GET call to
			// URI v1/payments/capture/{capture_id}
			Capture capture = Capture.get(apiContext, captureId);
			
			/// ###Refund
			/// Create a Refund object
			RefundRequest refund = new RefundRequest();

			// ###Amount
			// Let's you specify a capture amount.
			Amount amount = new Amount();
			amount.setCurrency("USD").setTotal("1");

			refund.setAmount(amount);

			// Do a Refund by
			// POSTing to
			// URI v1/payments/capture/{capture_id}/refund
			Refund responseRefund = capture.refund(apiContext, refund);

			LOGGER.info("Refund id = " + responseRefund.getId()
					+ " and status = " + responseRefund.getState());
			ResultPrinter.addResult(req, resp, "Refund a Capture", Refund.getLastRequest(), Refund.getLastResponse(), null);
			
		} catch (PayPalRESTException e) {
			ResultPrinter.addResult(req, resp, "Refund a Capture", Refund.getLastRequest(), null, e.getMessage());
		}
		req.getRequestDispatcher("response.jsp").forward(req, resp);
	}
	
	private Capture getCapture(APIContext apiContext, Authorization authorization) throws PayPalRESTException{
		// ###Amount
		// Let's you specify a capture amount.
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal("4.54");

		// ###Capture
		Capture capture = new Capture();
		capture.setAmount(amount);
		
		// ##IsFinalCapture
		// If set to true, all remaining 
		// funds held by the authorization 
		// will be released in the funding 
		// instrument. Default is �false�.
		capture.setIsFinalCapture(true);

		// Capture by POSTing to
		// URI v1/payments/authorization/{authorization_id}/capture
		Capture responseCapture = authorization.capture(apiContext, capture);
		return responseCapture;
	}

	private Authorization getAuthorization(APIContext apiContext)
			throws PayPalRESTException {

		// ###Details
		// Let's you specify details of a payment amount.
		Details details = new Details();
		details.setShipping("0.03");
		details.setSubtotal("107.41");
		details.setTax("0.03");

		// ###Amount
		// Let's you specify a payment amount.
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal("107.47");
		amount.setDetails(details);

		// ###Transaction
		// A transaction defines the contract of a
		// payment - what is the payment for and who
		// is fulfilling it. Transaction is created with
		// a `Payee` and `Amount` types
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction
				.setDescription("This is the payment transaction description.");

		// The Payment creation API requires a list of
		// Transaction; add the created `Transaction`
		// to a List
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		// ###Address
		// Base Address object used as shipping or billing
		// address in a payment. [Optional]
		Address billingAddress = new Address();
		billingAddress.setCity("Johnstown");
		billingAddress.setCountryCode("US");
		billingAddress.setLine1("52 N Main ST");
		billingAddress.setPostalCode("43210");
		billingAddress.setState("OH");

		// ###CreditCard
		// A resource representing a credit card that can be
		// used to fund a payment.
		CreditCard creditCard = new CreditCard();
		creditCard.setBillingAddress(billingAddress);
		creditCard.setCvv2(874);
		creditCard.setExpireMonth(11);
		creditCard.setExpireYear(2018);
		creditCard.setFirstName("Joe");
		creditCard.setLastName("Shopper");
		creditCard.setNumber("4669424246660779");
		creditCard.setType("visa");

		// ###FundingInstrument
		// A resource representing a Payeer's funding instrument.
		// Use a Payer ID (A unique identifier of the payer generated
		// and provided by the facilitator. This is required when
		// creating or using a tokenized funding instrument)
		// and the `CreditCardDetails`
		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setCreditCard(creditCard);

		// The Payment creation API requires a list of
		// FundingInstrument; add the created `FundingInstrument`
		// to a List
		List<FundingInstrument> fundingInstruments = new ArrayList<FundingInstrument>();
		fundingInstruments.add(fundingInstrument);

		// ###Payer
		// A resource representing a Payer that funds a payment
		// Use the List of `FundingInstrument` and the Payment Method
		// as 'credit_card'
		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstruments);
		payer.setPaymentMethod("credit_card");

		// ###Payment
		// A Payment Resource; create one using
		// the above types and intent as 'authorize'
		Payment payment = new Payment();
		payment.setIntent("authorize");
		payment.setPayer(payer);
		payment.setTransactions(transactions);

		Payment responsePayment = payment.create(apiContext);
		return responsePayment.getTransactions().get(0).getRelatedResources()
				.get(0).getAuthorization();
	}
}
