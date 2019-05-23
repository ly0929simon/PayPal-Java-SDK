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
public class Item extends PayPalModel {

	/**
	 * Stock keeping unit corresponding (SKU) to item.
	 */
	private String sku;

	/**
	 * Item name. 127 characters max.
	 */
	private String name;

	/**
	 * Description of the item. Only supported when the `payment_method` is set to `paypal`.
	 */
	private String description;

	/**
	 * Number of a particular item. 10 characters max.
	 */
	private String quantity;

	/**
	 * Item cost. 10 characters max.
	 */
	private String price;

	/**
	 * 3-letter [currency code](https://developer.paypal.com/docs/integration/direct/rest_api_payment_country_currency_support/).
	 */
	private String currency;

	/**
	 * Tax of the item. Only supported when the `payment_method` is set to `paypal`.
	 */
	private String tax;

	/**
	 * URL linking to item information. Available to payer in transaction history.
	 */
	private String url;

	/**
	 * Category type of the item.
	 */
	private String category;

	/**
	 * Weight of the item.
	 */
	private Measurement weight;

	/**
	 * Length of the item.
	 */
	private Measurement length;

	/**
	 * Height of the item.
	 */
	private Measurement height;

	/**
	 * Width of the item.
	 */
	private Measurement width;

	/**
	 * Set of optional data used for PayPal risk determination.
	 */
	private List<NameValuePair> supplementaryData;

	/**
	 * Set of optional data used for PayPal post-transaction notifications.
	 */
	private List<NameValuePair> postbackData;

	/**
	 * Default Constructor
	 */
	public Item() {
	}

	/**
	 * Parameterized Constructor
	 */
	public Item(String name, String quantity, String price, String currency) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.currency = currency;
	}
}
