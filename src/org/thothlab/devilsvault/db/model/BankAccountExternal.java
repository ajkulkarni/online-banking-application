package org.thothlab.devilsvault.db.model;

public class BankAccountExternal {
	public int getExternal_users_id() {
		return external_users_id;
	}
	public void setExternal_users_id(int external_users_id) {
		this.external_users_id = external_users_id;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public float getHold() {
		return hold;
	}
	public void setHold(float hold) {
		this.hold = hold;
	}
	public int getAccount_number() {
		return account_number;
	}
	public void setAccount_number(int account_number) {
		this.account_number = account_number;
	}
	private int external_users_id;
	private String account_type;
	private String card_number;
	private float balance;
	private float hold;
	private int account_number;
}
