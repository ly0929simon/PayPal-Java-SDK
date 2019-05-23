// #AuthorizationCapture Sample
// This sample code demonstrate how you 
// do a Capture on an Authorization
// API used: /v1/payments/authorization/{authorization_id}/capture
package com.paypal.api.payments.servlet;

import static com.paypal.api.payments.util.SampleConstants.clientID;
import static com.paypal.api.payments.util.SampleConstants.clientSecret;
import static com.paypal.api.payments.util.SampleConstants.mode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.paypal.api.payments.Address;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Authorization;
import com.paypal.api.payments.Capture;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.api.payments.util.ResultPrinter;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class AuthorizationCaptureServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger
			.getLogger(AuthorizationCaptureServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	// ##AuthorizationCapture
	// Sample showing how to do a Capture using Authorization
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

			// ###Authorization
			// Retrieve a Authorization object
			// by making a Payment with intent
			// as 'authorize'
			String authorizationId = "<your authorization id here>";
			Authorization authorization = Authorization.get(apiContext, authorizationId);

			// ###Amount
			// Let's you specify a capture amount.
			Amount amount = new Amount();
			amount.setCurrency("USD");
			amount.setTotal("4.54");

			// ###Capture
			// A capture transaction
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

			LOGGER.info("Capture id = " + responseCapture.getId()
					+ " and status = " + responseCapture.getState());
			ResultPrinter.addResult(req, resp, "Authorization Capture", Authorization.getLastRequest(), Authorization.getLastResponse(), null);
		} catch (PayPalRESTException e) {
			ResultPrinter.addResult(req, resp, "Authorization Capture", Authorization.getLastRequest(), null, e.getMessage());
		}
		req.getRequestDispatcher("response.jsp").forward(req, resp);
	}

}
