package org.thothlab.devilsvault.db.model;

import java.math.BigInteger;
import java.sql.Date;

public class CreditAccountDB {
	private Integer id;
	private Integer interest;
	private BigInteger credit_card_number;
	private Integer available_balance;
	private Integer last_bill_amount;
	private Date due_date;
	private float apr;
	private Integer account_number;
	private Date cycle_date;
	private Integer current_due_amount;
	private Integer credit_limit;
	private Integer payment;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInterest() {
		return interest;
	}
	public void setInterest(Integer interest) {
		this.interest = interest;
	}
	public BigInteger getCredit_card_number() {
		return credit_card_number;
	}
	public void setCredit_card_number(BigInteger credit_card_number) {
		this.credit_card_number = credit_card_number;
	}
	public Integer getAvailable_balance() {
		return available_balance;
	}
	public void setAvailable_balance(Integer available_balance) {
		this.available_balance = available_balance;
	}
	public Integer getLast_bill_amount() {
		return last_bill_amount;
	}
	public void setLast_bill_amount(Integer last_bill_amount) {
		this.last_bill_amount = last_bill_amount;
	}
	public Date getDue_date() {
		return due_date;
	}
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}
	public float getApr() {
		return apr;
	}
	public void setApr(float apr) {
		this.apr = apr;
	}
	public Integer getAccount_number() {
		return account_number;
	}
	public void setAccount_number(Integer account_number) {
		this.account_number = account_number;
	}
	public Date getCycle_date() {
		return cycle_date;
	}
	public void setCycle_date(Date cycle_date) {
		this.cycle_date = cycle_date;
	}
	public Integer getCurrent_due_amount() {
		return current_due_amount;
	}
	public void setCurrent_due_amount(Integer current_due_amount) {
		this.current_due_amount = current_due_amount;
	}
	public Integer getCredit_limit() {
		return credit_limit;
	}
	public void setCredit_limit(Integer credit_limit) {
		this.credit_limit = credit_limit;
	}
	public Integer getPayment() {
		return payment;
	}
	public void setPayment(Integer payment) {
		this.payment = payment;
	}

}