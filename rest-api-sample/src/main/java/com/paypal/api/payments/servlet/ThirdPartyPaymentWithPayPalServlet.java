// #Create Payment Using PayPal Sample
// This sample code demonstrates how you can process a 
// PayPal Account based Payment.
// API used: /v1/payments/payment
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
import java.util.*;

import static com.paypal.api.payments.util.SampleConstants.*;


public class ThirdPartyPaymentWithPayPalServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger
			.getLogger(ThirdPartyPaymentWithPayPalServlet.class);
	Map<String, String> map = new HashMap<String, String>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	// ##Create
	// Sample showing to create a Payment using PayPal
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		createPayment(req, resp);
		req.getRequestDispatcher("response.jsp").forward(req, resp);
	}

	public Payment createPayment(HttpServletRequest req, HttpServletResponse resp) {
		Payment createdPayment = null;

		// ### Api Context
		// Pass in a `ApiContext` object to authenticate
		// the call and to send a unique request id
		// (that ensures idempotency). The SDK generates
		// a request id if you do not pass one explicitly.
		APIContext apiContext = new APIContext(clientID, clientSecret, mode);
		if (req.getParameter("PayerID") != null) {
			Payment payment = new Payment();
			if (req.getParameter("guid") != null) {
				payment.setId(map.get(req.getParameter("guid")));
			}

			PaymentExecution paymentExecution = new PaymentExecution();
			paymentExecution.setPayerId(req.getParameter("PayerID"));
			try {
				
				createdPayment = payment.execute(apiContext, paymentExecution);
				ResultPrinter.addResult(req, resp, "Executed The Payment", Payment.getLastRequest(), Payment.getLastResponse(), null);
			} catch (PayPalRESTException e) {
				ResultPrinter.addResult(req, resp, "Executed The Payment", Payment.getLastRequest(), null, e.getMessage());
			}
		} else {

			// ###Details
			// Let's you specify details of a payment amount.
			Details details = new Details();
			details.setShipping("1");
			details.setSubtotal("5");
			details.setTax("1");

			// ###Amount
			// Let's you specify a payment amount.
			Amount amount = new Amount();
			amount.setCurrency("USD");
			// Total must be equal to sum of shipping, tax and subtotal.
			amount.setTotal("7");
			amount.setDetails(details);

			// ### Payee
			// Specify a payee with that user's email or merchant id
			// Merchant Id can be found at https://www.paypal.com/businessprofile/settings/
			Payee payee = new Payee();
			payee.setEmail("stevendcoffey-facilitator@gmail.com");

			// ###Transaction
			// A transaction defines the contract of a
			// payment - what is the payment for and who
			// is fulfilling it. Transaction is created with
			// a `Payee` and `Amount` types
			Transaction transaction = new Transaction();
			transaction.setAmount(amount);
			transaction.setPayee(payee);
			transaction
					.setDescription("This is the payment transaction description.");

			// ### Items
			Item item = new Item();
			item.setName("Ground Coffee 40 oz").setQuantity("1").setCurrency("USD").setPrice("5");
			ItemList itemList = new ItemList();
			List<Item> items = new ArrayList<Item>();
			items.add(item);
			itemList.setItems(items);
			
			transaction.setItemList(itemList);
			
			
			// The Payment creation API requires a list of
			// Transaction; add the created `Transaction`
			// to a List
			List<Transaction> transactions = new ArrayList<Transaction>();
			transactions.add(transaction);

			// ###Payer
			// A resource representing a Payer that funds a payment
			// Payment Method
			// as 'paypal'
			Payer payer = new Payer();
			payer.setPaymentMethod("paypal");

			// ###Payment
			// A Payment Resource; create one using
			// the above types and intent as 'sale'
			Payment payment = new Payment();
			payment.setIntent("sale");
			payment.setPayer(payer);
			payment.setTransactions(transactions);

			// ###Redirect URLs
			RedirectUrls redirectUrls = new RedirectUrls();
			String guid = UUID.randomUUID().toString().replaceAll("-", "");
			redirectUrls.setCancelUrl(req.getScheme() + "://"
					+ req.getServerName() + ":" + req.getServerPort()
					+ req.getContextPath() + "/thirdpartypaymentwithpaypal?guid=" + guid);
			redirectUrls.setReturnUrl(req.getScheme() + "://"
					+ req.getServerName() + ":" + req.getServerPort()
					+ req.getContextPath() + "/thirdpartypaymentwithpaypal?guid=" + guid);
			payment.setRedirectUrls(redirectUrls);

			// Create a payment by posting to the APIService
			// using a valid AccessToken
			// The return object contains the status;
			try {
				createdPayment = payment.create(apiContext);
				LOGGER.info("Created payment with id = "
						+ createdPayment.getId() + " and status = "
						+ createdPayment.getState());
				// ###Payment Approval Url
				Iterator<Links> links = createdPayment.getLinks().iterator();
				while (links.hasNext()) {
					Links link = links.next();
					if (link.getRel().equalsIgnoreCase("approval_url")) {
						req.setAttribute("redirectURL", link.getHref());
					}
				}
				ResultPrinter.addResult(req, resp, "Payment with PayPal", Payment.getLastRequest(), Payment.getLastResponse(), null);
				map.put(guid, createdPayment.getId());
			} catch (PayPalRESTException e) {
				ResultPrinter.addResult(req, resp, "Payment with PayPal", Payment.getLastRequest(), null, e.getMessage());
			}
		}
		return createdPayment;
	}
}
