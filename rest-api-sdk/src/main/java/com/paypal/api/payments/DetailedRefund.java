package com.paypal.api.payments;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DetailedRefund extends Refund {

	/**
	 * free-form field for the use of clients
	 */
	private String custom;

	/**
	 * Amount refunded to payer of the original transaction, in the current Refund call
	 */
	private Currency refundToPayer;

	/**
	 * List of external funding that were refunded by the Refund call. Each external_funding unit should have a unique reference_id
	 */
	private List<ExternalFunding> refundToExternalFunding;

	/**
	 * Transaction fee refunded to original recipient of payment.
	 */
	private Currency refundFromTransactionFee;

	/**
	 * Amount subtracted from PayPal balance of the original recipient of payment, to make this refund.
	 */
	private Currency refundFromReceivedAmount;

	/**
	 * Total amount refunded so far from the original purchase. Say, for example, a buyer makes $100 purchase, the buyer was refunded $20 a week ago and is refunded $30 in this transaction. The gross refund amount is $30 (in this transaction). The total refunded amount is $50.
	 */
	private Currency totalRefundedAmount;

	/**
	 * Default Constructor
	 */
	public DetailedRefund() {
	}

}
