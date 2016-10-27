package org.thothlab.devilsvault.db.model;

import java.math.BigInteger;
import java.util.Date;


public class CreditAccount extends BankAccount {
	
	private int Id;
	private int interset;
	private BigInteger credit_card_no;
	
	private double availBalance;
	private double lastBillAmount;
	private Date dueDateTimestamp;
	private float apr;
	
	private Date cycleDate;
	private int currentDueAmount;
	private int creditLimit;
	
	
	public int getInterset() {
		return interset;
	}

	public void setInterset(int interset) {
		this.interset = interset;
	}
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public BigInteger getCredit_card_no() {
		return credit_card_no;
	}

	public void setCredit_card_no(BigInteger credit_card_no) {
		this.credit_card_no = credit_card_no;
	}

	public CreditAccount() {
		this.setAccountType(AccountType.CREDIT);
	}
	
	public double getAvailBalance() {
		return availBalance;
	}
	
	public void setAvailBalance(double availBalance) {
		this.availBalance = availBalance;
	}
	
	public double getLastBillAmount() {
		return lastBillAmount;
	}
	
	public void setLastBillAmount(double lastBillAmount) {
		this.lastBillAmount = lastBillAmount;
	}
	
	public Date getDueDateTimestamp() {
		return dueDateTimestamp;
	}
	
	public void setDueDateTimestamp(Date dueDateTimestamp) {
		this.dueDateTimestamp = dueDateTimestamp;
	}
	
	public float getApr() {
		return apr;
	}
	
	public void setApr(float apr) {
		this.apr = apr;
	}

	public Date getCycleDate() {
		return cycleDate;
	}

	public void setCycleDate(Date cycleDate) {
		this.cycleDate = cycleDate;
	}

	public int getCurrentDueAmount() {
		return currentDueAmount;
	}

	public void setCurrentDueAmount(int currentDueAmount) {
		this.currentDueAmount = currentDueAmount;
	}

	public int getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(int creditLimit) {
		this.creditLimit = creditLimit;
	}
	
	
	

}
