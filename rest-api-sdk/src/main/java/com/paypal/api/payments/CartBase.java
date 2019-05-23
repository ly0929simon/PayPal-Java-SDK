package com.paypal.api.payments;

import com.paypal.base.rest.PayPalModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CartBase extends PayPalModel {

	/**
	 * Merchant identifier to the purchase unit. Optional parameter
	 */
	private String referenceId;

	/**
	 * Amount being collected.
	 */
	private Amount amount;

	/**
	 * Recipient of the funds in this transaction.
	 */
	private Payee payee;

	/**
	 * Description of what is being paid for.
	 */
	private String description;

	/**
	 * Note to the recipient of the funds in this transaction.
	 */
	private String noteToPayee;

	/**
	 * free-form field for the use of clients
	 */
	private String custom;

	/**
	 * invoice number to track this payment
	 */
	private String invoiceNumber;

	/**
	 * Soft descriptor used when charging this funding source. If length exceeds max length, the value will be truncated
	 */
	private String softDescriptor;

	/**
	 * Soft descriptor city used when charging this funding source. If length exceeds max length, the value will be truncated. Only supported when the `payment_method` is set to `credit_card`
	 */
	private String softDescriptorCity;

	/**
	 * Payment options requested for this purchase unit
	 */
	private PaymentOptions paymentOptions;

	/**
	 * List of items being paid for.
	 */
	private ItemList itemList;

	/**
	 * URL to send payment notifications
	 */
	private String notifyUrl;

	/**
	 * Url on merchant site pertaining to this payment.
	 */
	private String orderUrl;

	/**
	 * List of external funding being applied to the purchase unit. Each external_funding unit should have a unique reference_id
	 */
	private List<ExternalFunding> externalFunding;

	/**
	 * Default Constructor
	 */
	public CartBase() {
	}

	/**
	 * Parameterized Constructor
	 */
	public CartBase(Amount amount) {
		this.amount = amount;
	}
}
