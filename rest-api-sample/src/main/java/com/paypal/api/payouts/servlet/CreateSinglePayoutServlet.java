// #Create Single Payout
// You can make payouts to multiple PayPal accounts or to a single PayPal account.
// If you are submitting a single payout, you can make a synchronous payout call, which immediately returns the results of the payout. 
// In a synchronous payout call, the response is similar to a batch payout status response. 
// To make a synchronous payout, specify sync_mode=true in the URL: /v1/payments/payouts?sync_mode=true

// API used: POST /v1/payments/payouts
package com.paypal.api.payouts.servlet;

import static com.paypal.api.payments.util.SampleConstants.clientID;
import static com.paypal.api.payments.util.SampleConstants.clientSecret;
import static com.paypal.api.payments.util.SampleConstants.mode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.paypal.api.payments.Currency;
import com.paypal.api.payments.Payout;
import com.paypal.api.payments.PayoutBatch;
import com.paypal.api.payments.PayoutItem;
import com.paypal.api.payments.PayoutSenderBatchHeader;
import com.paypal.api.payments.util.ResultPrinter;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class CreateSinglePayoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger
			.getLogger(CreateSinglePayoutServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	// ##Create
	// Sample showing how to create a Single Payout with Synchronous Mode.
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		createSynchronousSinglePayout(req, resp);
		req.getRequestDispatcher("response.jsp").forward(req, resp);
	}
	
	public PayoutBatch createSynchronousSinglePayout(HttpServletRequest req,
			HttpServletResponse resp) {
		
		
		// ###Payout
		// A resource representing a payout
		Payout payout = new Payout();

		PayoutSenderBatchHeader senderBatchHeader = new PayoutSenderBatchHeader();

		// ### NOTE:
		// You can prevent duplicate batches from being processed. If you
		// specify a `sender_batch_id` that was used in the last 30 days, the
		// batch will not be processed. For items, you can specify a
		// `sender_item_id`. If the value for the `sender_item_id` is a
		// duplicate of a payout item that was processed in the last 30 days,
		// the item will not be processed.
		// #### Batch Header Instance
		Random random = new Random();
		senderBatchHeader.setSenderBatchId(
				new Double(random.nextDouble()).toString()).setEmailSubject(
				"You have a Payout!");

		// ### Currency
		Currency amount = new Currency();
		amount.setValue("1.00").setCurrency("USD");

		// #### Sender Item
		// Please note that if you are using single payout with sync mode, you
		// can only pass one Item in the request
		PayoutItem senderItem = new PayoutItem();
		senderItem.setRecipientType("Email")
				.setNote("Thanks for your patronage")
				.setReceiver("shirt-supplier-one@gmail.com")
				.setSenderItemId("201404324234").setAmount(amount);

		List<PayoutItem> items = new ArrayList<PayoutItem>();
		items.add(senderItem);

		payout.setSenderBatchHeader(senderBatchHeader).setItems(items);

		PayoutBatch batch = null;
		try {

			// ### Api Context
			// Pass in a `ApiContext` object to authenticate
			// the call and to send a unique request id
			// (that ensures idempotency). The SDK generates
			// a request id if you do not pass one explicitly.
			APIContext apiContext = new APIContext(clientID, clientSecret, mode);

			// ###Create Payout Synchronous
			batch = payout.createSynchronous(apiContext);

			LOGGER.info("Payout Batch With ID: "
					+ batch.getBatchHeader().getPayoutBatchId());
			ResultPrinter.addResult(req, resp,
					"Created Single Synchronous Payout",
					Payout.getLastRequest(), Payout.getLastResponse(), null);
		} catch (PayPalRESTException e) {
			ResultPrinter.addResult(req, resp,
					"Created Single Synchronous Payout",
					Payout.getLastRequest(), null, e.getMessage());
		}
		return batch;
	}

}
