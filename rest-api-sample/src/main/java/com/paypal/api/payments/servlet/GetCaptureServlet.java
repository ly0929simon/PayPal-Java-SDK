// #GetCapture Sample
// This sample code demonstrate how you
// can retrieve the details of a Capture
// resource
// API used: /v1/payments/capture/{capture_id}
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

public class GetCaptureServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger
			.getLogger(GetAuthorizationServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	// ##GetCapture
	// Sample showing how to Get a Capture using
	// CaptureId
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
			
			LOGGER.info("Capture id = " + capture.getId()
					+ " and status = " + capture.getState());
			ResultPrinter.addResult(req, resp, "Get Capture", Capture.getLastRequest(), Capture.getLastResponse(), null);
		} catch (PayPalRESTException e) {
			ResultPrinter.addResult(req, resp, "Get Capture", Capture.getLastRequest(), null, e.getMessage());
		}
		req.getRequestDispatcher("response.jsp").forward(req, resp);
	}
}
