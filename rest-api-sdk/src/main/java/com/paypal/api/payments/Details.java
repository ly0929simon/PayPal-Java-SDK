package com.paypal.api.payments;

import com.paypal.base.rest.PayPalModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Details extends PayPalModel {

	/**
	 * Amount of the subtotal of the items. **Required** if line items are specified. 10 characters max, with support for 2 decimal places.
	 */
	private String subtotal;

	/**
	 * Amount charged for shipping. 10 characters max with support for 2 decimal places.
	 */
	private String shipping;

	/**
	 * Amount charged for tax. 10 characters max with support for 2 decimal places.
	 */
	private String tax;

	/**
	 * Amount being charged for the handling fee. Only supported when the `payment_method` is set to `paypal`.
	 */
	private String handlingFee;

	/**
	 * Amount being discounted for the shipping fee. Only supported when the `payment_method` is set to `paypal`.
	 */
	private String shippingDiscount;

	/**
	 * Amount being charged for the insurance fee. Only supported when the `payment_method` is set to `paypal`.
	 */
	private String insurance;

	/**
	 * Amount being charged as gift wrap fee.
	 */
	private String giftWrap;

	/**
	 * Fee charged by PayPal. In case of a refund, this is the fee amount refunded to the original receipient of the payment.
	 */
	private String fee;

	/**
	 * Default Constructor
	 */
	public Details() {
	}

}
