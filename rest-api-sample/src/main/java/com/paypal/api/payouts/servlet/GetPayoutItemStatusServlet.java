// #Get Payout Item Status
// Use this call to get data about a payout item, including the status, without retrieving an entire batch. 
// You can get the status of an individual payout item in a batch in order to review the current status of a previously-unclaimed, or pending, payout item.
// API used: GET /v1/payments/payouts-item/<Payout-Item-Id>

package com.paypal.api.payouts.servlet;

import static com.paypal.api.payments.util.SampleConstants.clientID;
import static com.paypal.api.payments.util.SampleConstants.clientSecret;
import static com.paypal.api.payments.util.SampleConstants.mode;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.paypal.api.payments.PayoutBatch;
import com.paypal.api.payments.PayoutItem;
import com.paypal.api.payments.PayoutItemDetails;
import com.paypal.api.payments.util.ResultPrinter;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class GetPayoutItemStatusServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger
			.getLogger(GetPayoutItemStatusServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	// ##Get Payout Item Status
	// Sample showing how to get a Payout Item Status
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		getPayoutItemStatus(req, resp);
		req.getRequestDispatcher("response.jsp").forward(req, resp);
	}

	public PayoutItemDetails getPayoutItemStatus(HttpServletRequest req,
			HttpServletResponse resp) {

		// ### Get a Payout Batch
		// We are re-using the GetPayoutBatchStatusServlet to get a batch payout
		// for us. This will make sure the samples will work all the time.
		GetPayoutBatchStatusServlet servlet = new GetPayoutBatchStatusServlet();
		PayoutBatch batch = servlet.getPayoutBatchStatus(req, resp);

		// ### Retrieve PayoutItem ID
		// In the samples, we are extractingt he payoutItemId of a payout we
		// just created.
		// In reality, you might be using the payoutItemId stored in your
		// database, or passed manually.
		PayoutItemDetails itemDetails = batch.getItems().get(0);
		String payoutItemId = itemDetails.getPayoutItemId();

		// Initiate the response object
		PayoutItemDetails response = null;
		try {

			// ### Api Context
			// Pass in a `ApiContext` object to authenticate
			// the call and to send a unique request id
			// (that ensures idempotency). The SDK generates
			// a request id if you do not pass one explicitly.
			APIContext apiContext = new APIContext(clientID, clientSecret, mode);

			// ###Get Payout Item
			response = PayoutItem.get(apiContext, payoutItemId);

			LOGGER.info("Payout Item With ID: " + response.getPayoutItemId());
			ResultPrinter.addResult(req, resp, "Got Payout Item Status",
					PayoutItem.getLastRequest(), PayoutItem.getLastResponse(),
					null);
		} catch (PayPalRESTException e) {
			ResultPrinter.addResult(req, resp, "Got Payout Item Status",
					PayoutItem.getLastRequest(), null, e.getMessage());
		}

		return response;
	}
}
