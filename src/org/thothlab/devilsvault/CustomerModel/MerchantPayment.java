package org.thothlab.devilsvault.CustomerModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantPayment {
	private Float amount;
	private String card_no;
	private String pin;
	private String date;
	private String year;
	private String merchant_secret;
	private String description;


	public MerchantPayment(Float amount, String card_no, String pin, String date, String year) {
		this.card_no = card_no;
		this.pin = pin;
		this.date = date;
		this.year = year;
		this.amount = amount;
	}

	public MerchantPayment() {
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMerchant_secret() {
		return merchant_secret;
	}

	public String setMerchant_secret() {
		this.merchant_secret = merchant_secret;
	}
}
