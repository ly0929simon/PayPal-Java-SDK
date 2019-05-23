package com.paypal.api.payments;

import java.util.ArrayList;
import java.util.List;

public class WebhooksInputData {

	public static final String[][] availableEvents = {
		{"PAYMENT.CAPTURE.REFUNDED", "A capture payment was refunded"},
        {"PAYMENT.AUTHORIZATION.VOIDED", "A payment authorization was voided"},
        {"PAYMENT.AUTHORIZATION.CREATED", "A payment authorization was created"},
        {"PAYMENT.SALE.COMPLETED", "A sale payment was completed"},
        {"PAYMENT.SALE.REFUNDED", "A sale payment was refunded"},
        {"PAYMENT.SALE.REVERSED", "A sale payment was reversed"},
        {"PAYMENT.CAPTURE.REVERSED", "A capture payment was reversed"}
	};

	public static final String WEBHOOK_URL = "https://github.com/paypal/";
	public static final String WEBHOOK_HATEOAS_URL = "https://api.sandbox.paypal.com/v1/notifications/webhooks/";

	public static final String CLIENT_ID = "AYSq3RDGsmBLJE-otTkBtM-jBRd1TCQwFf9RGfwddNXWz0uFU9ztymylOhRS";
	public static final String CLIENT_SECRET = "EGnHDxD_qRPdaLdZz8iCr8N7_MzF-YHPTkjs6NKYQvQSBngp4PTTVWkPZRbL";
	public static final String EVENT_ID = "WH-SDK-1S115631EN580315E-9KH94552VF7913711";

	public static final String LINK_HREF = "https://api.sandbox.paypal.com/v1/notifications/webhooks-events/" + EVENT_ID;
	public static final String LINK_HREF_RESEND = LINK_HREF + "/resend";
	public static final String LINK_REL_SELF = "self";
	public static final String LINK_REL_RESEND = "resend";
	public static final String LINK_METHOD_GET = "GET";
	public static final String LINK_METHOD_POST = "POST";

	/**
	 * Static method top generate List of Links
	 */
	public static List<Links> createLinksList() {
		List<Links> linksList = new ArrayList<Links>();
		Links link1 = new Links();
		link1.setHref(LINK_HREF);
		link1.setRel(LINK_REL_SELF);
		link1.setMethod(LINK_METHOD_GET);
		Links link2 = new Links();
		link2.setHref(LINK_HREF_RESEND);
		link2.setRel(LINK_REL_RESEND);
		link2.setMethod(LINK_METHOD_POST);
		linksList.add(link1);
		linksList.add(link2);
		return linksList;
	}

}
