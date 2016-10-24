package org.thothlab.devilsvault.db.model;

import java.math.BigDecimal;

public class BankAccountDB {
	private int account_number;
	private String account_type;
	private int external_user_id;
	private BigDecimal balance;
	private BigDecimal hold;
	public int getAccount_number() {
		return account_number;
	}
	public void setAccount_number(int account_number) {
		this.account_number = account_number;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public int getExternal_user_id() {
		return external_user_id;
	}
	public void setExternal_user_id(int external_user_id) {
		this.external_user_id = external_user_id;
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
}