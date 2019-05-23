// #Update Payment
package com.paypal.api.payments.servlet;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Patch;
import com.paypal.api.payments.Payment;
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

public class UpdatePaymentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger
			.getLogger(UpdatePaymentServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		updatePayment(req, resp);
		req.getRequestDispatcher("response.jsp").forward(req, resp);
	}

	public boolean updatePayment(HttpServletRequest req,
			HttpServletResponse resp) {

		// ### Create a Payment
		// We are re-using the PaymentWithPayPalServlet to create a payment
		// for us. This will make sure the samples will work all the time.
		PaymentWithPayPalServlet servlet = new PaymentWithPayPalServlet();
		Payment payment = servlet.createPayment(req, resp);

		// ### Create Patch Request
		List<Patch> patchRequest = new ArrayList<Patch>();

		// ### Amount
		// Let's update the payment amount as an example.
		Amount amount = new Amount();
		amount.setCurrency("USD");
		// Total must be equal to sum of shipping, tax and subtotal.
		amount.setTotal("17.50");
		amount.setDetails(new Details().setShipping("11.50").setTax("1")
				.setSubtotal("5"));

		// ### Patch Object
		// Create a patch object, and fill these 3 properties accordingly.
		Patch patch1 = new Patch();
		patch1.setOp("replace").setPath("/transactions/0/amount").setValue(amount);
		patchRequest.add(patch1);

		try {
			// ### Api Context
			// Pass in a `ApiContext` object to authenticate
			// the call and to send a unique request id
			// (that ensures idempotency). The SDK generates
			// a request id if you do not pass one explicitly.
			APIContext apiContext = new APIContext(clientID, clientSecret, mode);

			// Update a payment by posting to the APIService
			// using a valid apiContext
			payment.update(apiContext, patchRequest);

			LOGGER.info("Updated Payment with " + payment.getId());
			ResultPrinter.addResult(req, resp, "Update Payment",
					Payment.getLastRequest(), "", null);
			return true;
		} catch (PayPalRESTException e) {
			ResultPrinter.addResult(req, resp, "Update Payment",
					Payment.getLastRequest(), null, e.getMessage());
		}
		return false;
	}

}
