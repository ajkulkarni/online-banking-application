package org.thothlab.devilsvault.db.model;

import java.math.BigDecimal;

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
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getHold() {
		return hold;
	}
	public void setHold(BigDecimal hold) {
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
	private BigDecimal balance;
	private BigDecimal hold;
	private int account_number;
}

