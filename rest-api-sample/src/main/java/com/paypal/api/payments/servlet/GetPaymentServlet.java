// #GetPayment Sample
// This sample code demonstrates how you can retrieve
// the details of a payment resource.
// API used: /v1/payments/payment/{payment-id}
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

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.util.ResultPrinter;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;


public class GetPaymentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger
			.getLogger(GetPaymentServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);

	}

	// ##GetPayment
	// Call the method with a valid Payment ID
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

			// Retrieve the payment object by calling the
			// static `get` method
			// on the Payment class by passing a valid
			// AccessToken and Payment ID
			Payment payment = Payment.get(apiContext,
					"PAY-0XL713371A312273YKE2GCNI");
			LOGGER.info("Payment retrieved ID = " + payment.getId()
					+ ", status = " + payment.getState());
			ResultPrinter.addResult(req, resp, "Get Payment", Payment.getLastRequest(), Payment.getLastResponse(), null);
		} catch (PayPalRESTException e) {
			ResultPrinter.addResult(req, resp, "Get Payment", Payment.getLastRequest(), null, e.getMessage());
		}
		req.getRequestDispatcher("response.jsp").forward(req, resp);
	}

}
