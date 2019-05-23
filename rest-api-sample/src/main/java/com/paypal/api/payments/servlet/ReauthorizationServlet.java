package com.paypal.api.payments.servlet;

import static com.paypal.api.payments.util.SampleConstants.clientID;
import static com.paypal.api.payments.util.SampleConstants.clientSecret;
import static com.paypal.api.payments.util.SampleConstants.mode;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Authorization;
import com.paypal.api.payments.util.ResultPrinter;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class ReauthorizationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger
			.getLogger(ReauthorizationServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	// ##Reauthorization
	// Sample showing how to do a reauthorization
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
			
			// ###Reauthorization
			// Retrieve a authorization id from authorization object
			// by making a `Payment Using PayPal` with intent
			// as `authorize`. You can reauthorize a payment only once 4 to 29
			// days after 3-day honor period for the original authorization
			// expires.
			Authorization authorization = Authorization.get(apiContext,
					"7GH53639GA425732B");

			// ###Amount
			// Let's you specify a capture amount.
			Amount amount = new Amount();
			amount.setCurrency("USD");
			amount.setTotal("4.54");

			authorization.setAmount(amount);
			// Reauthorize by POSTing to
			// URI v1/payments/authorization/{authorization_id}/reauthorize
			Authorization reauthorization = authorization
					.reauthorize(apiContext);

			LOGGER.info("Reauthorization id = " + reauthorization.getId()
					+ " and status = " + reauthorization.getState());
			ResultPrinter.addResult(req, resp, "Reauthorized a Payment", Authorization.getLastRequest(), Authorization.getLastResponse(), null);
		} catch (PayPalRESTException e) {
			ResultPrinter.addResult(req, resp, "Reauthorized a Payment", Authorization.getLastRequest(), null, e.getMessage());
		}
		
		req.getRequestDispatcher("response.jsp").forward(req, resp);
	}

}
